package com.ict.teamProject.exercise_record.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ict.teamProject.exercise_record.ERDto;
import com.ict.teamProject.exercise_record.ERService;




@Controller
@RequestMapping("/exer")
@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class ERController {
	
	//서비스 주입
	@Autowired
	private ERService<ERDto> service;
	
	
	

}
