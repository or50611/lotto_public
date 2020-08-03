package com.board.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("com.board.mapper.LottoBatchMapper")
public interface LottoBatchMapper {

	//로또회차 + 1
	public int lottoCountPlus() throws Exception;
	//로또 인서트 로그
	public void lottoBatchHistory(HashMap<String, Object> map) throws Exception;
	
	public void lottoSaveMain(HashMap<String, Object> map) throws Exception;
	
	public void lottoSaveRank(HashMap<String, Object> map) throws Exception;
	//로또 전국판매지점 insert
	public void lottoSaveAddrAll(HashMap<String, Object> map) throws Exception;
	//로또 1등지점 insert
	public void lottoSaveAddrFirst(HashMap<String, Object> map) throws Exception;
	//로또 2등지점 insert
	public void lottoSaveAddrSecond(HashMap<String, Object> map) throws Exception;
	//로또 2등지점 insert
	public int lottoAddrSecondMaxDrawNo() throws Exception;
	
	public HashMap<String, Object> lottoMainDrawNo() throws Exception;
	
	public List<HashMap<String, Object>> lottoRecommentList(HashMap<String, Object> map) throws Exception;
	
	public void lottoRecommentResult(HashMap<String, Object> map) throws Exception;
	
}
