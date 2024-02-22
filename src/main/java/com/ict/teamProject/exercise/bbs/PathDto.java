package com.ict.teamProject.exercise.bbs;

import org.apache.ibatis.type.Alias;

import com.ict.teamProject.comm.dto.FriendDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("PathDto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PathDto {
	private int rpath_no; //경로 아이디
	private String id; //경로를 등록한 유저
	private float lat; //위도
	private float lng; //경로
	private int order_num; //방문 순서
}
