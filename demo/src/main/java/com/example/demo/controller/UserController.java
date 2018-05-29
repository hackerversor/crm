package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;

@Controller
public class UserController {

	@Autowired
	private UserMapper userMapper;

	@RequestMapping("/getUsers")
	public List<User> getUsers() {
		List<User> users = userMapper.getAll();
		return users;
	}

	@RequestMapping("/getUser")
	public User getUser(Long id) {
		User user = userMapper.getOne(id);
		return user;
	}

	@RequestMapping("/add")
	public void save(User user) {
		userMapper.insert(user);
	}

	@RequestMapping(value = "update")
	public void update(User user) {
		userMapper.update(user);
	}

	@RequestMapping(value = "/delete/{id}")
	public void delete(@PathVariable("id") Long id) {
		userMapper.delete(id);
	}

}