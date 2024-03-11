package vnua.fita.bookstore.config;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import vnua.fita.bookstore.util.Constant;

public class SecurityConfig  {
	
	private static final Map<Integer, List<String>> mapConfig = new HashMap<Integer,List<String>>();
	
	static {
		init();
	}

	private static void init() {
		List<String> urlPatterns1 = new ArrayList<String>();
		
		urlPatterns1.add("/accountInfo");
		urlPatterns1.add("/cartBook");
		urlPatterns1.add("/order");
		urlPatterns1.add("/customerOrderList");
		mapConfig.put(Constant.CUSTOMER_ROLE, urlPatterns1);
		
		List<String> urlPatterns2 = new ArrayList<String>();
		urlPatterns2.add("/accountInfo");
		urlPatterns2.add("/adminHome");
		urlPatterns2.add("/adminOrderList");
		urlPatterns2.add("/createBook");
		urlPatterns2.add("/deleteBook");
		urlPatterns2.add("/editBook");
		mapConfig.put(Constant.ADMIN_ROLE, urlPatterns2);
	}
	
	public static boolean checkPermission(int role,String servletPath) {
		List<String> urlPatternsForRole = mapConfig.get(role);
		if(urlPatternsForRole.contains(servletPath)) {
			return true;
		}
		return false;
	}
	
	public static boolean checkDenyUrlPattern(String servletPath) {
		for(Map.Entry<Integer, List<String>> entry : mapConfig.entrySet()) {
			List<String> urlPatterns = entry.getValue();
			if(urlPatterns.contains(servletPath)) {
				return true;
			}
			
		}
		return false;
	}

	
}
