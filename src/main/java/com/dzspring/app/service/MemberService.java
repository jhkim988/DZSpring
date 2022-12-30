package com.dzspring.app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Member;

@Service("memerService")
public interface MemberService {
	Optional<Member> login(Member loginInfo);
}
