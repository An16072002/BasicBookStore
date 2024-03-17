package vnua.fita.bookstore.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vnua.fita.bookstore.bean.Book;
import vnua.fita.bookstore.model.BookDAO;
import vnua.fita.bookstore.util.MyUtil;

public class AdminHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;

	public void init() {
		bookDAO = new BookDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String errors = null;
		List<Book> list = null;
		String keyword = request.getParameter("keyword");
		Date today = new Date();
		Date todaySubtract12Month = MyUtil.subtractFromDate(12, today);
		String todaySubtract12MonthStr = MyUtil.convertDateToString(todaySubtract12Month);
		String todayStr = MyUtil.convertDateToString(today);
		if(keyword != null && !keyword.isEmpty()) {
			list = bookDAO.listAllBooks(keyword, todaySubtract12MonthStr, todayStr);
		}else {
			list = bookDAO.listAllBooks(todaySubtract12MonthStr, todayStr);
		}
		if (list.isEmpty()) {
			errors = "Không thể lấy dữ liệu";
		}

		request.setAttribute("errors", errors);
		request.setAttribute("turnover", calSumOfMoney(list));
		request.setAttribute("bookList", list);
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher("/views/adminHomeView.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String errors = null;
		String forwardPage;
		List<Book> list = null;
		
		String fromDateParam = request.getParameter("fromDate");
		String toDateParam = request.getParameter("toDate");
		if(validateDate(fromDateParam, toDateParam)) {
			String fromDate = MyUtil.attachTailToDate(fromDateParam);
			String toDate = MyUtil.attachTailToDate(toDateParam);
			list = bookDAO.listAllBooks(fromDate, toDate);
			if(list.isEmpty()) {
				errors = "Không thể lấy sách";
			}
			
			request.setAttribute("errors", errors);
			request.setAttribute("fromDate", fromDateParam);
			request.setAttribute("toDate", toDateParam);
			request.setAttribute("turnover", calSumOfMoney(list));
			request.setAttribute("bookList", list);
			forwardPage = "/views/adminHomeView.jsp";
		}else {
			forwardPage = "adminHome";
		}
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(forwardPage);
		rd.forward(request, response);
	}

	private boolean validateDate(String fromDate, String toDate) {
		if(fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
			return true;
		}
		return false;
	}
	
	private int calSumOfMoney(List<Book> list) {
		int sum = 0;
		for(Book book: list) {
			sum+=book.getSumOfSoldBook();
		}
		return sum;
	}
}
