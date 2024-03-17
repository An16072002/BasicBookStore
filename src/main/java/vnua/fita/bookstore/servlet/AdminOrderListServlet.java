package vnua.fita.bookstore.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vnua.fita.bookstore.bean.Order;
import vnua.fita.bookstore.model.OrderDAO;
import vnua.fita.bookstore.util.Constant;
import vnua.fita.bookstore.util.MyUtil;

@WebServlet(urlPatterns = { "/adminOrderList/waiting", "/adminOrderList/delivering", "/adminOrderList/delivered",
		"/adminOrderList/reject" })
public class AdminOrderListServlet extends HttpServlet {
	private OrderDAO orderDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		orderDAO = new OrderDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath=req.getServletPath();
		String pathInfo=MyUtil.getPathInfoFromServletPath(servletPath);
		List<Order> orders=new ArrayList<Order>();
		if(Constant.WAITING_APPROVE_ACTION.equals(pathInfo)) {
			orders=orderDAO.getOrderList(Constant.WAITING_CONFIRM_ORDER_STATUS);
			req.setAttribute("listType", "CHỜ XÁC NHẬN");
		}else if(Constant.DELEVERING_ACTION.equals(pathInfo)) {
			orders=orderDAO.getOrderList(Constant.DELEVERING_ORDER_STATUS);
			req.setAttribute("listType", "ĐANG CHỜ GIAO");
		}else if(Constant.DELEVERED_ACTION.equals(pathInfo)) {
			orders=orderDAO.getOrderList(Constant.DELEVERED_ORDER_STATUS);
			req.setAttribute("listType", "ĐÃ GIAO");
		}else if(Constant.REJECT_ACTION.equals(pathInfo)) {
			orders=orderDAO.getOrderList(Constant.REJECT_ORDER_STATUS);
			req.setAttribute("listType", "KHÁCH TRẢ LẠI");
		}
		
		req.setAttribute(Constant.ORDER_LIST_OF_CUSTOMER, orders);
		RequestDispatcher dispatcher=this.getServletContext().getRequestDispatcher("/views/adminOrderListView.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> errors=new ArrayList<String>();
		String orderIdStr=req.getParameter("orderId");
		String confirmTypeStr=req.getParameter("confirmType");
		int orderId=-1;
		try {
			orderId=Integer.parseInt(orderIdStr);
		} catch (Exception e) {
			// TODO: handle exception
			errors.add(Constant.ORDER_ID_INVALID_VALIDATE_MSG);
		}
		byte confirmType=-1;
		try {
			confirmType=Byte.parseByte(confirmTypeStr);
		} catch (Exception e) {
			// TODO: handle exception
			errors.add(Constant.VALUE_INVALID_VALIDATE_MSG);
		}
		if(errors.isEmpty()) {
			boolean updateResult=false;
			if(Constant.DELEVERING_ORDER_STATUS==confirmType) {
				updateResult=orderDAO.updateOrderNo(orderId,confirmType);
			}else if(Constant.DELEVERED_ORDER_STATUS==confirmType) {
				updateResult=orderDAO.updateOrder(orderId,confirmType);
			}else if(Constant.REJECT_ORDER_STATUS==confirmType) {
				updateResult=orderDAO.updateOrder(orderId,confirmType);
			}
			if(updateResult) {
				req.setAttribute("message", Constant.UPDATE_ORDER_SUCCESS);
			}else {
				errors.add(Constant.UPDATE_ORDER_FAIL);
			}
			if(!errors.isEmpty()) {
				req.setAttribute("errors", String.join(", ", errors));
			}
			doGet(req, resp);
		}
	}
}
