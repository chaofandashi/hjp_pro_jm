import java.util.HashMap;

import net.sf.json.JSONObject;


public class beifen1 {
//	String url = "http://45.32.225.217:8080/examples/servlets/servlet/RequestParamExample";
//	String url = "http://httpbin.org/post";
	String url = "https://jdev.bhsgd.net/user/login/h5";
	public void sendMsg(){
//		HttpRespons hr = new HttpRespons();
//		HttpRequester request = new HttpRequester();
//		
//		HashMap<String,String> headers = new HashMap<String,String>();
//		HashMap<String,String> param = new HashMap<String,String>();
//		headers.put("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) CriOS/56.0.2924.75 Mobile/14E5239e Safari/602.1");
//		headers.put("Content-Type", "application/json");
//		headers.put("Connection", "keep-alive");
//		
//		param.put("lastname", "aa");//body�������
//		param.put("firstname", "bb");
//		String resStr = "";//������Ӧ���
//		String execptStr = "aa";
		
		
		HttpRespons hr = new HttpRespons();
		HttpRequester request = new HttpRequester();
		
		HashMap<String, String> header_params=new HashMap<String,String>();
		JSONObject login_Jparams =new JSONObject();
		
		header_params.put("Content-Type", "application/json");
		
		login_Jparams.put("phone","13076220975");
		login_Jparams.put("password","a9825c9802e96ee7422a93f387fa864fb0f740f7");
		
		String resStr = "";//������Ӧ���
		String execptStr = "aa";
		
		
		try {
			hr= request.sendPost(url,login_Jparams,header_params);
			resStr = hr.getContent();
			resStr=hr.getContent().toString();
			System.out.println(resStr);
			
			JSONObject json = JSONObject.fromObject(resStr);
			//����json
			
//			json.getJSONObject("headers").getString("User-Agent");
			System.out.println(json.toString());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		json_demo c =new json_demo();
		c.sendMsg();
	}

}