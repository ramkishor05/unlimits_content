package com.brijframework.content.modal;

public class UIEntityObject {

	private Long id;
	
	private Double orderSequence;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getOrderSequence() {
		if(orderSequence==null) {
			orderSequence=0.0d;
		}
		return orderSequence;
	}

	public void setOrderSequence(Double orderSequence) {
		this.orderSequence = orderSequence;
	}
	
	
}
