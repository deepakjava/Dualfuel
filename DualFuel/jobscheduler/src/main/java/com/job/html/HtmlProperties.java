package com.job.html;

public class HtmlProperties {

	private String id;
	private String displayName;
	private String defaultValue = "";
	private boolean isRequired;
	private int htmlWidth = 0;
	private boolean isReadOnly = false;
	private String fieldType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public int getHtmlWidth() {
		return htmlWidth;
	}

	public void setHtmlWidth(int htmlWidth) {
		this.htmlWidth = htmlWidth;
	}

	public String getRequiredStr() {
		return (this.isRequired) ? "required" : "";
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public String getReadOnlyStr() {
		return (this.isReadOnly) ? "readonly" : "";
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	

}
