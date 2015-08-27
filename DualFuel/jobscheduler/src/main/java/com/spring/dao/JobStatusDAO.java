package com.spring.dao;

import com.entity.JobStatus;
import com.spring.dao.base.AbstractDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class JobStatusDAO extends AbstractDaoImpl<JobStatus, String>{
	
	public JobStatusDAO() {
		super(JobStatus.class);
	}
	
	public void saveOrUodateStatus(JobStatus status) {
		saveOrUpdate(status);
	}

}
