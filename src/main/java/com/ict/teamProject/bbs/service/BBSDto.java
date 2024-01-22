package com.ict.teamProject.bbs.service;
import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("BBSDto")
@Builder
public class BBSDto {
	private String bno;
	private String id;
	private int type;
	private String Content;
	private char disclosureYN;
	private String hashTag;
	private java.sql.Date postDate;
}
