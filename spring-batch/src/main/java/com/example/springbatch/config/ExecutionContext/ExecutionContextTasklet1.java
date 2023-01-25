package com.example.springbatch.config.ExecutionContext;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ExecutionContextTasklet1 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("step1 was executed");

        ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
        ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

        String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

        if (Objects.isNull(jobExecutionContext.get("jobName"))) {
            jobExecutionContext.put("jobName", jobName);
        }


        if (Objects.isNull(stepExecutionContext.get("stepName"))) {
            stepExecutionContext.put("stepName", stepName);
        }


        System.out.println("jobName : " + jobExecutionContext.get("jobName"));
        System.out.println("stepName : " + stepExecutionContext.get("stepName"));

        return RepeatStatus.FINISHED;
    }

}

