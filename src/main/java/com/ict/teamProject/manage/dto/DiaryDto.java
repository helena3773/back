package com.ict.teamProject.manage.dto;

import org.apache.ibatis.type.Alias;

import com.ict.teamProject.comm.dto.FriendDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("DiaryDto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDto {
	private String id;
	private String diary_content;
	private int stress;
	private int emotion;
}
