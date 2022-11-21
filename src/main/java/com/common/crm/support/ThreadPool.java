package com.common.crm.support;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@Component
public class ThreadPool {
	@Value("${pool_count}")
	private int pool_count;
	@Bean
	public ExecutorService executorService(){
		ExecutorService executorService = Executors.newFixedThreadPool(pool_count);
		return executorService;
	};
}
