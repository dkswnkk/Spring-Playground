package com.example.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobInstanceConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
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
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("========================");
                    System.out.println(" >> step2 was executed");
                    System.out.println("========================");
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
