package com.ict.teamProject.schedule.service;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("SCHDto")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class SCHDto {
	private int sNo; // 글번호 o
	private String id; // 아이디 o
	private String sTitle; // 제목 o
	private String sContent; // 내용 o
	private String sEat;  // 음식
	private String sExer; // 운동
	private String sDest; // 목적지 o
	private int cal; // 타입 o
	private String sArea; //시작 지역
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime  start; //시작 o
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime  end; //끝 o
	private char sCom; //완료여부 o
	private int rPathNo;  //경로 o
	private String sMate; //메이트

	@Override
	public String toString() {
		return String.format("calendar 값 확인: %d", this.cal);
	}
}
