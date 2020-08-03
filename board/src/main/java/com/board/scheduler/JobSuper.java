package com.board.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class JobSuper extends QuartzJobBean{

    private ApplicationContext ctx;
    
    @Override
    public void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        ctx = (ApplicationContext)jobexecutioncontext.getJobDetail().getJobDataMap().get("applicationContext");
  
        executeJob(jobexecutioncontext);
    }
 
    public Object getBean(String beanId) {
        return ctx.getBean(beanId);
    }
 
    public abstract void executeJob(JobExecutionContext jobexecutioncontext);

}
