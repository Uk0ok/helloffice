package com.kh.helloffice.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.helloffice.member.dao.MemberDao;
import com.kh.helloffice.member.entity.MemberDto;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDao dao;

	@Override
	public MemberDto login(MemberDto dto) {
		
		// db���� ȸ�� ���� ��ȸ
		MemberDto dbUser = dao.getMember(dto);
		
		// ��� �³� üũ
		dbUser.getUserPwd();
		
		// ��� ������ ��� ����
		
		// Ʋ���� null ����
		
		return null;
	}

}
