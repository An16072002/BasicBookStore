package vnua.fita.bookstore.formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

public class BookForm {
	private String bookId;
	private String title;
	private String author;
	private String price;
	private String quantityInStock;
	private String detail; // Add this line
    private Part filePath;
	
	public BookForm(String bookId, String title, String author, String price,
			String quantityInStock) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.price = price;
		this.quantityInStock = quantityInStock;
	}


	public BookForm(String title, String author, String price, String quantityInStock) {
		super();
		this.title = title;
		this.author = author;
		this.price = price;
		this.quantityInStock = quantityInStock;
	}
	
	public BookForm(String title, String author, String price, String quantityInStock,
			String detail, Part filePath) {
		super();
		this.title = title;
		this.author = author;
		this.price = price;
		this.quantityInStock = quantityInStock;
		this.detail = detail;
		this.filePath = filePath;
	}


	

	 public BookForm(String bookId, String title, String author, String priceStr,
             String quantityInStockStr, String detail) {
		 this.bookId = bookId;
		 this.title = title;
		 this.author = author;
		 this.price = priceStr;
		 this.quantityInStock = quantityInStockStr;
		 this.detail = detail;
}

	public String getBookId() {
		return bookId;
	}



	public void setBookId(String bookId) {
		this.bookId = bookId;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	public String getQuantityInStock() {
		return quantityInStock;
	}


	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public Part getFilePath() {
		return filePath;
	}
	public void setQuantityInStock(String quantityInStock) {
		this.quantityInStock = quantityInStock;
	}



	public List<String> validateEditBookForm(){
		List<String> errors = new ArrayList<String>();
		if(title == null || title.trim().isEmpty()) {
			errors.add("Tên sách không được để trống");
		}
		if(author == null || author.trim().isEmpty()) {
			errors.add("Tên tác giả không được để trống");
		}
		
		if(bookId == null || bookId.trim().isEmpty()) {
			errors.add("Giá sách không được để trống");
		}else {
			try {
				Integer.parseInt(bookId);
			} catch (NumberFormatException e) {
				errors.add("Id không hợp lệ");
			}
		}
		
		if(price == null || price.trim().isEmpty()) {
			errors.add("Giá sách không được để trống");
		}else {
			try {
				Integer.parseInt(price);
			} catch (NumberFormatException e) {
				errors.add("Giá không hợp lệ");
			}
		}
		if(quantityInStock == null || quantityInStock.trim().isEmpty()) {
			errors.add("Nhập số lượng có trong kho");
		}else {
			try {
				Integer.parseInt(quantityInStock);
			} catch (NumberFormatException e) {
				errors.add("Giá số lượng không hợp lệ");
			}
		}
		return errors;
	}
	
	public List<String> validateCreateBookForm(){
		List<String> errors = new ArrayList<String>();
		if(title == null || title.trim().isEmpty()) {
			errors.add("Tên sách không được để trống");
		}
		if(author == null || author.trim().isEmpty()) {
			errors.add("Tên tác giả không được để trống");
		}
		
		if(price == null || price.trim().isEmpty()) {
			errors.add("Giá sách không được để trống");
		}else {
			try {
				Integer.parseInt(price);
			} catch (NumberFormatException e) {
				errors.add("Giá không hợp lệ");
			}
		}
		if(quantityInStock == null || quantityInStock.trim().isEmpty()) {
			errors.add("Nhập số lượng có trong kho");
		}else {
			try {
				Integer.parseInt(quantityInStock);
			} catch (NumberFormatException e) {
				errors.add("Giá số lượng không hợp lệ");
			}
		}
		return errors;
	}
}
