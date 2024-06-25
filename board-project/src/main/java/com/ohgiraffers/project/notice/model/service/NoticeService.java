package com.ohgiraffers.project.notice.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ohgiraffers.project.member.model.dao.MemberDAO;
import com.ohgiraffers.project.notice.model.dao.NoticeDAO;
import com.ohgiraffers.project.notice.model.dto.NoticeDTO;

import static com.ohgiraffers.project.common.mybatis.Template.getSqlSession;

public class NoticeService {
	
	private NoticeDAO noticeDAO;
	
//	여기 요약
//	- mybatis 사용해 db와 교류
//	- SqlSession으로 db연결 관리
//	- 메서드 실행 후 close - db연결 닫아 리소스 정리
	
	
	/* 공지사항 전체 목록 조회용 메소드 */
//	mybatis 사용해 db로부터 공지사항 목록 가져오는 메소드임 selectAllNoticeList()
//	이 메소드는 NoticeDTO 객체를 리스트로 반환함
	public List<NoticeDTO> selectAllNoticeList() {
		
//		SqlSession 객체 생성  - db와 연결을 관리
		SqlSession session = getSqlSession();
//		noticeDAO 인터페이스의 구현체(mybatis가 자동 생성해줌. sql 쿼리 호출 처리?)를 가져옴
		noticeDAO = session.getMapper(NoticeDAO.class);
		
//		noticeDAO(객체)의 selectAllNoticeList 메소드 호출(db에서 공지사항 목록 가져옴)
//		-> 가져온 목록을 list<noticeDTO>타입으로 반환 
		List<NoticeDTO> noticeList = noticeDAO.selectAllNoticeList();
		
//		SqlSession 객체 닫음(db연결 종료)
		session.close();
		
//		가져온 공지사항 목록을 호출한 곳으로 반환함
		return noticeList;
	}
	
	/* 신규 공지 사항 등록용 메소드 */
	public int insertNotice(NoticeDTO newNotice) {
		
		SqlSession session = getSqlSession();
		noticeDAO = session.getMapper(NoticeDAO.class);
		
		int result = noticeDAO.insertNotice(newNotice);
		
//		새 공지 삽입 결과 0이상이면 성공.
		if(result > 0) {
//			새 공지 삽입 성공 시 트랜잭션을 커밋
			session.commit();
		} else {
//			새 공지 삽입 실패 시 트랜잭션을 롤백함			
			session.rollback();
		}
		
		session.close();
		
		return result;
	}
	
	/* 공지사항 상세보기용 메소드 */
	public NoticeDTO selectNoticeDetail(int no) {
		
		SqlSession session = getSqlSession();
		noticeDAO = session.getMapper(NoticeDAO.class);
		
//		공지사항 상세정보를 담을 noticeDTO 객체 초기화해줌
		NoticeDTO noticeDetail = null;
		
//		공지사항 조회수 증가시키는 메소드 호출, 그 결과를 result 변수에 저장
		int result = noticeDAO.incrementNoticeCount(no);
		
//		조회수 증가 성공하면 상세정보를 조회한다
		if(result > 0) {
			noticeDetail = noticeDAO.selectNoticeDetail(no);
			
//			상세정보 가져오면 트랜잭션 커밋
			if(noticeDetail != null) {
				session.commit();
//			상세정보 못가져오면 트랜잭션 롤백
			} else {
				session.rollback();
			}
//		조회수 증가 실패하면 트랜잭션 롤백함
		} else {
			session.rollback();
		}
		
		session.close();
		
//		공지사항 상세정보 반환
		return noticeDetail;
	}
	
}
