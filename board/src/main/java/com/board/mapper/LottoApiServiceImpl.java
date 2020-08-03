package com.board.mapper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("com.board.mapper.LottoApiServiceImpl")
public class LottoApiServiceImpl implements LottoApiMapper {
	
	@Resource(name="com.board.mapper.LottoApiMapper")
	LottoApiMapper lottoApiMapper;

	@Override
	public List<HashMap<String, Object>> lottoMainList(HashMap<String, Object> map) throws Exception {
		return lottoApiMapper.lottoMainList(map);
	}
	
	@Override
	public HashMap<String, Object> lottoWinnerType(HashMap<String, Object> map) throws Exception {
		return lottoApiMapper.lottoWinnerType(map);
	}

	@Override
	public int lottoLatestNum() throws Exception {
		return lottoApiMapper.lottoLatestNum();
	}

	@Override
	public int lottoAddrRadiusNum(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return lottoApiMapper.lottoAddrRadiusNum(map);
	}

	@Override
	public List<HashMap<String, Object>> lottoAddrRadius(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return lottoApiMapper.lottoAddrRadius(map);
	}

	@Override
	public HashMap<String, Object> lottoRecommendMain() throws Exception {
		return lottoApiMapper.lottoRecommendMain();
	}
	
	
	

}
