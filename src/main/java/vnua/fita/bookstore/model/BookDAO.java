package vnua.fita.bookstore.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import vnua.fita.bookstore.model.DBConnection;
import vnua.fita.bookstore.util.Constant;
import vnua.fita.bookstore.util.MyUtil;

import vnua.fita.bookstore.bean.Book;
import vnua.fita.bookstore.bean.Order;

public class BookDAO {
	public List<Book> listAllBook() {
		Connection connection = DBConnection.createConnection();
		String sql = "SELECT * FROM tblbook";
		List<Book> list = new ArrayList<Book>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Book book = new Book(resultSet.getInt("book_id"), resultSet.getString("title"),
						resultSet.getString("author"), resultSet.getInt("price"), resultSet.getInt("quantity_in_stock"),
						resultSet.getString("detail"), resultSet.getString("image_path"));
				book.setImagePath(resultSet.getString("image_path"));
				book.setDetail(resultSet.getString("detail"));
				list.add(book);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}
		return list;
	}
	
	public List<Book> listAllBook(String keyword) {
		Connection connection = DBConnection.createConnection();
		String sql = "SELECT * FROM tblbook where title like ?";
		List<Book> list = new ArrayList<Book>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, keyword);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Book book = new Book(resultSet.getInt("book_id"), resultSet.getString("title"),
						resultSet.getString("author"), resultSet.getInt("price"), resultSet.getInt("quantity_in_stock"),
						resultSet.getString("detail"), resultSet.getString("image_path"));
				book.setImagePath(resultSet.getString("image_path"));
				book.setDetail(resultSet.getString("detail"));
				list.add(book);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}
		return list;
	}

	
	public List<Book> listAllBooks(String fromDate, String toDate) {
	    List<Book> listBooks = new ArrayList<>();
	    String sql = "SELECT b.*, sum(obor.quantity) AS sum_quantity, sum(obor.price) AS sum_price FROM tblbook b "
	            + "LEFT JOIN "
	            + "(SELECT ob.* from tblorder_book ob INNER JOIN tblorder o ON ob.order_id = o.order_id "
	            + "WHERE o.order_status = ? AND (o.status_date BETWEEN ? AND ?)) obor "
	            + "ON b.book_id = obor.book_id "
	            + "GROUP BY b.book_id "
	            + "ORDER BY sum_quantity DESC, b.create_date DESC";

	    try (Connection jdbcConnection = DBConnection.createConnection();
	         PreparedStatement preStatement = jdbcConnection.prepareStatement(sql)) {

	        preStatement.setInt(1, Constant.DELEVERED_ORDER_STATUS);
	        preStatement.setString(2, fromDate);
	        preStatement.setString(3, toDate);

	        try (ResultSet resultSet = preStatement.executeQuery()) {
	            while (resultSet.next()) {
	                // Xử lý kết quả và tạo các đối tượng Book
	                int id = resultSet.getInt("book_id");
	                String title = resultSet.getString("title");
	                String author = resultSet.getString("author");
	                int price = resultSet.getInt("price");
	                int quantityInStock = resultSet.getInt("quantity_in_stock");
	                String detail = resultSet.getString("detail");
	                String imagePath = resultSet.getString("image_path");
	                Date createDate = resultSet.getTimestamp("create_date");
	                int soldQuantity = resultSet.getInt("sum_quantity");
	                int sumOfSoldBook = resultSet.getInt("sum_price");

	                Book book = new Book(id, title, author, price, quantityInStock);
	                book.setDetail(detail);
	                book.setImagePath(imagePath);
	                book.setCreateDate(createDate);
	                book.setSoldQuantity(soldQuantity);
	                book.setSumOfSoldBook(sumOfSoldBook);

	                listBooks.add(book);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return listBooks;
	}

	public List<Book> listAllBooks(String keyword, String fromDate, String toDate) {
	    List<Book> searchBookList = new ArrayList<>();
	    String sql = "SELECT b.*, sum(obor.quantity) AS sum_quantity, sum(obor.price) AS sum_price FROM tblbook b "
	            + "LEFT JOIN "
	            + "(SELECT ob.* from tblorder_book ob INNER JOIN tblorder o ON ob.order_id = o.order_id "
	            + "WHERE o.order_status = ? AND (o.status_date BETWEEN ? AND ?)) obor "
	            + "ON b.book_id = obor.book_id "
	            + "WHERE title LIKE ? "
	            + "GROUP BY b.book_id "
	            + "ORDER BY sum_quantity DESC, b.create_date DESC";

	    try (Connection jdbcConnection = DBConnection.createConnection();
	         PreparedStatement preStatement = jdbcConnection.prepareStatement(sql)) {

	        preStatement.setInt(1, Constant.DELEVERED_ORDER_STATUS);
	        preStatement.setString(2, fromDate);
	        preStatement.setString(3, toDate);
	        preStatement.setString(4, "%" + keyword + "%");

	        try (ResultSet resultSet = preStatement.executeQuery()) {
	            while (resultSet.next()) {
	                // Xử lý kết quả và tạo các đối tượng Book
	                int id = resultSet.getInt("book_id");
	                String title = resultSet.getString("title");
	                String author = resultSet.getString("author");
	                int price = resultSet.getInt("price");
	                int quantityInStock = resultSet.getInt("quantity_in_stock");
	                String detail = resultSet.getString("detail");
	                String imagePath = resultSet.getString("image_path");
	                Date createDate = resultSet.getTimestamp("create_date");
	                int soldQuantity = resultSet.getInt("sum_quantity");
	                int sumOfSoldBook = resultSet.getInt("sum_price");

	                Book book = new Book(id, title, author, price, quantityInStock);
	                book.setDetail(detail);
	                book.setImagePath(imagePath);
	                book.setCreateDate(createDate);
	                book.setSoldQuantity(soldQuantity);
	                book.setSumOfSoldBook(sumOfSoldBook);

	                searchBookList.add(book);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return searchBookList;
	}

	
	public Book getBook(int bookId) {
		Connection connection = DBConnection.createConnection();
		String sql = "SELECT * FROM tblbook WHERE book_id=?";
		Book book = new Book();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, bookId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				book = new Book(resultSet.getInt("book_id"), resultSet.getString("title"),
						resultSet.getString("author"), resultSet.getInt("price"), resultSet.getInt("quantity_in_stock"),
						resultSet.getString("detail"), resultSet.getString("image_path"));
				book.setImagePath(resultSet.getString("image_path"));
				book.setDetail(resultSet.getString("detail"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}
		return book;
	}

	public boolean deleteBook(int bookId) {
		boolean isDelete = false;
		Connection connection = DBConnection.createConnection();
		String sql = "DELETE FROM tblbook t WHERE t.book_id=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, bookId);
			int count = preparedStatement.executeUpdate();

			isDelete = count > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}
		return isDelete;
	}

	public boolean updateBook(Book book) {
        boolean updated = false;
        String sql = "UPDATE tblbook SET title=?, author=?, price=?, quantity_in_stock=?, detail=?, image_path=? WHERE book_id=?";
        try (Connection conn = DBConnection.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPrice());
            ps.setInt(4, book.getQuantityInStock());
            ps.setString(5, book.getDetail());
            ps.setString(6, book.getImagePath());
            ps.setInt(7, book.getBookId());
            int rowsUpdated = ps.executeUpdate();
            updated = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

	public boolean createBook(Book book) {
		boolean isSuccess = false;
		Connection connection = DBConnection.createConnection();
		String sql = "INSERT INTO tblbook (title , author , price , quantity_in_stock , detail,image_path)\r\n"
				+ "VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, book.getTitle());
			preparedStatement.setString(2, book.getAuthor());
			preparedStatement.setInt(3, book.getPrice());
			preparedStatement.setInt(4, book.getQuantityInStock());
			preparedStatement.setString(5, book.getDetail());
			preparedStatement.setString(6, book.getImagePath());
			int count = preparedStatement.executeUpdate();

			isSuccess = count > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}
		return isSuccess;
	}

	public List<Book> searchBooks(String keyword) {

		List<Book> listBook = new ArrayList<>();
		String sql = "SELECT * FROM tblbook WHERE title LIKE ?";
		Connection connection = DBConnection.createConnection();

		try {
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, "%" + keyword + "%"); // Them % de tim gan dung
			ResultSet resultSet = pst.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("book_id");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				int price = resultSet.getInt("price");
				int quantityInStock = resultSet.getInt("price");
				Book book = new Book(id, title, author, price, quantityInStock, resultSet.getString("detail"),
						resultSet.getString("image_path"));
				listBook.add(book);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}
		
		return listBook;
	}
	
	public boolean insertBook(Book book) {
		boolean insertResult = false;
		String sql = "INSERT INTO tblbook(title, author, price, quantity_in_stock, detail, image_path, create_date) VALUE (?,?,?,?,?,?,?)";
		Connection jdbcConnection = DBConnection.createConnection();
		PreparedStatement preStatement = null;
		try {
			preStatement = jdbcConnection.prepareStatement(sql);
			preStatement.setString(1, book.getTitle());
			preStatement.setString(2, book.getAuthor());
			preStatement.setInt(3, book.getPrice());
			preStatement.setInt(4, book.getQuantityInStock());
			preStatement.setString(5, book.getDetail());
			preStatement.setString(6, book.getImagePath());
			preStatement.setString(7, MyUtil.convertDateToString(book.getCreateDate()));
			insertResult = preStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closePreparedStatement(preStatement);
			DBConnection.closeConnection(jdbcConnection);
		}
		return insertResult;
	}
	
	public int getNoOfRecords(String keyword) {
        String sql = "SELECT count(book_id) FROM tblbook";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " WHERE title LIKE ?";
        }

        Connection jdbcConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            jdbcConnection = DBConnection.createConnection();
            preparedStatement = jdbcConnection.prepareStatement(sql);

            if (keyword != null && !keyword.isEmpty()) {
                preparedStatement.setString(1, "%" + keyword + "%");
            }

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closePreparedStatement(preparedStatement);
            DBConnection.closeConnection(jdbcConnection);
        }
        return count;
    }

    public List<Book> listAllBooks(int offset, int noOfRecords, String keyword) {
        List<Book> listBook = new ArrayList<>();
        String sql = "SELECT * FROM tblbook";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " WHERE title LIKE ?";
        }

        sql += " ORDER BY create_date DESC"; // Đưa ORDER BY xuống đây
        sql += " LIMIT ?,?";

        Connection jdbcConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            jdbcConnection = DBConnection.createConnection();
            preparedStatement = jdbcConnection.prepareStatement(sql);

            int index = 0;
            if (keyword != null && !keyword.isEmpty()) {
                preparedStatement.setString(++index, "%" + keyword + "%");
            }
            preparedStatement.setInt(++index, offset);
            preparedStatement.setInt(++index, noOfRecords);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int price = resultSet.getInt("price");
                int quantityInStock = resultSet.getInt("quantity_in_stock");

                String detail = resultSet.getString("detail");
                String imagePath = resultSet.getString("image_path");

                Book book = new Book(id, title, author, price, quantityInStock);
                book.setDetail(detail);
                book.setImagePath(imagePath);
                listBook.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeResultSet(resultSet);
            DBConnection.closePreparedStatement(preparedStatement);
            DBConnection.closeConnection(jdbcConnection);
        }
        return listBook;
    }}
