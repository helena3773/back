package com.ict.teamProject.eating_record;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ict.teamProject.eating_record.dto.EatingRecordDto;
@Service
public class EatingRecordService {
	
	//mapper 생성자 주입
	private EatingRecordMapper mapper;
	public EatingRecordService(EatingRecordMapper mapper) {
		this.mapper = mapper;
	}
	public int savediet(String id, String mealtype, String eating_foodname, int eating_recipeCode) {
		return mapper.savediet(id, mealtype, eating_foodname, eating_recipeCode);
	}
	public List<EatingRecordDto> getdailydiet(String id){
		return mapper.getdailydiet(id);
	}
	public int updatediet(String id, String mealtype, String eating_foodname, int eating_recipeCode) {
		return mapper.updatediet(id, mealtype, eating_foodname, eating_recipeCode);
	}
	public int checkdailydata(String id) {
		return mapper.checkdailydata(id);
	}
	
//	public List<Map<String, Object>> getIngredients(int recipeCode) {
//	    return mapper.getIngredients(recipeCode);
//	}
}
