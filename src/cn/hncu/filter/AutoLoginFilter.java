package cn.hncu.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hncu.domain.User;

public class AutoLoginFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getSession().getAttribute("user")==null){//没有登录，帮助自动登录
			//通过response去拿cookie数组
			Cookie[] cs = req.getCookies();
			if(cs!=null){
				for(Cookie c : cs){
					if(c.getName().equals("autoLogin")){
						String val = c.getValue();
						String[] strs = val.split(",");
						//为了兼容中文那边编码 这里要解码
						String name = URLDecoder.decode(strs[0], "utf-8");
						String pwd = URLDecoder.decode(strs[1], "utf-8");
						
						if (name.equals(pwd)) {//去后台数据库验证--------这里假装name和pwd相等就通过
							User user = new User();
							user.setName(name);
							user.setPwd(pwd);
							req.getSession().setAttribute("user", user);
							//System.out.println("我将自动登录");
							break;
						}
					}
				}
			}
			
		}
		chain.doFilter(req, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
