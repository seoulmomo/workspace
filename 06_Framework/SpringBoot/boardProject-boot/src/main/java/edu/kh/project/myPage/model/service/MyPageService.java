package edu.kh.project.myPage.model.service;

import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;

@Transactional
public interface MyPageService {

	/** 회원 정보 수정
	 * @param updateMember
	 * @param memberAddress
	 * @return result
	 */
	int info(Member updateMember, String[] memberAddress);

	/** 비밀번호 변경
	 * @param currentPw
	 * @param newPw
	 * @param memberNo
	 * @return
	 */
	int changePw(String currentPw, String newPw, int memberNo);

	/** 회원 탈퇴
	 * @param memberPw
	 * @param memberNo
	 * @return
	 */
	int secession(String memberPw, int memberNo);

	/** 프로필 이미지 수정
	 * @param profileImg
	 * @param loginMember
	 * @return result
	 */
	int profile(MultipartFile profileImg, Member loginMember) throws IllegalStateException, IOException;



}