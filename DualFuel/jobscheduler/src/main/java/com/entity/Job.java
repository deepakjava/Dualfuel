package com.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "job")
@FilterDef(name = Job.LATEST_STATUS_FILTER)
public class Job implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String LATEST_STATUS_FILTER = "latestStatusFilter";


	@Id
    @Column (name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
    private String id;
	
	@Column (name = "JOB_NAME", unique=true)
    @NotEmpty(message="JOB_NAME field is mandatory.")
    private String jobName;
	
	@Column (name = "JOB_GROUP", nullable = false)
    @NotEmpty(message="JOB_GROUP field is mandatory.")
    private String jobGroup;
	
	@Column (name = "JOB_TRIGGER", nullable = false)
    @NotEmpty(message="JOB_TRIGGER field is mandatory.")
    private String jobTrigger;
	
	@Column (name = "JOB_TYPE", nullable = false)
    @NotEmpty(message="JOB_TYPE field is mandatory.")
    private String jobType;
	
	@Column (name = "JOB_REFIRE_SEC", nullable = false)
    private Long refireSec;
	
	@Column (name = "JOB_REFIRE_TILL", nullable = true)
    private Long refireTill;
	
	
	@Column (name = "JOB_CLASS", nullable = false)
    @NotEmpty(message="JOB_CLASS field is mandatory.")
    private String jobClass;
	
	
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE }, mappedBy = "job")
	@NotNull
	private List<JobParam> jobParam;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE }, mappedBy = "job")
	@Filter(name = LATEST_STATUS_FILTER, condition = "ID =(select max(M.ID) from job_status M where M.JOB_ID=JOB_ID)")
	private List<JobStatus> jobStatus = new ArrayList<JobStatus>();
	
	@Transient
	private JobStatus latestStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobTrigger() {
		return jobTrigger;
	}

	public void setJobTrigger(String jobTrigger) {
		this.jobTrigger = jobTrigger;
	}

	public List<JobParam> getJobParam() {
		return jobParam;
	}

	public void setJobParam(List<JobParam> jobParam) {
		this.jobParam = jobParam;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public List<JobStatus> getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(List<JobStatus> jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	public void loadLatestStatus() {
		if(jobStatus.isEmpty()){
			return;
		}
		latestStatus = jobStatus.get(0);
	}

	public JobStatus getLatestStatus() {
		return latestStatus;
	}

	public Long getRefireSec() {
		return refireSec;
	}

	public void setRefireSec(Long refireSec) {
		this.refireSec = refireSec;
	}

	public Long getRefireTill() {
		return refireTill;
	}

	public void setRefireTill(Long refireTill) {
		this.refireTill = refireTill;
	}
	

}
