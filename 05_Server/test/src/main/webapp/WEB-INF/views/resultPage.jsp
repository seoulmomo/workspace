<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>html문서 제목</title>
</head>
<body>    
  <h1>${name}님의 구매 목록</h1>
  <h3>검은 펜 ${blackPen}개 : ${blackPen*500}</h3>
  <h3>빨간 펜 ${redPen}개 : ${redPen*700}</h3>
  <h3>파란 펜 ${bluePen}개 : ${bluePen*700}</h3>   
  <h3>화이트 ${white}개 : ${white*1000}</h3>            
  <hr>
  <h2>총합 ${total}원을 결제하셨습니다.</h2>    
</body>
</html>