package com.situ.mall.core.constant;

public class Const {
	//user表里面两个角色
	public interface Role{
		int ROLE_ADMIN = 0;
		int ROLE_USER = 1;
		
	}
	
	public interface CartChecked{
		int UNCHECKD = 0;
		int CHECKED = 1;
		
	}
	
	
	public static final String CURRENT_USER="CURRENT_USER";
}
