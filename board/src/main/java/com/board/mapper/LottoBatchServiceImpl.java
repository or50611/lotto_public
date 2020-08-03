package com.board.mapper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("com.board.mapper.LottoBatchServiceImpl")
public class LottoBatchServiceImpl implements LottoBatchMapper {
	
	@Resource(name="com.board.mapper.LottoBatchMapper")
	LottoBatchMapper lottoBatchMapper;

	@Override
	public int lottoCountPlus() throws Exception {
		return lottoBatchMapper.lottoCountPlus();
	}

	@Override
	public void lottoBatchHistory(HashMap<String, Object> map) throws Exception {
		lottoBatchMapper.lottoBatchHistory(map);
	}

	@Override
	public void lottoSaveMain(HashMap<String, Object> map) throws Exception {
		lottoBatchMapper.lottoSaveMain(map);
	}

	@Override
	public void lottoSaveRank(HashMap<String, Object> map) throws Exception {
		lottoBatchMapper.lottoSaveRank(map);
	}

	@Override
	public void lottoSaveAddrAll(HashMap<String, Object> map) throws Exception {
		lottoBatchMapper.lottoSaveAddrAll(map);
	}

	@Override
	public void lottoSaveAddrFirst(HashMap<String, Object> map) throws Exception {
		lottoBatchMapper.lottoSaveAddrFirst(map);
	}

	@Override
	public void lottoSaveAddrSecond(HashMap<String, Object> map) throws Exception {
		lottoBatchMapper.lottoSaveAddrSecond(map);
	}

	@Override
	public int lottoAddrSecondMaxDrawNo() throws Exception {
		return lottoBatchMapper.lottoAddrSecondMaxDrawNo();
	}

	@Override
	public HashMap<String, Object> lottoMainDrawNo() throws Exception {
		return lottoBatchMapper.lottoMainDrawNo();
	}

	@Override
	public List<HashMap<String, Object>> lottoRecommentList(HashMap<String, Object> map) throws Exception {
		return lottoBatchMapper.lottoRecommentList(map);
	}

	@Override
	public void lottoRecommentResult(HashMap<String, Object> map) throws Exception {
		lottoBatchMapper.lottoRecommentResult(map);
	}
	
	
	
	
	
	
	
	
	
}
