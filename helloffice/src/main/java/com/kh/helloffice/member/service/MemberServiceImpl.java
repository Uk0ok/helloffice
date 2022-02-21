package com.kh.helloffice.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.helloffice.member.dao.MemberDao;
import com.kh.helloffice.member.entity.MemberDto;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDao dao;
	
	@Autowired
	private PasswordEncoder pe;

	@Override
	public MemberDto login(MemberDto dto) {
		
		// db���� ȸ�� ���� ��ȸ
		MemberDto dbUser = dao.getMember(dto);
		
		// ��� �³� üũ
		if(pe.matches(dto.getUserPwd(), dbUser.getUserPwd())) {
			// �α���
			return dbUser;
		} else {
			
			return null;
		}
	}

}
