package com.ict.teamProject.challenge_room.web;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.teamProject.challenge_room.service.CPDto;
import com.ict.teamProject.challenge_room.service.CRDto;
import com.ict.teamProject.challenge_room.service.CRService;
import com.ict.teamProject.member.service.MemberDto;


//24.02.18 생성
@Controller
@RequestMapping("/croom")
@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class CRController {
	
	//서비스 주입
	@Autowired
	private CRService<CRDto> service;
	
	//방 찾기]
	@PostMapping("/myRoomNum.do")
	@ResponseBody
	public Integer myRoomNum(@RequestBody Map<String, String> map) {
		String id = map.get("id");
		Integer room = null;
		System.out.println("id-----:"+id);
		room = service.selectMyRoom(id);
		System.out.println("너의 방 번호는?"+room);
		return room;
	}/////
	
	//방 생성]
	@PostMapping("/createRoom.do")
	@ResponseBody
	public int createRoom(@RequestBody Map room) throws Exception {
		System.out.println("room.get(\"selectedCheckbox\"):"+room.get("selectedCheckbox"));
		System.out.println("room.get(\"sliderValues\"):"+room.get("sliderValues"));
		System.out.println("room.get(\"selectedOption1\"):"+room.get("selectedOption1"));
		System.out.println("room.get(\"openRoomYN\"):"+room.get("openRoomYN"));
		System.out.println("room.get(\"dateRange\"):"+room.get("dateRange"));
		System.out.println("room.get(\"content\"):"+room.get("content"));
		CRDto dto = new CRDto();
		Map<String, String> goalMap = (Map<String, String>) room.get("goal");
		String goalValue = goalMap.get("value");

		dto.setGoal(goalValue);
		System.out.println("dto.dto.getGoal():"+dto.getGoal());
		
		dto.setChallCapacity(Integer.parseInt(room.get("userset").toString()));
		System.out.println("dto.getChallCapacity():"+dto.getChallCapacity());
		
		dto.setImplementation(Integer.parseInt(room.get("achievementset").toString()));
		System.out.println("dto.getImplementation():"+dto.getImplementation());
		
		dto.setChallArea(room.get("areaSet").toString());
		System.out.println("dto.getChallArea():"+dto.getChallArea());	
		
		dto.setChallTitle(room.get("title").toString());
		System.out.println("dto.getChallTitle():"+dto.getChallTitle());
		
	    ObjectMapper mapper = new ObjectMapper();
	    int[] selectedCheckbox = mapper.readValue(room.get("selectedCheckbox").toString(), int[].class);
	    
	    int gLimit = 0;
	    if (selectedCheckbox.length == 1) {
	        gLimit = selectedCheckbox[0];
	    }
	    dto.setGLimit(gLimit);
		System.out.println("dto.getGLimit():"+dto.getGLimit());
		
	    int[] ageRange = new int[2];
	    if (!room.get("sliderValues").toString().equals("[]")) {
	        ageRange = mapper.readValue(room.get("sliderValues").toString(), int[].class);
	    }
	    dto.setAgeMin(ageRange[0]); // 나이 최소값
		System.out.println("dto.getAgeMin():"+dto.getAgeMin());
	    dto.setAgeMax(ageRange[1]); // 나이 최대값
		System.out.println("dto.getAgeMax():"+dto.getAgeMax());
		
	    String feeWithCurrency = room.get("selectedOption1").toString();
	    String feeOnlyDigits = feeWithCurrency.replaceAll("[^0-9]", ""); // 숫자만 추출
	    dto.setPFee(Integer.parseInt(feeOnlyDigits));
		System.out.println("dto.getPFee():"+dto.getPFee());
		
	    String openRoomYN = Boolean.parseBoolean(room.get("openRoomYN").toString()) ? "Y" : "N";
	    dto.setCYN(openRoomYN);
		System.out.println("dto.getCYN():"+dto.getCYN());
		
	    String[] dateRange = room.get("dateRange").toString().split(" to ");
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date startDateUtil = formatter.parse(dateRange[0]);
	    java.util.Date endDateUtil = formatter.parse(dateRange[1]);
	    
	    Date startDate = new Date(startDateUtil.getTime()); 
	    Date endDate = new Date(endDateUtil.getTime());

	    dto.setCStartDate(startDate); // 챌린지 시작일
	    System.out.println("dto.getCStartDate():"+dto.getCStartDate());
	    dto.setCEndDate(endDate); // 챌린지 종료일
	    System.out.println("dto.getCEndDate():"+dto.getCEndDate());
	    
		dto.setChallContent(room.get("content").toString());
		int affected = 0;
		int seqValue = service.getSeqValue()+1;
		System.out.println("seqValue---"+seqValue);
		service.insert(dto);
		System.out.println("성공했으면 1 아니면 0?"+affected);
		CPDto cpdto = new CPDto();
		System.out.println("room.get(\"id\")"+room.get("id"));
		cpdto.setId(room.get("id").toString());
		cpdto.setChallNo(seqValue);
		affected = service.insertP(cpdto);
		return seqValue;
	}/////
	
	//마지막 사람이 방 나갈때]
	@DeleteMapping("/deleteRoom.do")
	@ResponseBody
	public int deleteRoom(@RequestBody Map<String, String> map) {
		System.out.println("난 마지막사람~~~");
		String id = map.get("id");
		int room = 0;
		int affected = 0;
		System.out.println("id:"+id);
		room = service.selectMyRoom(id);
		service.deletep(id);
		System.out.println("room:"+room);
		affected = service.delete(room);
		System.out.println("너의 삭제된 방 번호는?"+room);
		return affected;
	}/////
	
	
	//챌린지 방 가져오기]
	@GetMapping("/listChall.do")
	@ResponseBody
	public List listChall() {
		CRDto dto = new CRDto();
		List<CRDto> record = new ArrayList();
		record = service.selectAll();
		for (CRDto item : record) {
		    System.out.println("방장은??----" + item.getManager());
		}
		return record;
	}/////
	
	//내 정보 가져오기]
	@GetMapping("/myData.do")
	@ResponseBody
	public Map myData(@RequestParam String id) {
		System.out.println("받은 아이디 값:"+id);
		Map map = new HashMap();
		map = service.findmyData(id);
		System.out.println("map.get(\"name\")" + (map.get("name") != null ? map.get("name").toString() : "null"));
		System.out.println("map.get(\"GENDER\")" + (map.get("GENDER") != null ? map.get("GENDER").toString() : "null"));
		System.out.println("map.get(\"B_DAY\")" + (map.get("B_DAY") != null ? map.get("B_DAY").toString() : "null"));
		System.out.println("map.get(\"PRO_FILEPATH\")" + (map.get("PRO_FILEPATH") != null ? map.get("PRO_FILEPATH").toString() : "null"));
		return map;
	}/////
	
	//참여자 정보 가져오기]
	@GetMapping("/participantsData.do")
	@ResponseBody
	public List participantsData() {
		List record = new ArrayList();
		record = service.participantsdata();
		return record;
	}/////
	
	//방 참가]
	@PostMapping("/joinRoom.do")
	@ResponseBody
	public int joinRoom(@RequestBody Map map) {
	    int record = 0;
	    System.out.println("map.get(\"challNo\").toString()"+map.get("challNo").toString());
	    System.out.println("map.get(\"id\").toString()"+map.get("id").toString());
	    CPDto dto = new CPDto();
	    dto.setChallNo(Integer.parseInt(map.get("challNo").toString()));
	    dto.setId(map.get("id").toString());
	    record = service.join(dto);
	    return record;
	}/////
	
	//방의 데이타 가져오기]
	@PostMapping("/roomData.do")
	@ResponseBody
	public CRDto roomData(@RequestBody Map map) {
	    System.out.println("challNo----"+Integer.parseInt(map.get("challNo").toString()));
	    int challNo = Integer.parseInt(map.get("challNo").toString());
	    CRDto dto = new CRDto();
	    dto = service.findRoomData(challNo);
	    return dto;
	}/////
	
	//방장이 방 나갔을때]
	@DeleteMapping("/deleteManager.do")
	@ResponseBody
	public int deleteManager(@RequestBody Map<String, String> map) {
		System.out.println("난 방장~~~");
		String id = map.get("id");
		int room = 0;
		int affected = 0;
		System.out.println("id:"+id);
		room = service.selectMyRoom(id);
		String manager = service.selectManager(room);
		service.deletep(id);
		System.out.println("manager:"+manager);
		affected = service.update(manager);
		System.out.println("너의 수정된 방 번호는?"+room);
		return affected;
	}/////
	
	//일반 사람이 방 나갈때]
	@DeleteMapping("/deletePeople.do")
	@ResponseBody
	public int deletePeople(@RequestBody Map<String, String> map) {
		System.out.println("일반 사람~~~");
		String id = map.get("id");
		int room = 0;
		int affected = 0;
		System.out.println("id:"+id);
		room = service.selectMyRoom(id);
		service.deletep(id);
		System.out.println("나간 방 번호는?"+room);
		return affected;
	}/////

}
