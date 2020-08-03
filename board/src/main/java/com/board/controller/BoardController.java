package com.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.BoardVO;
import com.board.mapper.BoardServiceImpl;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Resource(name="com.board.mapper.BoardServiceImpl")
    BoardServiceImpl serviceImpl;
    
    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView list() throws Exception{
    	List<BoardVO> list = serviceImpl.boardList();
    	return new ModelAndView("boardList","list",list);
    } 
    @RequestMapping(value="/lotto", method=RequestMethod.GET)
    public ModelAndView lotto() throws Exception{
    	return new ModelAndView("lotto/index");
    } 
    
    @RequestMapping(value="/{bno}", method=RequestMethod.GET)
    public ModelAndView boardDtl(@PathVariable("bno") int bno) throws Exception{
    	BoardVO boardVO = serviceImpl.boardDtl(bno);
    	return new ModelAndView("boardDtl","board",boardVO);
    }

    @RequestMapping(value="/write", method=RequestMethod.POST)
    public String boardWrite(@ModelAttribute("BoardVO") BoardVO vo) throws Exception{
    	serviceImpl.boardWrite(vo);
    	return "redirect:/board";
    }
    
    @RequestMapping(value="/write/{bno}", method=RequestMethod.GET)
    public ModelAndView goBoardUdt(@PathVariable("bno") int bno) throws Exception{
    	BoardVO boardVO = serviceImpl.boardDtl(bno);
    	return new ModelAndView("boardUpdate","board",boardVO);
    }
    
    @RequestMapping(value="/write/{bno}", method=RequestMethod.POST)
    public String boardUpdate(@ModelAttribute("BoardVO") BoardVO vo) throws Exception{
    	serviceImpl.boardUpdate(vo);
    	return "redirect:/board";
    }
    
    @RequestMapping(value="/write/del/{bno}", method=RequestMethod.GET)
    public String boardDel(@PathVariable("bno") int bno) throws Exception{
    	serviceImpl.boardDel(bno);
    	return "redirect:/board";
    }
    
    @RequestMapping(value="/appWrite", method=RequestMethod.POST)
    @ResponseBody
    public Map boardAppWrite(@ModelAttribute("BoardVO") BoardVO vo) throws Exception{
    	serviceImpl.boardWrite(vo);
    	
    	Map result = new HashMap(); 
    	result.put("result", "한글테스트"); 
    	result.put("data", vo);

    	return result;
    }
    
    @RequestMapping(value="/testWrite", method=RequestMethod.GET)
    public ModelAndView goTestWrite() throws Exception{
    	return new ModelAndView("boardTestWrite");
    }
    

}




