package com.ohgiraffers.project.notice.controller;

import java.io.IOException;

import com.ohgiraffers.project.notice.model.dto.NoticeDTO;
import com.ohgiraffers.project.notice.model.service.NoticeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/detail")
public class NoticeSelectDetailServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		여기 요약
//		공지사항 서비스 호출 및 상세정보 조회
		
//		no(공지사항 번호) 문자열로 받아서 정수로 변환함 (Integer.parseInt)
		int no = Integer.parseInt(request.getParameter("no"));
		
//		noticeService 객체 생성
		NoticeService noticeService = new NoticeService();
//		공지사항 번호*no에 해당하는 공지 "상세정보" 를 조회 -> 결과를 noticeDetail 변수(DTO)에 저장
		NoticeDTO noticeDetail = noticeService.selectNoticeDetail(no);
		
//		객체내용 콘솔 출력
		System.out.println("noticeDetail : " + noticeDetail);
		
//		경로 설정 및 요청 dispatch
//		경로 초기화
		String path = "";
//		공지사항 "상세정보가 존재" 하는지 여부를 조건문으로 검사
		if(noticeDetail != null) {
//			공지 상세 페이지 경로 설정해줌
			path = "/WEB-INF/views/notice/noticeDetail.jsp";
//			조회된 공지사항 상세정보를 요청 속성에 추가함
			request.setAttribute("notice", noticeDetail);
		} else {
			path = "/WEB-INF/views/common/failed.jsp";
//			실패 메시지를 요청 속성에 추가함
			request.setAttribute("message", "공지사항 상세 보기 조회에 실패하였습니다.");
		}
		
//		지정된 경로로 포워딩함. 클라이언트가 해당 jsp 페이지를 보게 됨
		request.getRequestDispatcher(path).forward(request, response);
	}

}
