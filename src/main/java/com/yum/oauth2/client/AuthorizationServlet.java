package com.yum.oauth2.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yum.secure.oauth.OAuthService;

@WebServlet(name = "AuthorizationServlet", urlPatterns = "/Authorize")
public class AuthorizationServlet extends HttpServlet {

	private static final long serialVersionUID = -6141729519391049460L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String requestContextPath=request.getRequestURL().toString();
		String contextPath=request.getContextPath();
		requestContextPath=requestContextPath.substring(0, requestContextPath.indexOf(contextPath)+contextPath.length());
		// System.out.println(requestContextPath);
		
		String callback = requestContextPath+"/callback";
		// System.out.println("callback : "+callback);
    	OAuthService service =new  OAuthService("021434637155289","somesecret",callback);
    	if(request.getSession().getAttribute("OAuthService")==null){
    		request.getSession().setAttribute("OAuthService", service);
    	}
    	// System.out.println("Authorization Url :"+service.getAuthorizationUrl());
    	response.sendRedirect(service.getAuthorizationUrl());
	}
}
