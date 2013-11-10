package com.ncepu.action.account;

import com.ncepu.service.AccountService;
import com.ncepu.util.StringHelp;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

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

	public String loginForward() throws Exception {
		return SUCCESS;
	}

	public String login() throws Exception {
		if (StringHelp.isNullOrEmpty(username)
				|| StringHelp.isNullOrEmpty(password))
			return LOGIN;
		boolean result = new AccountService().login(username, password);
		if (result)
			return SUCCESS;
		return LOGIN;
	}
}
