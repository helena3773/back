package com.ict.teamProject.bbs.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.bbs.service.BBSService;



@Service("service")
public class BBSServiceImpl implements BBSService<BBSDto> {

	//매퍼 인터페이스 주입
	@Autowired
	private BBSMapper mapper;	
	
	@Override
	public int insert(Map map) {
		int affected=0;
		try {
			affected=mapper.save(map);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return affected;
	}

	@Override
	public BBSDto selectOne(Map map) {		
		return mapper.findByBBS(map);
	}



	@Override
	public int update(BBSDto record) {		
		return mapper.updateByKey(record);
	}
	/*
	 
	 https://mybatis.org/spring/transactions.html
	 [삭제 작업을 트랜잭션 처리하기]
	 방법1 - 스프링 레거시 기준
	 1.빈 설정파일(root-context.xml)에 DataSourceTransactionManager 등록
	 2.빈 설정파일(root-context.xml)에 TransactionTemplate 등록
	 3.TransactionTemplate 주입받는다
	 4.TransactionTemplate객체의 execute()메소드로 트랜잭션 작업 실행
	   You can omit to call the commit and rollback method 
	   using the TransactionTemplate.
	 방법2- @Configuration 및 @Bean어노테이션으로 자바코드로 빈 등록 - 스프링 레거시 혹은 부트
	 
	 ※트랜잭션 작업에서 런타임 오류시 롤백,정상 실행시 커밋된다
	 ※트랜잭션 처리는 @Service어노테이션이 붙은 클래스에서 하자
	 */
	
	//트랜잭션 처리 관련 빈 주입 받기
	@Autowired
	private TransactionTemplate template;
	@Autowired
	private CommentMapper commentMapper;
	
	
	@Override
	public int delete(OnememoDto record) {		
		//return mapper.deleteByKey(record);//댓글이 있는 메모글은 에러		
		int affected=0;//특정 글번호에 따른 삭제된 총 댓글 수
		try {
			//TransactionCallback<T>: 타입 파라미터 T는 트랜잭션 처리 작업후 반환할 타입으로 지정
			affected=template.execute(new TransactionCallback<Integer>() {
				//※doInTransaction()의 반환값이 execute()메소드의 반환값이다 
				@Override
				public Integer doInTransaction(TransactionStatus status) {
					//글 번호에 따른 모든 댓글 삭제
					int deleteCommentsCount=commentMapper.deleteAllByNo(record);
					//일부러 트랜잭션 테스트를 위한 에러를 발생시키자
					//Integer.parseInt("에러");
					//해당 원본 글 삭제
					mapper.deleteByKey(record);
					return deleteCommentsCount;
				}
			});
		}
		catch(Exception e) {
			return -1;
		}
		
		return affected;
	}

	@Override
	public int update(BBSDto record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(BBSDto record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isLogin(Map map) {
		// TODO Auto-generated method stub
		return false;
	}

}
