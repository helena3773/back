package com.ict.teamProject.manage;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.manage.dto.DiaryDto;

@Mapper
public interface ManageMapper {
	public DiaryDto findDiaryById(String UserNDate);
}
