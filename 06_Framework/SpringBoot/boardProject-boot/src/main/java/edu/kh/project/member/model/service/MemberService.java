package edu.kh.project.member.model.service;

import edu.kh.project.member.model.dto.Member;

public interface MemberService {

	/** 로그인 서비스
	 * @param inputMember
	 * @return loginMember
	 */
	Member login(Member inputMember);

	/** 회원가입 서비스
	 * @param inputMember
	 * @param memberAddress
	 * @return
	 */
	int signup(Member inputMember, String[] memberAddress);

	/** 빠른 로그인
	 * @param memberEmail
	 * @return
	 */
	Member quickLogin(String memberEmail);

	/** 이메일 중복 검사
	 * @param email
	 * @return
	 */
	int checkEmail(String email);

	int checkNickname(String nickname);

	

}
