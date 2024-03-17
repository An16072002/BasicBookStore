package vnua.fita.bookstore.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vnua.fita.bookstore.bean.User;
import vnua.fita.bookstore.model.UserDAO;
import vnua.fita.bookstore.util.MyUtil;

@WebFilter(filterName = "cookieFilter", urlPatterns = { "/*" })
public class CookieFilter implements Filter {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

	@Override
	public void init(FilterConfig filterConfig) {
		userDAO = new UserDAO();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		
		User userInSession=MyUtil.getLoginedUser(session);
		if(userInSession!=null) {
			session.setAttribute("CHECKED_COOKIE", "CHECKED");
			chain.doFilter(request, res);
			return;
		}
		String checked=(String) session.getAttribute("CHECKED_COOKIE");
		if(checked==null) {
			String username=MyUtil.getUsernameInCookie(request);
			if(username!=null && !username.isEmpty()) {
				User user=userDAO.findUser(username);
				if(user!=null) {
					String token = MyUtil.getTokenInCookie(request);
					if ( token.equals(MyUtil.createTokenFromUserInfo(user))) {
					    MyUtil.storeLoginedUser(session, user);
					    session.setAttribute("CHECKED_COOKIE", "CHECKED");
					}

				}
			}
		}
		// TODO Auto-generated method stub
		chain.doFilter(req, res);
	}

}
