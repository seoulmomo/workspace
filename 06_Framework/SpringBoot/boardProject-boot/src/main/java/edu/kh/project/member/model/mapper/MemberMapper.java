package edu.kh.project.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;

/* @Mapper 
 * - 해당 인터페이스의 메서드명과
 * 	 mapper.xml에 작성된 태그 아이디가 같은 것 끼리
 * 	 연결해주는 어노테이션
 * 
 * - sqlSessionTemplate bean을 의존성 주입 받는 코드를 별도 작성X
 * 	 (작성 안해도 Spring이 알아서 불러와서 작성함)
 * 
 * - namespace.id 형식으로 mapper를 찾는 별도 작성 X
 *   (메서드 이름 == id)
 *   
 * - selectOne, insert, update 메서드 구분 필요 X
 * 	 (mapper의 태그를 보고 구분)
 * 
 * **** 주의 사항 ****
 * - 해당 인터페이스와 연결할 mapper.xml 파일의 namespace 속성 값을
 * 	 현재 인터페이스의 패키지명.인터페이스명 으로 작성해야된다.
 * 	 (이래야 연결된다)
 * */
@Mapper
public interface MemberMapper {

	/** 로그인
	 * @param inputMember
	 * @return
	 */
	Member login(Member inputMember);

	/** 회원가입
	 * @param inputMember
	 * @return
	 */
	int signup(Member inputMember);

	/** 빠른 로그인
	 * @param memberEmail
	 * @return loginMember
	 */
	Member login(String memberEmail);

	/** 이메일 중복 검사
	 * @param email
	 * @return
	 */
	int checkEmail(String email);

	int checkNickname(String nickname);
}
