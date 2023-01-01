package com.dzspring.app.repository;

import java.util.Map;

import com.dzspring.app.entity.Member;

public interface MemberRepository {
	Member findOneById(String id);
	Member findOneByPhone(String phone);
	Member findOneByEmail(String email);
	int insert(Member register);
	int update(Member updateInfo);
	int delete(String id);
	int insertTmpTable(String id);
	Map<String, Object> getTmpPwd(String id);
}
