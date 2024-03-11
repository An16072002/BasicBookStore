package vnua.fita.bookstore.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vnua.fita.bookstore.model.BookDAO;

/**
 * Servlet implementation class DeleteBookServlet
 */

public class DeleteBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO=new BookDAO();

	public DeleteBookServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		int id = Integer.parseInt(bookId);
		boolean check = bookDAO.deleteBook(id);
		response.sendRedirect(request.getContextPath() + "/adminHome");
	}

}
