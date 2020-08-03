//package com.board.scheduler.job;
//
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.HashMap;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
//import org.json.simple.JSONObject;
//import org.json.simple.JSONValue;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import com.board.mapper.LottoBatchServiceImpl;
//
//@Service("ScheduleService")
//public class JobLottoInsert_bak extends LottoBatchServiceImpl{
//	
//    public void executeJob() throws Exception {
//        try {
//			TrustManager[] trustAllCerts = new TrustManager[]{
//				new X509TrustManager() {
//				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//					return null;
//				}
//				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
//				}
//				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
//				}
//			}
//			};
//
//			SSLContext sc = SSLContext.getInstance("TLS");
//			sc.init(null, trustAllCerts, new java.security.SecureRandom());
//			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//
//			HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
//				public boolean verify(String string,SSLSession ssls) {
//					return true;
//				}
//			});
//
//			HashMap<String, Object> map = null;
//			HashMap<String, Object> historyMap = null;
//			
//	        URL result;
//	        String repl = "";
//	        
//	        int lottoNum = lottoCountPlus();
//	        System.out.println(lottoNum);
//	        
//	        result = new URL("https://www.nlotto.co.kr/common.do?method=getLottoNumber&drwNo=" + Integer.toString(lottoNum));
//        	URLConnection urlConn = result.openConnection();
//        	urlConn.setRequestProperty("User-Agent", "Mozilla/5.0"); 
//        	InputStreamReader isr = new InputStreamReader(urlConn.getInputStream(), "UTF-8");
//        	JSONObject object = (JSONObject)JSONValue.parse(isr);
//        	
//        	System.out.println("읽기 결과 : " + object.toJSONString());
//        	if ("success".equals(object.get("returnValue"))) {
//        		map = new HashMap<String, Object>();
//        		repl = StringUtils.replace((String)object.get("drwNoDate"), "-", "");
//        		
//        		map.put("drwNo", 			object.get("drwNo"));
//        		map.put("totSellamnt", 		object.get("totSellamnt"));
//        		map.put("drwNoDate", 		repl);
//        		map.put("firstWinamnt", 	object.get("firstWinamnt"));
//        		map.put("firstPrzwnerCo", 	object.get("firstPrzwnerCo"));
//        		map.put("firstAccumamnt", 	object.get("firstAccumamnt"));
//        		
//        		map.put("drwtNo1", 			object.get("drwtNo1"));
//        		map.put("drwtNo2", 			object.get("drwtNo2"));
//        		map.put("drwtNo3", 			object.get("drwtNo3"));
//        		map.put("drwtNo4", 			object.get("drwtNo4"));
//        		map.put("drwtNo5", 			object.get("drwtNo5"));
//        		map.put("drwtNo6", 			object.get("drwtNo6"));
//        		map.put("bnusNo", 			object.get("bnusNo"));
//        		
//        		lottoSavePro(map); //인서트
//        		
//        		
//        		historyMap = new HashMap<String, Object>();
//        		historyMap.put("contents", object.get("drwNo")+"회 인서트 성공");
//        		lottoBatchHistory(historyMap);
//        	}else {
//        		//로그테스트 나중에 지움
//        		historyMap = new HashMap<String, Object>();
//        		historyMap.put("contents", object.get("drwNo")+"회 인서트 실패");
//        		lottoBatchHistory(historyMap);
//        	}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
//}
