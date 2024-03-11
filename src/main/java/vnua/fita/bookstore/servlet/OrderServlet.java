package vnua.fita.bookstore.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import vnua.fita.bookstore.bean.Cart;
import vnua.fita.bookstore.bean.CartItem;
import vnua.fita.bookstore.bean.Order;
import vnua.fita.bookstore.bean.User;
import vnua.fita.bookstore.model.BookDAO;
import vnua.fita.bookstore.model.OrderDAO;
import vnua.fita.bookstore.util.Constant;
import vnua.fita.bookstore.util.MyUtil;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/order")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024
		* 10, maxRequestSize = 1024 * 1024 * 20)
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		orderDAO = new OrderDAO();
	}

	public OrderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		String deliveryAddress = request.getParameter("deliveryAddress");
		String paymentMode = request.getParameter("paymentMode");
		Part filePart = request.getPart("file");
		HttpSession session = request.getSession();

		// validate và ghi nhận lỗi
		validateOrderForm(deliveryAddress, paymentMode, filePart, errors);

		// nếu đầu vào không hợp lệ
		if (!errors.isEmpty()) {
			request.setAttribute("errors", String.join(",", errors));
			RequestDispatcher rd = this.getServletContext()
					.getRequestDispatcher("/views/cartView.jsp");
			rd.forward(request, response);
			return;
		}

		// Nếu đầu vào hợp lệ
		Order order = createOrder(deliveryAddress, paymentMode, filePart, session);
		String forwardPage;
		/*
		 * kiểm tra và cập nhật số lượng còn lại của mặt hàng trong kho vào đơn hàng nếu
		 * số lượng hiện có trong kho nhỏ hơn số lượng đặt mua
		 */
		if (orderDAO.checkAndUpdateAvaiableBookOfOrder(order)) {
			boolean insertResult = orderDAO.insertOrder(order);
			if (insertResult) {// hoàn tất việc tạo đơn hàng
				request.setAttribute("cartOfCustomer", MyUtil.getCartOfCustomer(session));
				request.setAttribute("orderOfCustomer", order);
				MyUtil.deleteCart(session); // xóa giỏ hàng khỏi session
				forwardPage = "/views/detailOrderView.jsp";
			} else {
				// nếu ghi dữ liệu vào db gặp lỗi
				request.setAttribute("errors", "Lỗi thêm đơn hàng");
				forwardPage = "/views/cartView.jsp";
			}

		} else {
			// Nếu mặt hàng nào đó không còn đủ hàng
			request.setAttribute("errors", "Không còn đủ hàng");
			// cập nhật lại giỏ hành trong Session với số lượng sẵn có mới của mặt
			// hàng không còn đủ hàng
			MyUtil.updateCartOfCustomer(session,
					convertListToMap(order.getOrderBookList()));
			forwardPage = "/views/cartView.jsp";
		}
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(forwardPage);
		rd.forward(request, response);
	}

	private Map<Integer, CartItem> convertListToMap(List<CartItem> orderBookList) {
		Map<Integer, CartItem> cartItemList = new HashMap<Integer, CartItem>();
		for (CartItem cartItem : orderBookList) {
			cartItemList.put(cartItem.getSelectedBook().getBookId(), cartItem);
		}
		return cartItemList;
	}

	// Phương thức kiểm tra tính hợp lệ của dữ liệu thanh toán
	private void validateOrderForm(String deliveryAddress, String paymentMode,
			Part filePart, List<String> errors) {
		if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
			errors.add("Địa chỉ nhận hàng trống");
		}
		// kích thước file phải >0 nếu thanh toán chuyển khoản
		if ("transfer".equals(paymentMode)) {
			if (filePart == null || filePart.getSize() <= 0) {
				errors.add("Ảnh xác nhận chuyển khoản trống");
			}
		}
	}

	// Phương thức tạo đơn hàng
	private Order createOrder(String deliveryAddress, String paymentMode, Part filePart,
			HttpSession session) throws IOException {
		Order order = new Order();
		Date now = new Date();

		Cart cartOfCustomer = MyUtil.getCartOfCustomer(session);
		String customerUsername = MyUtil.getLoginedUser(session).getUsername();
		User customer = new User();
		customer.setUsername(customerUsername);

		order.setCustomer(customer);
		order.setDeliveryAddress(deliveryAddress);
		order.setPaymentMode(paymentMode);
		order.setOrderDate(now); // ngày lập hóa đơn hiện tại
		order.setStatusDate(now); // ngày ứng với trạng thái hóa đơn hiện tại
		order.setTotalCost(cartOfCustomer.getTotalCost());
		order.setOrderBookList(
				new ArrayList<CartItem>(cartOfCustomer.getCartItemList().values()));
		if ("cash".equals(paymentMode)) {
			order.setOrderStatus(Constant.DELEVERING_ORDER_STATUS); // đã xác nhận và đang
																	// chuyển hàng
			order.setOrderApproveDate(now); // ngày xác minh
			order.setPaymentStatus(false); // chưa thanh toán
		} else if ("transfer".equals(paymentMode)) {
			// Lưu ảnh thanh toán vào thư mục nếu có
			String fileName = customerUsername + "_" + MyUtil.getTimeLabel()
					+ MyUtil.extracFileExtension(filePart);
			String appPath = getServletContext().getRealPath(""); // Thư mục gốc của ứng
																	// dụng Web
			filePart.write(MyUtil.getFolderUpload(appPath, "transfer-img-upload")
					.getAbsolutePath() + File.separator + fileName);
			order.setOrderStatus(Constant.WAITING_CONFIRM_ORDER_STATUS); //chờ xác nhận chuyển khoản
			order.setPaymentStatus(false); //chưa thanh toán
			order.setPaymentImagePath("transfer-img-upload"+File.separator+fileName);
		}
		return order;
	}
}
