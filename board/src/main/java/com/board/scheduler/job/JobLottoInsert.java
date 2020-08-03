package com.board.scheduler.job;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.board.mapper.LottoBatchServiceImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service("ScheduleService")
public class JobLottoInsert extends LottoBatchServiceImpl{
	
    public void executeJob() throws Exception {
    	lottoMainJob();
    }
    
    public void executeJob1() throws Exception {
    	lottoWinnerAddrInsertJob();
    }
    
    public void executeJob2() throws Exception {
    	lottoRecommendResultInsertJob();
    }
    
    public void lottoRecommendResultInsertJob() throws Exception {
		HashMap<String, Object> data = new HashMap<String, Object>();
		List<HashMap<String, Object>> recoList = new ArrayList<HashMap<String,Object>>();
		
		data = lottoMainDrawNo(); //최신회차 당첨번호
		
		recoList = lottoRecommentList(data); //최근 1주 추천번호 목록
		
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
		
		if(recoList != null) {
			for(HashMap<String, Object> recoData : recoList ) {
//				numMap = new LinkedHashMap<>();
				numTemp = new LinkedHashMap<>();
				
				Set<String> num = new HashSet<String>();
				num.add(recoData.get("num1").toString());
				num.add(recoData.get("num2").toString());
				num.add(recoData.get("num3").toString());
				num.add(recoData.get("num4").toString());
				num.add(recoData.get("num5").toString());
				num.add(recoData.get("num6").toString());
				
				
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
					if(k == 3) {
						rank5++;
					}else if(k == 4) {
						rank4++;
					}else if(k == 5 && bonus == 1) {
						rank2++;
					}else if(k == 5) {
						rank3++;
					}else if(k == 6) {
						rank1++;
					}
					resultList.add(numMap);
				}
			}
		}

//		System.out.println("resultList : "+resultList);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("drawNo", data.get("drawNo"));
		result.put("total", recoList.size());
		result.put("rank1", rank1);
		result.put("rank2", rank2);
		result.put("rank3", rank3);
		result.put("rank4", rank4);
		result.put("rank5", rank5);
		
		lottoRecommentResult(result);
    }
    
    public void lottoWinnerAddrInsertJob() throws Exception {
		
    	int drawNo = lottoWinnerDrawNo();
		int pageSize = 0;
		int maxDrawNo = lottoAddrSecondMaxDrawNo() + 1;
		
		if(drawNo < maxDrawNo) {
			return;
		}
		
		pageSize = lottoWinnerPageNo(drawNo);
		
		for(int i = 1; i <=pageSize; i++){
			Document doc = Jsoup.connect("https://www.dhlottery.co.kr/store.do"+lottoWinnerParameter(drawNo,i)).get();
			Elements eles2 = doc.select("div.group_content");
			HashMap<String, Object> map = null;
			String storeId = "";
			
			if(!eles2.get(0).select("tbody tr td").text().equals("조회 결과가 없습니다.")) {
				if(i == 1) {
					System.out.println("1등 데이터");
					for(Element element : eles2.get(0).select("tbody tr")) {
						map = new HashMap<String, Object>();
						
						storeId = element.select("td a").attr("onClick").replaceAll("[^0-9]", "");
						
						map.put("drawNo", drawNo);
						map.put("shopName", element.select("td").get(1).text());
						map.put("gameType", element.select("td").get(2).text());
						map.put("address", element.select("td").get(3).text());
						map.put("storeId", storeId);
						lottoSaveAddrFirst(map);
					}
				}
			}
			if(!eles2.get(1).select("tbody tr td").text().equals("조회 결과가 없습니다.")) {
				for(Element element : eles2.get(1).select("tbody tr")) {
					map = new HashMap<String, Object>();
					
					storeId = element.select("td a").attr("onClick").replaceAll("[^0-9]", "");
					map.put("drawNo", drawNo);
					map.put("shopName", element.select("td").get(1).text());
					map.put("address", element.select("td").get(2).text());
					map.put("storeId", storeId);
					lottoSaveAddrSecond(map);
				}
			}
		}
	}
    
    public void lottoMainJob() throws Exception{
        URL result;
        int lottoNum = lottoCountPlus();
        
		result = new URL("https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo="+Integer.toString(lottoNum));
		HttpsURLConnection urlConn = (HttpsURLConnection) result.openConnection();
    	
    	if(urlConn.getResponseCode() == 200) {
    		InputStreamReader isr = new InputStreamReader(urlConn.getInputStream(), "UTF-8");
    		JsonParser parser = new JsonParser();
    		JsonObject object = (JsonObject)parser.parse(isr);
    		
    		HashMap<String, Object> listMap = null;
    		HashMap<String, Object> map = null;
    		String repl = "";
    		if ("success".equals(object.get("returnValue").getAsString())) {
    			map = new HashMap<>();
    			
    			repl = StringUtils.replace(object.get("drwNoDate").getAsString(), "-", "");
    			map.put("drawNo", 			object.get("drwNo").getAsString());			//회차번호
    			map.put("drawDate", 		repl);											//추첨일자
    			map.put("num1", 			object.get("drwtNo1").getAsString());				//1번째 추첨번호
    			map.put("num2", 			object.get("drwtNo2").getAsString());				//2번째 추첨번호
    			map.put("num3", 			object.get("drwtNo3").getAsString());				//3번째 추첨번호
    			map.put("num4", 			object.get("drwtNo4").getAsString());				//4번째 추첨번호
    			map.put("num5", 			object.get("drwtNo5").getAsString());				//5번째 추첨번호
    			map.put("num6", 			object.get("drwtNo6").getAsString());				//6번째 추첨번호
    			map.put("bonusNum", 		object.get("bnusNo").getAsString());			//추첨번호
    			map.put("totalSellingPrice",object.get("totSellamnt").getAsString());	//총 판매금액
    			
    			System.out.println("map : "+map); 
    			lottoSaveMain(map); //메인 insert
    			
    			
    			
    			Document doc = Jsoup.connect("https://dhlottery.co.kr/gameResult.do?method=byWin&drwNo="+object.get("drwNo").getAsString()).get();
    			
    			Elements check = doc.select("div.win_result");
    			
    			if(check.select("strong").text().indexOf(object.get("drwNo").getAsString()) > -1){ //데이터 유무 체크
    				Elements eles = doc.select("table.tbl_data");
    				
    				for(Element element : eles.select("tbody tr")) {
    					listMap = new HashMap<>();
    					
    					String rank = "";
    					switch (element.select("td").get(0).text()) {
    					case "1등":
    						rank = "FIRST";
    						break;
    					case "2등":
    						rank = "SECOND";
    						break;
    					case "3등":
    						rank = "THIRD";
    						break;
    					case "4등":
    						rank = "FOURTH";
    						break;
    					case "5등":
    						rank = "FIFTH";
    						break;
    					}
    					
    					
    					listMap.put("drawNo", 				Integer.toString(lottoNum));
    					listMap.put("rank", 				rank);					//랭킹 (FIRST : 1등, SECOND : 2등 , THIRD : 3등 , FOURTH : 4등, FIFTH : 5등 )
    					listMap.put("sellingPriceByRank", 	element.select("td").get(1).select("strong").text().replace(",", "").replace("원", ""));	//등위별 총 당첨금액
    					listMap.put("winningPriceByRank", 	element.select("td").get(3).text().replace(",", "").replace("원", ""));	//각 당첨자 별 당첨금액
    					listMap.put("winningCnt", 			element.select("td").get(2).text().replace(",", ""));			//당첨자 수
    					
    					lottoSaveRank(listMap); //랭크별 insert
    					
    					
//    					System.out.println("회차 : "+Integer.toString(lottoNum));
//    					System.out.println("등수 :  "+element.select("td").get(0).text().replace("등", ""));
//    					System.out.println("총당첨금 :  "+element.select("td").get(1).select("strong").text().replace(",", "").replace("원", ""));
//    					System.out.println("당첨자수 :  "+element.select("td").get(2).text().replace(",", ""));
//    					System.out.println("1인당첨금 :  "+element.select("td").get(3).text().replace(",", "").replace("원", ""));
    				}
    			}
    			
    		}
		}
	}
    
	public String lottoWinnerParameter(int drwNo,int page) {
		String parameter = "?method=topStore&pageGubun=L645"
				+ "&method=topStore"
				+ "&nowPage="+Integer.toString(page)
				+ "&rankNo="
				+ "&gameNo=5133"
				+ "&drwNo=" + Integer.toString(drwNo)
				+ "&schKey=all"
				+ "&schVal=";
		return parameter;
	}
	
	public int lottoWinnerDrawNo() throws Exception{
		int drawNo = 0;
		Document doc = Jsoup.connect("https://www.dhlottery.co.kr/store.do"+lottoWinnerParameter(262,1)).get();
		Elements eles1 = doc.select("#drwNo > option");
		drawNo = Integer.parseInt(eles1.first().text());
		
		return drawNo;
	}
	
	public int lottoWinnerPageNo(int drawNo) throws Exception{
		Document doc = Jsoup.connect("https://www.dhlottery.co.kr/store.do"+lottoWinnerParameter(drawNo,1)).get();
		
		Elements ele1 = doc.select("#page_box a");
		
		String lastPage = ele1.last().text();
		
		int pageSize = Integer.parseInt(lastPage);
		
		return pageSize;
	}
}
