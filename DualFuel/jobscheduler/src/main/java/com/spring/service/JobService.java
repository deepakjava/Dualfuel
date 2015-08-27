package com.spring.service;

import com.entity.Job;
import com.entity.JobStatus;
import com.spring.dao.JobDAO;
import com.spring.dao.JobStatusDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("jobService")
@Transactional(readOnly = true)
public class JobService {
	
	@Autowired
    private JobDAO jobDAO;
	
	@Autowired
    private JobStatusDAO jobStatusDAO;
	
	@Transactional(readOnly = false)
	public void saveOrUpdateEvent(Job job){
		jobDAO.addNewJob(job);
	}
	
	@Transactional(readOnly = false)
	public List<Job>  listAllJob(){
		List<Job> jobs = jobDAO.listAllJob();
		return jobs;
	}
	
	
	@Transactional(readOnly = false)
	public Job findJobByName(String jobName){
		return jobDAO.findJobByName(jobName);
	}
	
	
	@Transactional(readOnly = false)
	public void saveJobStatus(JobStatus status){
		jobStatusDAO.saveOrUodateStatus(status);
	}
	
	@Transactional(readOnly = false)
	public JobStatus getJobStatus(String id){
		return jobStatusDAO.findById(id);
	}

}
