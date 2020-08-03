package com.board.mapper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("com.board.mapper.LottoAnalysisApiServiceImpl")
public class LottoAnalysisApiServiceImpl implements LottoAnalysisApiMapper {
	
	@Resource(name="com.board.mapper.LottoAnalysisApiMapper")
	LottoAnalysisApiMapper lottoMapper;

	
	@Override
	public int lottoMaxDrawNo() throws Exception {
		return lottoMapper.lottoMaxDrawNo();
	}

	@Override
	public List<HashMap<String, Object>> lottoAllRoundList() throws Exception {
		return lottoMapper.lottoAllRoundList();
	}

	@Override
	public List<HashMap<String, Object>> lottoTurnRangeCount(HashMap<String, Object> map) throws Exception {
		return lottoMapper.lottoTurnRangeCount(map);
	}

	@Override
	public int lottoTurnRangeMaxCount(HashMap<String, Object> map) throws Exception {
		return lottoMapper.lottoTurnRangeMaxCount(map);
	}

	@Override
	public List<HashMap<String, Object>> lottoNumExtraction30() throws Exception {
		return lottoMapper.lottoNumExtraction30();
	}

	@Override
	public HashMap<String, Object> lottoNumExtraction10() throws Exception {
		return lottoMapper.lottoNumExtraction10();
	}
	
	@Override
	public HashMap<String, Object> lottoNumPrevWeek01() throws Exception {
		return lottoMapper.lottoNumPrevWeek01();
	}
	
	@Override
	public HashMap<String, Object> lottoNumPrevNext01() throws Exception {
		return lottoMapper.lottoNumPrevWeek01();
	}

	@Override
	public void lottoRecommendInsert(HashMap<String, Object> map) throws Exception {
		lottoMapper.lottoRecommendInsert(map);
		
	}

	@Override
	public HashMap<String, Object> lottoNumExtraction02() throws Exception {
		return lottoMapper.lottoNumExtraction02();
	}
	
	
	
	
	
	

}
