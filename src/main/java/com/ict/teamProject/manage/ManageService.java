package com.ict.teamProject.manage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ict.teamProject.manage.dto.DiaryDto;
import com.ict.teamProject.manage.dto.DiaryImagesDto;

@Service
public class ManageService {
	
	private ManageMapper mapper;
	public ManageService(ManageMapper mapper) {
		this.mapper = mapper;
	}
	
	public DiaryDto findDiaryById(String UserNDate) {
		DiaryDto dto = new DiaryDto();
		dto = mapper.findDiaryById(UserNDate);
		System.out.println(mapper.findDiaryImgUrlsByDiaryId(UserNDate));
		dto.setImgUrls(mapper.findDiaryImgUrlsByDiaryId(UserNDate));
		return dto;
	}
	
	public void uploadDiaryContentsById(DiaryDto diaryDto, List<DiaryImagesDto> imgs) {
		mapper.uploadDiaryById(diaryDto); //다이어리 내용 저장
		mapper.uploadDiaryImageById(imgs);
	}
}
