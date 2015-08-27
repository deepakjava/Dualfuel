package com.entity;

import org.hibernate.validator.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table (name = "job_status")
public class JobStatus implements Serializable{
	
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
	
	@Column (name = "START_TIME", nullable = false)
    private Date jobStartTime;
	
	@Column (name = "END_TIME", nullable = false)
    private Date jobEndTime;
	
	@Column (name = "STATUS", nullable = false)
    @NotEmpty(message="STATUS field is mandatory.")
    private String status;
	
	@Column (name = "MESSAGE", nullable = true)
    private String message;
	
	
	@Column (name = "EXCEPTION", nullable = true)
    private String errorDetail;
	

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

	public Date getJobStartTime() {
		return jobStartTime;
	}

	public void setJobStartTime(Date jobStartTime) {
		this.jobStartTime = jobStartTime;
	}

	public Date getJobEndTime() {
		return jobEndTime;
	}

	public void setJobEndTime(Date jobEndTime) {
		this.jobEndTime = jobEndTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}
	
	

}
