package com.momo.server.service;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momo.server.domain.Meet;
import com.momo.server.domain.User;
import com.momo.server.dto.request.LoginRequestDto;
import com.momo.server.repository.MeetRepository;
import com.momo.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MeetRepository meetRepository;

    // 로그인 메소드
    @Transactional
    public User login(LoginRequestDto loginRequestDto) {

	User userEntity = userRepository.isUserExist(loginRequestDto);
	if (userEntity == null) {// 유저 존재하지않음(신규유저)
	    createUser(loginRequestDto);
	    return null;
	} else {// 유저 존재(기존 유저)
	    return userEntity;
	}
    }

    // 유저 생성
    @Transactional
    public User createUser(LoginRequestDto loginRequestDto) {

	User userEntity = new User();

	BigInteger userid = BigInteger.valueOf(Integer.valueOf(Math.abs(loginRequestDto.hashCode())));

	userEntity.setUserId(userid);
	userEntity.setUsername(loginRequestDto.getUsername());
	userEntity.setCookieRemember(loginRequestDto.getRemember());
	userEntity.setMeetId(loginRequestDto.getMeetId());

	Meet meetEntity = meetRepository.findMeet(loginRequestDto.getMeetId());

	int dates = meetEntity.getDates().size();
	int timeslots = Integer.parseInt(meetEntity.getEnd().split(":")[0])
		- Integer.parseInt(meetEntity.getStart().split(":")[0]);
	int[][] userTimes = new int[timeslots * ((int) 60 / meetEntity.getGap())][dates];
	userEntity.setUserTimes(userTimes);

	userRepository.createUser(userEntity);

	return userEntity;
    }

    public void addUser(Meet meetEntity, User userEntity) {

	// userId 업데이트 연산
	ArrayList<BigInteger> userList = new ArrayList<BigInteger>();

	if (meetEntity.getUsers() == null) {
	    userList.add(userEntity.getUserId());
	} else {
	    userList = meetEntity.getUsers();
	    userList.add(userEntity.getUserId());
	}

	// username 업데이트 연산
	ArrayList<String> userNameList = new ArrayList<String>();

	if (meetEntity.getUsers() == null) {
	    userNameList.add(userEntity.getUsername());
	} else {
	    userNameList = meetEntity.getUserNames();
	    userNameList.add(userEntity.getUsername());
	}

	meetRepository.addUser(meetEntity, userList, userNameList);

    }

}
