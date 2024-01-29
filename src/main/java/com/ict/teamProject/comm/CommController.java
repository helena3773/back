package com.ict.teamProject.comm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ict.teamProject.comm.dto.FriendDto;
import com.ict.teamProject.comm.dto.MateDto;
import com.ict.teamProject.comm.dto.MySubscriberDto;
import com.ict.teamProject.comm.dto.SubscribeToDto;
import com.ict.teamProject.comm.dto.UserProfileDto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import java.nio.file.StandardCopyOption;
@RestController
@RequestMapping("/comm")
@CrossOrigin(origins = "http://localhost:3333")
public class CommController {
	CommService service;
	public CommController(CommService service) {
		this.service = service;
	}
	//mate
	@GetMapping("/mate") //조회
	public List<MateDto> getAllMates(@RequestParam String id){
		List<MateDto> mates = service.findAllMatesById(id);
		for(MateDto m : mates) {
			m.setName(service.findNameById(m.getMate_id()));//이름
			m.setFNum(service.findFMSnumById(m.getMate_id(), "f"));//친구 수
			m.setMNum(service.findFMSnumById(m.getMate_id(), "m"));//메이트 수
			m.setSNum(service.findFMSnumById(m.getMate_id(), "s"));//구독자 수
			m.setProfilePath(service.findProPathById(m.getMate_id()));//프로필 사진
		}
		return mates;
	}
	
	@PutMapping("/mate/changeFavorable")//호감도 수정
	public void updateFavorableRating(@RequestBody Map map) {
		MateDto dto = new MateDto().builder()
						.mate_id(String.valueOf(map.get("mate_id")))
						.favorable_rating(((Integer)map.get("favorable_rating")))
						.build();
		service.putFavorableRating(dto);
	}
	
	//friend
	@GetMapping("/friend") //조회
	public List<FriendDto> getAllFriends(@RequestParam String id){
		List<FriendDto> friends = service.findAllFriendsById(id);
		for(FriendDto f : friends) {
			f.setName(service.findNameById(f.getFriend_id()));
			f.setFNum(service.findFMSnumById(f.getFriend_id(), "f"));
			f.setSNum(service.findFMSnumById(f.getFriend_id(), "s"));
			f.setMNum(service.findFMSnumById(f.getFriend_id(), "m"));
			f.setProfilePath(service.findProPathById(f.getFriend_id()));
		}
		return friends;
	}
	
	//subscribe
	@GetMapping("/subscribe") //조회
	public Map getAllSubInfo(@RequestParam String id){
		List<SubscribeToDto> subscribeTo = service.findAllSubToById(id); //내가 구독한 목록
		for(SubscribeToDto dto : subscribeTo) {
			dto.setName(service.findNameById(dto.getSubscribe_id()));
			dto.setFNum(service.findFMSnumById(dto.getSubscribe_id(), "f"));
			dto.setSNum(service.findFMSnumById(dto.getSubscribe_id(), "s"));
			dto.setMNum(service.findFMSnumById(dto.getSubscribe_id(), "m"));
			dto.setProfilePath(service.findProPathById(dto.getSubscribe_id()));
		}
		
		List<MySubscriberDto> subscribers = service.findAllMySubById(id); //나의 구독자 목록
		for(MySubscriberDto dto : subscribers) {
			dto.setName(service.findNameById(dto.getId()));
			dto.setFNum(service.findFMSnumById(dto.getId(), "f"));
			dto.setSNum(service.findFMSnumById(dto.getId(), "s"));
			dto.setMNum(service.findFMSnumById(dto.getId(), "m"));
			dto.setProfilePath(service.findProPathById(dto.getId()));
		}
		Map total = new HashMap();
		total.put("subTo", subscribeTo);
		total.put("MySub", subscribers);
		return total;
	}
	
	//유저프로필
	@GetMapping("/profile")
	public UserProfileDto getUserProfile(@RequestParam String id) {
		UserProfileDto dto = new UserProfileDto().builder()
				.id(id)
				.name(service.findNameById(id))
				.profilePath(service.findProPathById(id))
				.proIntroduction(service.findIntroductionById(id))
				.date(service.findJoinDateById(id))
				.build();
		return dto;
	}
	
	//친구끊기
	@DeleteMapping("/friend/delete")
	public void deleteFriend(@RequestBody Map map) {
		service.deleteFriend(String.valueOf(map.get("id")));
	}
	
	//구독 취소
	@DeleteMapping("/subscribe/delete")
	public void deleteSubTo(@RequestBody Map map) {
		service.deleteSubTo(String.valueOf(map.get("id")));
	}
	
	//친구 차단
	@PutMapping("/friend/block")
	public void blockFriend(@RequestBody Map map) {
		service.putFriendBlocking(String.valueOf(map.get("id")));
	}
	
	//메이트 끊기
	@DeleteMapping("/mate/delete")
	public void deleteMate(@RequestBody Map map) {
		service.deleteMate(String.valueOf(map.get("id")));
	}
	
	//구독자 삭제
	@DeleteMapping("/subscribe/deleteSubscriber")
	public void deleteSubscriber(@RequestBody Map map) {
		service.deleteSubscriber(map);
	}
	
	//유저프로필 사진경로 변경
	@PutMapping("/profile/update")
	public void updateProfilePath(@RequestBody Map map) {
		System.out.println(map);
		UserProfileDto dto = new UserProfileDto().builder()
				.id(String.valueOf(map.get("id")))
				.profilePath(String.valueOf(map.get("profilePath")))
				.build();
		service.putProfileImage(dto);
	}
	
    // 파일 업로드 처리
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void uploadFile(MultipartFile file) throws IOException {
	    System.out.println("파일 업로드"+file);

	    // 파일 처리 로직
	    if (file != null && !file.isEmpty()) {
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	        String uploadDir = "./src/main/resources/static/images/";
	        // 파일 저장 경로 설정
	        Path filePath = Paths.get(uploadDir + fileName);

	        // 파일 저장
	        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	    }
	}
}
