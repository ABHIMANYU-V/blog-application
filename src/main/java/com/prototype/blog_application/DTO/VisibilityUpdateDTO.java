package com.prototype.blog_application.DTO;


public class VisibilityUpdateDTO {
	
	private boolean isHidden;
	
	
	public VisibilityUpdateDTO() {
		super();
	}


	public VisibilityUpdateDTO(boolean isHidden) {
		super();
		this.isHidden = isHidden;
	}


	public boolean isHidden() {
		return isHidden;
	}


	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
	
	
	
	
}
