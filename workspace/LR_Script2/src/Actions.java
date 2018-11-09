import java.util.HashMap;

import lrapi.lr;

public class Actions {
	String url="http://45.32.225.217:8080/examples/servlets/servlet/RequestParamExample";
	
	public void sendMsg(){
		HttpRespons hr = new HttpRespons();//接收器
		HttpRequester request = new HttpRequester();//请求器
		//类似数组 用来存请求参数
		HashMap<String,String> headers = new HashMap<String,String>();
		HashMap<String,String> param = new HashMap<String,String>();
		//body请求参数
		param.put("lastname", "aa");
		param.put("firstname", "bb");
		String resStr = "";//保存响应结果
		String execptStr = "aa";
		
//		lr.start_transaction("send_msg_str");//事务开始  请求开始前写
		try {
			hr = request.sendPost(url,param,null);//发送请求
			resStr= hr.getContent();//得到响应内容
			System.out.println(hr.getContent());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		if(resStr.contains(execptStr)){//检查响应体重包含期望值，事务结束（成功）
			System.out.println("success");
//			lr.end_transaction("send_msg_str",lr.PASS);
		}else{
			System.out.println("fail");//事务结束 失败
//			lr.end_transaction("send_msg_str",lr.FAIL);
		}
	}
	
	public int init() throws Throwable {
		return 0;
	}//end of init

	public int action() throws Throwable {
		sendMsg();
		return 0;
	}//end of action
	
	public int end() throws Throwable {
		return 0;
	}//end of end
	
	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		Actions Actions =new Actions();
		Actions.action();
	}

}
