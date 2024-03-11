package vnua.fita.bookstore.servlet;

import java.io.IOException;
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

@WebServlet(urlPatterns = {"/customerOrderList"})
public class CustomerOrderListServlet extends HttpServlet{
	private static final long serialVersionUID=1L;
	private OrderDAO orderDAO=new OrderDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String customerUsername=MyUtil.getLoginedUser(req.getSession()).getUsername();
		System.out.println(customerUsername);
		List<Order> orderlistOfCustomer = orderDAO.getOrderList(customerUsername);
		req.setAttribute(Constant.ORDER_LIST_OF_CUSTOMER, orderlistOfCustomer);

for (Order order : orderlistOfCustomer) {
    System.out.println(order.getOrderNo()); // In thông tin về đơn hàng hoặc xử lý thông tin theo yêu cầu của bạn
}
		RequestDispatcher dispatcher //
			=
		this.getServletContext().getRequestDispatcher("/views/customerOrderListView.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	public static void main(String[] args) {
		OrderDAO hehe = new OrderDAO();
		hehe.getOrderList("nunu");
		
	}
}
