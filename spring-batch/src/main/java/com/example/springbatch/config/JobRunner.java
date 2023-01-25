package com.example.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;


//@Component
public class JobRunner implements ApplicationRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /**
         * Paremeters의 값으로 올 수 있는 총 4가지 타입
         * - String, Long, Date, Double
         */
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user11")
                .addLong("seq", 2L)
                .addDate("date", new Date())
                .addDouble("double", 16.5)
                .toJobParameters();

        jobLauncher.run(job, jobParameters);

        /**
         * JAR를 실행시킬 때 parameter 인자 넘기기
         * java -jar spring-batch-0.0.1-SNAPSHOT.jar "name=user1" "seq(long)=2L" "date(date)=2023/01/19" "double(double)=16.5"
         */
    }
}
