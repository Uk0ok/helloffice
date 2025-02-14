package com.kh.helloffice.workflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.helloffice.hr.service.HrService;
import com.kh.helloffice.member.entity.MemberDto;
import com.kh.helloffice.workflow.entity.AllCusDto;
import com.kh.helloffice.workflow.entity.TagDto;
import com.kh.helloffice.workflow.entity.WfFormDto;
import com.kh.helloffice.workflow.service.WorkflowService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("workflow")
public class WorkflowController {
	
	@Autowired
	private WorkflowService service;
	
	@Autowired
	private HrService hrService;
	
	//내문서함
	@GetMapping("")
	public String myDoc() {
		return "workflow/myDoc";
	}
	
	//모든 문서함
	@GetMapping("/allWf")
	public String allDoc() {
		return "workflow/allDoc";
	}
	
	//양식
//	@GetMapping("/wfForm")
//	public String wfForm() {
//		return "workflow/wfForm";
//	}
	
	
	//태그 리스트 보여주기 && 워크플로우 양식 보여주기
	@GetMapping("/wfForm")
	public String getTagAndForm(Model model) throws Exception{
		List<TagDto> tagList = service.selectTagList();
		model.addAttribute("tagList", tagList);
		List<WfFormDto> wfFormList = service.selectWfFormList();
		model.addAttribute("wfFormList", wfFormList);
//		log.info(wfFormList.toString());
		for(WfFormDto f : wfFormList) {
			System.out.println(f);	
		}
		return "workflow/wfForm";
	}
	
	//태그 별로 양식 보여주기
	@GetMapping("/wfForm/getFormTag")
	@ResponseBody
	public List<WfFormDto> getFormByTag(Model model, String tagNo) throws Exception{
//		List<TagDto> tagList = service.selectTagList();
//		model.addAttribute("tagList", tagList);
		List<WfFormDto> wfFormList;
		if("0".equals(tagNo)) {
			wfFormList = service.selectWfFormList();
		} else {
			wfFormList = service.selectFormByTag(tagNo);
		}
		model.addAttribute("wfFormList", wfFormList);
//		log.info(tagNo);
//		log.info(wfFormList.toString());
		return wfFormList;
	}
	
	//각 양식 상세 조회
	@GetMapping("/wfForm/getEachForm")
	@ResponseBody
	public List<WfFormDto> getEachForm(Model model, String formName) throws Exception{
		List<WfFormDto> wfEachForm = service.selectEachForm(formName);
		System.out.println(wfEachForm);
		model.addAttribute("wfEachForm", wfEachForm);
		return wfEachForm;
	}
	
	//각 양식의 승인단계 조회
	@GetMapping("/wfForm/getEachStep")
	@ResponseBody
	public List<WfFormDto> getEachStep(Model model, String formName) throws Exception{
		List<WfFormDto> wfEachStep = service.selectEachStep(formName);
		System.out.println(wfEachStep);
		model.addAttribute("wfEachStep", wfEachStep);
		return wfEachStep;
	}
	
	//승인대상을 선택하기 위해 사원 전체 조회
	@GetMapping("/wfForm/getHrList")
	@ResponseBody
	public List<MemberDto> getHrList(Model model) throws Exception{
		List<MemberDto> memList = hrService.getTeamList();
		System.out.println(memList);
		model.addAttribute("memList", memList);
		return memList;
	}
	
		
	
	//양식 작성
	@PostMapping("/wfForm/addWfForm")
	@ResponseBody
	public String addWfForm(WfFormDto wfFormDto, @RequestBody HashMap<String, Object> params) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//일반 파라미터는 map에 그대로
		map.put("formName", params.get("formName"));
		map.put("formDetail", params.get("formDetail"));
		map.put("tagNo", params.get("tagNo"));
		map.put("conDb", params.get("conDb"));
//		map.put("boolFile", params.get("cusFile"));
		
		//map 파라미터
//		HashMap<String, Object> fileMap = new HashMap<String, Object>();
//		fileMap.put("cusFile", params.get("cusFile"));
		Map<String, Object> cusFile = (Map<String, Object>) params.get("cusFile");
//		fileMap.put("cusFile", params.get("cusFile"));
		map.put("cusFile", cusFile);
		
		//배열 파라미터는 list에 put하고 그 list를 map에 put
		List<Map<String, Object>> acList = (List<Map<String, Object>>) params.get("objArr");
		map.put("acList", acList);
		
		List<Map<String, Object>> appList = (List<Map<String, Object>>) params.get("approveArr");
		map.put("appList", appList);
				
		map.forEach((k, v)-> {
			System.out.println(k+" : " +v);
		});
		System.out.println("===============");
		System.out.println(map);
		System.out.println(cusFile);
		System.out.println(acList);
		System.out.println(appList);
		
		//미친짓의 시작이다. 폼/커스텀 따로 넣자
		//폼
			
		int resultForm = service.insertForm(map);
		System.out.println("resultForm: " + resultForm);
		if(resultForm>0) {
			if(acList.isEmpty() == true) {
				if((String)map.get("conDb") != null || !"".equals((String)map.get("conDb"))) {
					int resultCon = service.insertCon(map);
					System.out.println("//////////resultCon: "+ resultCon);
				}
			}else {
				int resultCus = service.insertCus(map);				
				System.out.println("******************"+resultCus);				
			}
//			if("1".equals(cusFile.get("beFile_"))) {
//				System.out.println("beFile_은 1임~~~~~~~~~~~~");
//				int resultFile = service.insertFile(cusFile);
//				System.out.println("resultFile: "+ resultFile);
//			}				
//			if(acList.isEmpty() != true) {
//			}
			if(appList.isEmpty() != true) {
				int resultApp = service.insertApp(map);
				System.out.println("=-=-=-=-=-=-"+resultApp);
			}
			System.out.println("폼 입력 성공 :: ");
			return "폼 입력 성공 :: ";
		} else {
			return "폼 입력 자체 실패";
		}
		
	}
	
	//양식 삭제
	@PostMapping("/wfForm/deleteWfForm")
	@ResponseBody
	public String deleteWfForm(@RequestParam("formName") String formName) throws Exception{
//		String formName = req.getParameter("formName");
		log.info(formName);
		int result = service.deleteForm(formName);
		System.out.println(result);
		if(result>0) {
			return "delete 폼 success";
		}else {
			return "delete 폼 error";
		}
	}
	

	
	//태그 이름 중복 체크
	@PostMapping("/wfForm/tagDupCheck")
	@ResponseBody
	public int dupCheckTag(@RequestParam("tagName") String tagName) throws Exception{
//		log.info("입력한 태그이름 :: "+tagName);
		int result = service.countTagName(tagName);
		return result;
	}
	  
	//태그 작성
	@PostMapping("/wfForm/addTag")
	@ResponseBody
	public String addTag(TagDto tagDto, @RequestParam("tagName") String tagName) throws Exception { 
		int countResult = dupCheckTag(tagName);
		if(countResult>0) {
			return "fail by dupCheck";
		}else {
			int result = service.insertTag(tagDto); 
			if(result>0) { 
				return "insert success :: "+tagDto;
			} else {
				return "insert error"; 
			}			
		}
	}
	
	//태그 삭제
	@PostMapping("/wfForm/deleteTag")
	@ResponseBody
	public String deleteTag(HttpServletRequest req) throws Exception {
		String tagName = req.getParameter("tagName");
		int result = service.deleteTag(tagName);
		if(result>0) {
			return "delete success";
		}else {
			return "delete error";
		}
	}
	
	//태그 수정
	@PostMapping("/wfForm/updateTag")
	@ResponseBody
	public String updateTag(@RequestBody TagDto tagDto) throws Exception{
//		System.out.println("받은 태그이름 데이터 :: "+tagDto.getTagName());
//		System.out.println("수정할 태그이름 :: "+tagDto.getToChange());
		int countResult = dupCheckTag(tagDto.getToChange());
		log.info(tagDto.toString());
		if(countResult>0) {
			return "fail by dupCheck";
		}else {
			int result = service.updateTag(tagDto);
			System.out.println(result);
			if(result>0) {
				return "update success";
			}else {
				return "update error";
			}
		}
	}
	
	 
	
}
