package com.ict.teamProject.database;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class DatabaseController {
	//DatabaseConfig.java에 빈으로 등록
	//위에 방법에 의해 싱글톤으로 생성된 한 히카리 데이타소스가 주입 된다
	@Autowired	
	private DataSource dataSource;

	public void hikari(Model model) throws SQLException {
		//주입받은 DataSource객체로 Connection객체 얻기
		Connection conn= dataSource.getConnection();
		//데이타 저장
		System.out.println(conn==null?"[데이타 베이스 연결 실패]":"[데이타 베이스 연결 성공]");
		//커넥션 객체 풀에 반납
		if(conn !=null) conn.close();
		//뷰정보 반환
	}
}
