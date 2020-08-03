package com.board.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("com.board.mapper.LottoAnalysisApiMapper")
public interface LottoAnalysisApiMapper {
	
	public int lottoMaxDrawNo() throws Exception;
	
	public List<HashMap<String, Object>> lottoAllRoundList() throws Exception;
	
	public HashMap<String, Object> lottoNumExtraction10() throws Exception;
	
	public HashMap<String, Object> lottoNumExtraction02() throws Exception;
	
	public HashMap<String, Object> lottoNumPrevWeek01() throws Exception;
	
	public HashMap<String, Object> lottoNumPrevNext01() throws Exception;
	
	public List<HashMap<String, Object>> lottoNumExtraction30() throws Exception;
	
	public List<HashMap<String, Object>> lottoTurnRangeCount(HashMap<String, Object> map) throws Exception;
	
	public int lottoTurnRangeMaxCount(HashMap<String, Object> map) throws Exception;
	
	public void lottoRecommendInsert(HashMap<String, Object> map) throws Exception;
}
