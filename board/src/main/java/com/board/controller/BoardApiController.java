package com.board.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.domain.BoardVO;
import com.board.mapper.BoardServiceImpl;

@RestController
@RequestMapping("/boardApi")
public class BoardApiController {
 
    @Resource(name="com.board.mapper.BoardServiceImpl")
    BoardServiceImpl serviceImpl;
    
    @RequestMapping
    public List<BoardVO> board() throws Exception{
        List<BoardVO> board = serviceImpl.boardList();
        return board;
    }

}




