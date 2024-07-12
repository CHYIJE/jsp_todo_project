package com.tenco.model;

import java.util.List;

public interface UserDAO {

	// 회원가입 기능
	int addUser(UserDTO userDTO);

	// 사용자 정보 조회
	UserDTO getUserById(int id);
	UserDTO getUserByUsername(String username);
	
	// 사용자 정보 수정
	int updateUser(UserDTO user, int principalId); // 권한 ( 마이 정보 나만) - 인증 (세션ID)
	
	// 회원삭제
	int deleteUser(int id); // 인증 -- 세션
	
	// 관리자 기능
	// 전체 조회
	List<UserDTO> getAllUsers();

}
