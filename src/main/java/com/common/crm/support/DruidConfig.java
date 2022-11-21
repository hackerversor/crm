package com.common.crm.support;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@ConfigurationProperties(prefix="druid.db")
@PropertySource("classpath:${spring.profiles.active}/druid.db.properties")
@Component
public class DruidConfig {
	private String name;
	private String jdbcUrl;
	private String username;
	private String password;
	private String driverClassName;
	private Integer initialSize;
	private Integer maxActive;
	private Integer minIdle;
	private Long maxWait;
	private String poolPreparedStatements;
	private String maxOpenPreparedStatements;
	private String validationQuery;
	private Boolean testOnBorrow;
	private Boolean testOnReturn;
	private Boolean testWhileIdle;
	private Long timeBetweenEvictionRunsMillis;
	private String numTestsPerEvictionRun;
	private Long minEvictableIdleTimeMillis;
	private String connectionInitSqls;
	private String exceptionSorter;
	private String filters;
	private String proxyFilters;
	private String connectionProperties;


	public String getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getJdbcUrl() {
		return jdbcUrl;
	}


	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getDriverClassName() {
		return driverClassName;
	}


	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}


	public Integer getInitialSize() {
		return initialSize;
	}


	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}


	public Integer getMaxActive() {
		return maxActive;
	}


	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}


	public Integer getMinIdle() {
		return minIdle;
	}


	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}


	public Long getMaxWait() {
		return maxWait;
	}


	public void setMaxWait(Long maxWait) {
		this.maxWait = maxWait;
	}


	public String getPoolPreparedStatements() {
		return poolPreparedStatements;
	}


	public void setPoolPreparedStatements(String poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}


	public String getMaxOpenPreparedStatements() {
		return maxOpenPreparedStatements;
	}


	public void setMaxOpenPreparedStatements(String maxOpenPreparedStatements) {
		this.maxOpenPreparedStatements = maxOpenPreparedStatements;
	}


	public String getValidationQuery() {
		return validationQuery;
	}


	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}


	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}


	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}


	public Boolean getTestOnReturn() {
		return testOnReturn;
	}


	public void setTestOnReturn(Boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}


	public Boolean getTestWhileIdle() {
		return testWhileIdle;
	}


	public void setTestWhileIdle(Boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}


	public Long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}


	public void setTimeBetweenEvictionRunsMillis(Long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}


	public String getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}


	public void setNumTestsPerEvictionRun(String numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}


	public Long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}


	public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}


	public String getConnectionInitSqls() {
		return connectionInitSqls;
	}


	public void setConnectionInitSqls(String connectionInitSqls) {
		this.connectionInitSqls = connectionInitSqls;
	}


	public String getExceptionSorter() {
		return exceptionSorter;
	}


	public void setExceptionSorter(String exceptionSorter) {
		this.exceptionSorter = exceptionSorter;
	}


	public String getFilters() {
		return filters;
	}


	public void setFilters(String filters) {
		this.filters = filters;
	}


	public String getProxyFilters() {
		return proxyFilters;
	}


	public void setProxyFilters(String proxyFilters) {
		this.proxyFilters = proxyFilters;
	}

	@Override
	public String toString() {
		
		return JSONObject.toJSONString(this);
	}
	
	
}
