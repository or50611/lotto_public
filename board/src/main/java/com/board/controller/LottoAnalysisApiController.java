package com.board.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.mapper.LottoAnalysisApiServiceImpl;

@RestController
@RequestMapping("/lottoAnalysis")
public class LottoAnalysisApiController{
 
    @Resource(name="com.board.mapper.LottoAnalysisApiServiceImpl")
    LottoAnalysisApiServiceImpl lottoService;
    
    @RequestMapping(value="/lottoCreat")
    public HashMap<String, Object> lottoNumCreat(HttpServletRequest request){
    	//로또 번호생성 ver 1.3
    	LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    	
    	String tempCreatNum = StringUtils.defaultString(request.getParameter("creatNum"), "1");
    	String gameKind 	= StringUtils.defaultString(request.getParameter("gameKind"), "autoAnalysis");
		
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
			
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
			Set<Integer> set = null;
			HashMap<String, Object> listMap = null;
			List<Integer> lottoList = null;
			
			listMap = new LinkedHashMap<String, Object>();
			
			boolean creatFlag = true;
			
			List<Integer> numCheckList = null;
			int sumCheck = 0;
			int oddEvenCheck = 0;
			int numberGap = 0;
    		int numberTemp = 0;
    		
    		boolean sumFlag = false;
    		boolean oddEvenFlag = false;
    		boolean sumGapFlag = false;
    		boolean balanceFlag = false;
    		int numFlag = 0;
    		
    		List<HashMap<String, Object>> extlist = new ArrayList<HashMap<String,Object>>();
    		HashMap<String, Object> ext10 = new HashMap<String,Object>();
    		
			while (creatFlag) {
				
				extlist = lottoService.lottoNumExtraction30();
				
				numCheckList = new ArrayList<>();
				
				sumCheck = 0;
				oddEvenCheck = 0;
				numberGap = 0;
	    		numberTemp = 0;
	    		
				set = new HashSet<Integer>();
				
	    		ext10 = lottoService.lottoNumExtraction10();
	    		
	    		//최근 10회차에 출현하지 않은 번호중 랜덤 1개 추가
	    		if(ext10 != null) {
	    			set.add(Integer.valueOf(ext10.get("lottoNum").toString()));
	    		}
				
	    		//최근 30회차에 3~7번 출현한 번호중 랜덤 2개 추가
				for(HashMap<String, Object> extMap : extlist) {
					set.add(Integer.valueOf(extMap.get("num").toString()));
				}
				
				while( set.size() < 6){
					Double d = Math.random() * 45 + 1;
					set.add(d.intValue()); 
				}
				int num01 = 0;
				int num10 = 0;
				int num20 = 0;
				int num30 = 0;
				int num40 = 0;
				
				for(Integer tempNum : set) {
					if(tempNum > 0 && tempNum <= 10) {
						num01++;
					}else if(tempNum > 10 && tempNum <= 20) {
						num10++;
					}else if(tempNum > 20 && tempNum <= 30) {
						num20++;
					}else if(tempNum > 30 && tempNum <= 40) {
						num30++;
					}else {
						num40++;
					}
					sumCheck += tempNum;
					oddEvenCheck += tempNum % 2; 
					numCheckList.add(tempNum);
				}
				
	    		Collections.sort(numCheckList);
	    		for(int i = numCheckList.size() - 1; i > 0; i--) {
	    			numberTemp = numCheckList.get(i) - numCheckList.get(i - 1);
	    			numberGap += numberTemp;
	    		}
	    		//번호합
	    		if(sumCheck < 190  && sumCheck > 80) {
	    			sumFlag = true;
	    		}
	    		//번호차이
	    		if(numberGap >= 20 && numberGap < 44) {
	    			sumGapFlag = true;
	    		}
	    		//홀짝확인
	    		if(oddEvenCheck != 0 && oddEvenCheck != 6) {
	    			oddEvenFlag = true;
	    		}
	    		//번호밸런스
	    		if(num01 < 5 && num10 < 5 && num20 < 5 && num30 < 5 && num40 < 4) {
	    			balanceFlag = true;
	    		}
//	    		System.out.println("==============================");
//	    		System.out.println("번호합 : "+sumFlag);
//				System.out.println("번호차이 : "+sumGapFlag);
//				System.out.println("홀짝확인 : "+oddEvenFlag);
//				System.out.println("번호밸런스 : "+balanceFlag);
				
	    		//전체조건 체크
	    		if(sumFlag && sumGapFlag && oddEvenFlag && balanceFlag) {
					creatFlag = false;
				}else if (numFlag >= 100) {
					creatFlag = false;
				}
				numFlag++;
				
			}
			System.out.println("numFlag : "+numFlag);
			
			lottoList = new ArrayList<Integer>(set);
			Collections.sort(lottoList);
			
			int k = 1;
			for(int data : lottoList) {
				listMap.put("num"+k, data);
				k++;
			}
			list.add(listMap);
			
			lottoService.lottoRecommendInsert(listMap);
			
//			for(int i = 0; i < creatNum; i++) {
//			}
			
			map.put("gameKind", gameKind);
			map.put("items", list);
			resultMap.put("resultCode", "LT21000");
			resultMap.put("statusCode", "200");
			resultMap.put("body", map);
			
			return resultMap;
			
		} catch (Exception e) {
			resultMap.put("statusCode", "400");
			resultMap.put("body", map);
			return resultMap;
		}
    }
    
    @RequestMapping(value="/lottoNumAllSearch")
    public HashMap<String, Object> lottoAllSearch(HttpServletRequest request){
    	LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    	
		try {
			
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
			
			list = lottoService.lottoAllRoundList();
			
			LinkedHashMap<String, Object> listMap = null;
			List<HashMap<String, Object>> resultList = new ArrayList<>();
			
			for(HashMap<String, Object> data : list ) {
				listMap = new LinkedHashMap<>();
				
				listMap.put("drawNo",			data.get("drawNo"));
				listMap.put("num1",				data.get("num1"));
				listMap.put("num2",				data.get("num2"));
				listMap.put("num3",				data.get("num3"));
				listMap.put("num4",				data.get("num4"));
				listMap.put("num5",				data.get("num5"));
				listMap.put("num6",				data.get("num6"));
				listMap.put("bonusNum",			data.get("bonusNum"));
				listMap.put("totalSellingPrice",data.get("totalSellingPrice"));
				listMap.put("drawDate",			data.get("drawDate"));
				listMap.put("rankFirst",		data.get("rankFirst"));
				listMap.put("rankSecond",		data.get("rankSecond"));
				listMap.put("rankThird",		data.get("rankThird"));
				listMap.put("rankFourth",		data.get("rankFourth"));
				listMap.put("rankFifth",		data.get("rankFifth"));
				
				resultList.add(listMap);
			}
			
			map.put("items", resultList);
			resultMap.put("resultCode", "LT21000");
			resultMap.put("statusCode", "200");
			resultMap.put("body", map);
			
			return resultMap;
			
		} catch (Exception e) {
			resultMap.put("statusCode", "400");
			resultMap.put("body", map);
			return resultMap;
		}
    }
    
    @RequestMapping(value="/lottoNumAllSearchA")
    public HashMap<String, Object> lottoAllSearchA(HttpServletRequest request){
    	LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    	
    	try {
    		
    		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
    		
    		list = lottoService.lottoAllRoundList();
    		
    		LinkedHashMap<String, Object> listMap = null;
    		List<HashMap<String, Object>> resultList = new ArrayList<>();
    		
    		for(HashMap<String, Object> data : list ) {
    			listMap = new LinkedHashMap<>();
    			
    			listMap.put("drawNo",			data.get("drawNo"));
    			listMap.put("num1",				data.get("num1"));
    			listMap.put("num2",				data.get("num2"));
    			listMap.put("num3",				data.get("num3"));
    			listMap.put("num4",				data.get("num4"));
    			listMap.put("num5",				data.get("num5"));
    			listMap.put("num6",				data.get("num6"));
    			listMap.put("bonusNum",			data.get("bonusNum"));
    			listMap.put("totalSellingPrice",data.get("totalSellingPrice"));
    			listMap.put("drawDate",			data.get("drawDate"));
    			listMap.put("rankFirst",		data.get("rankFirst"));
    			listMap.put("rankSecond",		data.get("rankSecond"));
    			listMap.put("rankThird",		data.get("rankThird"));
    			listMap.put("rankFourth",		data.get("rankFourth"));
    			listMap.put("rankFifth",		data.get("rankFifth"));
    			
    			resultList.add(listMap);
    		}
    		
//			map.put("gameKind", gameKind);
    		map.put("items", resultList);
    		resultMap.put("resultCode", "LT21000");
    		resultMap.put("statusCode", "200");
    		resultMap.put("body", map);
    		
    		return resultMap;
    		
    	} catch (Exception e) {
    		resultMap.put("statusCode", "400");
    		resultMap.put("body", map);
    		return resultMap;
    	}
    }
    
    @RequestMapping(value="/lottoPastRewardSearch")
    public HashMap<String, Object> lottoNumAppearBasic(HttpServletRequest request){
    	LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    	
    	List<HashMap<String, Object>> mainList = new ArrayList<HashMap<String,Object>>();
    	List<HashMap<String, Object>> rangeList = new ArrayList<HashMap<String,Object>>();
    	
    	String num1 	= StringUtils.defaultString(request.getParameter("num1"), "13");
    	String num2 	= StringUtils.defaultString(request.getParameter("num2"), "26");
    	String num3 	= StringUtils.defaultString(request.getParameter("num3"), "12");
    	String num4 	= StringUtils.defaultString(request.getParameter("num4"), "10");
    	String num5 	= StringUtils.defaultString(request.getParameter("num5"), "8");
    	String num6 	= StringUtils.defaultString(request.getParameter("num6"), "9");
    	
    	String turnRange 	= StringUtils.defaultString(request.getParameter("turnRange"), "");
    	
		
    	try {
    		Set<String> num = new HashSet<String>();
    		num.add(num1);
    		num.add(num2);
    		num.add(num3);
    		num.add(num4);
    		num.add(num5);
    		num.add(num6);
    		
    		for(String obj : num){ 
    			if(!isNum(obj)) {
    				resultMap.put("resultCode", "LT40001");
            		map.put("messageCode", "숫자만 입력 가능합니다.");
            		throw new Exception();
    			}else if(Integer.parseInt(obj) > 45 || Integer.parseInt(obj) < 1) {
    				resultMap.put("resultCode", "LT40003");
            		map.put("messageCode", "1~45 숫자만 가능합니다.");
            		throw new Exception();
    			}
    		}
    		
    		if(num.size() != 6) {
    			resultMap.put("resultCode", "LT40004");
        		map.put("messageCode", "중복된 값이 있습니다.");
        		throw new Exception();
    		}
    		
    		HashMap<String, Object> paraMap = new HashMap<>();
    		
    		if(!turnRange.equals("")) {
    			paraMap.put("turnRange", Integer.valueOf(turnRange));
    		}else {
    			paraMap.put("turnRange", 9999);
    		}
    		
    		ArrayList<Integer> numItem = new ArrayList<>();
    		
    		int temp1 = 0;
    		int numberSum = 0;
    		int even = 0;
    		int odd = 0;
    		
    		for(String tempNum : num) {
    			temp1 = Integer.valueOf(tempNum);
    			numItem.add(temp1);
    			numberSum += temp1;
    			
    			if(temp1 % 2 == 0) {
    				even++;
    			}else {
    				odd++;	
    			}
    		}
    		
    		Collections.sort(numItem);
    		int numberGap = 0;
    		int numberTemp = 0;
    		for(int i = numItem.size() - 1; i > 0; i--) {
    			System.out.println(numItem.get(i));
    			numberTemp = numItem.get(i) - numItem.get(i - 1);
    			numberGap += numberTemp;
    		}

    		paraMap.put("numItem", numItem);
    		
    		int maxValue = lottoService.lottoTurnRangeMaxCount(paraMap);
    		
    		rangeList = lottoService.lottoTurnRangeCount(paraMap);
    		
    		HashMap<String, Object> numTemp1 = null;
    		List<HashMap<String, Object>> resultList1 = new ArrayList<HashMap<String,Object>>();
    		for(HashMap<String, Object> data : rangeList ) {
    			numTemp1 = new LinkedHashMap<>();
    			
    			numTemp1.put("num", data.get("num"));
    			numTemp1.put("cnt", data.get("cnt"));
    			numTemp1.put("bonusCnt", data.get("bonusCnt"));
    			
    			resultList1.add(numTemp1);
    			
    		}
    		
    		System.out.println(rangeList);
    		
    		mainList = lottoService.lottoAllRoundList();
    		
    		
    		LinkedHashMap<String, Object> numResult = null;
    		List<HashMap<String, Object>> numList = null;
    		List<HashMap<String, Object>> resultList = null;
    		
    		HashMap<String, Object> numMap = null;
    		HashMap<String, Object> numTemp = null;
    		resultList = new ArrayList<HashMap<String,Object>>();
    		
    		int rank1 = 0;
    		int rank2 = 0;
    		int rank3 = 0;
    		int rank4 = 0;
    		int rank5 = 0;
    		
    		for(HashMap<String, Object> data : mainList ) {
    			numMap = new LinkedHashMap<>();
    			numTemp = new LinkedHashMap<>();
    			
    			numTemp.put("num1", data.get("num1"));
    			numTemp.put("num2", data.get("num2"));
    			numTemp.put("num3", data.get("num3"));
    			numTemp.put("num4", data.get("num4"));
    			numTemp.put("num5", data.get("num5"));
    			numTemp.put("num6", data.get("num6"));
    			numTemp.put("bonusNum", data.get("bonusNum"));
    			
    			
    			int k = 0;
    			int bonus = 0;
    			int numSum = 0;
    			int count = 1;
    			numList = new ArrayList<HashMap<String,Object>>();
    			
    			Iterator<String> keys = numTemp.keySet().iterator();
    	        while( keys.hasNext() ){
    	            String key = keys.next();
    	            numResult = new LinkedHashMap<String, Object>();
    	            numResult.put("num", numTemp.get(key));
    	            numResult.put("flag", false);
    	            if(!key.equals("bonusNum")) {
    	            	numSum += Integer.parseInt(numTemp.get(key).toString());
    	            	numResult.put("type", String.valueOf(count));
    	            }else {
    	            	numResult.put("type", "bonus");    	            	
    	            }
    	            for(String userNum : num) {
        				if(userNum.equals(numTemp.get(key).toString().trim()) && !key.equals("bonusNum")) {
        					numResult.put("flag", true);
        					k++;
        					break;
        				}else if(userNum.equals(numTemp.get(key).toString().trim()) && key.equals("bonusNum")) {
        					numResult.put("flag", true);
        					bonus++;
        					break;
        				}
        			}
    	            count++;
    	            numList.add(numResult);
    	        }
    			if(k > 2) {
    				numMap.put("drawNo", data.get("drawNo"));
    				numMap.put("drawDate", data.get("drawDate"));
    				numMap.put("totalSellingPrice", data.get("totalSellingPrice"));
    				numMap.put("numberSum", numSum);
    				if(k == 3) {
    					numMap.put("rank", "5등");
    					numMap.put("reward", data.get("rankFifth"));
    					rank5++;
    				}else if(k == 4) {
    					numMap.put("rank", "4등");
    					numMap.put("reward", data.get("rankFourth"));
    					rank4++;
    				}else if(k == 5 && bonus == 1) {
    					numMap.put("rank", "2등");
    					numMap.put("reward", data.get("rankSecond"));
    					rank2++;
    				}else if(k == 5) {
    					numMap.put("rank", "3등");
    					numMap.put("reward", data.get("rankThird"));
    					rank3++;
    				}else if(k == 6) {
    					numMap.put("rank", "1등");
    					numMap.put("reward", data.get("rankFirst"));
    					rank1++;
    				}
    				numMap.put("lottoNum", numList);
    				resultList.add(numMap);
    			}
    		}
    		
			map.put("rank1", rank1);
			map.put("rank2", rank2);
			map.put("rank3", rank3);
			map.put("rank4", rank4);
			map.put("rank5", rank5);
			map.put("numSum", numberSum);
			map.put("numEven", even);
			map.put("numOdd", odd);
			map.put("numGap", numberGap);
			map.put("maxValue", maxValue);
			map.put("turnRange", resultList1);
			map.put("items", resultList);
			resultMap.put("resultCode", "LT21000");
			resultMap.put("statusCode", "200");
			resultMap.put("body", map);
    	
	    	return resultMap;
			
		} catch (Exception e) {
			resultMap.put("statusCode", "400");
			resultMap.put("body", map);
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
    
    public int randomRange(int a, int b) {
    	return  (int) (Math.random() * (b - a + 1)) + a;
    }
}




