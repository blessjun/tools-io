package cn.com.coho.tools.io.core.exception;

import cn.com.coho.tools.io.core.util.ResponseCode;

/**
 * @author scott
 * 业务异常处理类
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer code;
	
	private String msg;

	public BizException(){}

	public BizException(ResponseCode responseCode){
		super(responseCode.getCode() + responseCode.getMsg());
		this.code = responseCode.getCode();
		this.msg = responseCode.getMsg();
	}

	public BizException(String message){
		super(message);
		this.msg = message;
	}

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }


    public BizException(Integer code, String message){
		super(code + message);
		this.code = code;
		this.msg = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
