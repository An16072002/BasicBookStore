package vnua.fita.bookstore.bean;

import java.util.Date;
import java.util.List;

import vnua.fita.bookstore.util.Constant;

public class Order implements Comparable<Order> {
	private int orderId; // id đơn hàng quản lý trong hệ thống
	private String orderNo; // mã đơn hàng giao dịch với khách hàng
	private User customer; // đối tượng khách hàng chứa nhiều thông tin
	private Date orderDate; // ngày đặt hàng
	private Date orderApproveDate; // ngày phê duyệt đơn
	private String paymentMode; // hình thức thanh toán
	private String paymentModeDescription; // tên mô tả hình thức thanh toán
	private byte orderStatus; // trạng thái đơn hàng
	private String orderStatusDescription; // mô tả trạng thái thanh toán
	private float totalCost; // tổng số tiền đơn hàng
	private String paymentImagePath; // đường dẫn ảnh chụp màn hình chuyển tiền hoặc phiếu
										// ATM
	private boolean paymentStatus; // trạng thái thanh toán
	private String paymentStatusDescription; // mô tả trạng thái thanh toán
	private Date statusDate; // ngày tương ứng trạng thái đơn hàng
	private String deliveryAddress; // địa chỉ giao hàng
	private List<CartItem> orderBookList; // danh sách mặt hàng đặt hàng

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getOrderApproveDate() {
		return orderApproveDate;
	}

	public void setOrderApproveDate(Date orderApproveDate) {
		this.orderApproveDate = orderApproveDate;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	// cập nhật hình thức thanh toán và mô tả hình thức cùng nhau
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
		if ("cash".equals(paymentMode)) {
			this.paymentModeDescription = "Tiền mặt khi nhận sách";
		} else if ("transfer".equals(paymentMode)) {
			this.paymentModeDescription = "Chuyển khoản";
		}
	}

	public String getPaymentModeDescription() {
		return paymentModeDescription;
	}

	public void setPaymentModeDescription(String paymentModeDescription) {
		this.paymentModeDescription = paymentModeDescription;
	}

	public byte getOrderStatus() {
		return orderStatus;
	}

	//cập nhật trạng thái đơn hàng cùng mô tả trạng thái đơn hàng cùng nhau
	public void setOrderStatus(byte orderStatus) {
		this.orderStatus = orderStatus;
		switch(orderStatus) {
			case Constant.WAITING_CONFIRM_ORDER_STATUS:
				this.orderStatusDescription = "Chờ xác nhận";
				break;
			case Constant.DELEVERING_ORDER_STATUS:
				this.orderStatusDescription = "Đang giao hàng";
				break;
			case Constant.DELEVERED_ORDER_STATUS:
				this.orderStatusDescription = "Đã giao hàng";
				break;
			case Constant.CANCEL_ORDER_STATUS:
				this.orderStatusDescription = "Khách hủy đơn";
				break;
			case Constant.REJECT_ORDER_STATUS:
				this.orderStatusDescription = "Khách trả hàng";
				break;
			case Constant.NOT_AVAIABLE_ORDER_STATUS:
				this.orderStatusDescription = "Hàng không còn đủ";
				break;
		}
	}

	public String getOrderStatusDescription() {
		return orderStatusDescription;
	}

	public void setOrderStatusDescription(String orderStatusDescription) {
		this.orderStatusDescription = orderStatusDescription;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public String getPaymentImagePath() {
		return paymentImagePath;
	}

	public void setPaymentImagePath(String paymentImagePath) {
		this.paymentImagePath = paymentImagePath;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	//cập nhật trạng thái thanh toán cùng mô tả trạng thái thanh toán cùng nhau
	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
		if(paymentStatus) {
			this.paymentStatusDescription = Constant.PAYMENTED_STATUS;
		}else {
			this.paymentStatusDescription = Constant.UNPAYMENTED_STATUS;
		}
	}

	public String getPaymentStatusDescription() {
		return paymentStatusDescription;
	}

	public void setPaymentStatusDescription(String paymentStatusDescription) {
		this.paymentStatusDescription = paymentStatusDescription;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public List<CartItem> getOrderBookList() {
		return orderBookList;
	}

	public void setOrderBookList(List<CartItem> orderBookList) {
		this.orderBookList = orderBookList;
	}

	@Override
	public int compareTo(Order o) {
		return o.orderId - this.orderId; //sắp xếp giảm
	}

}
