package com.yum.oauth2.client;

import com.yum.secure.client.model.UserInfo;
import com.yum.secure.exceptions.OAuthApiException;
import com.yum.secure.model.OAuth20Config;
import com.yum.secure.model.Token;
import com.yum.secure.oauth.IOAuth20PasswordService;
import com.yum.secure.oauth.OAuth20PasswordServiceImpl;

public class OAuthClientPasswordDemo {

	public static void main(String[] args) {
		String clientId = "0214346371552222";
		String clientSecret = "somesecret";
		String userName = "test";
		String password = "tivoli";
		OAuth20Config config = new OAuth20Config(clientId, clientSecret, "");
		IOAuth20PasswordService service = new OAuth20PasswordServiceImpl(config);
		
		Token accessToken = service.getAccessToken(userName, password);
		UserInfo user_ = new UserInfo(accessToken);
		try {
			UserInfo user = user_.requestUserInfo();
			 System.out.println(user);
		} catch (OAuthApiException e) {
			e.printStackTrace();
		}
	}
}
