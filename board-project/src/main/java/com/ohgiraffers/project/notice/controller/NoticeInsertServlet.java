package com.ohgiraffers.project.notice.controller;

import java.io.IOException;

import com.ohgiraffers.project.member.model.dto.MemberDTO;
import com.ohgiraffers.project.notice.model.dto.NoticeDTO;
import com.ohgiraffers.project.notice.model.service.NoticeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/insert")
public class NoticeInsertServlet extends HttpServlet {


//	protected(같은 패키지 내의 클래스, 서브클래스 접근가능함)
//  void (값 반환 X)
//	doGet메소드 (두 개의 매개변수 받음) HttpRequest( 클라의 요청정보 담은 객체), HResponse( 클라에대한 서버의 응답 객채)
//	SevletException(서블릿 예외), IOException 예외 던지기 가능 -> 그냥 서블릿 규칙..
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		여기 요약
//		1. 클라가 get 요청 보냄
//		2. path 페이지 반환
//		3. 클라는 insertForm.jsp 볼 수 있음		
		
//		클라에게 포워딩(전달해줄)할 JSP 파일경로 -> String 문자열로 저장함
		String path = "/WEB-INF/views/notice/insertForm.jsp";
		
//		path 경로로 요청을 보낼 객체 반환. 클라의 요청을 지정된 경로*inserForm.jsp로 포워딩(전달)함.
//		-> 요청 url은 변경되지 않지만 서버측에서 다른 리소스가 응답 생성해서 클라이언트에게 반환함.(dispatcher기능)
		request.getRequestDispatcher(path).forward(request, response);
	}

	
//	doPost(url 데이터 없음. form 태그로 데이터 받음 / 보안 유용)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		여기 요약
//		1. 클라가 post 요청
//		2. 공지사항 제목, 본문을 파라미터로 받아 새 공지 db에 삽입
//		3. 삽입 성공 시 경로, 실패 시 경로 페이지로 포워딩해서 성공/실패 결과를 클라에게 보여줌
		
		
//		클라 요청의 title 내용(parameter)을 가져옴(get)
		String title = request.getParameter("title");
		String body = request.getParameter("body");
//		request.getSession() - 요청의 세션이 존재하면 그걸 반환, 없으면 새 세션 생성
//		getAttribute("loginMember") - 로그인한 사용자(객체)의 속성을 가져옴
//		getNo() - 로그인한 사용자의 no(회원번호)를 받아옴 
//		-> writeMemberNo 변수에 loginMember의 no(회원번호)
		int writerMemberNo = ((MemberDTO) request.getSession().getAttribute("loginMember")).getNo();
		
//		새로운 newNoticeDTO 객체 생성 및 설정
		NoticeDTO newNotice = new NoticeDTO();
		newNotice.setTitle(title);   // 새 공지 객체에 제목 넣기
		newNotice.setBody(body);     // 새 공지 객체에 내용 넣기
		newNotice.setWriterMemberNo(writerMemberNo);    // 새 공지 객체에 작성자 회원번호 넣기
		
//		서비스 호출 및 결과 처리하기
//		noticeService 객체 생성
		NoticeService noticeService = new NoticeService();
//		insertNotice메소드 호출-> 새 공지를 db에 삽입
//		새 공지 삽입한 결과를 result 변수에 저장
//		result 가 0보다 크면 삽입 성공한것임!
		int result = noticeService.insertNotice(newNotice);
		
//		결과에 따른 경로 설정 및 forwarding(전달)
//		path(경로) 저장할 문자열변수 초기화해놓음
		String path = "";
//		새 공지 삽입 결과 조건문
		if(result > 0) {
//			성공 시 경로 넣어줌 (성공페이지)
			path = "/WEB-INF/views/common/success.jsp";
//			성공코드를 요청 속성에 추가함
			request.setAttribute("successCode", "insertNotice");
		} else {
//			실패 시 경로 넣어줌 (실패페이지)
			path = "/WEB-INF/views/common/failed.jsp";
//			실패 메시지를 요청 속성에 추가
			request.setAttribute("message", "공지사항 등록에 실패하셨습니다.");
		}
		
//		지정된 경로로 요청을 forwarding(전달)함
		request.getRequestDispatcher(path).forward(request, response);
		
	}

}
