package com.ict.teamProject.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.teamProject.security.util.JWTTokens;
import com.ict.teamProject.member.service.MemberDetailService;
import com.ict.teamProject.member.service.MemberDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class MemberDtailController {

	@Autowired
	private MemberDetailService service;
	

	
	

	
	

	// 로그인 후 user권한으로 쿠키에있는 token전달
	@PostMapping("/user/getToken")
	@CrossOrigin(origins = "http://localhost:3333/")
	public String getToken(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                //System.out.println("쿠키 값: " + cookieValue);
	                log.info(cookieValue);
	                return cookieValue;
	                
	            }
	        }
	    }
	    
	    System.out.println("쿠키를 찾을+ 수 없습니다.");
		return "NoCookie";
	}
	
	// 토큰으로 이름(닉네임),프로필이미지 전달
	
	  @GetMapping("/user/getMemberInfo")
	    @CrossOrigin(origins = "http://localhost:3333")
	public MemberDto getMemberInfo(HttpServletRequest request) {
		
		String token = null;
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	                System.out.println("Token: " + token);
	                
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(token);
	    String id = (String)username.get("username");
	    
	    System.out.println("id : "+id);
	    
		MemberDto userInfo = service.findByMemberInfo(id);
		
		System.out.println("dto확인 : "+userInfo.getAuthority());
		System.out.println("dto확인 : "+userInfo.getId());
		System.out.println("dto확인 : "+userInfo.getProfileimage());
		
		 System.out.println("Member info: " + userInfo);
		return userInfo;
		 
	}
	
	  @GetMapping("/isSocialLogin")
	    @CrossOrigin(origins = "http://localhost:3333")
	public String isSocialLogin(HttpServletRequest request) {
		
		String token = null;
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	                
	            }
	        }
	    }
	    
		return token;
	}
}
