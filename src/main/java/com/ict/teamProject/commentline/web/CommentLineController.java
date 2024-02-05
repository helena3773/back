
package com.ict.teamProject.commentline.web;

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

import com.fasterxml.jackson.databind.JsonMappingException;
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
	
	//댓글 입력]
	@PostMapping(value="/Write.do")
	@ResponseBody
	public int input(@RequestParam Map map) throws JsonMappingException{
		System.out.println("map값"+map);
		int affected = service.insert(map);
		System.out.println("2번 동작");
		System.out.println(affected);
		return affected;
	}///////////////////	
}