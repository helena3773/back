package com.ict.teamProject.eating_record.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("EatingRecordDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EatingRecordDto {
	private String id;
	private String mealType;
	private String eating_foodname;
	private int eating_recipeCode;
	private Date eating_date;
}
