package com.spring;


import com.appmanager.JobProperties;

import com.entity.Job;
import com.entity.JobParam;

import com.job.html.HtmlProperties;
import com.spring.service.JobService;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class JobController {
	
	@Autowired
	private QuartzFactory singletonQuartzFactory;
	
	@Autowired
	private JobProperties jobProperties;
	
	@Autowired
    private JobService jobService;
	
//	@Autowired
//	private CertificateService certificateService;
	
	private static Logger log = Logger.getLogger(JobController.class);
	
	
	@RequestMapping(value = "/private/job/errorLog", method = RequestMethod.GET)
	@ResponseBody
	public String errorLog(Model model, @RequestParam("logId") String logId) {
		
		
		return jobService.getJobStatus(logId).getErrorDetail();
	}
	
	
	@RequestMapping(value = "/private/job/runJob", method = RequestMethod.GET)
	@ResponseBody
	public String home(Model model, @RequestParam("jobId") String jobId) {
		
		boolean sucess = false;
		 try {
			singletonQuartzFactory.runJob(jobId);
			sucess = true;
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error(e);
			throw new RuntimeException(e);
		}
		return (sucess + "").toUpperCase();
	}
	
	@RequestMapping(value = "/private/job/home", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("isJobSchedularRunning", singletonQuartzFactory.isStarted());
		model.addAttribute("runningJob", singletonQuartzFactory.runningJobs());
		
		List<Job> allJob = jobService.listAllJob();
		List<List<Job>> allJobByGroup = new ArrayList<List<Job>>();
		
		Map<String, List<Job>> jobByGroupMap = new TreeMap<String, List<Job>>();
		
		for(Job j : allJob){
			List<Job> temp = jobByGroupMap.get(j.getJobGroup());
			if(temp == null){
				temp = new ArrayList<Job>();
				jobByGroupMap.put(j.getJobGroup(), temp);
			}
			
			temp.add(j);
		}
		
		for(String key : jobByGroupMap.keySet()){
			allJobByGroup.add(jobByGroupMap.get(key));
		}
		
		model.addAttribute("allJob", allJobByGroup);
		
		//runningJobs()
		return "jobHome";
	}
	
	@RequestMapping(value = "/private/job/update/table", method = RequestMethod.GET)
	public String ajaxJobTable(Model model) {
//		model.addAttribute("isJobSchedularRunning", singletonQuartzFactory.isStarted());
		List<Job> allJob = jobService.listAllJob();
		List<List<Job>> allJobByGroup = new ArrayList<List<Job>>();
		
		Map<String, List<Job>> jobByGroupMap = new TreeMap<String, List<Job>>();
		
		for(Job j : allJob){
			List<Job> temp = jobByGroupMap.get(j.getJobGroup());
			if(temp == null){
				temp = new ArrayList<Job>();
				jobByGroupMap.put(j.getJobGroup(), temp);
			}
			
			temp.add(j);
		}
		
		for(String key : jobByGroupMap.keySet()){
			allJobByGroup.add(jobByGroupMap.get(key));
		}
		
		model.addAttribute("allJob", allJobByGroup);
		model.addAttribute("runningJob", singletonQuartzFactory.runningJobs());
		model.addAttribute("scheduledJob", singletonQuartzFactory.scheduledJobs());
		//runningJobs()
		return "jobTable";
	}
	
	@RequestMapping(value = "/private/job/save/action", method = RequestMethod.POST)
	public String saveJob(Model model, @RequestParam("inputRefireSec") Long inputRefireSec, @RequestParam("inputRefireTill") Long inputRefireTill, 
			@RequestParam("inputJobName") String inputJobName, @RequestParam("inputJobGroup") String inputJobGroup, @RequestParam("inputTrigger") String inputTrigger, @RequestParam("inputJobType") String jobId, org.springframework.web.context.request.WebRequest webRequest) {
		
		 Map<String, HtmlProperties> params = jobProperties.jobParams.get(jobId);
		 
		 Job job = new Job();
		 job.setJobName(inputJobName);
		 job.setJobGroup(inputJobGroup);
		 job.setJobTrigger(inputTrigger);
		 job.setJobType(jobId);
		 job.setRefireSec(inputRefireSec);
		 job.setRefireTill(inputRefireTill);
		 job.setJobClass(jobProperties.jobTypesToClass.get(jobId));
		 List<JobParam> jobParams = new ArrayList<JobParam>();
		 for(String param : params.keySet()){
			 JobParam p = new JobParam();
			 p.setFieldName(param);
			 String value = webRequest.getParameter(param);
			 if(value == null || value.trim().length() == 0){
				 continue;
			 }
			 p.setFieldValue(value);
			 p.setJob(job);
			 jobParams.add(p);
			 
		 }
		 
		 job.setJobParam(jobParams);
		 
		 jobService.saveOrUpdateEvent(job);
		 
//		 org.quartz.CronExpression.isValidExpression(expression);

		return "redirect:/private/job/home";
	}
	
	@RequestMapping(value = "/private/job/param", method = RequestMethod.POST)
	public String getParam(Model model, @RequestParam("jobId") String jobId) {
		
		//List<CertificatesMapping> allCert = certificateService.listAllCertificate();
		model.addAttribute("jobParams", jobProperties.jobParams.get(jobId).values());
		model.addAttribute("allCertOption", new ArrayList<>());
//		model.addAttribute("jobParamKeys", jobProperties.jobParams.get(jobId).keySet());
		
		return "jobParam";
	}
	
	@RequestMapping(value = "/private/job/add", method = RequestMethod.GET)
	public String add(Model model) {
		
		model.addAttribute("allAvailableJob", jobProperties.jobTypes);
		return "addJob";
	}
	
	@RequestMapping(value = "/private/job/service/action", method = RequestMethod.POST)
	public String service(Model model) throws SchedulerException {
		if(singletonQuartzFactory.isStarted())
			singletonQuartzFactory.stop();
		else
			singletonQuartzFactory.start();
		
		return "redirect:/private/job/home";
	}
	


	public QuartzFactory getSingletonQuartzFactory() {
		return singletonQuartzFactory;
	}

	public void setSingletonQuartzFactory(QuartzFactory singletonQuartzFactory) {
		this.singletonQuartzFactory = singletonQuartzFactory;
	}

	public JobProperties getJobProperties() {
		return jobProperties;
	}

	public void setJobProperties(JobProperties jobProperties) {
		this.jobProperties = jobProperties;
	}
	
	

}
