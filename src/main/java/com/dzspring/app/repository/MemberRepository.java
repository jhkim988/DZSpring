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
	int delete(List<String> ids);
	int insertTmpTable(String id);
	int insertTmpTable(List<String> ids);
	Map<String, Object> getTmpPwd(String id);
	List<Member> findAllLimit(String value, String last);
	List<Member> findByCreatedAtLimit10(String value, String last);
	List<Member> findByEmailLimit10After(String value, String last);
	List<Member> findByIdLimit10(String value, String last);
	List<Member> findByNameLimit10(String value, String last);
	List<Member> findByPhoneLiit10(String value, String last);
	List<Member> findByUpdatedAtLimit10(String value, String last);
	List<Member> findByAuthorityAtLimit(String value, String last);
}
