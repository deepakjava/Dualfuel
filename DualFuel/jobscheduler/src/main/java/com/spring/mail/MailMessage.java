package com.spring.mail;

public class MailMessage {
	private static final long serialVersionUID = 1L;

	public static enum Type {
		PLAIN, HTML
	};

	private Type type = Type.PLAIN;

	public MailMessage() {
	}

	private MailMessage(MailMessage m) {
		this.type = m.type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
