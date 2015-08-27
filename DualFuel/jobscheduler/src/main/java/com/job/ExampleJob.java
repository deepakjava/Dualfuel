package com.job;

import com.job.core.MantraJobRegistry;
import com.job.core.base.MantraJob;
import com.job.core.base.MantraJobExecutionResult;
import com.job.core.base.MantraJobParam;
import com.job.html.HtmlFieldType;


@MantraJobRegistry(jobType="Example Job")
public class ExampleJob extends MantraJob{
	
	@MantraJobParam(displayName="Message", fieldType= HtmlFieldType.INPUT, isRequired=true)
	private String message;

	@Override
	public MantraJobExecutionResult execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Hi");
		Thread.sleep(3000);
		return MantraJobExecutionResult.createRetryOpetation("Need to try again");
	}


}
