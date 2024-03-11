package vnua.fita.bookstore.bean;

public class CartItem {
	private Book selectedBook;
	
	private int quantity;

	public CartItem(Book selectedBook, int quantity) {
		this.selectedBook = selectedBook;
		this.quantity = quantity;
	}

	public Book getSelectedBook() {
		return selectedBook;
	}

	public void setSelectedBook(Book selectedBook) {
		this.selectedBook = selectedBook;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
