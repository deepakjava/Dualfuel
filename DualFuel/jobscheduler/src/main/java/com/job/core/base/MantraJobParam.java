package com.job.core.base;


import com.job.html.HtmlFieldType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MantraJobParam {
	public boolean isRequired();
	public String displayName();
	public HtmlFieldType fieldType();
	public boolean readOnlyField() default false;
	public int htmlFieldWidth() default 200;
	
}
