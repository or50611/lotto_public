package com.board.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.board.domain.BoardVO;

@Service("com.board.mapper.BoardServiceImpl")
public class BoardServiceImpl implements BoardMapper {
	
	@Resource(name="com.board.mapper.BoardMapper")
	BoardMapper boardMapper;

	@Override
	public int boardCtn() throws Exception {
		return boardMapper.boardCtn();
	}

	@Override
	public List<BoardVO> boardList() throws Exception {
		return boardMapper.boardList();
	}

	@Override
	public BoardVO boardDtl(int bno) throws Exception {
		boardMapper.hitPlus(bno);
		return boardMapper.boardDtl(bno);
	}


	@Override
	public void boardWrite(BoardVO vo) throws Exception {
		boardMapper.boardWrite(vo);
	}


	@Override
	public void hitPlus(int bno) throws Exception {
		boardMapper.hitPlus(bno);
	}


	@Override
	public void boardUpdate(BoardVO vo) throws Exception {
		boardMapper.boardUpdate(vo);
	}


	@Override
	public void boardDel(int bno) throws Exception {
		boardMapper.boardDel(bno);
	}



}
