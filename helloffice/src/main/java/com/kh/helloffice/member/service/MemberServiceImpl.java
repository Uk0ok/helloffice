package com.kh.helloffice.member.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.helloffice.member.dao.MemberDao;
import com.kh.helloffice.member.entity.MemberDto;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDao dao;
	
	@Autowired
	private PasswordEncoder pe;

	@Override
	public MemberDto login(MemberDto dto) throws Exception {
		
		MemberDto dbEmp = dao.getMember(dto);
		
		if(pe.matches(dto.getEmpPwd(), dbEmp.getEmpPwd())) {
			return dbEmp;
		} else {
			return null;
		}
	}

	@Override
	public int emailCheck(String email) throws Exception {
		
		return dao.emailCheck(email);
	}

	@Override
	public int join(MemberDto dto) throws Exception {
		
		int no = dao.getMemberSeq();
		dto.setEmpNo(no);
		dto.setEmpPwd(pe.encode(dto.getEmpPwd()));
		int result = dao.insertMember(dto);
		
		// 서버에 파일 업로드
		MultipartFile f = dto.getF();
				
		// 파일이 있는지?
		if(!f.isEmpty()) {
			// 변경된 이름
			String changeName = System.currentTimeMillis() + "_" + f.getOriginalFilename();
			dto.setChangeName(changeName);
			
			// 파일을 서버에 저장
			File file = new File("C:/dev/upload/imgs/profile" + dto.getChangeName());
			f.transferTo(file);	
			
			// db에 insert
			dao.insertProfile(dto);
		}		
		return result;
	}

}
