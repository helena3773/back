package com.ict.teamProject.bbs.web;

import java.util.Collection;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.bbs.service.BBSService;


@Controller
@RequestMapping("/bbs")
public class BBSController {
	
	//서비스 주입
	@Autowired
	private BBSService<BBSDto> service;
	
	//입력처리]
	@PostMapping("/Write.do")
	public String writeOk(Authentication auth,@RequestParam Map map,Model model) {
		
		//서비스 호출
		//스프링 씨큐리티 적용시 인증(로그인)되었다면 Authentication객체에 로그인에 관련된 정보가 전달됨
		//로그인이 안되어 있으면 auth는 null
		//System.out.println("[Authentication객체]");
		//System.out.println("auth"+auth);
		
		//UserDetails principal = (UserDetails)auth.getPrincipal();
		//System.out.println("아이디:"+principal.getUsername());
		//System.out.println("비밀번호:"+principal.getPassword());
		//System.out.println(principal.getUsername()+"의 권한들]");
		//Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) principal.getAuthorities();
		//String authorties = authorities.stream().map(authority -> authority.toString()).collect(Collectors.joining(","));
		//System.out.println(authorties);
		//map.put("id", principal.getUsername());
		
		int affected= service.insert(map);
		if(affected==0) {//입력 실패
			model.addAttribute("INPUT_ERROR", "입력 오류 입니다");
			//뷰정보 반환
			return "onememo09/bbs/Write.ict";
		}
		//뷰정보 반환		
		// /onememo/bbs/List.do로 뷰정보 반환시 접두어/접미어 붙는다
		//JSP file [/WEB-INF/views/onememo09/bbs/List.do.jsp] not found
		//forward:를 붙이면 접두어/접미어가 붙지 않는다 (forward: 디폴트)
		//redirect:는 리다이렉트 방식 이동
		return "forward:/onememo/bbs/List.do";
	}/////
	//상세보기]
	//GET/POST둘다 받아야 함으로 @RequestMapping사용
	@RequestMapping(value="/View.do",method = {RequestMethod.GET,RequestMethod.POST})
	public String view(//@ModelAttribute("id") String id,
			@RequestParam Map map,Model model) {
		
		System.out.println("상세보기의 NO:"+map.get("no"));
		//서비스 호출
		BBSDto record= service.selectOne(map);
		//줄바꿈
		record.setContent(record.getContent().replace("\r\n", "<br/>"));
		//데이타 저장
		model.addAttribute("record", record);
		//뷰정보 반환
		return "onememo09/bbs/View.ict";
	}///////////////////
	
	@GetMapping("/Edit.do")
	public String edit(//@ModelAttribute("id") String id,
			@RequestParam Map map,Model model) {
		//서비스 호출	
		BBSDto record= service.selectOne(map);
		//데이타 저장
		model.addAttribute("record", record);
		//뷰정보 반환
		return "onememo09/bbs/Edit.ict";
	}
	@PostMapping("/Edit.do")
	public String editOk(Model model,BBSDto dto) {
	
		//서비스 호출		
		int affected=service.update(dto);
		if(affected==0) {//수정 실패
			model.addAttribute("UPDATE_ERROR", "수정 오류 입니다");
			//뷰정보 반환
			return "onememo09/bbs/Edit.ict";
		}
		//뷰정보 반환		
		return "forward:/onememo/bbs/View.do";
	}/////
	@GetMapping("/{no}/Delete.do")
	public String delete(//@ModelAttribute("id") String id,
			@PathVariable String no,Model model) {
		//서비스 호출
		BBSDto dto = BBSDto.builder().no(no).build();
		int affected=service.delete(dto);
		if(affected == -1) {
			System.out.println("affected:"+affected);
			model.addAttribute("FAILURE", "댓글이 있어 삭제할 수 없어요");
			//뷰정보 반환		
			return "forward:/onememo/bbs/View.do?no="+no;
		}
		//뷰정보 반환]-목록을 처리하는 컨트롤러로 이동
		return "forward:/onememo/bbs/List.do";
	}
}
