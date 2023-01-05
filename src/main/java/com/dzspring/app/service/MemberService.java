package com.dzspring.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Member;

@Service("memerService")
public interface MemberService {
	Optional<Member> findOneById(String id);
	Optional<Member> login(Member loginInfo);
	boolean register(Member registerInfo);
	Optional<Member> update(Member updateInfo);
	boolean validate(String id, String pwd);
	boolean delete(String id) throws SQLException;
	boolean delete(List<String> ids) throws SQLException;
	boolean hasMember(String type, String value);
	Optional<Member> findMemberBy(String method, String name, String value);
	Optional<String> initPwdAuth(String id, String auth);
	String generateInitPwd(Member member);
	int deleteTmpPwd(String id);
}
