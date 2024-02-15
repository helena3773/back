package com.ict.teamProject.chat.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.bbs.service.BBSService;
import com.ict.teamProject.bbs.service.BBSUsersProfileDto;
import com.ict.teamProject.bbs.service.LikesDto;
import com.ict.teamProject.chat.service.ChatDto;
import com.ict.teamProject.chat.service.ChatService;
import com.ict.teamProject.command.FileUtils;
import com.ict.teamProject.files.service.FilesDto;


@RequestMapping("/chat")
@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class ChatController {
	
	//서비스 주입
	@Autowired
	private ChatService<ChatDto> service;
	
	//입력처리]
	@PostMapping("/SoloWrite.do")
	@ResponseBody
	public int writeOk(@RequestBody Map map) {
		int affected = 0;
		System.out.println("id:"+map.get("id"));
		System.out.println("ruser:"+map.get("ruser"));
		System.out.println("content:"+map.get("content"));
		
		ChatDto dto = new ChatDto();
		affected = service.insert(map);
		
		if(affected==0) {//입력 실패
			return affected;
		}
		return affected;
	}/////
	

	@RequestMapping(value="/selectChat.do")
	@ResponseBody
	public ChatDto selectChat(@RequestBody Map map) {
		System.out.println("접속한 사람:"+map.get("id").toString());
		System.out.println("내가 보낼 사람:"+map.get("ruser").toString());
		
		//서비스 호출
		ChatDto record= service.selectChat(map);
		//줄바꿈
		if (record != null) {
			record.setContent(record.getContent().replace("\r\n", "<br/>"));
		}
		System.out.println("record-----"+record);
		//뷰정보 반환
		return record;
	}
	
	@RequestMapping(value="/whoChating.do")
	@ResponseBody
	public ChatDto whoChating(@RequestBody String id) {
		System.out.println("접속한 사람:"+id);
		
		//서비스 호출
		ChatDto record= service.whoChating(id);
		//줄바꿈
		if (record != null) {
			record.setContent(record.getContent().replace("\r\n", "<br/>"));
		}
		System.out.println("record-----"+record);
		//뷰정보 반환
		return record;
	}
	
	@RequestMapping(value="/allChating.do")
	@ResponseBody
	public List<ChatDto> allChating(@RequestParam String id) {
	    System.out.println("접속한 사람:"+id);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 시간을 포함한 형식
	    //서비스 호출
	    List<ChatDto> records = service.allChating(id);
	    //줄바꿈
	    for (ChatDto record : records) {
	    	if (record != null && record.getContent() != null) {
	    	    record.setContent(record.getContent().replace("\r\n", "<br/>"));
	    	}
	        if (record != null && record.getSendDate() != null) {
	            Timestamp timestamp = Timestamp.valueOf(record.getSendDate());
	            String formattedDate = format.format(timestamp);
	            record.setSendDate(formattedDate); 
	        }
	    	System.out.println("record-----"+record.getId());
	    	System.out.println("record-----"+record.getRuser());
	        System.out.println("record-----"+record.getContent());
	        System.out.println("record-----"+record.getSendDate());
	    }
	    //뷰정보 반환
	    return records;
	}

	
	/*
	
	@GetMapping("/ViewMy.do")
	@ResponseBody
	public List<BBSDto> viewMy(@RequestParam String id) {
		System.out.println("ID:"+id);
		//서비스 호출
		List<BBSDto> records= service.selectMy(id);
		//줄바꿈
	    for (BBSDto record : records) {
	        int bno = record.getBno();
	        record.setContent(record.getContent().replace("\r\n", "<br/>"));
	        List<String> files = service.selectFiles(bno);
	        record.setFiles(files);  // 게시글에 파일들을 추가
	        System.out.println("files:"+record.getFiles());
	    }
		return records;
	}
	
	//게시물 전체 뿌려주기
	@RequestMapping ("/List.do")
	public List view(@RequestBody Map map) {
	    List<Integer> types = new ArrayList<>();
	    List<String> selectedItems = (List<String>)map.get("selectedItems");
	    System.out.println("selectedItems---"+selectedItems);
	    if(selectedItems != null) {
	        for(String item : selectedItems) {
	            switch(item) {
	                case "식단":
	                    types.add(1);
	                    break;
	                case "운동":
	                    types.add(2);
	                    break;
	                case "심리":
	                    types.add(4);
	                    break;
	                default:
	                    break;
	            }
	        }
	    }
	    if(types.isEmpty()) {
	        types.add(0);
	    }
	    map.put("types", types);
	    System.out.println("types----"+map.get("types"));
	    //서비스 호출
	    List<BBSDto> records = service.selectAll(map);
	    System.out.println("records:"+records);
	    for (BBSDto record : records) {
	        int bno = record.getBno();
	        System.out.println("bno:"+bno);
	        List<String> files = service.selectFiles(bno);
	        record.setFiles(files);  // 게시글에 파일들을 추가
	        record.setContent(record.getContent().replace("\r\n", "<br/>"));
	        System.out.println("files:"+record.getFiles());
	        System.out.println("record.likes()"+record.getLikes());
	        System.out.println("record.type()"+record.getType());
	    }
	    
		return records;
	}
	
	//수정하기
	@PostMapping("/Edit.do")
	public int edit(@RequestBody Map<String, Object> map) {
		int affected=0;
		BBSDto record = null;
		System.out.println("데이타 넘어오냐??"+map);
        int bno = Integer.parseInt(map.get("bno").toString());
        System.out.println("글번호~~~:"+bno);
        // 기존 레코드
        record = service.selectOne(bno);
        System.out.println("record.getDisclosureYN():"+record.getDisclosureYN());
        System.out.println("record.getHashTag():"+record.getHashTag());
        System.out.println("record.getContent():"+record.getContent());
	    // map에 있는 값만 변경.
        
        if (map.get("content") != null && !map.get("content").toString().isEmpty()) {
            record.setContent(map.get("content").toString());
            System.out.println("getContent()::"+record.getContent());
        }
        if (map.get("disclosureYN") != null && !map.get("disclosureYN").toString().isEmpty()) {
            record.setDisclosureYN(map.get("disclosureYN").toString().charAt(0));
            System.out.println("getDisclosureYN()::"+record.getDisclosureYN());
        }
        if (map.get("hashTag") != null && !map.get("hashTag").toString().isEmpty()) {
            record.setHashTag(map.get("hashTag").toString());
            System.out.println("getHashTag()::"+record.getHashTag());
        }

        if(record.getHashTag() == null) record.setHashTag("");
        if(record.getContent() == null) record.setContent("");
	    System.out.println("=============수정 후?=======");
        System.out.println("record.getDisclosureYN():"+record.getDisclosureYN());
        System.out.println("record.getHashTag():"+record.getHashTag());
        System.out.println("record.getContent():"+record.getContent());
	    // 변경된 레코드를 업데이트.
	    affected = service.update(record);				
	    return affected;
	}/////
	
	//삭제하기
	@GetMapping("/{bno}/Delete.do")
	public ResponseEntity delete(@PathVariable String bno) {
		try {
	        int bnoInt = Integer.parseInt(bno);
	        int affected = 0;
	        List<String> files = service.selectFiles(bnoInt);
	        
	        String baseDirectory = "E:/images/";
	        String imageDirectory = "src/main/resources/static/images/";
	        
	        // files에 값이 있으면 해당 파일들을 삭제
	        if (files != null && !files.isEmpty()) {
	            for (String fileUrl : files) {
	                String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
	                Path filePath = Paths.get(baseDirectory + fileName);
	                Path imagePath = Paths.get(imageDirectory + fileName);
	                try {
	                    Files.deleteIfExists(filePath);
	                    Files.deleteIfExists(imagePath);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        System.out.println("bnoInt:"+bnoInt);
	        service.deleteFiles(bnoInt);
	        affected += service.deleteBBS(bnoInt);
	        System.out.println("affected2:"+affected);
	        return ResponseEntity.ok(String.valueOf(affected));
	    } catch (NumberFormatException e) {
	        return ResponseEntity.badRequest().body("bno: " + bno);
	    }
	}
	
	
	@PostMapping("/userProfile")
	public List<BBSUsersProfileDto> getAllUsersById(@RequestBody Map<String,List<String>> map){
		System.out.println("값이 요청됨:"+map.get("ids"));
		List<BBSUsersProfileDto> dtos = new ArrayList<BBSUsersProfileDto>();
		int flag = 0;
		Map<String, String> param = new HashMap<>();
		for (String id : map.get("ids")) {
			if (flag==0) {
				param.put("userId", id);
			}
			else {
				param.put("otherId", id);
				BBSUsersProfileDto tempDto = new BBSUsersProfileDto().builder()
										.id(id)
										.isFriend(service.findIsFriend(param))
										.isSubTo(service.findIsSubto(param))
										.profilePath(service.findProfilePathById(id))
										.build();
				System.out.println(String.format("요청보낸 아이디: %s, 요청받는 아이디: %s, 친구 수: %s", param.get("userId"), id, service.findIsFriend(param)));
				dtos.add(tempDto);
			}
			flag++;
		}
		return dtos;
	}
	
	//좋아요 얻어오기
	@PostMapping("/likes.do")
	public Map likes(@RequestBody Map map) {
		LikesDto likes = new LikesDto();
		String id = map.get("id").toString();
		System.out.println("id:-- "+id);
		String state = map.get("isLiked").toString();
		System.out.println("state:-- "+state);
		int bno = Integer.parseInt(map.get("bno").toString());
		System.out.println("bno:-- "+bno);
		int cno = 0;
		if(map.get("cno") != null && !map.get("cno").toString().isEmpty()) {
			cno = Integer.parseInt(map.get("cno").toString());
			System.out.println("cno:-- "+cno);
		}
		likes.setBno(bno);
		likes.setId(id);
	    if (state.equals("true")) {
	        service.setLikes(likes);
	        System.out.println("좋아요 누른 사람~~"+likes);
	    } else if (state.equals("false")) {
	        service.deleteLikes(likes);
	        System.out.println("좋아요 해제한 사람~~"+likes);
	    }
	    int likesnum = service.findLikes(bno);
	    String likesId = service.whereLikes(bno);
	    Map like = new HashMap();
	    like.put("likesnum", likesnum);
	    like.put("likes", likesId);
	    
		return like;
	}*/
}
