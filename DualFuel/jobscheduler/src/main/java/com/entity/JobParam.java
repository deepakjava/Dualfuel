package com.entity;

import org.hibernate.validator.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table (name = "job_param")
public class JobParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column (name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
    private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JOB_ID", nullable = false)
	private Job job;
	
	@Column (name = "FIELD_NAME", nullable = false)
    @NotEmpty(message="FIELD_NAME field is mandatory.")
    private String fieldName;
	
	@Column (name = "FIELD_VALUE", nullable = false)
    @NotEmpty(message="FIELD_VALUE field is mandatory.")
    private String fieldValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	
}
