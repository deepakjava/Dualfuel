package com.entity;

import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "email_group")
public class UserGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column (name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
    private String id;
	
	@Column (name = "USER_GROUP", unique=true)
    @NotEmpty(message="USER_GROUP field is mandatory.")
    private String userGroup;
	
	@Column (name = "DESCRIPTION", unique=true)
    @NotEmpty(message="DESCRIPTION field is mandatory.")
    private String description;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE }, mappedBy = "userGroup")
	@NotNull
	private List<User> users = new ArrayList<User>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	

}
