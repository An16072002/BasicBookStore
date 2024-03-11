package vnua.fita.bookstore.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.sql.Statement;
import java.util.Date;




import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import vnua.fita.bookstore.bean.Book;
import vnua.fita.bookstore.bean.CartItem;
import vnua.fita.bookstore.bean.Order;
import vnua.fita.bookstore.bean.User;
import vnua.fita.bookstore.util.Constant;
import vnua.fita.bookstore.util.MyUtil;

public class OrderDAO {
    private DBConnection dbConnection;

    public OrderDAO() {
        dbConnection = new DBConnection();
    }

    public boolean checkAndUpdateAvaiableBookOfOrder(Order order) {
        boolean checkAvailable = true;
        String sql;
        List<CartItem> orderBookList = order.getOrderBookList();
        Connection jdbcConnection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        try {
            jdbcConnection = dbConnection.createConnection();
            for (CartItem cartItem : orderBookList) {
                sql = "SELECT quantity_in_stock FROM tblbook WHERE book_id=?";
                preStatement = jdbcConnection.prepareStatement(sql);
                preStatement.setInt(1, cartItem.getSelectedBook().getBookId());
                resultSet = preStatement.executeQuery();
                if (resultSet.next()) {
                    int presentQuantityInStock = resultSet.getInt("quantity_in_stock");
                    if (presentQuantityInStock < cartItem.getQuantity()) {
                        checkAvailable = false;
                        cartItem.getSelectedBook().setQuantityInStock(presentQuantityInStock);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeResultSet(resultSet);
            dbConnection.closePreparedStatement(preStatement);
            dbConnection.closeConnection(jdbcConnection);
        }
        return checkAvailable;
    }

    public boolean insertOrder(Order order) {
        boolean insertResult = false;
        int orderId = -1;
        String orderNo = null;

        String sql1 = "INSERT INTO tblorder(customer_username, payment_mode, order_status, total_cost, payment_img, payment_status, delivery_address, order_approve_date, status_date, order_date) VALUES(?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "SELECT max(order_id) from tblorder";
        String sql3 = "UPDATE tblorder SET order_no = ? WHERE order_id = ?";
        String sql4 = "INSERT INTO tblorder_book(book_id, order_id, quantity, price) VALUES(?,?,?,?)";
        String sql5 = "UPDATE tblbook SET quantity_in_stock = ? WHERE book_id=?";

        Connection jdbcConnection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;

        try {
            jdbcConnection = dbConnection.createConnection();
            jdbcConnection.setAutoCommit(false);

            preStatement = jdbcConnection.prepareStatement(sql1);
            preStatement.setString(1, order.getCustomer().getUsername());
            preStatement.setString(2, order.getPaymentMode());
            preStatement.setByte(3, order.getOrderStatus());
            preStatement.setFloat(4, order.getTotalCost());
            preStatement.setString(5, order.getPaymentImagePath());
            preStatement.setBoolean(6, order.isPaymentStatus());
            preStatement.setString(7, order.getDeliveryAddress());
            if (order.getOrderApproveDate() != null) {
                preStatement.setString(8, MyUtil.convertDateToString(order.getOrderApproveDate()));
            } else {
                preStatement.setString(8, null);
            }
            preStatement.setString(9, MyUtil.convertDateToString(order.getStatusDate()));
            preStatement.setString(10, MyUtil.convertDateToString(order.getOrderDate()));
            insertResult = preStatement.executeUpdate() > 0;

            resultSet = preStatement.executeQuery(sql2);
            if (resultSet.next()) {
                orderId = resultSet.getInt(1);
                if ("cash".equals(order.getPaymentMode())) {
                    preStatement = jdbcConnection.prepareStatement(sql3);
                    orderNo = MyUtil.createOrderNo(orderId);
                    preStatement.setString(1, orderNo);
                    preStatement.setInt(2, orderId);
                    insertResult = preStatement.executeUpdate() > 0;
                    if (!insertResult) {
                        throw new SQLException();
                    }
                }
                List<CartItem> orderBookList = order.getOrderBookList();
                for (CartItem cartItem : orderBookList) {
                    preStatement = jdbcConnection.prepareStatement(sql4);
                    preStatement.setInt(1, cartItem.getSelectedBook().getBookId());
                    preStatement.setInt(2, orderId);
                    preStatement.setInt(3, cartItem.getQuantity());
                    preStatement.setInt(4, cartItem.getSelectedBook().getPrice());
                    insertResult = preStatement.executeUpdate() > 0;
                    if (!insertResult) {
                        throw new SQLException();
                    }

                    preStatement = jdbcConnection.prepareStatement(sql5);
                    preStatement.setInt(1, cartItem.getSelectedBook().getQuantityInStock() - cartItem.getQuantity());
                    preStatement.setInt(2, cartItem.getSelectedBook().getBookId());
                    insertResult = preStatement.executeUpdate() > 0;
                    if (!insertResult) {
                        throw new SQLException();
                    }
                }
                jdbcConnection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (jdbcConnection != null) {
                    jdbcConnection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            dbConnection.closeResultSet(resultSet);
            dbConnection.closePreparedStatement(preStatement);
            dbConnection.closeConnection(jdbcConnection);
        }
        if (insertResult) {
            order.setOrderId(orderId);
            order.setOrderNo(orderNo);
        }
        return insertResult;
    }
    
    public List<Order> getOrderList(String customerUserName) {
        Map<Integer, Order> orderListHashMap = new HashMap<Integer,Order>();
        String sql = "SELECT ord.*, ordb.quantity, ordb.price, b.*, u.* " +
                "FROM tblorder ord " +
                "INNER JOIN tblorder_book ordb ON ord.order_id COLLATE utf8mb4_unicode_ci = ordb.order_id " +
                "INNER JOIN tblbook b ON ordb.book_id COLLATE utf8mb4_unicode_ci = b.book_id " +
                "INNER JOIN tbluser u ON ord.customer_username COLLATE utf8mb4_unicode_ci = u.username " +
                "WHERE ord.customer_username = ? " +
                "ORDER BY ord.order_date DESC";
        Connection jdbcConnection = DBConnection.createConnection();
        
        try {
            PreparedStatement preStatement = jdbcConnection.prepareStatement(sql);
            preStatement.setString(1, customerUserName);
            ResultSet resultSet = preStatement.executeQuery();
            resultSet.next();
            while (resultSet.next()) {                
            	int orderId = resultSet.getInt("order_id");
                if (!orderListHashMap.containsKey(orderId)) {
                    Order order = new Order();
                    fillOrderFromResultSet(resultSet, order);
                    List<CartItem> orderBookList = new ArrayList<CartItem>();
                    Book orderBook = new Book();
                    fillBookFromResultSet(resultSet, orderBook);
                    CartItem cartItem = new CartItem(orderBook, resultSet.getInt("ordb.quantity"));
                    orderBookList.add(cartItem);
                    order.setOrderBookList(orderBookList);
                    orderListHashMap.put(orderId, order);
                } else {
                    Order order = orderListHashMap.get(orderId);
                    List<CartItem> orderBookList = order.getOrderBookList();
                    Book orderBook = new Book();
                    fillBookFromResultSet(resultSet, orderBook);
                    CartItem cartItem = new CartItem(orderBook, resultSet.getInt("ordb.quantity"));
                    orderBookList.add(cartItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        		DBConnection.closeConnection(jdbcConnection);
        }

        Collection<Order> values = orderListHashMap.values();
        ArrayList<Order> orderList = new ArrayList<Order>(values);
        Collections.sort(orderList);
        return orderList;
    }
    public List<Order> getOrderList(byte orderStatus) {
        Map<Integer, Order> orderListHashMap = new HashMap<Integer,Order>();
        String sql = "SELECT ord.*, ordb.quantity, ordb.price, b.*, u.* " +
                "FROM tblorder ord " +
                "INNER JOIN tblorder_book ordb ON ord.order_id COLLATE utf8mb4_unicode_ci = ordb.order_id " +
                "INNER JOIN tblbook b ON ordb.book_id COLLATE utf8mb4_unicode_ci = b.book_id " +
                "INNER JOIN tbluser u ON ord.customer_username COLLATE utf8mb4_unicode_ci = u.username " +
                "WHERE ord.order_status = ? " +
                "ORDER BY ord.order_date DESC, ord.order_date DESC";
        Connection jdbcConnection = DBConnection.createConnection();
        
        try {
            PreparedStatement preStatement = jdbcConnection.prepareStatement(sql);
            preStatement.setByte(1, orderStatus);
            ResultSet resultSet = preStatement.executeQuery();
            resultSet.next();
            while (resultSet.next()) {                
            	int orderId = resultSet.getInt("order_id");
                if (!orderListHashMap.containsKey(orderId)) {
                    Order order = new Order();
                    fillOrderFromResultSet(resultSet, order);
                    List<CartItem> orderBookList = new ArrayList<CartItem>();
                    Book orderBook = new Book();
                    fillBookFromResultSet(resultSet, orderBook);
                    CartItem cartItem = new CartItem(orderBook, resultSet.getInt("ordb.quantity"));
                    orderBookList.add(cartItem);
                    order.setOrderBookList(orderBookList);
                    orderListHashMap.put(orderId, order);
                } else {
                    Order order = orderListHashMap.get(orderId);
                    List<CartItem> orderBookList = order.getOrderBookList();
                    Book orderBook = new Book();
                    fillBookFromResultSet(resultSet, orderBook);
                    CartItem cartItem = new CartItem(orderBook, resultSet.getInt("ordb.quantity"));
                    orderBookList.add(cartItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        		DBConnection.closeConnection(jdbcConnection);
        }

        Collection<Order> values = orderListHashMap.values();
        ArrayList<Order> orderList = new ArrayList<Order>(values);
        Collections.sort(orderList);
        return orderList;
    }
    
    public boolean updateOrder(int orderId,byte orderStatus) {
		boolean updateResult = false;
		String sql = "UPDATE tblorder SET order_status = ?, status_date = ?, payment_status = ? "
					+"WHERE order_id = ?";
		
		Date statusDate = new Date();
		Connection jdbcConnection = DBConnection.createConnection();
		try {
			 PreparedStatement preStatement = jdbcConnection.prepareStatement(sql);
	            preStatement.setByte(1, orderStatus);
	            preStatement.setString(2, MyUtil.convertDateToString(statusDate));
	            preStatement.setBoolean(3, Constant.DELIVERED_ORDER_STATUS == orderStatus ? true:false);
	            preStatement.setInt(4, orderId);
	            updateResult = preStatement.executeUpdate()>0;
	            
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnection(jdbcConnection);
		}
		return updateResult;
	}
    public boolean updateOrderNo(int orderId,byte orderStatus) {
		boolean updateResult = false;
		String sql = "UPDATE tblorder SET order_no = ?, order_approve_date = ?, order_status = ?, status_date = ?, payment_status = ? "
					+"WHERE order_id = ?";
		Connection jdbcConnection = DBConnection.createConnection();
		Date now = new Date();
		String orderNo = MyUtil.createOrderNo(orderId);
		try {
			 PreparedStatement preStatement = jdbcConnection.prepareStatement(sql);
	            preStatement.setString(1, orderNo);
	            preStatement.setString(2, MyUtil.convertDateToString(now));
	            preStatement.setByte(3, orderStatus);
	            preStatement.setString(4, MyUtil.convertDateToString(now));
	            preStatement.setBoolean(5, true);
	            preStatement.setInt(6, orderId);
	            updateResult = preStatement.executeUpdate()>0;
	            
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnection(jdbcConnection);
		}
		return updateResult;
	}
    
    private void fillBookFromResultSet(ResultSet resultSet, Book orderBook) throws SQLException {
        orderBook.setBookId(resultSet.getInt("b.book_id"));
        orderBook.setTitle(resultSet.getString("b.title"));
        orderBook.setAuthor(resultSet.getString("b.author"));
        orderBook.setPrice(resultSet.getInt("ordb.price"));
        orderBook.setImagePath(resultSet.getString("b.image_path"));
    }

    private void fillOrderFromResultSet(ResultSet resultSet, Order order) throws SQLException {
        order.setOrderId(resultSet.getInt("order_id"));
        order.setOrderNo(resultSet.getString("ord.order_no")); // Thay đổi tên cột order_no từ "t.order_no" thành "ord.order_no"
        order.setOrderDate(resultSet.getTimestamp("ord.order_date")); // Thay đổi tên cột order_date từ "t.order_date" thành "ord.order_date"
        order.setOrderApproveDate(resultSet.getTimestamp("ord.order_approve_date")); // Thay đổi tên cột order_approve_date từ "t.order_approve_date" thành "ord.order_approve_date"
        order.setOrderStatus(resultSet.getByte("ord.order_status")); // Thay đổi tên cột order_status từ "t.order_status" thành "ord.order_status"
        order.setStatusDate(resultSet.getTimestamp("ord.status_date")); // Thay đổi tên cột status_date từ "t.status_date" thành "ord.status_date"
        order.setPaymentMode(resultSet.getString("ord.payment_mode")); // Thay đổi tên cột payment_mode từ "t.payment_mode" thành "ord.payment_mode"
        order.setPaymentStatus(resultSet.getBoolean("ord.payment_status")); // Thay đổi tên cột payment_status từ "t.payment_status" thành "ord.payment_status"
        order.setTotalCost(resultSet.getInt("ord.total_cost")); // Thay đổi tên cột total_cost từ "t.total_cost" thành "ord.total_cost"
        order.setDeliveryAddress(resultSet.getString("ord.delivery_address")); // Thay đổi tên cột delivery_address từ "t.delivery_address" thành "ord.delivery_address"
        order.setPaymentImagePath(resultSet.getString("ord.payment_img")); // Thay đổi tên cột payment_img từ "t.payment_img" thành "ord.payment_img"

        User customer = new User();
        customer.setUsername(resultSet.getString("u.username"));
        customer.setFullname(resultSet.getString("u.fullname"));
        customer.setMobile(resultSet.getString("u.mobile"));
        order.setCustomer(customer);
    }

}
