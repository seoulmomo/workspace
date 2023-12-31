package edu.kh.project.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.project.member.model.dto.Member;

@Service
public interface AjaxService {

	/** 회원번호로 이메일 조회
	 * @param memberNo
	 * @return email
	 */
	String selectMemberNo(int memberNo);

	/** 이메일로 전화번호 조회
	 * @param inputEmail
	 * @return
	 */
	String selectEmail(String inputEmail);

	/** 회원번호로 회원 정보 조회
	 * @param no
	 * @return
	 */
	Member selectMember(int no);

	/** 문자열을 입력 받아 일부라도 일치하는 이메일 조회
	 * @param keyword
	 * @return emailList
	 */
	List<String> selectEmailList(String keyword);

	/** 모든 회원 정보 조회
	 * @return memberList
	 */
	List<Member> selectAll();

	/** 샘플 계정 가입
	 * @param member
	 * @return result
	 */
	int insertMember(Member member);

	/** 탈퇴여부 변경
	 * @param paramMap
	 * @return result
	 */
	int updateFlag(Map<String, Object> paramMap);

}
