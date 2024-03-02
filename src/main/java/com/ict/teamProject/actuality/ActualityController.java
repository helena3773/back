package com.ict.teamProject.actuality;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ict.teamProject.actuality.dto.ActualityEatingDto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import java.nio.file.StandardCopyOption;
@RestController

@CrossOrigin(origins = "http://localhost:3333")
public class ActualityController {
	ActualityService service;
	public ActualityController(ActualityService service) {
		this.service = service;
	}
	
	@GetMapping("/saverecord/dietinfo.do") //조회
	public List<ActualityEatingDto> saveActualityEating(String id, String ae_foodname, String ae_diettype){
		System.out.println("들어온 유저 아이디 :" + id + "받은 음식:" + ae_foodname+ "식사 : " + ae_diettype);
		int checkdiet = service.checkdailydiet(id, ae_diettype);
		if(checkdiet == 0) {
			int saveYN = service.saveActuality(id, ae_foodname, ae_diettype);
			System.out.println("삽입 결과 : "+ saveYN);
		}else if(checkdiet == 1){
			System.out.println("들어왔는지 체크");
			int updateYN = service.updateActuality(id, ae_foodname, ae_diettype);
			System.out.println("업데이트 결과 : "+ updateYN);
		}else {
			System.out.println("null이나 undefined 말고는.. 여기 들어올 이유가 없는데");
		}
		List<ActualityEatingDto> list = service.dailyActuality(id);
//		for(ActualityEatingDto l : list) {
//			System.out.println("가져온 Id 값은?"+l.getId());
//		}		
		return list;
	}
}