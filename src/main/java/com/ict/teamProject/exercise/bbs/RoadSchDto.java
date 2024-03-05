package com.ict.teamProject.exercise.bbs;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("RoadSchDto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoadSchDto {
	private String id;
}
