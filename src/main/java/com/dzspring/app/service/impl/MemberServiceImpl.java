package com.dzspring.app.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;
import com.dzspring.app.service.MemberService;
import com.dzspring.app.service.admin_member_search.MemberSearchCommand;
import com.dzspring.app.service.member_findid.FindIdCommand;
import com.dzspring.app.service.member_has_member.HasMemberCommand;

@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberSearchCommand memberSearchCommand;

	@Autowired
	private FindIdCommand findIdCommand;
	
	@Autowired
	private HasMemberCommand hasMemberCommand;
	
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
	public boolean hasMember(String type, String value) {
		if (!hasMemberCommand.hasType(type)) throw new UnsupportedOperationException();
		return hasMemberCommand.invoke(type, value) != null;
	}

	@Override
	public Optional<Member> findId(String method, String value) {
		if (!findIdCommand.hasMethod(method)) throw new UnsupportedOperationException();
		return findIdCommand.invoke(method, value);
	}

	@Override
	public boolean initPwd(String id) {
		Member member = memberRepository.findOneById(id);
		if (member == null)	return false;
		
		Map<String, Object> tmp = memberRepository.getTmpPwd(id);
		String tmpPwd = (String) tmp.get("tmpPwd");
		Timestamp createdAt = (Timestamp) tmp.get("createdAt");
		Timestamp before5min = Timestamp.valueOf(LocalDateTime.now().minusMinutes(5));
		if (tmpPwd == null || createdAt == null || createdAt.before(before5min))	return false;
		
		member.setPwd(tmpPwd);
		return memberRepository.update(member) == 1;
	}
	
	@Override
	public List<Member> list(Map<String, String> map) {
		if (!memberSearchCommand.hasMethod(map.get("method"))) throw new UnsupportedOperationException();
		return memberSearchCommand.invoke(map);
	}
}
