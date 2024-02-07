package com.ict.teamProject.member.service;



import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Alias("MemberDto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
	private String id;
	private String pwd;
	private String name;
	private String gender;
	private String b_day;
	private double height;
	private double weight;
	private String tel;
	private String userAddress;
	private int goal_No;
	private Date regidate;
	private String authority;
	
	//소셜로그인
	private String profileimage;
	private String provider;
	private String pro_filepath;
}
