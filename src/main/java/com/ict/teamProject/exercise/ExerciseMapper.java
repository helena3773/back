package com.ict.teamProject.exercise;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.exercise.bbs.PathDto;

@Mapper
public interface ExerciseMapper {
	public void uploadPath(PathDto dto); //경로 등록
}
