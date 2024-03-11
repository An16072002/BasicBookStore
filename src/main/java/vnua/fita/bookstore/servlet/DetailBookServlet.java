package vnua.fita.bookstore.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vnua.fita.bookstore.bean.Book;
import vnua.fita.bookstore.model.BookDAO;
import vnua.fita.bookstore.util.Constant;

public class DetailBookServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO=new BookDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();

		String bookIdStr = request.getParameter("bookId");
		int bookId = -1;
		try {
			bookId = Integer.parseInt(bookIdStr);
		} catch (Exception e) {
			errors.add(Constant.BOOK_ID_INVALID_VALIDATE_MSG);
		}

		if (errors.isEmpty()) {
			Book book = bookDAO.getBook(bookId);
			if (book == null) {
				errors.add(Constant.GET_BOOK_FAIL);
			} else {
				request.setAttribute("book", book);
				RequestDispatcher rd = request.getServletContext()
						.getRequestDispatcher("/views/detailBookView.jsp");
				rd.forward(request, response);
			}
		}
		if (!errors.isEmpty()) {
			request.setAttribute("errors", String.join(", ", errors));
			RequestDispatcher rd = request.getServletContext()
					.getRequestDispatcher("/adminHome");
			rd.forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
