package vnua.fita.bookstore.bean;

public class User {
	private String username;
	private String password;
	private String fullname;
	private String mobile;
	private String email;
	private String address;
	private int role;
	
	
	public User() {
		super();
	}
	public User(String username, String password, String fullname,String email, String mobile, String address, int role) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.mobile = mobile;
		this.address = address;
		this.role = role;
	}
	
	public User(String username, String password, String fullname,int role, String email, String mobile,String adress) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getFullname() {
		return fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
