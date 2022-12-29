package com.dzspring.app.service;

import java.util.Optional;

import com.dzspring.app.entity.Member;

public interface MemberService {
	Optional<Member> login(Member loginInfo);
}
