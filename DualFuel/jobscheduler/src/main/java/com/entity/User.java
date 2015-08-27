package com.entity;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.NotEmpty;
import org.jasypt.hibernate3.type.EncryptedStringType;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table (name = "app_user")
@TypeDef(
	    name="encryptedString",
	    typeClass=EncryptedStringType.class,
	    parameters= {
	       @Parameter(name="encryptorRegisteredName", value="strongHibernateStringEncryptor")
	    }
	)
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column (name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
    private String id;
	
	@Column (name = "FIRST_NAME", unique=true)
    @NotEmpty(message="FIRST_NAME field is mandatory.")
    private String firstName;
	
	@Column (name = "LAST_NAME", unique=true)
    @NotEmpty(message="LAST_NAME field is mandatory.")
    private String lastName;
	
	@Column (name = "USER_ID", unique=true)
    @NotEmpty(message="USER_ID field is mandatory.")
    private String userId;
	
	@Column (name = "EMAIL_ADDRESS", unique=true)
    @NotEmpty(message="EMAIL_ADDRESS field is mandatory.")
    private String emailAddress;
	
	@Column (name = "PASSWORD")
    @NotEmpty(message="Password field is mandatory.")
	@Type(type="encryptedString")
    private String password;
	
	@Column (name = "LOGIN_ASK_RESET_PASSWORD")
    private Boolean isAskForResetPassword;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_GROUP_ID", nullable = false)
	private UserGroup userGroup;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsAskForResetPassword() {
		return isAskForResetPassword;
	}

	public void setIsAskForResetPassword(Boolean isAskForResetPassword) {
		this.isAskForResetPassword = isAskForResetPassword;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	
	

}
