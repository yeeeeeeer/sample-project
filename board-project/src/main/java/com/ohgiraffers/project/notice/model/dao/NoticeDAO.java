package com.ohgiraffers.project.notice.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ohgiraffers.project.notice.model.dto.NoticeDTO;

// mybatis 를 이용해 만든 interface DAO
// db구현체를 마이바티스가 만들어줘서 메소드 기본틀만 선언하면 됨

// 하는 일: db 테이블에 접근, 필요한 작업 수행하는 메소드 선언 (service에서 호출됨-> DAO.xml 호출시켜서 일함)
public interface NoticeDAO {
	
	/* 공지사항 목록 전체 조회용 메소드 */
	// DTO 객체 리스트로 반환함
	public List<NoticeDTO> selectAllNoticeList();
	
	/* 공지사항 테이블 삽입용 메소드 */
	// insertNotice(매개변수 noticeDTO 안에 newNotice 정보 담겨있음)
	// 삽입 결과를 정수형태로 반환받음 (삽입된 행의 수를 반환?)
	public int insertNotice(NoticeDTO newNotice);
	
	/* 공지사항 조회수 증가용 메소드 */
	// incrementNoticeCount(매개변수 no: 조회수 증가시킬 공지사항의 번호)
	// 조회수 증가 실행의 결과를 정수형태로 반환받음 (업데이트된 행의 수 반환)
	public int incrementNoticeCount(int no);
	
	/* 공지사항 상세보기 조회용 메소드 */
	// selectNoticeDetail(매개변수 no: 조회할 공지사항의 번호)
	// 반환타입 noticeDTO: 조회된 공지사항의 상세정보가 담긴 DTO 객체로 반환
	public NoticeDTO selectNoticeDetail(int no);	
	
}
