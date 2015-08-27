package com.spring.dao;

import com.entity.Job;
import com.spring.dao.base.AbstractDaoImpl;
import org.hibernate.Filter;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobDAO extends AbstractDaoImpl<Job, String> {

	public JobDAO() {
		super(Job.class);
	}

	public void addNewJob(Job job) {
		saveOrUpdate(job);
	}

	public List<Job> listAllJob() {
		List<Job> jobs = listAll();
		for(Job j : jobs){
			Filter filter = this.sessionFactory.getCurrentSession().enableFilter("latestStatusFilter"); 

			j.loadLatestStatus();
		}
		return jobs;
	}

	public Job findJobByName(String jobName) {
		return findUniqueRecord(Restrictions.eq("jobName", jobName));
	}

}
