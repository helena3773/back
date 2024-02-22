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
	
}
