package com.kh.helloffice.workflow.entity;

import lombok.Data;

@Data
public class WfFormDto {

	private int formNo;
	private String formName;
	private String formDetail;
	private int tagNo;
	private String tagName;
	
	private String formCon;
	private String formFile;
	private String cusType;
	private String cusLabel;
	private String cusReq;
	private int cusOrder;
	private String eachDb;

	
	private TagDto tagDto;
}
