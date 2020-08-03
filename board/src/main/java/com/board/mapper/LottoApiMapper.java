package com.board.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("com.board.mapper.LottoApiMapper")
public interface LottoApiMapper {
	
	//회차별 번호목록
	public List<HashMap<String, Object>> lottoMainList(HashMap<String, Object> map) throws Exception;
	//1등 당첨종류
	public HashMap<String, Object> lottoWinnerType(HashMap<String, Object> map) throws Exception;
	//최근회차 번호
	public int lottoLatestNum() throws Exception;
	//전국 판매점반경cnt
	public int lottoAddrRadiusNum(HashMap<String, Object> map) throws Exception;
	//전국 판매점반경
	public List<HashMap<String, Object>> lottoAddrRadius(HashMap<String, Object> map) throws Exception;
	
	public HashMap<String, Object> lottoRecommendMain() throws Exception;
	
}
