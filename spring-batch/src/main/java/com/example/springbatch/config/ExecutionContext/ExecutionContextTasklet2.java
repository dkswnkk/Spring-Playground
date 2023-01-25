package com.example.springbatch.config.ExecutionContext;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ExecutionContextTasklet2 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("step2 was executed");

        ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        ExecutionContext stepExecutionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();

        System.out.println("jobName : " + jobExecutionContext.get("jobName"));
        System.out.println("stepName : " + stepExecutionContext.get("stepName"));       // step 끼리는 공유가 되지 않기 때문에 볼 수 없어야 정상이다.


        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

        if (Objects.isNull(stepName)) {
            stepExecutionContext.put("stepName", stepName);
        }

        return RepeatStatus.FINISHED;
    }

}

