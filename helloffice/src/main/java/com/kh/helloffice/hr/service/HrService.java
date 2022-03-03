package com.kh.helloffice.hr.service;

import java.util.List;

import com.kh.helloffice.hr.entity.PageVo;
import com.kh.helloffice.member.entity.MemberDto;
import com.kh.helloffice.reservation.entity.AssetDto;

public interface HrService {

	List<MemberDto> getTeamList() throws Exception;

	List<MemberDto> getRepTeamList() throws Exception;

	List<MemberDto> getMarkTeamList() throws Exception;

	List<MemberDto> getSalesTeamList() throws Exception;

	List<MemberDto> getDesignTeamList() throws Exception;


}
