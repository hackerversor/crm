package com.common.crm.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	@Autowired
	private RedisTemplate redisTemplate;
//	@Autowired
//	private CacheService cacheService;
//	@Bean
//	public RedisMessageListenerContainer messageListenerContainer(RedisConnectionFactory connectionFactory,
//            MessageListenerAdapter listenerAdapter){
//		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		String weixin_notice_redis_channel =  cacheService.getCacheParamValue("weixin_notice_redis_channel");
//		container.addMessageListener(listenerAdapter, new PatternTopic(weixin_notice_redis_channel));
//		//container.addMessageListener(listenerAdapter, new PatternTopic("test_channel"));
//		return container;
//	}
//	@Bean
//	public MessageListenerAdapter messageListenerAdapter(RedisMessage redisMessage){
//		return new MessageListenerAdapter(redisMessage, "receiveMessage");
//		return new MessageListenerAdapter(redisMessage, "receiveMessage");
//	}
	@Bean
	public RedisTemplate redisTemplate(){
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}
	
	
}

 