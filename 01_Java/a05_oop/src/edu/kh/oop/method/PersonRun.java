package edu.kh.oop.method;

public class PersonRun {
	public static void main(String[] args) {
		
		// 사람 객체 생성(기본 생성자)
		Person p1 = new Person();
		
		// p1이 참조하는 객체의 name 필드 값을 세팅
		p1.setName("홍길동");
		
		String result = p1.getName(); // 홍길동
		// 반환형 String -> 반환 값을 Stirng 변수에 저장 가능
		
		System.out.println("result : " + result);
	
		System.out.println("종료");
		
		
		
		
		
	}
}
