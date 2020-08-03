package com.board.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.board.domain.BoardVO;

@Repository("com.board.mapper.BoardMapper")
public interface BoardMapper {

	public int boardCtn() throws Exception;
	
	public List<BoardVO>boardList()throws Exception;
	
	public BoardVO boardDtl(int bno) throws Exception;
	
	public void boardWrite(BoardVO vo) throws Exception;
	
	public void hitPlus(int bno) throws Exception;
	
	public void boardUpdate(BoardVO vo) throws Exception;
	
	public void boardDel(int bno) throws Exception;
	
	
}
