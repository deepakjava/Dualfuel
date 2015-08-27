package com.job.core.base;

import com.entity.JobStatus;
import com.spring.service.JobService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.DateBuilder.IntervalUnit;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import java.lang.reflect.Field;
import java.util.Date;

@DisallowConcurrentExecution
public abstract class MantraJob implements Job {

	private static Logger log = Logger.getLogger(MantraJob.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		try {

			JobDataMap dataMap = context.getJobDetail().getJobDataMap();
			Boolean isRefire = (Boolean) dataMap.get("IS_REFIRE");

			Long jobRefireTime = (Long) dataMap.get("jobRefireTime");
			Long jobRefireTill = (Long) dataMap.get("jobRefireTill");
			
			Boolean isAutoScheduled = (Boolean) dataMap.get("isAutoScheduled");
			
			isAutoScheduled = (isAutoScheduled == null)?false:isAutoScheduled;

			Class clazz = this.getClass();

			Field[] fields = clazz.getDeclaredFields();

			for (Field f : fields) {
				if (dataMap.containsKey(f.getName())) {
					f.setAccessible(true);
					Class<?> type = f.getType();

					if (type == Integer.class || type == int.class) {
						if (dataMap.get(f.getName()) != null
								&& dataMap.get(f.getName()).toString().length() > 0) {
							f.set(this, new Integer(dataMap.get(f.getName())
									.toString()));
						}
					} else if (type == Double.class || type == double.class) {
						if (dataMap.get(f.getName()) != null
								&& dataMap.get(f.getName()).toString().length() > 0) {
							f.set(this, new Double(dataMap.get(f.getName())
									.toString()));
						}
					} else {
						f.set(this, dataMap.get(f.getName()));
					}

				}
			}

			String jobName = context.getJobDetail().getKey().getName();
			String jobNameKey = jobName;
			String group = context.getJobDetail().getKey().getGroup();
			int idxFirst = jobName.indexOf("_R", 1);
			if (idxFirst > 0)
				jobNameKey = jobName.substring(0, idxFirst);

			log.info(jobName + "----> Running");
			MantraJobExecutionResult jobExecutionResult = null;
			long startTime = System.currentTimeMillis();
			try {
				jobExecutionResult = execute();
			} catch (Exception e) {
				// log error
				// send email
				jobExecutionResult = MantraJobExecutionResult.createError(e);

			}

			long endTime = System.currentTimeMillis();
			jobExecutionResult.setJobStartTime(startTime);
			jobExecutionResult.setJobEndTime(endTime);

			ApplicationContext ctx = ContextLoader
					.getCurrentWebApplicationContext();
			JobService jobService = (JobService) ctx.getBean("jobService");

			com.entity.Job currentJob = jobService.findJobByName(jobNameKey);

			JobStatus status = new JobStatus();
			status.setJob(currentJob);
			status.setJobStartTime(new Date(startTime));
			status.setJobEndTime(new Date(endTime));
			status.setMessage(jobExecutionResult.getMessage());
			status.setErrorDetail(jobExecutionResult.getError());
			status.setStatus(jobExecutionResult.getResultType().name());

			// currentJob.getJobStatus().add(status);

			if (isAutoScheduled && jobExecutionResult.getResultType() == MantraJobResultType.TRY_LATER) {
				// schedule again

				Long mainStart = (Long) context.getJobDetail().getJobDataMap()
						.get("REFIRE_BEGIN_TIME");
				if (isRefire == null || isRefire == false) {
					context.getJobDetail()
							.getJobDataMap()
							.put("REFIRE_BEGIN_TIME",
									System.currentTimeMillis());
					context.getJobDetail().getJobDataMap()
							.put("IS_REFIRE", true);
				}

				if (jobRefireTime != null && jobRefireTill != null) {

					log.info("Scheduling retry;");

					boolean continueJobRetry = true;

					if (mainStart != null) {
						long currentTime = System.currentTimeMillis();
						mainStart = mainStart + (jobRefireTill * 1000);

						if (currentTime > mainStart) {
							status.setStatus(MantraJobResultType.ERROR.name());
							status.setErrorDetail("Cannot able to finish job ater all retries");
							continueJobRetry = false;
						}
					}

					if (continueJobRetry) {
						
					
						
						JobDetail retryJob = context.getScheduler().getJobDetail(new JobKey(jobNameKey + "_RETRY"));
						Trigger oldRetryTrigger = null;
						if(retryJob == null){
							retryJob = JobBuilder
									.newJob(this.getClass())
									.requestRecovery(true)
									.withIdentity(jobName + "_RETRY")
									.usingJobData(
											new JobDataMap(context.getJobDetail()
													.getJobDataMap())).build();
						}
						
						Trigger simpleRetryTrigger = (SimpleTrigger) TriggerBuilder
								.newTrigger()
								.withIdentity(jobNameKey + "_" + "RETRY", "RETRY")
								.startAt(
										DateBuilder.futureDate(
												jobRefireTime.intValue(),
												IntervalUnit.SECOND)) // use
																		// DateBuilder
																		// to
																		// create
																		// a
																		// date
																		// in
																		// the
																		// future
								.forJob(retryJob) // identify job with its
													// JobKey
								.build();


						oldRetryTrigger = context.getScheduler().getTrigger(simpleRetryTrigger.getKey());
						
						if (oldRetryTrigger != null){
							context.getScheduler().rescheduleJob(
									oldRetryTrigger.getKey(), simpleRetryTrigger);
						}else{
							context.getScheduler().scheduleJob(retryJob,
									simpleRetryTrigger);
						}
						
						log.info("Job Scheduled");
					}
				}

			}

			jobService.saveJobStatus(status);

		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);

		}
		// save result

	}

	public abstract MantraJobExecutionResult execute() throws Exception;

}
