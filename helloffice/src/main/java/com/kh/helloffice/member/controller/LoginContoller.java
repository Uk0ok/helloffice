package com.kh.helloffice.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.helloffice.member.entity.MemberDto;
import com.kh.helloffice.member.service.MemberService;

@Controller
@RequestMapping("member")
public class LoginContoller {
	
	@Autowired
	private MemberService service;
	
	// �α��� ȭ�� �����ֱ�
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	// �α��� ���� ó��
	@PostMapping("login")
	public String login(MemberDto dto) {
		
		MemberDto loginUser = service.login(dto);
		
		return "member/login";
	}
}
