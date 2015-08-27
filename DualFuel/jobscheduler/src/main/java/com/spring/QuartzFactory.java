package com.spring;

import com.entity.Job;
import com.entity.JobParam;
import com.spring.service.JobService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("singletonQuartzFactory")
@Scope(value = "singleton")
@DependsOn("jobService")
public class QuartzFactory {

	private Scheduler scheduler = null;
	private boolean isStarted = false;

	@Autowired
	private JobService jobService;

	private static Logger log = Logger.getLogger(QuartzFactory.class);

	public QuartzFactory() {
	}

	@PostConstruct
	public void init() {
		try {
			if (jobService != null)
				start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
	}

	public synchronized void start() throws SchedulerException {
		if (scheduler == null) {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			isStarted = true;
			autoScheduleJob();
		}
	}

	public boolean isStarted() {
		return isStarted;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public synchronized void stop() throws SchedulerException {
		if (scheduler != null) {
			scheduler.shutdown();
			scheduler = null;
			isStarted = false;
		}
	}

	private void autoScheduleJob() {

		List<Job> allJob = jobService.listAllJob();
		for (Job j : allJob) {
			// String jobClass = prop.getProperty(jprefix.trim() + ".class");
			log.info("scheduling job ---> " + j.getJobName());
			String jobClass = j.getJobClass();
			String jobTrigger = j.getJobTrigger();
			String jobNames = j.getJobName();
			Long jobRefireTime = j.getRefireSec();
			Long jobRefireTill = j.getRefireTill();

			if (jobClass != null && jobTrigger != null) {
				try {
					Class clazz = Class.forName(jobClass);
					
					//calls setter me
					
					JobDetail job = JobBuilder.newJob(clazz)
							.withIdentity(jobNames).build();

					Trigger trigger = TriggerBuilder
							.newTrigger()
							.withIdentity(jobNames, "group1")
							.withSchedule(
									CronScheduleBuilder
											.cronSchedule(jobTrigger)).build();
					
					JobDataMap dataMap = job.getJobDataMap();
					
					dataMap.put("isAutoScheduled", true);
					
					if(jobRefireTime != null){
						dataMap.put("jobRefireTime", jobRefireTime);
					}
					
					if(jobRefireTill != null){
						dataMap.put("jobRefireTill", jobRefireTill);
					}

					for(JobParam param : j.getJobParam()){
						dataMap.put(param.getFieldName(), param.getFieldValue());
					}
					
					
					scheduler.scheduleJob(job, trigger);
					log.info(jobClass + " ---> scheduled");
				} catch (Exception e) {
					log.error(jobClass + "-- Cannot schedule", e);
				}
			}

		}

	}

	public Set<String> runningJobs() {
		Set<String> running = new HashSet<String>();
		if (scheduler == null) {
			return running;
		}
		try {
			List<JobExecutionContext> currentJobs = scheduler
					.getCurrentlyExecutingJobs();
			for (JobExecutionContext jobCtx : currentJobs) {
				String jobName = jobCtx.getJobDetail().getKey().getName();
				running.add(jobName);
			}
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		return running;
	}
	
	public void runJob(String jobId) throws SchedulerException{
		
		for (String groupName : scheduler.getJobGroupNames()) {

		    // get jobkey
		    for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher
		        .jobGroupEquals(groupName))) {

		        String jobName = jobKey.getName();
		        String jobGroup = jobKey.getGroup();
		        if(jobName.equalsIgnoreCase(jobId))
		        	scheduler.triggerJob(jobKey);
		    }

		  }
	}

	public Set<String> scheduledJobs() {
		Set<String> scheduled = new HashSet<String>();
		if (scheduler == null) {
			return scheduled;
		}
		try {
			List<String> currentJobs = scheduler.getJobGroupNames();
			for (String groupName : currentJobs) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher
						.jobGroupEquals(groupName))) {
					String jobName = jobKey.getName();
					String jobGroup = jobKey.getGroup();

					scheduled.add(jobName);
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		return scheduled;
	}

	// private void scheduleJob() {
	//
	// try {
	// InputStream in = new FileInputStream(
	// "C:\\Users\\rparikh\\workspace_new\\JobScheduler\\src\\job.properties");
	// Properties prop = new Properties();
	// prop.load(in);
	// in.close();
	//
	// String jobNames = (String) prop.get("job.names");
	// String[] jobNameArray = jobNames.split(",");
	//
	// for (String jprefix : jobNameArray) {
	// String jobClass = prop.getProperty(jprefix.trim() + ".class");
	// String jobTrigger = prop.getProperty(jprefix.trim()
	// + ".trigger");
	//
	// if (jobClass != null && jobTrigger != null) {
	// try {
	// Class clazz = Class.forName(jobClass);
	//
	// JobDetail job = JobBuilder.newJob(clazz)
	// .withIdentity(jobNames).build();
	//
	// Trigger trigger = TriggerBuilder
	// .newTrigger()
	// .withIdentity(jobNames, "group1")
	// .withSchedule(
	// CronScheduleBuilder
	// .cronSchedule(jobTrigger))
	// .build();
	// scheduler.scheduleJob(job, trigger);
	// log.info(jobClass + " ---> scheduled");
	// } catch (Exception e) {
	// log.error(jobClass + "-- Cannot schedule", e);
	// }
	// }
	// }
	// } catch (Exception e) {
	// log.error("No Jobs are scheduled.", e);
	// }
	// }

}