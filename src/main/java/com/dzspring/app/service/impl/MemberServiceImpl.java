package com.dzspring.app.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;
import com.dzspring.app.service.MemberService;
import com.dzspring.app.service.admin_member_search.MemberSearchCommand;
import com.dzspring.app.service.admin_member_search.SearchCommandMap;

@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberSearchCommand memberSearchCommand;

	@Override
	public Optional<Member> login(Member loginInfo) {
		Member member = memberRepository.findOneById(loginInfo.getId());
		if (member == null || member.getPwd().equals(loginInfo.getPwd())) {
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
	@Transactional
	public boolean delete(String id) throws SQLException {
		int move = memberRepository.insertTmpTable(id);
		int delete = memberRepository.delete(id);
		if (1 != move || 1 != delete)
			throw new SQLException();
		return true;
	}
	
	@Override
	@Transactional
	public boolean delete(List<String> ids) throws SQLException {
		int moves = memberRepository.insertTmpTable(ids);
		int deletes = memberRepository.delete(ids);
		if (moves != deletes) throw new SQLException();
		return true;
	}

	@Override
	public boolean hasMember(String type, String value) {
		Member result = null;
		if ("id".equals(type))
			memberRepository.findOneById(value);
		else if ("phone".equals(type))
			memberRepository.findOneByPhone(value);
		else if ("email".equals(type))
			memberRepository.findOneByEmail(value);
		else
			throw new UnsupportedOperationException();
		return result != null;
	}

	@Override
	public Optional<Member> findId(String method, String value) {
		Member result = null;
		if ("phone".equals(method))
			result = memberRepository.findOneByPhone(value);
		else if ("email".equals(method))
			result = memberRepository.findOneByEmail(value);
		else
			throw new UnsupportedOperationException();
		return Optional.ofNullable(result);
	}

	@Override
	public boolean initPwd(String id) {
		Member member = memberRepository.findOneById(id);
		if (member != null)	return false;
		
		Map<String, Object> tmp = memberRepository.getTmpPwd(id);
		String tmpPwd = (String) tmp.get("tmpPwd");
		Timestamp createdAt = (Timestamp) tmp.get("createdAt");
		Timestamp before5min = Timestamp.valueOf(LocalDateTime.now().minusMinutes(10));
		if (tmpPwd == null || createdAt == null || createdAt.after(before5min))	return false;

		return memberRepository.update(member) == 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Member> list(String method, String value, String last) {
		if (SearchCommandMap.MAP.getMap().containsKey(method)) throw new UnsupportedOperationException();
		try {
			return (List<Member>) SearchCommandMap.MAP.getMap().get(method).invoke(memberSearchCommand, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
