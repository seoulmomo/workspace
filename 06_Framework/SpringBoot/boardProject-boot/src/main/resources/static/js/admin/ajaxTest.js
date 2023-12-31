// 회원 번호를 입력받아 일치하는 회원의 이메일 조회하기(비동기)

const inputMemberNo = document.querySelector("#inputMemberNo");
const selectMemberNo = document.querySelector("#selectMemberNo");
const result1 = document.querySelector("#result1");

// 조회 버튼 클릭 시
selectMemberNo.addEventListener("click", () => {
  /* fetch() API를 이용한 GET 방식 요청 */
  // GET : 데이터를 얻어오다 (조회 SELECT)
  // 쿼리스트링 : 주소에 담겨진 파라미터를 지칭하는 단어
  // ?key=value&?key=value (띄어쓰기가 없어야됨)

  // fetch(주소?쿼리스트링)

  // then() : 그리고나서, 앞 동작이 완료된 후에
  fetch("/ajax/selectMemberNo?memberNo=" + inputMemberNo.value)
    // 요청에 대한 응답이 돌아왔을 때 수행
    .then((resp) => {
      // resp : 응답이 담겨져있는 객체(promise 타입)
      console.log(resp); // promise객체
      return resp.text(); // promis에 담긴 응답 결과를 text형태로 파싱 반환
    })
    // 첫번째 then()에서 반환된 결과를 이용해 기능을 수행
    .then((email) => {
      // email : 첫 번째 then에서 파싱된 데이터
      console.log(email);
      // 응답 결과(email)이 존재하는 경우
      // 있으면 : 이메일 문자열
      // 없으면 : 빈칸
      if (email == "") {
        result1.innerText = "일치하는 회원이 없습니다.";
      } else {
        result1.innerText = email;
      }
    })
    // 비동기 통신중 예외 발생 시 수행
    .catch((e) => {
      // e : 예외 관련 정보 객체
      console.log(e);
    });
});

// 이메일이 일치하는 회원의 전화번호 조회(비동기)
const inputEmail = document.querySelector("#inputEmail");
const btn2 = document.querySelector("#btn2");
const result2 = document.querySelector("#result2");

btn2.addEventListener("click", () => {
  fetch("/ajax/selectEmail?inputEmail=" + inputEmail.value)
    // 응답이 왔을 때 수행, 응답 결과를 text로 파싱
    .then((resp) => resp.text())
    // 첫번째 then의 반환된 결과를 이용해 기능 수행
    .then((tel) => {
      if (tel == "") {
        result2.innerText = "이메일이 일치하는 회원이 존재하지 않습니다.";
      } else {
        result2.innerText = tel;
      }
    })
    .catch((e) => {
      // 비동기 통신중 예외 발생 시 수행
      console.log(e);
    });
});

// 회원번호가 일치하는 회원 정보 모두 조회
const no2 = document.querySelector("#inputMemberNo2");
const btn3 = document.querySelector("#btn3");
const result3 = document.querySelector("#result3");

btn3.addEventListener("click", () => {
  fetch("/ajax/selectMember?no=" + no2.value)
    // 응답 데이터가 JSON인 경우 JS 객체로 파싱
    .then((resp) => resp.json()) // JS객체 반환 또는 예외 발생
    .then((member) => {
      result3.innerHTML = ""; // 내용 삭제
      const ul = document.createElement("ul");
      // 객체 전용 향상된 for문
      for (let key in member) {
        // console.log(`${key} : ${member[key]}`);
        const li = document.createElement("li"); // li 요소 생성
        li.innerText = `${key} : ${member[key]}`; // li 내용 추가
        ul.append(li); // ul의 마지막 자식으로 li 추가
      }
      result3.append(ul); // result3의 마지막 자식으로 ul 추가
    })
    .catch((e) => {
      // 조회결과가 null이면 JSON 파싱에서 예외 발생
      result3.innerHTML = "";
      const h4 = document.createElement("h4");
      h4.innerText = "일치하는 회원이 없습니다.";
      result3.append(h4);
    });
});

// 문자열을 입력 받아 일부라도 일치하는 이메일 조회
const input4 = document.querySelector("#input4");
const btn4 = document.querySelector("#btn4");
const result4 = document.querySelector("#result4");

btn4.addEventListener("click", () => {
  fetch("/ajax/selectEmailList?keyword=" + input4.value)
    .then((resp) => resp.json()) // List 객체 -> JSON Array -> JS 객체 배열 변환
    .then((emailList) => {
      // console.log(emailList);
      result4.innerHTML = "";
      if (emailList.length == 0) {
        const tr = document.createElement("tr");
        const th = document.createElement("th");
        th.innerText = "조회 결과가 없습니다.";
        tr.append(th);
        result4.append(tr);
      } else {
        // 조회 결과가 있을 경우
        // JS의 향상된 for문 : for ... of
        for (let email of emailList) {
          const tr = document.createElement("tr");
          const td = document.createElement("td");
          td.innerText = email;
          // td클릭 시 해당 이메일 회원 정보 상세 조회 페이지 이동
          td.addEventListener("click", (e) => {
            location.href = "/admin/selectMember?inputEmail=" + e.target.innerText;
          });

          tr.append(td);
          result4.append(tr);
        }
      }
    })
    .catch((e) => console.log(e));
});

// 회원 조회 버튼 클릭 시 모든 회원 정보 조회
const btn5 = document.querySelector("#btn5");
const result5 = document.querySelector("#result5");

btn5.addEventListener("click", () => {
  // 쿼리스트링이 없는 GET방식 비동기 요청
  fetch("/ajax/selectAll")
    .then((resp) => {
      // resp == 응답정보가 담긴 객체(상태코드, 데이터)
      console.log(resp);
      console.log(resp.status);
      console.log(resp.body);
      if (resp.ok) {
        console.log("비동기 통신 성공");
        // console.log(resp.json());
        return resp.json();
        // JSON형태의 응답 데이터(memberList)를 JS객체 형태로 변환해서 반환
        // (첫 번째 then에서 리턴 시 promise객체에 데이터만 반환)
      }
    })
    .then((memberList) => {
      // memberList : 첫 번째 then에서 반환된 JS객체(변환까지 완료)
      console.log(memberList);
      result5.innerHTML = "";
      for (let member of memberList) {
        // 향상된 for문
        const tr = document.createElement("tr");
        const td1 = document.createElement("td");
        const td2 = document.createElement("td");
        const td3 = document.createElement("td");
        const td4 = document.createElement("td");
        const td5 = document.createElement("td");

        td1.innerText = member.memberNo; // 회원번호
        td2.innerText = member.memberEmail; // 이메일
        td3.innerText = member.memberNickname; // 닉네임
        td4.innerText = member.memberTel; // 전화번호
        // td5 컬럼에는 버튼을 만들어서 집어넣기(탈퇴,복구 버튼)
        const btn = document.createElement("button");
        if (member.memberDelFl == "Y") {
          // 탈퇴 상태인 경우
          btn.innerText = "복구";
          btn.style.backgroundColor = "green";
        } else {
          btn.innerText = "탈퇴";
          btn.style.backgroundColor = "pink";
        }
        // 탈퇴 여부('Y','N')을 속성으로 추가
        btn.setAttribute("data-flag", member.memberDelFl);

        // --------------------------------------------
        // 버튼 클릭 시 회원 탈퇴/복구 AJAX
        btn.addEventListener("click", (e) => {
          // 버튼에 속성 중 "data-flag" 값이 'N'이면 'Y', 'Y'이면 'N' 저장
          // -> DB UPDATE 시 MEMBER_DEL_FL에 대입할 값으로 이용
          const flag = e.target.getAttribute("data-flag") == "N" ? "Y" : "N";

          // 회원 번호
          // - 클릭된 버튼의 부모의 부모의 자식들 중 0번째
          const temp = e.target.parentElement.parentElement.children[0];
          const targetNo = temp.innerText;

          // 필요한 데이터를 한 번에 저장한 JS 객체 생성
          const obj = {};
          obj.flag = flag;
          obj.targetNo = targetNo;
          // AJAX 코드 작성
          fetch("/ajax/updateFlag", {
            method: "PUT", // PUT 방식으로 요청
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(obj),
          })
            .then((resp) => resp.text())
            .then((result) => {
              if (result > 0) {
                // 성공 시
                if (flag == "Y") {
                  btn.innerText = "복구";
                  btn.style.backgroundColor = "green";
                } else {
                  btn.innerText = "탈퇴";
                  btn.style.backgroundColor = "pink";
                }
                btn.setAttribute("data-flag", flag);
              } else {
                alert("변경 실패");
              }
            })
            .catch((e) => {
              console.log(e);
            });
        });
        // --------------------------------------------

        td5.append(btn);
        tr.append(td1, td2, td3, td4, td5);
        result5.append(tr);
      }
    })
    .catch((e) => console.log(e));
});

// 샘플 계정 생성
const sampleInputList = document.querySelectorAll("#sample-container > input");
const btn6 = document.querySelector("#btn6");

btn6.addEventListener("click", () => {
  // 입력된 값들을 한번에 저장할 JS객체 생성
  const member = {};
  // JS객체에 key, value 추가
  // (JS객체는 key가 존재하지 않으면 자동으로 추가되는 특징)
  member.memberEmail = sampleInputList[0].value;
  member.memberNickname = sampleInputList[1].value;
  member.memberTel = sampleInputList[2].value;
  console.log(member);

  // 비동기로 샘플 회원 가입
  /* 
    요청 방식에 따른 용도 (REST API)
    GET == SELECT
    POST == INSERT
    PUT == UPDATE
    DELETE == DELETE
  */
  fetch("/ajax/insertMember", {
    method: "post",
    headers: { "Content-Type": "application/json" }, // 요청 관련 정보 작성(요청 데이터의 형식 등)
    body: JSON.stringify(member), // POST, PUT, DELETE는 body에 데이터를 담아서 전달
    // JSON.stringify(member) : member객체를 JSON으로 변환(문자열화)
  })
    .then((resp) => resp.text())
    .then((result) => {
      if (result > 0) {
        alert("가입 성공");
        // input 태그 작성 값 모두 삭제
        sampleInputList.forEach((input) => (input.value = ""));
      } else {
        alert("가입 실패");
      }
    })
    .catch((e) => {
      console.log(e);
    });
});
