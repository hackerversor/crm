package com.example.demo.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.domain.User;
import com.example.demo.enums.UserSexEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

	@Autowired
	private UserMapper UserMapper;

	@Test
	public void testInsert() throws Exception {
		UserMapper.insert(new User("seven", "123456",UserSexEnum.男));
		UserMapper.insert(new User("eleven", "123456",UserSexEnum.男));
		UserMapper.insert(new User("zero", "123456",UserSexEnum.女));

		Assert.assertEquals(18, UserMapper.getAll().size());//比较实际数量18
	}

	@Test
	public void testQuery() throws Exception {
		List<User> users = UserMapper.getAll();
		if(users==null || users.size()==0){
			System.out.println("is null");
		}else{
			System.out.println(users.toString());
		}
	}
	
	
	@Test
	public void testUpdate() throws Exception {
		User user = UserMapper.getOne(33l);
		System.out.println(user.toString());
		user.setNickName("neo");
		UserMapper.update(user);
		Assert.assertTrue(("neo".equals(UserMapper.getOne(33l).getNickName())));
	}

}