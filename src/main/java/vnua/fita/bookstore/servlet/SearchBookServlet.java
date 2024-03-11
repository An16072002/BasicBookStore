package vnua.fita.bookstore.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vnua.fita.bookstore.bean.Book;
import vnua.fita.bookstore.model.BookDAO;

public class SearchBookServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO = new BookDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = (String) req.getSession().getAttribute("name");
		String keyword = new String(req.getParameter("keyword").getBytes("ISO-8859-1"), "UTF-8");
		
		if (name != null && !name.isEmpty()) {
			List<Book> listBook = bookDAO.searchBooks(keyword);
			req.setAttribute("searchListBook", listBook);
			RequestDispatcher dispatcher = req.getServletContext()
					.getRequestDispatcher("/views/searchResultArea.jsp");
			dispatcher.forward(req, resp);
		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/loginView.jsp");

			dispatcher.forward(req, resp);
		}
	}
}
