package vnua.fita.bookstore.formbean;

import java.util.ArrayList;
import java.util.List;

import vnua.fita.bookstore.util.Constant;

public class LoginForm {
	private String username;
	private String password;
	private String rememberMe;
	public LoginForm(String username, String password,String rememberMe) {
		this.username = username;
		this.password = password;
		this.rememberMe=rememberMe != null ? "checked" : "";
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
	public String getRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe != null ? "checked" : "";
	}
	public List<String> validate(){
		List<String> errors = new ArrayList<String>();
		if(username==null || username.trim().isEmpty()) {
			errors.add(Constant.USERNAME_EMPTY_VALIDATE_MSG);
		}
		if(password==null || password.trim().isEmpty()) {
			errors.add(Constant.PASSWORD_EMPTY_VALIDATE_MSG);
		}
		return errors;
	}
}
