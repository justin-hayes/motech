package org.motechproject.batch.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.batch.operations.JobOperator;
//import javax.batch.runtime.BatchRuntime;

import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;
import javax.batch.runtime.BatchRuntime;

import org.motechproject.batch.exception.ApplicationErrors;
import org.motechproject.batch.exception.BatchException;
import org.motechproject.batch.mds.BatchJob;
import org.motechproject.batch.mds.BatchJobParameters;
import org.motechproject.batch.mds.service.BatchJobMDSService;
import org.motechproject.batch.mds.service.BatchJobParameterMDSService;
import org.motechproject.batch.service.JobTriggerService;
import org.springframework.batch.core.jsr.launch.JsrJobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class to perform the trigger operation for all types of jobs
 * @author Naveen
 *
 */
@Service(value="jobTriggerService")
//@Transactional
public class JobTriggerServiceImpl implements JobTriggerService {
	
//	@Autowired
//	JobRepository jobRepo;
//	
//	@Autowired
//	private JobParametersRepository jobParameterRepo;
//
//	public JobParametersRepository getJobParameterRepo() {
//		return jobParameterRepo;
//	}
//
//	public void setJobParameterRepo(JobParametersRepository jobParameterRepo) {
//		this.jobParameterRepo = jobParameterRepo;
//	}
//
//	
//	public JobRepository getJobRepo() {
//		return jobRepo;
//	}
//
//	public void setJobRepo(JobRepository jobRepo) {
//		this.jobRepo = jobRepo;
//	}

	
	JobOperator jsrJobOperator;
	
	
	@Autowired
	public JobTriggerServiceImpl(BatchJobMDSService jobRepo,
			BatchJobParameterMDSService jobParameterRepo, JobOperator jsrJobOperator) {
		this.jobRepo = jobRepo;
		this.jobParameterRepo = jobParameterRepo;
		this.jsrJobOperator = jsrJobOperator;

	}

	private BatchJobParameterMDSService  jobParameterRepo;

	private BatchJobMDSService jobRepo;
	
	
	@Override
	public void triggerJob(String jobName, Date date) throws BatchException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		List<BatchJob> batchJobList = jobRepo.findByJobName(jobName);
		boolean jobExists = true;
		if(batchJobList == null || batchJobList.size() == 0) {
			jobExists = false;
		}
		if(jobExists == false)
			throw new BatchException(ApplicationErrors.JOB_NOT_FOUND);
		BatchJob batchJob = batchJobList.get(0);
		long batchJobId = (long)jobRepo.getDetachedField(batchJob, "id");
			
			List<BatchJobParameters> parametersList = jobParameterRepo.findByJobId((int)batchJobId);
			
			JobOperator jobOperator = BatchRuntime.getJobOperator();
			Properties jobParameters = new Properties();
			
			for(BatchJobParameters batchJobParameter : parametersList)
				{
					jobParameters.put(batchJobParameter.getParameterName(),batchJobParameter.getParameterValue());
				}
			      
			Long executionId = null;
			try{
				//TODO NAVEEN - NAME IS HARDCODED ?
				executionId = jsrJobOperator.start("logAnalysis", jobParameters);
			//executionId = jobOperator.start("logAnalysis", jobParameters);
			}catch(JobStartException | JobSecurityException e){
				
				throw new BatchException(ApplicationErrors.JOB_TRIGGER_FAILED, e.getCause());
			}
			
			Thread.currentThread().setContextClassLoader(classLoader);
			// TODO Implement the datetime
		
	}

	

}