package cn.com.daybreak.blog.common.bean;

import java.io.Serializable;

public class FormErrorInfo implements Serializable{

	private static final long serialVersionUID = -5817422209974652925L;
	
	private String field;
	private String error;
	
	public FormErrorInfo(String field, String error) {
		this.field = field;
		this.error = error;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
}
