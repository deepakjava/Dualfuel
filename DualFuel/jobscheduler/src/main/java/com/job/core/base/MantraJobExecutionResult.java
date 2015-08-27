package com.job.core.base;

import java.io.PrintWriter;
import java.io.StringWriter;


public class MantraJobExecutionResult {
	
	private MantraJobResultType resultType;
	private String message;
	private String error;
	private long jobStartTime;
	private long jobEndTime;
	
	public MantraJobExecutionResult(MantraJobResultType resultType, String message, String error) {
		super();
		this.resultType = resultType;
		this.message = message;
		this.error = error;
	}

	public void setJobStartTime(long jobStartTime) {
		this.jobStartTime = jobStartTime;
	}

	public void setJobEndTime(long jobEndTime) {
		this.jobEndTime = jobEndTime;
	}

	public MantraJobResultType getResultType() {
		return resultType;
	}

	public String getMessage() {
		return message;
	}

	public long getJobStartTime() {
		return jobStartTime;
	}

	public long getJobEndTime() {
		return jobEndTime;
	}
	
	
	
	public String getError() {
		return error;
	}
	
	public static MantraJobExecutionResult createRetryOpetation(String message){
		return new MantraJobExecutionResult(MantraJobResultType.TRY_LATER, message, null);
	}

	public static MantraJobExecutionResult createSucess(String message){
		return new MantraJobExecutionResult(MantraJobResultType.SUCESS, message, null);
	}
	
	public static MantraJobExecutionResult createError(Exception ex){
		
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return new MantraJobExecutionResult(MantraJobResultType.ERROR, "Error", errors.toString());
	}
	
	public static MantraJobExecutionResult createError(String errorMsg, Exception ex){
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return new MantraJobExecutionResult(MantraJobResultType.ERROR, errorMsg, errors.toString());
	}
	
	public static MantraJobExecutionResult createError(String errorMsg){
		return new MantraJobExecutionResult(MantraJobResultType.ERROR, errorMsg, null);
	}

}
