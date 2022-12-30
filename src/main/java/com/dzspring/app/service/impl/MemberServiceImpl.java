package com.dzspring.app.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Member;
import com.dzspring.app.service.MemberService;

@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService {

	@Override
	public Optional<Member> login(Member loginInfo) {
		return Optional.of(null);
	}

}
