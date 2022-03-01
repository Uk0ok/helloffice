package com.kh.helloffice.workflow.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.helloffice.workflow.entity.TagDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class WorkflowDaoImpl implements WorkflowDao{

	@Autowired 
	private SqlSession sqlSession;
	
	@Override
	public int insertTag(TagDto tagDto) throws Exception {
		return sqlSession.insert("workflow.insertTag", tagDto);
	}

	@Override
	public List<TagDto> selectTagList() throws Exception {
//		List<TagDto> a = sqlSession.selectList("workflow.selectTagList");
//		log.info(a.toString());
		return sqlSession.selectList("workflow.selectTagList");
	}

	@Override
	public int deleteTag(String targetName) throws Exception {
		return sqlSession.delete("workflow.deleteTag", targetName);
	}

	@Override
	public TagDto selectTagOne(String targetName) throws Exception {
		return sqlSession.selectOne("workflow.selectTagOne", targetName);
	}

	@Override
	public int updateTag(TagDto tagDto) throws Exception {
		int s = sqlSession.update("workflow.updateTag", tagDto);
		log.info("dao임플 :: "+s);
		return s;
	}


}
