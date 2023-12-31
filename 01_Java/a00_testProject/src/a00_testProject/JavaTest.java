package a00_testProject;

// 주석: 코드에 대한 설명을 작성하는 부분 (컴파일러(번역기) 해석 x)

// 한 줄 주석
/* 
범위 주석
여러줄 주석
*/

// class: 자바 코드를 작성하는 영역
public class JavaTest {

	// main 메서드(method, 기능)
	// - 자바 프로그램(프로젝트, 애플리케이션)을
	// 실행시키기 위해 반드시 필요한 구문(기능)
	public static void main(String[] args) {
		
		// 실행방법
		// 1) 위에 실행 버튼(Run 버튼)
		// 2) ctrl + f11
		
		// System.out.println(): ()안의 문자열 출력 후 엔터
		// 					     (한 줄 출력)
		System.out.println("Hello World!!!!!");
		System.out.println("안녕하세요");
		// 숫자만 작성하면 연산
		System.out.println(3500+6000);
		
		// 사칙연산 우선순위를 그대로 적용
		// 숫자 + "문자열" = 숫자문자열 (이어쓰기, 추가하기, 덧붙이기)
		System.out.println(1000+2000+"원"); // 3000원
		
		System.out.println("고재모");
		System.out.println("만 "+25+"세"); // 띄어쓰기도 문자열
		
		// "문자열"+숫자 = "문자열"
		// 연산 우선순위 문제
		System.out.println("합계 : " + (2000+3000) + " 원"); // 합계 : 5000원
	}
}
