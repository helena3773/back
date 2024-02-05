package com.ict.teamProject.manage.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("DiaryImagesDto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryImagesDto {
	private String name;
	private String imgUrl;
	private String diaryId;
}
