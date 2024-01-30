package com.ict.teamProject.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ict.teamProject.member.service.MemberDto;
import com.ict.teamProject.member.service.MemberService;

import org.springframework.ui.Model;



@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class MemberController {

	private MemberService service;
	
	public MemberController(MemberService service) {
		this.service=service;
		System.out.println("MemberController 생성자");
	}
	

	@RequestMapping(value = "/member_info/View.do", method = {RequestMethod.GET,RequestMethod.POST})
	public MemberDto view(@RequestParam String id){
		MemberDto memberdata = service.selectdata(id);
		return memberdata;
	}
	
	@RequestMapping(value = "/member_info/Edit.do", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
	public int edit(@RequestParam String id, String colname, String newcolval){
		int affected = service.updatedata(id, colname, newcolval);
		return affected;
	}
	
	@RequestMapping(value = "/usercheck", method = {RequestMethod.GET,RequestMethod.POST})
	public int usercheck(@RequestParam String id, String pwd) {
		System.out.println("chk");
		return service.logincheck(id, pwd);
	}
	
}
