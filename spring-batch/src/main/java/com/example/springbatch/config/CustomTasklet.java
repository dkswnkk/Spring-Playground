package com.example.springbatch.config;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Map;

public class CustomTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("========================");
        System.out.println(" >> step1 was executed");

        JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
        jobParameters.getString("name");
        jobParameters.getLong("seq");
        jobParameters.getDouble("double");
        jobParameters.getDate("date");


        Map<String, Object> jobParameters2 = chunkContext.getStepContext().getJobParameters();

        System.out.println("========================");
        return RepeatStatus.FINISHED;
    }
}
