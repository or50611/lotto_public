//package com.board.controller;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Set;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//public class ApiTest {
//	
//	public static void main(String[] args) throws Exception {
//		lottoTest();
////		lottoWinnerTest();
//	}
//	
//	public static void lottoTest() {
//		
//		Set<Integer> set = null;
//		HashMap<String, Object> listMap = null;
//		List<Integer> lottoList = null;
//		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
//		listMap = new LinkedHashMap<String, Object>();
//		
//		set = new HashSet<Integer>();
//		while( set.size() < 6){
//			Double d = Math.random() * 45 + 1;
//			set.add(d.intValue()); 
//		}
//		
//		lottoList = new ArrayList<Integer>(set);
//		Collections.sort(lottoList);
//		
//		int k = 1;
//		for(int data : lottoList) {
//			listMap.put("lottoNum"+k, data);
//			k++;
//		}
//		list.add(listMap);
//		
//		System.out.println(list);
//	}
//	
//	public static void lottoWinnerTest() throws Exception {
//		
//		int drawNo = lottoWinnerDrawNo();
//		
//		int pageSize = 0;
//		
//		pageSize = lottoWinnerPageNo();
//		
//		System.out.println("===main drwNo : "+drawNo+", pageSize : "+pageSize);
//		for(int i = 1; i <=pageSize; i++){
//			System.out.println("=====sub drwNo : "+drawNo+", pageSize : "+i);
//			Document doc = Jsoup.connect("https://www.dhlottery.co.kr/store.do"+lottoWinnerParameter(drawNo,i)).get();
//			
//			Elements eles2 = doc.select("div.group_content");
//			
//			
//			HashMap<String, Object> map = null;
//			String storeId = "";
//			if(!eles2.get(0).select("tbody tr td").text().contains("조회 결과가 없습니다.")) {
//				if(i == 1) {
//					System.out.println("1등 데이터");
//					for(Element element : eles2.get(0).select("tbody tr")) {
//						map = new HashMap<String, Object>();
//						
//						storeId = element.select("td a").attr("onClick").replaceAll("[^0-9]", "");
//						
//						map.put("drawNo", drawNo);
//						map.put("shopName", element.select("td").get(1).text());
//						map.put("gameType", element.select("td").get(2).text());
//						map.put("address", element.select("td").get(3).text());
//						map.put("storeId", storeId);
//						System.out.println(map);
//					}
//					
//				}
//				
//			}
//			System.out.println("2등 데이터");
//			for(Element element : eles2.get(1).select("tbody tr")) {
//				map = new HashMap<String, Object>();
//				
//				storeId = element.select("td a").attr("onClick").replaceAll("[^0-9]", "");
//				
//				map.put("drawNo", drawNo);
//				map.put("shopName", element.select("td").get(1).text());
//				map.put("address", element.select("td").get(2).text());
//				map.put("storeId", storeId);
////				serviceImpl.lottoSaveAddrSecond(map);
//				System.out.println(map);
//			}
//		}
//	}
//	
//	
//	public static String lottoWinnerParameter(int drwNo,int page) {
//		String parameter = "?method=topStore&pageGubun=L645"
//				+ "&method=topStore"
//				+ "&nowPage="+Integer.toString(page)
//				+ "&rankNo="
//				+ "&gameNo=5133"
//				+ "&drwNo=" + Integer.toString(drwNo)
//				+ "&schKey=all"
//				+ "&schVal=";
//		return parameter;
//	}
//	
//	public static int lottoWinnerDrawNo() throws Exception{
//		int drawNo = 0;
//		Document doc = Jsoup.connect("https://www.dhlottery.co.kr/store.do"+lottoWinnerParameter(262,1)).get();
//		Elements eles1 = doc.select("#drwNo > option");
//		drawNo = Integer.parseInt(eles1.first().text());
//		
//		return drawNo;
//	}
//	
//	public static int lottoWinnerPageNo() throws Exception{
//		Document doc = Jsoup.connect("https://www.dhlottery.co.kr/store.do"+lottoWinnerParameter(262,1)).get();
//		
//		Elements ele1 = doc.select("#page_box a");
//		
//		String lastPage = ele1.last().text();
//		
//		int pageSize = Integer.parseInt(lastPage);
//		
//		return pageSize;
//	}
//	
//}
