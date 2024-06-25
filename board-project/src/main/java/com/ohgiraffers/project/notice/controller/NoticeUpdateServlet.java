package com.ohgiraffers.project.notice.controller;

import java.io.IOException;

import com.ohgiraffers.project.notice.model.dto.NoticeDTO;
import com.ohgiraffers.project.notice.model.service.NoticeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/update")
public class NoticeUpdateServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		여기 요약
//		1. 클라가 get 요청 보냄
//		2. no 공지사항 번호 가져옴
//		3. 해당 공지사항의 상세정보 조회함
//		4. 조회된 공지사항 정보가 존재하면 수정페이지로 전달, 실패 시 실패메세지 보여줌
//		5. 최종: 클라는 공지사항 수정하는 페이지로 이동하게됨
		
		
//		공지사항 번호를 문자열로 받아 정수로 변환함 - no 변수에 저장
		int no = Integer.parseInt(request.getParameter("no"));
		
//		noticeService 객체 생성
		NoticeService noticeService = new NoticeService();
//		selectNoticeDetail 메소드 호출 - 공지사항번호에 해당하는 "상세정보" 를 조회, 결과를 notice 변수에 DTO로 저장함
		NoticeDTO notice = noticeService.selectNoticeDetail(no);
		
//		경로 저장할 변수 초기화해줌
		String path = "";
//		notice(상세정보)가 있을 경우
		if(notice != null) {
//			상세정보있을때 updateFrom페이지 경로 지정
			path = "/WEB-INF/views/notice/updateForm.jsp";
//			조회된 상세정보 요청 속성에 추가??
			request.setAttribute("notice", notice);
		} else {
//			상세정보 
			path = "/WEB-INF/views/common/failed.jsp";
			request.setAttribute("message", "공지사항 수정용 조회하기 실패!");
		}
		
		request.getRequestDispatcher(path).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
