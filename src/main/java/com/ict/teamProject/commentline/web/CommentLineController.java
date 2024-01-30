//package com.ict.teamProject.commentline.web;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import org.apache.ibatis.annotations.Mapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.HttpSessionRequiredException;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.SessionAttributes;
//
//import com.ict.teamProject.bbs.service.BBSDto;
//import com.ict.teamProject.bbs.service.BBSService;
//import com.ict.teamProject.files.service.FilesDto;
//
//
//@Controller
//@RequestMapping("/commentline")
//@CrossOrigin(origins = "http://localhost:3333")
//public class CommentLineController {
//	
//	//서비스 주입
//	@Autowired
//	private BBSService<BBSDto> service;
//	
//	//입력처리]
//	@PostMapping("/Write.do")
//	@ResponseBody
//	public int writeOk(//Authentication auth,
//			@RequestParam Map map) {
//		
//		//서비스 호출
//		//스프링 씨큐리티 적용시 인증(로그인)되었다면 Authentication객체에 로그인에 관련된 정보가 전달됨
//		//로그인이 안되어 있으면 auth는 null
//		//System.out.println("[Authentication객체]");
//		//System.out.println("auth"+auth);
//		
//		//UserDetails principal = (UserDetails)auth.getPrincipal();
//		//System.out.println("아이디:"+principal.getUsername());
//		//System.out.println("비밀번호:"+principal.getPassword());
//		//System.out.println(principal.getUsername()+"의 권한들]");
//		//Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) principal.getAuthorities();
//		//String authorties = authorities.stream().map(authority -> authority.toString()).collect(Collectors.joining(","));
//		//System.out.println(authorties);
//		//map.put("id", principal.getUsername());
//		System.out.println(map.get("id"));
//		System.out.println(map.get("content"));
//		System.out.println(map.get("hashTag"));
//		System.out.println(map.get("type"));
//		System.out.println(map.get("disclosureYN"));
//		System.out.println(map.get("file[0]"));
//		System.out.println(map.get("name"));
//		
//		
//		int affected= service.insert(map);
//		if(affected==0) {//입력 실패
//			return affected;
//		}
//		return affected;
//	}/////
//	
//	//상세보기]
//	@RequestMapping(value="/View.do",method = {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public String view(@RequestParam Map map,Model model) {
//		System.out.println("상세보기의 NO:"+map.get("no"));
//		//서비스 호출
//		BBSDto record= service.selectOne(map);
//		//줄바꿈
//		record.setContent(record.getContent().replace("\r\n", "<br/>"));
//		//데이타 저장
//		model.addAttribute("record", record);
//		//뷰정보 반환
//		return "onememo09/bbs/View.ict";
//	}///////////////////
//	
//	@GetMapping("/Edit.do")
//	public String edit(@RequestParam Map map,Model model) {
//		//서비스 호출	
//		BBSDto record= service.selectOne(map);
//		//데이타 저장
//		model.addAttribute("record", record);
//		//뷰정보 반환
//		return "onememo09/bbs/Edit.ict";
//	}
//	
//	@PostMapping("/Edit.do")
//	public String editOk(Model model,BBSDto dto, FilesDto files) {
//	
//		//서비스 호출		
//		int affected=service.update(dto,files);
//		if(affected==0) {//수정 실패
//			model.addAttribute("UPDATE_ERROR", "수정 오류 입니다");
//			//뷰정보 반환
//			return "onememo09/bbs/Edit.ict";
//		}
//		//뷰정보 반환		
//		return "forward:/onememo/bbs/View.do";
//	}/////
//	
//	/*
//	@GetMapping("/{no}/Delete.do")
//	public String delete(@PathVariable String bno,Model model) {
//		//서비스 호출
//		BBSDto dto = BBSDto.builder().bno(bno).build();
//		int affected=service.delete(dto);
//		if(affected == -1) {
//			return "forward:/onememo/bbs/View.do?no="+no;
//		}
//		//뷰정보 반환]-목록을 처리하는 컨트롤러로 이동
//		return "forward:/onememo/bbs/List.do";
//	}
//	*/
//}

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ict.teamProject.commentline.service.CommentLineDto;
import com.ict.teamProject.commentline.service.CommentLineService;
import com.ict.teamProject.files.service.FilesDto;


@Controller
@RequestMapping("/commentline")
@CrossOrigin(origins = "http://localhost:3333")
public class CommentLineController {
	
	//서비스 주입
	@Autowired
	private CommentLineService<CommentLineDto> service;
	
	//상세보기]
	@RequestMapping(value="/View.do",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List view(@RequestParam Map map) {
		System.out.println("1번 동작");
		List<CommentLineDto> records = service.selectAll(map);
		System.out.println("2번 동작");
		System.out.println(records);
		return records;
	}///////////////////	
}


