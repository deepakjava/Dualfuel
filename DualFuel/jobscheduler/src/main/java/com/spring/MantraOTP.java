package com.spring;

public class MantraOTP {
	
	private String otpString;
	private Long createdTime;
	
	
	public MantraOTP(String otpString, Long createdTime) {
		super();
		this.otpString = otpString;
		this.createdTime = createdTime;
	}

	public boolean isOld(){
		long currentTime = System.currentTimeMillis();
		return (currentTime - createdTime) > 1000;
	}

	public boolean isExpired(int time){
		long currentTime = System.currentTimeMillis();
		return ((currentTime - createdTime) > time);
	}

	public String getOtpString() {
		return otpString;
	}
	
	

}
