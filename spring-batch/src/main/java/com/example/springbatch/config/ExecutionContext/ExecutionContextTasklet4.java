package com.example.springbatch.config.ExecutionContext;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionContextTasklet4 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        System.out.println("name : " + chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("name"));

        System.out.println("step4 was executed");
        return RepeatStatus.FINISHED;
    }

}

