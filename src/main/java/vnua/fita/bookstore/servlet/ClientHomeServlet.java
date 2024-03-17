package vnua.fita.bookstore.servlet;

import java.io.IOException;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vnua.fita.bookstore.bean.Book;
import vnua.fita.bookstore.model.BookDAO;
import vnua.fita.bookstore.util.Constant;

@WebServlet("/clientHome")
public class ClientHomeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO=new BookDAO();
	
	@Override
	  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errors = null;
        List<Book> list = null;

        int page = 1;
        int recordsPerPage = 2;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        String keyword = request.getParameter("keyword");

        list = bookDAO.listAllBooks((page - 1) * recordsPerPage, recordsPerPage, keyword);
        int noOfRecords = bookDAO.getNoOfRecords(keyword);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("bookList", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("keyword", keyword);

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/clientHomeView.jsp");
        dispatcher.forward(request, response);
    }
}
