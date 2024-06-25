package com.ohgiraffers.project.notice.controller;

import java.io.IOException;
import java.util.List;

import com.ohgiraffers.project.notice.model.dto.NoticeDTO;
import com.ohgiraffers.project.notice.model.service.NoticeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/list")
public class NoticeSelectListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		여기 요약
//		1. 클라가 get요청 보냄
//		2. noticeService 이용해 모든 공지사항 목록 조회함
//		3. 조회된 목록 존재하면 목록페이지를 전달, 실패하면 실패 메세지 페이지로 포워딩
//		4. 최종 - 클라는 공지사항 목록을 확인가능한 페이지로 이동하게됨 
		
		
//		noticeService 객체 생성
//		noticeService의 selectAllNoticeServiceList 메소드 호출해서 "모든 공지사항 목록 조회"
//		-> 결과를 "변수 noticeList"에 저장 list<noticeDTO>
		List<NoticeDTO> noticeList = new NoticeService().selectAllNoticeList();
		
//		모든 공지사항 목록 조회한 결과(noticeList)를 콘솔에 출력
		System.out.println(noticeList);
		
//		경로 초기화
		String path = "";
//		공지사항 목록 존재하면
		if(noticeList != null) {
//			공지사항 목록 페이지로 조회된 목록을 요청 속성에 넣음
			path = "/WEB-INF/views/notice/noticeList.jsp";
			request.setAttribute("noticeList", noticeList);
		} else {
//			공지사항 목록 없으면 실패 메시지 요청 속성에 넣음
			path = "/WEB-INF/views/common/failed.jsp";
			request.setAttribute("message", "공지사항 조회 실패!");
		}
		
		request.getRequestDispatcher(path).forward(request, response);
	}

}
