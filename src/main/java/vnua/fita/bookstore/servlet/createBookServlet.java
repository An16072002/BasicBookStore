package vnua.fita.bookstore.servlet;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import vnua.fita.bookstore.bean.Book;
import vnua.fita.bookstore.formbean.BookForm;
import vnua.fita.bookstore.model.BookDAO;
import vnua.fita.bookstore.util.MyUtil;

/**
 * Servlet implementation class createBookServlet
 */

@WebServlet("/createBook")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 20 // 20MB
)
public class createBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public createBookServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		bookDAO = new BookDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getServletContext()
				.getRequestDispatcher("/views/createBookView.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String priceStr = request.getParameter("price");
		String quantityInStockStr = request.getParameter("quantityInStock");
		String detail = request.getParameter("detail");
		Part filePart = request.getPart("file");
		String imagePath;

		BookForm bookForm = new BookForm(title, author, priceStr, quantityInStockStr,
				detail, filePart);
		List<String> errors = bookForm.validateCreateBookForm();
		if (errors.isEmpty()) {
			int price = Integer.parseInt(priceStr);
			int quantityInStock = Integer.parseInt(quantityInStockStr);

			// lưu ảnh thanh toán vào thư mục nếu có
			// lưu ảnh vào thư mục 'img' nếu có
						String fileName = title + "_" + MyUtil.getTimeLabel() + MyUtil.extracFileExtension(filePart);
						String contextPath = getServletContext().getRealPath("/"); // Lấy đường dẫn thực của ứng dụng web
						String savePath = contextPath + "img"; // Đường dẫn đến thư mục 'img'

						File fileSaveDir = new File(savePath);
						if (!fileSaveDir.exists()) {
						    fileSaveDir.mkdir(); // Tạo thư mục 'img' nếu nó không tồn tại
						}

						String filePath = savePath + File.separator + fileName; // Đường dẫn file cuối cùng để lưu trữ ảnh
						filePart.write(filePath); // Lưu file ảnh
						imagePath = "img" + File.separator + fileName; // Đường dẫn tương đối để lưu trong cơ sở dữ liệu
			
			
			Book book = new Book(title, author, price, quantityInStock, detail, imagePath);
			book.setCreateDate(new Date());

			boolean insertResult = bookDAO.insertBook(book);
			if (!insertResult) {
				errors.add("Thêm sách không thành công");
			} else {
				response.sendRedirect(request.getContextPath() + "/adminHome");
			}
		}

		if (!errors.isEmpty()) {
			request.setAttribute("errors", String.join(", ", errors));
			request.setAttribute("book", bookForm);
			RequestDispatcher rd = request.getServletContext()
					.getRequestDispatcher("/views/createBookView.jsp");
			rd.forward(request, response);
		}
	}

}
