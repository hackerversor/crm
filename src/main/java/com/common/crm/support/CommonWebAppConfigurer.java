package com.common.crm.support;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.druid.pool.DruidDataSource;
@Configuration
public class CommonWebAppConfigurer extends WebMvcConfigurerAdapter{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DruidConfig druidConfig;
	
	@Bean
	public SessionInterceptor interceptor(){
		return new SessionInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor()).addPathPatterns("/**")
		.excludePathPatterns("/crm/cm_user/login","/crm/stat/queryForQR","/crm/cm_user/pwd_modify","/crm/test/**","/crm/stand_book/stat/**");
		super.addInterceptors(registry);
	 }
	
	@Bean 
	public DataSource dataSource() { 
		DruidDataSource datasource = new DruidDataSource();
		datasource.setName(druidConfig.getName());
		datasource.setUrl(druidConfig.getJdbcUrl());  
		datasource.setUsername(druidConfig.getUsername());  
		datasource.setPassword(druidConfig.getPassword());  
		datasource.setDriverClassName(druidConfig.getDriverClassName());  
		datasource.setInitialSize(druidConfig.getInitialSize());  
		datasource.setMaxActive(druidConfig.getMaxActive());
		datasource.setMinIdle(druidConfig.getMinIdle());  
		datasource.setMaxWait(druidConfig.getMaxWait());
		datasource.setValidationQuery(druidConfig.getValidationQuery());
		datasource.setTestOnBorrow(druidConfig.getTestOnBorrow());
		datasource.setTestOnReturn(druidConfig.getTestOnReturn());
		datasource.setTestWhileIdle(druidConfig.getTestWhileIdle());
		datasource.setTimeBetweenEvictionRunsMillis(druidConfig.getTimeBetweenEvictionRunsMillis());
		datasource.setRemoveAbandoned(true);
		datasource.setRemoveAbandonedTimeout(180);
		datasource.setLogAbandoned(true);
		datasource.setConnectionProperties(druidConfig.getConnectionProperties());
		try {  
	   		datasource.setFilters(druidConfig.getFilters());  
		} catch (SQLException e) {  
	   		logger.info(""+e);
		}  
		return datasource;
	}
	
}
