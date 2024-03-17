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

import vnua.fita.bookstore.model.BookDAO;
import vnua.fita.bookstore.util.Constant;

public class DeleteBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookDAO bookDAO = new BookDAO();

    public DeleteBookServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		 String bookIdStr =  request.getParameter("bookId");
		 int bookId = -1;
		 try {
			 bookId = Integer.parseInt(bookIdStr);
			 
		 }catch (Exception e) {
			 errors.add(Constant.BOOK_ID_INVALID_VALIDATE_MSG);
			 
		 }
		 if(errors.isEmpty()) {
			 boolean deleteResult = bookDAO.deleteBook(bookId);
			 if(!deleteResult) {
				 errors.add(Constant.DELETE_BOOK_FAIL);
			 
			 }else {
				response.sendRedirect(request.getContextPath()+"/adminHome");
			}
		 }
		 if(!errors.isEmpty()) {
			 request.setAttribute("errors", String.join(", ",errors));
			 RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/views/deleteBookErrorView.jsp");
			 dispatcher.forward(request, response);
		 }

	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
