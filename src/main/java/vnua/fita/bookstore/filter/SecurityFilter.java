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
import javax.servlet.http.HttpServletResponse;

import vnua.fita.bookstore.bean.User;
import vnua.fita.bookstore.config.SecurityConfig;
import vnua.fita.bookstore.util.MyUtil;

@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

	public SecurityFilter() {
		
	}
	
	public void init(FilterConfig fconfig) throws ServletException{
		
	}
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String servletPathFull = request.getServletPath();
		String servletPath = MyUtil.getServletPath(servletPathFull);
		
		if(!SecurityConfig.checkDenyUrlPattern(servletPath)) {
			chain.doFilter(request, response);
			return;
		}
		
		User loginedUser = MyUtil.getLoginedUser(request.getSession());
		boolean isPermission = false;
		if(loginedUser != null) {
			int role = loginedUser.getRole();
			isPermission = SecurityConfig.checkPermission(role, servletPath);
		}
		
		if(!isPermission) {
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		
	}
	
}
