package cn.com.coho.tools.io.core.cahe;

import cn.com.coho.tools.io.model.entity.UserLoginData;

/**
 * @author scott
 * 本地线程缓存类
 */
public class RequestDataContext {


	private static final ThreadLocal<UserLoginData> UserContext = new ThreadLocal<>();

	public static UserLoginData get(){
		if (null == UserContext.get()){
			UserContext.set(new UserLoginData());
		}
		return UserContext.get();
	}


	public static void set(UserLoginData userLoginData){
		UserContext.set(userLoginData);
	}

	public static void clear(){
		UserContext.remove();
	}
}
