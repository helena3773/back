package com.ict.teamProject.exercise_record;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("ERDto")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class ERDto {
	private String id;
	private String eName;
	private java.sql.Date postDate;
	private String eVideoPath;
	private String eContent;
	private String eType;
}
