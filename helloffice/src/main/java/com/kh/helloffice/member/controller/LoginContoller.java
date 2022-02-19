package com.kh.helloffice.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.helloffice.member.entity.MemberDto;

@Controller
@RequestMapping("member")
public class LoginContoller {
	
	// �α��� ȭ�� �����ֱ�
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	// �α��� ���� ó��
	@PostMapping("login")
	public String login(MemberDto dto) {
		return "member/login";
	}
}
