package com.ict.teamProject.mate_room.web;
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
import com.ict.teamProject.mate_room.service.MPDto;
import com.ict.teamProject.mate_room.service.MRDto;
import com.ict.teamProject.mate_room.service.MRService;


//24.02.18 생성
@Controller
@RequestMapping("/mroom")
@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class MRController {
	
	//서비스 주입
	@Autowired
	private MRService<MRDto> service;
	
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
		System.out.println("room.get(\"sport\"):"+room.get("sport"));
		System.out.println("room.get(\"openRoomYN\"):"+room.get("openRoomYN"));
		System.out.println("room.get(\"matchingYN\"):"+room.get("matchingYN"));
		System.out.println("room.get(\"date\"):"+room.get("date"));
		System.out.println("room.get(\"content\"):"+room.get("content"));
		System.out.println("room.get(\"title\"):"+room.get("title"));
		System.out.println("room.get(\"userset\"):"+room.get("userset"));
		System.out.println("room.get(\"areaSet\"):"+room.get("areaSet"));
		System.out.println("room.get(\"id\"):"+room.get("id"));
		
		MRDto dto = new MRDto();
		Map<String, String> goalMap = (Map<String, String>) room.get("sport");
		String goalValue = goalMap.get("value");

		dto.setMateSport(goalValue);
		System.out.println("dto.getMateSport():"+dto.getMateSport());
		
		dto.setMateCapacity(Integer.parseInt(room.get("userset").toString()));
		System.out.println("dto.getMateCapacity():"+dto.getMateCapacity());
		
		dto.setMateArea(room.get("areaSet").toString());
		System.out.println("dto.getMateArea():"+dto.getMateArea());	
		
		dto.setMateTitle(room.get("title").toString());
		System.out.println("dto.getMateTitle():"+dto.getMateTitle());
		
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
		
	    String openRoomYN = Boolean.parseBoolean(room.get("openRoomYN").toString()) ? "Y" : "N";
	    dto.setRYN(openRoomYN);
		System.out.println("dto.getRYN():"+dto.getRYN());
		
	    String matchingYN = Boolean.parseBoolean(room.get("matchingYN").toString()) ? "Y" : "N";
	    dto.setMYN(matchingYN);
		System.out.println("dto.getMYN():"+dto.getMYN());
		
	    String dateR = room.get("date").toString();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date dateUtil = formatter.parse(dateR);
	    Date date = new Date(dateUtil.getTime()); 

	    dto.setMateDate(date); // 메이트 시작일
	    System.out.println("dto.getMateDate():"+dto.getMateDate());
	    
		dto.setMateContent(room.get("content").toString());
		
		int affected = 0;
		int seqValue = service.getSeqValue()+1;
		System.out.println("seqValue---"+seqValue);
		service.insert(dto);
		System.out.println("성공했으면 1 아니면 0?"+affected);
		
		MPDto cpdto = new MPDto();
		System.out.println("room.get(\"id\")"+room.get("id"));
		cpdto.setId(room.get("id").toString());
		cpdto.setMateNo(seqValue);
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
		List<MRDto> record = new ArrayList();
		record = service.selectAll();
		for (MRDto item : record) {
		    System.out.println("방장은??----" + item.getManager());
		    System.out.println("방번호는???---"+item.getMateNo());
		    List result = service.participantsdata(item.getMateNo());
	        item.setParticipantsData(result);
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
	public List participantsData(@RequestParam int challNo) {
		System.out.println("받은 방 번호는???----"+challNo);
		List record = new ArrayList();
		List result = service.participantsdata(challNo);
		record.add(result);
		return record;
	}/////
	
	//방 참가]
	@PostMapping("/joinRoom.do")
	@ResponseBody
	public int joinRoom(@RequestBody Map map) {
	    int record = 0;
	    System.out.println("map.get(\"challNo\").toString()"+map.get("challNo").toString());
	    System.out.println("map.get(\"id\").toString()"+map.get("id").toString());
	    MPDto dto = new MPDto();
	    dto.setMateNo(Integer.parseInt(map.get("challNo").toString()));
	    dto.setId(map.get("id").toString());
	    record = service.join(dto);
	    return record;
	}/////
	
	//방의 데이타 가져오기]
	@PostMapping("/roomData.do")
	@ResponseBody
	public MRDto roomData(@RequestBody Map map) {
	    System.out.println("challNo----"+Integer.parseInt(map.get("challNo").toString()));
	    int challNo = Integer.parseInt(map.get("challNo").toString());
	    MRDto dto = new MRDto();
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
