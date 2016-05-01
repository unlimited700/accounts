package com.apnavaidya.treasure.accounts.v1.service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apnavaidya.treasure.accounts.dto.UserLoginRequest;
import com.apnavaidya.treasure.accounts.dto.UserLoginResponse;
import com.apnavaidya.treasure.accounts.dto.UserSignUpRequest;
import com.apnavaidya.treasure.accounts.dto.UserSignUpResponse;
import com.apnavaidya.treasure.accounts.model.User;
import com.apnavaidya.treasure.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private DateTimeService dateService;

	public UserSignUpResponse signUp(UserSignUpRequest userSignUpRequest) {
		UserSignUpResponse response = new UserSignUpResponse();

		if (userSignUpRequest == null || checkForValidityOfEmail(userSignUpRequest.getEmail())) {
			response.setMessage("Invalid input for SignUp");
			response.setResponseCode(500);
			return response;
		}

		/*
		 * Check for duplicate
		 */
		LOG.info("checking the user account exist for email:{0}", userSignUpRequest.getEmail());

		User user = null;
		user = userDao.findByEmail(userSignUpRequest.getEmail());

		if (null != user) {
			response.setMessage("User already exist in system");
			response.setResponseCode(510);
			return response;
		}

		/*
		 * Going to save user profile in database
		 */
		LOG.info("Creating the user account for email:{0}", userSignUpRequest.getEmail());
		user = new User();
		user.setName(userSignUpRequest.getName());
		user.setAge(userSignUpRequest.getAge());
		user.setEmail(userSignUpRequest.getEmail());
		user.setPassword(userSignUpRequest.getPassword());
		user.setPhone(userSignUpRequest.getPhone());
		user.setSex(userSignUpRequest.getSex());
		user.setCreatedAt(dateService.getCurrentTime("IST"));
		userDao.save(user);

		response.setAge(user.getAge());
		response.setEmail(user.getEmail());
		response.setName(user.getName());
		response.setPhone(user.getPhone());
		response.setSex(user.getSex());

		return response;
	}

	private boolean checkForValidityOfEmail(String email) {
		if (email == null || !email.contains("@")) {
			return true;
		}
		return false;
	}

	public UserLoginResponse login(UserLoginRequest userLoginRequest) {
		UserLoginResponse response = new UserLoginResponse();

		if (userLoginRequest == null || userLoginRequest.getUserName() == null
				|| userLoginRequest.getUserName().trim().isEmpty() || userLoginRequest.getPassword() == null
				|| userLoginRequest.getPassword().trim().isEmpty()) {
			response.setMessage("Invalid input for Login");
			response.setResponseCode(500);
			return response;
		}
		LOG.info("getting the user account for userName:{0}", userLoginRequest.getUserName());
		User user = userDao.findByEmail(userLoginRequest.getUserName());
		if (user == null) {
			response.setMessage("Account of User does not exist");
			response.setResponseCode(511);
			return response;
		}
		LOG.info("Checking the user passord for userName:{0}", userLoginRequest.getUserName());
		if (!user.getPassword().equals(userLoginRequest.getPassword())) {
			response.setMessage("Invalid Password");
			response.setResponseCode(512);
			return response;
		}

		String token = user.getToken();
		if (token == null) {
			LOG.info("Generating the user token for userName:{0}", userLoginRequest.getUserName());
			token = generatedLoginToken(userLoginRequest.getUserName());
			user.setToken(token);
			userDao.save(user);
		}

		response.setToken(token);
		response.setUserName(userLoginRequest.getUserName());
		response.setUserId(user.getId());

		return response;
	}

	private String generatedLoginToken(String userName) {
		String token = UUID.randomUUID().toString() + "-" + userName + "-" + Calendar.getInstance().getTimeInMillis();
		return token;

	}

	public String getToken(Long userId) {
		User user = null;
		if (userId != null) {
			user = userDao.findOne(userId);
		}
		if (user == null) {
			LOG.error("User does not exist for userId:{0}", userId);
			return null;
		}
		return user.getToken();
	}
}
