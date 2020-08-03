package com.board.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.mapper.LottoApiServiceImpl;

@RestController
@RequestMapping("/lottoApi")
public class LottoApiController{
 
    @Resource(name="com.board.mapper.LottoApiServiceImpl")
    LottoApiServiceImpl lottoService;
    
    @RequestMapping(value="/lottoNumCreat")
    public HashMap<String, Object> lottoNumCreat(HttpServletRequest request){
    	//로또 번호생성 ver 1.0
    	LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    	
    	String tempCreatNum = StringUtils.defaultString(request.getParameter("creatNum"), "1");
    	String gameKind 	= StringUtils.defaultString(request.getParameter("gameKind"), "allRandom");
		
		try {
			if(!isNum(tempCreatNum)) {
        		resultMap.put("resultCode", "LT40001");
        		map.put("messageCode", "숫자만 입력 가능합니다");
        		throw new Exception();
        	}
			
			int creatNum = Integer.parseInt(tempCreatNum); 
			
			if(creatNum > 30) {
        		resultMap.put("resultCode", "LT40005");
        		map.put("messageCode", "최대 30까지 입력 가능합니다");
        		throw new Exception();
        	}
			
			
			Set<Integer> set = null;
			HashMap<String, Object> listMap = null;
			List<Integer> lottoList = null;
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
			for(int i = 0; i < creatNum; i++) {
				listMap = new LinkedHashMap<String, Object>();
				
				set = new HashSet<Integer>();
				while( set.size() < 6){
					Double d = Math.random() * 45 + 1;
					set.add(d.intValue()); 
				}
				
				lottoList = new ArrayList<Integer>(set);
				Collections.sort(lottoList);
				
				int k = 1;
				for(int data : lottoList) {
					listMap.put("lottoNum"+k, data);
					k++;
				}
				list.add(listMap);
			}
			
			map.put("gameKind", gameKind);
			map.put("items", list);
			resultMap.put("resultCode", "LT21000");
			resultMap.put("statusCode", "200");
			resultMap.put("body", map);
			
			return resultMap;
			
		} catch (Exception e) {
			resultMap.put("statusCode", "400");
			resultMap.put("body", map);
		} finally {
			return resultMap;
		}
    }
    
    @RequestMapping(value="/lottoRecommendMain")
    public HashMap<String, Object> lottoRecommendMain(HttpServletRequest request){
    	LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    	HashMap<String, Object> dataMap = new HashMap<String, Object>();
    	LinkedHashMap<String, Object> dataResult = new LinkedHashMap<String, Object>();
    	
    	try {
    		
    		dataMap = lottoService.lottoRecommendMain();
    		
    		dataResult.put("drawNo", dataMap.get("drawNo"));
    		dataResult.put("total", dataMap.get("total"));
    		dataResult.put("rank1", dataMap.get("rank1"));
    		dataResult.put("rank2", dataMap.get("rank2"));
    		dataResult.put("rank3", dataMap.get("rank3"));
    		dataResult.put("rank4", dataMap.get("rank4"));
    		dataResult.put("rank5", dataMap.get("rank5"));
    		
    		resultMap.put("statusCode", "200");
    		resultMap.put("body", dataResult);
    		
    		return resultMap;
    		
    	} catch (Exception e) {
    		resultMap.put("statusCode", "400");
    		resultMap.put("body", map);
    	} finally {
    		return resultMap;
    	}
    }
    
	@RequestMapping(value="/lottoMain")
    public HashMap<String, Object> lottoMain(HttpServletRequest request){
		LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> paraMap = new LinkedHashMap<String, Object>();
    	List<HashMap<String, Object>> mainList = new ArrayList<HashMap<String,Object>>();
    	HashMap<String, Object> winTypeMap = new HashMap<String, Object>();
    	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    	
    	String drawNo = StringUtils.defaultString(request.getParameter("drawNo"), "");
    	
    	try {
    		if(drawNo.equals("")) {
    			drawNo = String.valueOf(lottoService.lottoLatestNum());
    		}
        	if(!isNum(drawNo)) {
        		resultMap.put("resultCode", "LT40001");
        		map.put("messageCode", "숫자만 입력 가능합니다");
        		throw new Exception();
        	}
    		
    		paraMap.put("drawNo", drawNo);
    		
    		mainList = lottoService.lottoMainList(paraMap);
    		
    		winTypeMap = lottoService.lottoWinnerType(paraMap);
    		
    		List<HashMap<String, Object>> list = new ArrayList<>();
    		LinkedHashMap<String, Object> listMap = null;
    		String rank = "";
    		if(mainList != null && mainList.size() > 0) {
    			map = new LinkedHashMap<String, Object>();
    			for(HashMap<String, Object> lottoMain : mainList) {
    				listMap = new LinkedHashMap<String, Object>();
    				
    				map.put("drawNo", 				lottoMain.get("drawNo"));
    				map.put("drawDate", 			lottoMain.get("drawDate"));
    				map.put("num1", 				lottoMain.get("num1"));
    				map.put("num2", 				lottoMain.get("num2"));
    				map.put("num3", 				lottoMain.get("num3"));
    				map.put("num4", 				lottoMain.get("num4"));
    				map.put("num5", 				lottoMain.get("num5"));
    				map.put("num6", 				lottoMain.get("num6"));
    				map.put("bonusNum", 				lottoMain.get("bonusNum"));
    				map.put("totalSellingPrice", 	lottoMain.get("totalSellingPrice"));
    				
    				if(lottoMain.get("rank") != null) {
    					rank = (String) lottoMain.get("rank");
    					
    					switch (rank) {
    					case "FIRST":
    						rank = "1";
    						break;
    					case "SECOND":
    						rank = "2";
    						break;
    					case "THIRD":
    						rank = "3";
    						break;
    					case "FOURTH":
    						rank = "4";
    						break;
    					case "FIFTH":
    						rank = "5";
    						break;
    					}
    					
    					listMap.put("rank", 				rank);
    					listMap.put("sellingPriceByRank", 	lottoMain.get("sellingPriceByRank"));
    					listMap.put("winningPriceByRank", 	lottoMain.get("winningPriceByRank"));
    					listMap.put("winningCnt", 			lottoMain.get("winningCnt"));
    					if(rank.equals("1")) {
    						listMap.put("auto", winTypeMap.get("auto"));
    						listMap.put("manual", winTypeMap.get("manual"));
    						listMap.put("semiAuto", winTypeMap.get("semiAuto"));
    					}
    					list.add(listMap);
    				}
    				
    			}
    			map.put("items", list);
    			resultMap.put("resultCode", "LT21000");
    			resultMap.put("statusCode", "200");
    			resultMap.put("body", map);
    		}else {
    			map.put("messageCode", "조회 결과가 없습니다.");
    			resultMap.put("resultCode", "LT40002");
        		resultMap.put("body", map);
        		throw new Exception();
    		}
    		
		} catch (Exception e) {
			resultMap.put("statusCode", "400");
			resultMap.put("body", map);
		} finally {
			return resultMap;
		}
    	
	}
    
    @RequestMapping(value="/lottoAddrRadius")
    public HashMap<String, Object> lottoAddrAllRadius(HttpServletRequest request) throws Exception{
    	LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	LinkedHashMap<String, Object> paraMap = new LinkedHashMap<String, Object>();
    	List<HashMap<String, Object>> mainList = new ArrayList<HashMap<String,Object>>();
    	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    	
    	String tempLat 		= StringUtils.defaultString(request.getParameter("lat"), "37.4685225");
    	String tempLng 		= StringUtils.defaultString(request.getParameter("lng"), "126.8943311");
    	String tempDist 	= StringUtils.defaultString(request.getParameter("dist"), "1000");
    	String winAddrYn 		= StringUtils.defaultString(request.getParameter("winAddrYn"), "N");
    	String tempCurPage 		= StringUtils.defaultString(request.getParameter("currentPage"), "");
    	String tempPageSize 	= StringUtils.defaultString(request.getParameter("pageSize"), "");
    	
    	double dist = 0;
    	double lat = 0;
    	double lng = 0;
    	
    	int currentPage = 0; 
    	int startNum 	= 0;
    	int pageSize 	= 0;

    	try {
    		if(!isNum(tempLat)) {
    			resultMap.put("resultCode", "LT40001");
    			map.put("messageCode", "숫자만 입력 가능합니다");
    			throw new Exception();
    		}else if(!isNum(tempLng)) {
    			resultMap.put("resultCode", "LT40001");
    			map.put("messageCode", "숫자만 입력 가능합니다");
    			throw new Exception();
    		}
    		
    		dist = Double.parseDouble(tempDist) / 1000;
    		lat = Double.parseDouble(tempLat);
    		lng = Double.parseDouble(tempLng);
    		
    		System.out.println("dist : "+dist);
    		
    		if(!tempCurPage.equals("") && !tempPageSize.equals("")) {
    			if(!isNum(tempCurPage)) {
    				resultMap.put("resultCode", "LT40001");
    				map.put("messageCode", "페이지 번호는 숫자만 입력 가능합니다");
    				throw new Exception();
    			}else if(!isNum(tempPageSize)) {
    				resultMap.put("resultCode", "LT40001");
    				map.put("messageCode", "페이지 사이즈는 숫자만 입력 가능합니다");
    				throw new Exception();
    			}
    			
    			pageSize = Integer.parseInt(tempPageSize);
    			
    			if(tempCurPage.equals("")) {
    				currentPage = 1;
    			}else {
    				currentPage = Integer.parseInt(tempCurPage);
    			}
    			paraMap.put("paging", "Y");
    		}else {
    			paraMap.put("paging", "N");
    		}
    		
    		
    		
    		
    		startNum = 0;
    		startNum = ( currentPage - 1) * pageSize;
    		
    		paraMap.put("startNum", startNum);
    		paraMap.put("pageSize", pageSize);
    		
    		paraMap.put("lat", 			lat);
    		paraMap.put("lng", 			lng);
    		paraMap.put("dist", 		dist);
    		paraMap.put("winAddrYn", 	winAddrYn);
    		
    		int addrCnt = lottoService.lottoAddrRadiusNum(paraMap);
    		
    		mainList = lottoService.lottoAddrRadius(paraMap);
    		
    		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
    		LinkedHashMap<String, Object> listMap = null;
    		if(mainList != null && mainList.size() > 0) {
    			map = new LinkedHashMap<String, Object>();
    			map.put("addrCnt", 	addrCnt);
//    			map.put("pageSize", 	pageSize);
    			for(HashMap<String, Object> lottoAddr : mainList) {
    				listMap = new LinkedHashMap<String, Object>();
    				listMap.put("storeId",		lottoAddr.get("storeId"));
    				listMap.put("shopName",		lottoAddr.get("shopName"));
    				listMap.put("address",		lottoAddr.get("address"));
    				listMap.put("addressDtl",		lottoAddr.get("addressDtl"));
    				listMap.put("tel",			lottoAddr.get("tel"));
    				listMap.put("lat",			lottoAddr.get("lat"));
    				listMap.put("lng",			lottoAddr.get("lng"));
    				listMap.put("distance",		lottoAddr.get("distance"));
    				listMap.put("dealSpeeto",	lottoAddr.get("dealSpeeto"));
    				listMap.put("deal645",		lottoAddr.get("deal645"));
    				listMap.put("deal520",		lottoAddr.get("deal520"));
    				listMap.put("winCnt",		lottoAddr.get("winCnt"));
    	   			
    				list.add(listMap);
    			}
    			map.put("items", 	list);
    			resultMap.put("resultCode", "LT21002");
    			resultMap.put("statusCode", "200");
    			resultMap.put("body", map);
    			
    		}else {
    			map.put("messageCode", "조회 결과가 없습니다.");
    			resultMap.put("resultCode", "LT40002");
    			resultMap.put("body", map);
    			throw new Exception();
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		resultMap.put("statusCode", "400");
    		resultMap.put("body", map);
    	} finally {
    		return resultMap;
    	}
    	
    }
    
    
    public boolean isNum(String data) {
    	try {
    		Double.parseDouble(data);
    		return true;
		} catch (Exception e) {
			return false; 
		}
    	
    }
}




