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

import org.apache.catalina.Contained;

import vnua.fita.bookstore.bean.Book;
import vnua.fita.bookstore.bean.Order;
import vnua.fita.bookstore.model.OrderDAO;
import vnua.fita.bookstore.util.Constant;
import vnua.fita.bookstore.util.MyUtil;

@WebServlet(urlPatterns = {"/adminOrderList/waiting","/adminOrderList/delivering","/adminOrderList/delivered","/adminOrderList/reject"})
public class AdminOrderListServlet extends HttpServlet {
	private static final long serialVersionUID=1L;
	private OrderDAO orderDAO=new OrderDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		String pathInfo = MyUtil.getPathInfoFromServletPath(servletPath);
		
		List<Order> orderListOfCustomer = new ArrayList<Order>();
		
		if(Constant.WAITING_APPROVE_ACTION.equals(pathInfo)) {
			orderListOfCustomer = orderDAO.getOrderList(Constant.WAITING_CONFIRM_ORDER_STATUS);
			request.setAttribute("listType","CHỜ XÁC NHẬN");
		}else if(Constant.DELIVERING_ACTION.equals(pathInfo)) {
			orderListOfCustomer = orderDAO.getOrderList(Constant.DELEVERING_ORDER_STATUS);
					request.setAttribute("listType","ĐANG CHỜ GIAO");
		}else if(Constant.DELIVERED_ACTION.equals(pathInfo)) {
			orderListOfCustomer = orderDAO.getOrderList(Constant.DELIVERED_ORDER_STATUS);
					request.setAttribute("listType","ĐÃ GIAO HÀNG");
		}else if(Constant.REJECT_ACTION.equals(pathInfo)) {
			orderListOfCustomer = orderDAO.getOrderList(Constant.REJECT_ORDER_STATUS);
					request.setAttribute("listType","KHÁCH TRẢ HÀNG");
		}
		
		request.setAttribute(Constant.ORDER_LIST_OF_CUSTOMER, orderListOfCustomer);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/adminOrderListView.jsp");
		dispatcher.forward(request, response);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();

		String orderIdStr = request.getParameter("orderId");
		String confirmTypeStr = request.getParameter("confirmType");
		int orderId = -1;
		
		try {
			orderId = Integer.parseInt(orderIdStr);
			
		}catch(NumberFormatException e) {
			errors.add(Constant.ORDER_ID_INVALID_VALIDATE_MSG);
		}
		
		Byte confirmType = -1;
		
		try {
			confirmType = Byte.parseByte(confirmTypeStr);
			
		}catch(NumberFormatException e) {
			errors.add(Constant.VALUE_INVALID_VALIDATE_MSG);
		}
		
		if(errors.isEmpty()) {
			boolean updateResult = false ;
			
			if(Constant.DELIVERING_ORDER_STATUS == confirmType) {
				updateResult = orderDAO.updateOrderNo(orderId,confirmType);
			}else if(Constant.DELIVERED_ORDER_STATUS == confirmType){
				updateResult = orderDAO.updateOrder(orderId,confirmType);
				
			}else if(Constant.REJECT_ORDER_STATUS == confirmType) {
				
				updateResult = orderDAO.updateOrder(orderId,confirmType);
			}
			
			if(updateResult) {
				request.setAttribute("message",Constant.UPDATE_ORDER_SUCCESS);
			}else {
				errors.add(Constant.UPDATE_ORDER_FAIL);
			}
		}
		if(!errors.isEmpty()) {
			request.setAttribute("errors", String.join(",","errors"));
		}
		doGet(request, respone);
	}
	
	
}
