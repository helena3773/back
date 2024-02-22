package com.ict.teamProject.eating_record;

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

import com.ict.teamProject.eating_record.dto.EatingRecordDto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import java.nio.file.StandardCopyOption;
@RestController

@CrossOrigin(origins = "http://localhost:3333")
public class EatingRecordController {
	EatingRecordService service;
	public EatingRecordController(EatingRecordService service) {
		this.service = service;
	}
	
    @PostMapping("/Dietfood/Insert.do")
    public void saveDiet(@RequestBody Map<String, Object> requestData) {
        System.out.println("Received request data: " + requestData);
        
        // morningData 처리
        Map<String, Object> morningData = (Map<String, Object>) requestData.get("morningData");
        String morningConnetId = (String) morningData.get("connetId");
        String morningMealType = (String) morningData.get("mealType");
        String morningFoodName = (String) morningData.get("foodName");
        String morningRecipeCode = (String) morningData.get("recipeCode");
        System.out.println(morningFoodName);
        // 나머지 필드에 대한 처리
        
        // lunchData 처리
        Map<String, Object> lunchData = (Map<String, Object>) requestData.get("lunchData");
        String lunchConnetId = (String) lunchData.get("connetId");
        String lunchMealType = (String) lunchData.get("mealType");
        String lunchFoodName = (String) lunchData.get("foodName");
        String lunchRecipeCode = (String) lunchData.get("recipeCode");
        // 나머지 필드에 대한 처리
        
        // dinnerData 처리
        Map<String, Object> dinnerData = (Map<String, Object>) requestData.get("dinnerData");
        String dinnerConnetId = (String) dinnerData.get("connetId");
        String dinnerMealType = (String) dinnerData.get("mealType");
        String dinnerFoodName = (String) dinnerData.get("foodName");
        String dinnerRecipeCode = (String) dinnerData.get("recipeCode");
        // 나머지 필드에 대한 처리
    }
}