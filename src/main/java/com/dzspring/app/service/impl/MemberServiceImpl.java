package com.dzspring.app.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;
import com.dzspring.app.service.MemberService;

@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	
	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	@Override
	public Optional<Member> login(Member loginInfo) {
		Member member = memberRepository.findOneById(loginInfo.getId());
		if (member == null || !member.getPwd().equals(loginInfo.getPwd())) {
			member = null;
		}
		return Optional.ofNullable(member);
	}

	@Override
	public boolean register(Member registerInfo) {
		return 1 == memberRepository.insert(registerInfo);
	}

	@Override
	public Optional<Member> update(Member updateInfo) {
		int update = memberRepository.update(updateInfo);
		if (update == 0)
			return Optional.ofNullable(null);
		return Optional.of(memberRepository.findOneById(updateInfo.getId()));
	}

	@Override
	public boolean validate(String id, String pwd) {
		Member member = memberRepository.findOneById(id);
		if (member == null)
			return false;
		return member.getPwd().equals(pwd);
	}

	@Override
	public boolean delete(String id) throws SQLException {
		int delete = memberRepository.delete(id);
		if (1 != delete)
			throw new SQLException();
		return true;
	}
	
	@Override
	public boolean delete(List<String> ids) throws SQLException {
		int deletes = memberRepository.deleteAll(ids);
		if (deletes <= 0) throw new SQLException();
		return true;
	}
	
	@Override
	public Optional<String> initPwdAuth(String id, String auth) {
		String tmpPwd = null;
		Member member = memberRepository.findOneById(id);
		if (member == null) return Optional.ofNullable(tmpPwd);
		
		Map<String, Object> tmp = memberRepository.getTmpPwd(id);
		tmpPwd = (String) tmp.get("tmpPwd");
		String authenticate = (String) tmp.get("authenticate");
		Timestamp createdAt = (Timestamp) tmp.get("createdAt");
		Timestamp before5min = Timestamp.valueOf(LocalDateTime.now().minusMinutes(5));
		if (tmpPwd == null || createdAt == null || authenticate == null || !authenticate.equals(auth) || createdAt.before(before5min)) {
			return Optional.ofNullable(null);
		}
		member.setPwd(tmpPwd);
		memberRepository.update(member);
		return Optional.ofNullable(tmpPwd);
	}

	@Override
	public String generateInitPwd(Member member) {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		String initPwd = Integer.toHexString(rand.nextInt());
		String auth = Integer.toHexString(rand.nextInt());
		Map<String, String> initPwdInfo = new HashMap<>();
		initPwdInfo.put("id", member.getId());
		initPwdInfo.put("tmpPwd", initPwd);
		initPwdInfo.put("authenticate", auth);
		memberRepository.insertTmpPwdTable(initPwdInfo);
		return auth;
	}

	@Override
	public int deleteTmpPwd(String id) {
		return memberRepository.deleteTmpPwdTable(id);
	}

	@Override
	public Optional<Member> findOneById(String id) {
		return Optional.ofNullable(memberRepository.findOneById(id));
	}
}
