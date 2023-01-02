package com.dzspring.app.repository;

import java.util.List;
import java.util.Map;

import com.dzspring.app.entity.Member;

public interface MemberRepository {
	Member findOneById(String id);
	Member findOneByPhone(String phone);
	Member findOneByEmail(String email);
	int insert(Member register);
	int update(Member updateInfo);
	int delete(String id);
	int deleteAll(List<String> ids);	
	Map<String, Object> getTmpPwd(String id);
	List<Member> findAllLimit10(Map<String, String> map);
	List<Member> findByEmailLimit10(Map<String, String> map);
	List<Member> findByIdLimit10(Map<String, String> map);
	List<Member> findByNameLimit10(Map<String, String> map);
	List<Member> findByPhoneLimit10(Map<String, String> map);
	List<Member> findByAuthorityLimit10(Map<String, String> map);
	List<Member> findByCreatedAtLimit10(Map<String, String> map);
	List<Member> findByUpdatedAtLimit10(Map<String, String> map);
	
	// USE TRIGGER
	int insertTmpTable(String id);
	int insertTmpTable(List<String> ids);
}
