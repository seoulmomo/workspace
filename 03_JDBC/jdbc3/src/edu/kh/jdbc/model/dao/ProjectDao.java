package edu.kh.jdbc.model.dao;
//static 필드/메서드 호출 시 클래스명(JDBCTemplate) 생략
import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.model.dto.Member;
public class ProjectDao {
	// JDBC 객체 참조 변수 선언
	
	// Connection : DB 연결 정보를 담고있는 객체
	
	// Statement : Connection을 통해 생성
	// SQL 수행 후 결과를 반환 받는 객체
	
	// PreparedStatement : Connection을 통해 생성 
	// SQL 중간에 삽입될 변수 자리를 ? (placeholder)로 지정해
	// SQL을 변수 값에 따라 쉽게 변화시킬 수 있는 Statement 자식 객체
	
	// ResultSet : SELECT 구문 수행 결과를 저장할 객체 (테이블 모양)
	// 1행씩 접근하면서 컬럼 값을 가져올 수 있음
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/** 회원 가입
	 * @param conn
	 * @param member
	 * @return
	 */
	public int insertMember(Connection conn, Member member) {
		
		// 결과 저장용 변수 선언
		int result = 0;
		
		// SQL 작성
		String sql = "INSERT INTO MEMBER "
				+ "VALUES(SEQ_MEMBER_NO.NEXTVAL, ?, ?, ?, ?, ?, DEFAULT, DEFAULT)";
		
		try {
			// PreparedStatment 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ?(placeholder)에 알맞은 값 대입
			pstmt.setString(1, member.getMemberEmail());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberNickname());
			pstmt.setString(4, member.getMemberTel());
			pstmt.setString(5, member.getMemberAddress());
			
			// SQl 수행 후 결과(성공한 행의 개수) 반환 받기
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 사용한 JDBC 객체 자원 반환
			close(pstmt);
		}
				
		
		// SQL 수행 결과 반환
		return result;
	}

	/** 로그인
	 * @param conn
	 * @param email
	 * @param pw
	 * @return
	 */
	public Member login(Connection conn, String email, String pw) {
		// 결과 저장용 변수 선언
		Member member = null;
		
		// SQL 작성
		String sql = "SELECT MEMBER_NO , MEMBER_NICKNAME , MEMBER_TEL , MEMBER_ADDRESS , "
				+ "		TO_CHAR(ENROLL_DATE,'YYYY\"년\" MM\"월\" DD\"일\" HH24:MI:SS') ENROLL_DATE "
				+ "FROM MEMBER "
				+ "WHERE MEMBER_EMAIL = ? "
				+ "AND MEMBER_PW = ?"
				+ "AND MEMBER_DEL_FL = 'N'"; 
		
		try {
			// PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 값 대입
			pstmt.setString(1, email);
			pstmt.setString(2, pw);
			
			// SQL(SELECT) 수행 후 결과(ResultSet) 반환 받기
			// SELECT -> executeQuery
			// DML -> executeUpdate
			rs = pstmt.executeQuery();
			
			// ResultSet에 조회된 다음 행이 있는지 확인
			// -> 아이디, 비밀번호가 일치하는 경우는 1행 밖에 없으므로
			// while(조건식) 대신 if(조건식)
//			while(rs.next()) {
			if(rs.next()) { // 결과가 있을 때 무조건 1행이면 if 사용 권고
				// 조회 결과가 있을 경우 컬럼 값을 모두 얻어오기
				int memeberNo = rs.getInt("MEMBER_NO");
				String memberNickName = rs.getString("MEMBER_NICKNAME");
				String memberTel = rs.getString("MEMBER_TEL");
				String memberAddress = rs.getString("MEMBER_ADDRESS");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				// + 매개변수 email
				
				// Member 객체를 생성하여 얻어온 값을 모두 세팅
				member = new Member();
				// 결과 저장용 변수가 Member 객체를 참조
				// == null 아님
				member.setMemberEmail(email);
				member.setMemberNo(memeberNo);
				member.setMemberNickname(memberNickName);
				member.setMemberTel(memberTel);
				member.setEnrollDate(enrollDate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 사용한 JDBC 객체 반환(역순)
			close(rs);
			close(pstmt);
		}
		
		// member == null -> 로그인 실패
		// member != null -> 로그인 성공
		return member;
	}

	/** 로그인(Statement)
	 * @param conn
	 * @param email
	 * @param pw
	 * @return
	 */
	public Member login2(Connection conn, String email, String pw) {
		// 1. 결과 저장용 변수 선언
		Member member = null;
		// 2. SQL 작성
		String sql = "SELECT MEMBER_NO , MEMBER_NICKNAME , MEMBER_TEL , MEMBER_ADDRESS ,\r\n"
				+ "		TO_CHAR(ENROLL_DATE,'YYYY\"년\" MM\"월\" DD\"일\" HH24:MI:SS') ENROLL_DATE \r\n"
				+ "FROM MEMBER\r\n"
				+ "WHERE MEMBER_EMAIL = '"+email+"'\r\n"
				+ "AND MEMBER_PW = '"+pw+"'\r\n"
				+ "AND MEMBER_DEL_FL = 'N'";
		
		try {
			// 3. Statement 객체 생성
			stmt = conn.createStatement(); // Statement 객체 생성 시 SQL을 전달하지 않음
			
			// 4. SQL(SELECT) 수행 후 결과(ResultSet) 반환 받기
			rs = stmt.executeQuery(sql); // Statement가 SQL을 수행하려고 할 때 SQL을 전달해줌
			
			// 5. ResultSet에 조회된 다음 행이 있는지 확인
			if(rs.next()) { // 결과가 있을 때 무조건 1행이면 if 사용 권고
				// 6. 조회 결과가 있을 경우 컬럼 값을 모두 얻어오기
				int memeberNo = rs.getInt("MEMBER_NO");
				String memberNickName = rs.getString("MEMBER_NICKNAME");
				String memberTel = rs.getString("MEMBER_TEL");
				String memberAddress = rs.getString("MEMBER_ADDRESS");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				// + 매개변수 email
				
				// 7. Member 객체를 생성하여 얻어온 값을 모두 세팅
				member = new Member();
				// 결과 저장용 변수가 Member 객체를 참조
				// == null 아님
				member.setMemberEmail(email);
				member.setMemberNo(memeberNo);
				member.setMemberNickname(memberNickName);
				member.setMemberTel(memberTel);
				member.setEnrollDate(enrollDate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 8. 사용한 JDBC 객체 반환
			close(rs);
			close(stmt);
		}

		return member;
	}
	
	
	/** MEMBER 테이블 전체 조회
	 * @param conn
	 * @param sort
	 * @return
	 */
	public List<Member> selectAllMember(Connection conn, int sort){
		// 1. 결과 저장용 객체 생성
		List<Member> memberList = new ArrayList<Member>();
		
		// 2. SQL 작성
		String sql = "SELECT * FROM MEMBER ORDER BY MEMBER_NO";
		
		// sort 값에 따라 SQL 변경
		if(sort == 1) sql += " DESC";
		
		// 3. 
		
		try {
			// Statement 버전
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(sql);
			
			// PreparedStatement 버전	
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// 4. 조회 결과 1행씩 접근(여러 행이면 while, 1행이면 if)
			while(rs.next()) {
//				int memberNo = rs.getInt("MEMBER_NO"); // 컬럼명
				int memberNo = rs.getInt(1); // 컬럼 순서
				String memberEmail = rs.getString(2);
				String memberPw = rs.getString(3);
				String memberNickname = rs.getString(4);
				String memberTel = rs.getString(5);
				String memberAddress = rs.getString(6);
				
				// java.sql.Date
				Date enrollDate = rs.getDate(7);
				
				String memberDelFl = rs.getString(8);
				
				// 5. Member 객체를 생성하여 컬럼 값 세팅 후 리스트에 추가
				Member member = new Member(memberNo, memberEmail, memberPw, memberNickname, memberTel, memberAddress, memberAddress.toString(), memberDelFl);
				
				memberList.add(member);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// 6. 사용한 JDBC 객체 반환
			close(rs);
			close(pstmt);
		}
		return memberList;
	}

	public int updateMember(Connection conn, String nickname, String tel, int memberNo) {
		int result = 0;
		
		String sql = "UPDATE MEMBER "
				+ "SET MEMBER_NICKNAME = ?, "
				+ "MEMBER_TEL = ? "
				+ "WHERE MEMBER_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, nickname);
			pstmt.setString(2, tel);
			pstmt.setInt(3, memberNo);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public int updateDelFl(Connection conn, int memberNo, String pw) {
		int result = 0;
		
		String sql = "UPDATE MEMBER\r\n"
				+ "SET MEMBER_DEL_FL = 'Y'\r\n"
				+ "WHERE MEMBER_NO = ?\r\n"
				+ "AND MEMBER_PW = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, memberNo);
			pstmt.setString(2, pw);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	/** 게시글 작성
	 * @param conn
	 * @param title
	 * @param content
	 * @param memberNo
	 * @return
	 */
	public int insertBoard(Connection conn, String title, String content, int memberNo) {
		int result = 0; // INSERT 결과 저장용 변수
		
		String sql = "INSERT INTO BOARD\r\n"
				+ "VALUES(SEQ_BOARD_NO.NEXTVAL, ?, ?,DEFAULT,DEFAULT,DEFAULT,?)\r\n";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, memberNo);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public Member login3(Connection conn, String email, String pw) {
		Member mem = null;
		
		String sql = "SELECT MEMBER_NO , MEMBER_NICKNAME , MEMBER_TEL , MEMBER_ADDRESS ,\r\n"
				+ "		TO_CHAR(ENROLL_DATE,'YYYY\"년\" MM\"월\" DD\"일\" HH24:MI:SS') ENROLL_DATE \r\n"
				+ "FROM MEMBER\r\n"
				+ "WHERE MEMBER_EMAIL = ?\r\n"
				+ "AND MEMBER_PW = ?\r\n"
				+ "AND MEMBER_DEL_FL = 'N'";
		
		try {
			pstmt = conn.prepareStatement(sql);
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(sql);
			// pstmt-> pstmt 생성할 때 sql참조
			// stmt -> ResultSet을 생성할 때 sql참조
			
			pstmt.setString(1, email);
			pstmt.setString(2, pw);
			// DML -> executeQuery
			// SELECT -> executeUpdate
			rs = pstmt.executeQuery();
			
			if(rs.next()) {// 결과가 1행만 나오니깐 if사용을 권고
				// 조회 결과가 있을 경우 컬럼값을 모두 가져오기
				int memeberNo = rs.getInt("MEMBER_NO");
				String memberNickName = rs.getString("MEMBER_NICKNAME");
				String memberTel = rs.getString("MEMBER_TEL");
				String memberAddress = rs.getString("MEMBER_ADDRESS");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				mem = new Member();
				
				mem.setMemberNO(memeberNo);
				mem.setMemberNickname(memberNickName);
				mem.setMemberTel(memberTel);
				mem.setMemberAddress(memberAddress);
				mem.setEnrollDate(enrollDate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(conn);
		}
		return mem;
		// mem == null 로그인 실패
		// mem != null 로그인 성공
	}
	
	

}
