package com.ict.teamProject.manage;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ict.teamProject.manage.dto.DiaryDto;

@RestController
@RequestMapping("/manage")
@CrossOrigin(origins = "http://localhost:3333")
public class ManageController {
	private ManageService service;
	public ManageController(ManageService service) {
		this.service = service;
	}
	
	@GetMapping("/diary")
	public DiaryDto getDiaryContent(@RequestParam String userNDate) {
		return service.findDiaryById(userNDate);
	}
}
