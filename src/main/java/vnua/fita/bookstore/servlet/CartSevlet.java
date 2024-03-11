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
import javax.servlet.http.HttpSession;

import vnua.fita.bookstore.bean.Book;
import vnua.fita.bookstore.bean.Cart;
import vnua.fita.bookstore.bean.CartItem;
import vnua.fita.bookstore.model.BookDAO;
import vnua.fita.bookstore.util.Constant;
import vnua.fita.bookstore.util.MyUtil;

@WebServlet(urlPatterns = {"/cartBook/addToCart","/cartBook/removeFromCart","/cartBook/viewCart"})
public class CartSevlet extends HttpServlet{
	private BookDAO bookDAO=new BookDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		
		List<String> errors=new ArrayList<String>();
		
		String path=req.getServletPath();
		String pathInfo=MyUtil.getPathInfoFromServlet(path);
		
		String bookIdStr=req.getParameter("bookId");
		String quatityPurchasedStr=req.getParameter("quatityPurchased");
		
		System.out.println(quatityPurchasedStr);
		
		int bookId=-1;
		int quatityPurchased=-1;
		
		try {
			if(bookIdStr!=null) {
				bookId=Integer.parseInt(bookIdStr);
			}
		} catch (NumberFormatException e) {
			errors.add(Constant.BOOK_ID_INVALID_VALIDATE_MSG);
		}
		
		try {
			if(quatityPurchasedStr!=null) {
				quatityPurchased=Integer.parseInt(quatityPurchasedStr);
			}
		} catch (NumberFormatException e) {
			errors.add(Constant.BOOK_QUANTITY_IN_STOCK_INVALID_VALIDATE_MSG);
		}
		if(errors.isEmpty()) {
			if(Constant.ADD_TO_CART_ACTION.equals(pathInfo)) {
				Book selectedBook=bookDAO.getBook(bookId);
				Cart cartOfCustomer=MyUtil.getCartOfCustomer(session);
				if(cartOfCustomer==null) {
					cartOfCustomer=new Cart();
				}
				cartOfCustomer.addCartItemToCart(bookId, new CartItem(selectedBook, quatityPurchased));
				MyUtil.storeCart(session, cartOfCustomer);
			}else if(Constant.REMOVE_FROM_CART_ACTION.equals(pathInfo)) {
				Cart cart=MyUtil.getCartOfCustomer(session);
				cart.removeCartItemToCart(bookId);
				MyUtil.storeCart(session, cart);
			}
			RequestDispatcher dispatcher=this.getServletContext().getRequestDispatcher("/views/cartView.jsp");
			dispatcher.forward(req, resp);
		}else {
			resp.sendRedirect(req.getContextPath()+"/clientHome");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
