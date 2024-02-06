package com.ict.teamProject.manage;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.manage.dto.DiaryDto;
import com.ict.teamProject.manage.dto.DiaryImagesDto;

@Mapper
public interface ManageMapper {
	public DiaryDto findDiaryById(String userNDate); //다이어리 텍스트 내용 뿌려주기 용
	public List<String> findDiaryImgUrlsByDiaryId(String diaryId); //다이어리에 들어간 사진 뿌려주기 용
	public void uploadDiaryById(DiaryDto diaryDto); //다이어리 내용 입력용
	public void uploadDiaryImageById(List<DiaryImagesDto> imgs); //다이어리 사진 입력용
}
