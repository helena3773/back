package com.ict.teamProject.food;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ict.teamProject.food.dto.FoodListDto;
@Service
public class FoodService {
	
	//mapper 생성자 주입
	private FoodMapper mapper;
	public FoodService(FoodMapper mapper) {
		this.mapper = mapper;
	}
	

	public List<FoodListDto> findrecipe(String id, String category) {
		return mapper.findrecipe(id, category);
	}
}
