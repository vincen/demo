package com.yum.oauth2.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yum.secure.client.model.UserInfo;
import com.yum.secure.exceptions.OAuthApiException;
import com.yum.secure.model.Token;
import com.yum.secure.oauth.OAuthService;

@WebServlet(name = "CallbackServlet", urlPatterns = "/callback")
public class CallbackServlet  extends HttpServlet {

	private static final long serialVersionUID = -7262034286119336180L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OAuthService service =null;
		if(request.getSession().getAttribute("OAuthService")!=null){
			 service = (OAuthService)request.getSession().getAttribute("OAuthService");
		}else{
			String requestContextPath=request.getRequestURL().toString();
			String contextPath=request.getContextPath();
			requestContextPath=requestContextPath.substring(0, requestContextPath.indexOf(contextPath)+contextPath.length());
			// System.out.println(requestContextPath);
			
			String callback = requestContextPath+"/callback";
//			System.out.println("callback : "+callback);
	    	service = new  OAuthService("021434637155289","somesecret",callback);
	    	if(request.getSession().getAttribute("OAuthService")==null){
	    		request.getSession().setAttribute("OAuthService", service);
	    	}
		}
			
			String code = request.getParameter("code");
			// System.out.println("=====code:" + code + "===========");
			Token accessToken = service.getAccessToken(code);

			
			// System.out.println("===========accessToken:" + accessToken + "===================");
			
			try {
				UserInfo user_ = new UserInfo(accessToken);
				UserInfo user = user_.requestUserInfo();
				String jsonUser = user_.requestJsonUserInfo();
				
				
				if(user != null) {
					request.setAttribute("userName", user.getYumPSID());
					request.setAttribute("user", user);
					request.setAttribute("jsonUser", jsonUser);
					request.getRequestDispatcher("/user.jsp").forward(request, response);
				}
			} catch (OAuthApiException e) {
				e.printStackTrace();
			}
    	}
}
