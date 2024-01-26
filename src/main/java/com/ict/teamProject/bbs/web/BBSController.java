package com.ict.teamProject.bbs.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.bbs.service.BBSService;
import com.ict.teamProject.command.FileUtils;
import com.ict.teamProject.files.service.FilesDto;


@Controller
@RequestMapping("/bbs")
@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class BBSController {
	
	//서비스 주입
	@Autowired
	private BBSService<BBSDto> service;
	
	//입력처리]
	@PostMapping("/Write.do")
	@ResponseBody
	public int writeOk(//Authentication auth,
			@RequestParam Map map,@RequestParam(name="files", required=false) MultipartFile[] files, @RequestParam(name="ciu", required=false) String ciuJson) throws JsonMappingException, JsonProcessingException {
		
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
		System.out.println(map.get("id"));
		System.out.println(map.get("content"));
		System.out.println(map.get("hashTag"));
		System.out.println(map.get("type"));
		System.out.println(map.get("disclosureYN"));
		int affected = 0;
	    
		map= service.insert(map);
		if (ciuJson != null) {
			 // ciuJson을 파싱하여 List<Map<String, String>> 형태로 변환
		    ObjectMapper objectMapper = new ObjectMapper();
		    JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class);
		    List<Map<String, String>> ciu = objectMapper.readValue(ciuJson, type);
	
		    // ciu를 사용하여 필요한 처리 수행
		    for (Map<String, String> item : ciu) {
		        String url = item.get("url");
		        File file = new File(url);
		        String name = file.getName();
	    		map.put("urls", url);
	    		map.put("names", name);
		        System.out.println(name);
		        System.out.println(url);
		        affected += service.insertFile(map);

		    }
		}
		
	    String uploadDirectory = "src/main/resources/static/images";  // 파일을 저장할 디렉토리
	    if (files != null) {
		    try {
		        Path uploadPath = Paths.get(uploadDirectory);
		        if (!Files.exists(uploadPath)) {
		            Files.createDirectories(uploadPath);// 디렉토리가 없으면 생성
		        }
	
		        for (MultipartFile file : files) {
		            String filename = file.getOriginalFilename();
		            String newFilename = FileUtils.getNewFileName(uploadDirectory, filename);
		            Path filePath = uploadPath.resolve("E:/images/"+newFilename);  // 파일이 저장될 경로
		            String filePathStr = filePath.toString().replace("\\", "/");  // 역슬래시를 슬래시로 바꾸기
		            
		            String baseUrl = "http://localhost:4000";  // 기본 URL
		            String imagePath = filePathStr.substring(filePathStr.indexOf("/images"));
		            
		    		map.put("urls", baseUrl+imagePath);
		    		map.put("names", newFilename);
		    	    System.out.println(map.get("urls"));
		    	    System.out.println(map.get("names"));
		            file.transferTo(filePath);  // 파일 저장
		            affected += service.insertFile(map);
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		        return 0;
		    }
	    }
		if(affected==0) {//입력 실패
			return affected;
		}
		return affected;
	}/////
	
	//상세보기]
	@RequestMapping(value="/View.do",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String view(@RequestParam Map map,Model model) {
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
	
	@GetMapping("/List.do")
	public List edit(@RequestParam Map map) {
	    //서비스 호출    
	    List<BBSDto> records = service.selectAll();
	    System.out.println("records:"+records);
	    for (BBSDto record : records) {
	        int bno = record.getBno();
	        System.out.println("bno:"+bno);
	        List<String> files = service.selectFiles(bno);
	        record.setFiles(files);  // 게시글에 파일들을 추가
	        System.out.println("files:"+record.getFiles());
	    }
		return records;
	}
	
	@PostMapping("/Edit.do")
	public String editOk(Model model,BBSDto dto, FilesDto files) {
	
		//서비스 호출		
		int affected=service.update(dto,files);
		if(affected==0) {//수정 실패
			model.addAttribute("UPDATE_ERROR", "수정 오류 입니다");
			//뷰정보 반환
			return "onememo09/bbs/Edit.ict";
		}
		//뷰정보 반환		
		return "forward:/onememo/bbs/View.do";
	}/////
	
	/*
	@GetMapping("/{no}/Delete.do")
	public String delete(@PathVariable String bno,Model model) {
		//서비스 호출
		BBSDto dto = BBSDto.builder().bno(bno).build();
		int affected=service.delete(dto);
		if(affected == -1) {
			return "forward:/onememo/bbs/View.do?no="+no;
		}
		//뷰정보 반환]-목록을 처리하는 컨트롤러로 이동
		return "forward:/onememo/bbs/List.do";
	}
	*/
}
