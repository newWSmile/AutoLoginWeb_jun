package cn.hncu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hncu.domain.User;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String time = request.getParameter("time");
		Integer itime = Integer.parseInt(time);//几天内自动登录
		User user = new User();
		user.setName(name);
		user.setPwd(pwd);
		
		if(name!=null && name.trim().length()>0 && pwd!=null && pwd.trim().length()>0 ){//输了
			if(name.equals(pwd)){//假设通过后台验证
				request.getSession().setAttribute("user", user);
				//将此时登录成功的用户名密码写到cookie中去
				//为了兼容中文，编码
				name = URLEncoder.encode(name, "utf-8");
				pwd = URLEncoder.encode(pwd, "utf-8");
				Cookie cookie = new Cookie("autoLogin",name+","+pwd);
				//设置cookie的权限，保存时间
				cookie.setPath(request.getContextPath());
				cookie.setMaxAge(60*60*60*24*itime);
				//存到客户端
				response.addCookie(cookie);
			}else{//没有通过后台验证
				request.getSession().setAttribute("error", "用户名密码错误");
			}
		}else{//输入不合法，或者没输
			request.getSession().setAttribute("error", "用户名密码输入格式错误");
		}
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		
	}

}
