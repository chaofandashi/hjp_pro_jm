import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
//import java.util.Map;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;
import java.io.*;
//import com.google.gson.Gson;


public class Test2 {
	static String login_cookie="";
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
	static ArrayList<String> arrayList = new ArrayList<String>(); 
	static ArrayList<String> cookie_list = new ArrayList<String>();  
	static int num0=0;
	static int num1=1;
	static String phone;
	static String pwd;
	static int cookie_num=0;
	//读取账号密码
    public static void read_txt(){
        try {  
            BufferedReader reader = new BufferedReader(new FileReader("D:\\jm_script\\username.txt"));//换成你的文件名 
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉 
            String line = null;  
            while((line=reader.readLine())!=null){  
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分 
                String user =item[0];
                String pwd =item[1];
                arrayList.add(user);  
                arrayList.add(pwd); 
//                String last = item[item.length-1];//这就是你要的数据了 
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值  
            } 
            reader.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    //获取账号密码传值
    public static void get_pwd(){
    	String[] strArrayTrue = (String[]) arrayList.toArray(new String[0]);
    	phone=strArrayTrue[num0];
    	pwd=strArrayTrue[num1];
    	num0+=2;
    	num1+=2;
    }  
    //存cookie
    public static void save_cookie(){
    	FileWriter write =null;
    	String file_name="d:/login_cookie.txt";
    	try{
    		write = new FileWriter(file_name,true);//true 表示不覆盖 false 表示覆盖
    		write.write(login_cookie+"\r\n");
    	}
    	
    	catch(Exception e){
    		System.out.println(e);
    	}
    	finally{
    		try{
    			write.close();
    			write=null;
    		}
    		catch(Exception ex){
    			System.out.println(ex);
    		}
    	}
    }
    //读取cookie
    public static void read_cookie(){
        try {  
            BufferedReader reader = new BufferedReader(new FileReader("d:/login_cookie.txt"));//换成你的文件名 
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉 
            String line = null;  
            while((line=reader.readLine())!=null){  
            	cookie_list.add(line);
            } 
            reader.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    //获取cookie传值
    public static void get_cookie(){
    	String[] strArrayTrue = (String[]) cookie_list.toArray(new String[0]);
    	login_cookie=strArrayTrue[cookie_num];
    	cookie_num++;
    }
	//加密1
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
	//加密2
	public static String getsha1(String phone) {
		String str="9823\"rhdAGF3\'4T>%$@gadgu8@324bhs@mangohmASosrg89025r\'ng^&@12-\"~!$^%sbvuofsd"+phone;
		System.out.println("未加密前的密码"+str);
		if (phone == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    //执行下单
    public static String Action(String url) {
    	String json = "{\"data\":{\"input_contents\":[{\"content_id\":\"57\",\"value\":\"短信文本内容\"}],\"pic_contents\":[]}}";
		String returnValue = "这是默认返回值，接口调用失败";
		//第一步：创建HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//第二步：创建httpPost对象
        HttpPost httpPost = new HttpPost(url);	
        //第三步：给httpPost设置JSON格式的参数
        StringEntity requestEntity = new StringEntity(json,"utf-8");
        //将POST参数以UTF-8编码并包装成表单实体对象
        requestEntity.setContentEncoding("UTF-8");
        
        //设置头信息
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpPost.setHeader("Cookie","sid="+login_cookie);
        System.out.println(login_cookie);
        httpPost.setEntity(requestEntity);
        
        
		try{	 	
			//创建响应处理器处理服务器响应内容
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
	       //第四步：发送HttpPost请求，获取返回值
	       returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法
     	   //解析json  多层次  单层就直接json.getString("origin")
	       //json.getJSONObject("headers").getString("User-Agent");
	       JSONObject json1 = JSONObject.fromObject(returnValue);
	       System.out.println(json1.toString());
	       
		}
		 catch(Exception e)
		{
			 e.printStackTrace();
		}finally {
			login_cookie=null;
	       try {
			httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
		 //第五步：处理返回值
	     return returnValue;
	}
    //登录
    public static String login(){
//    	String phone;
//    	String pwd;
    	String json="{\"data\":{\"phone\":\""+phone+"\",\"password\": \""+pwd+"\"}}";
//    	System.out.println(json);
        String url="https://jdev.bhsgd.net/user/login/h5";
    	//第一步创建HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//第二步创建httpPost对象
		HttpPost httpPost  = new HttpPost(url);
		//第三步设置头信息
		httpPost.setHeader("Accept", "application/json"); 
		httpPost.setHeader("Content-Type", "application/json");
		//第四步给httpPost设置JSON格式的utf-8参数
		StringEntity entity = new StringEntity(json, "UTF-8");
		//携带json
		httpPost.setEntity(entity);      
		CloseableHttpResponse response = null;
    	try {
    		//发送请求 不需要范沪指
    		response =httpClient.execute(httpPost);
    		StatusLine status = response.getStatusLine();
    		
    		String s1= response.getFirstHeader("Set-Cookie").toString();
    		String[] strs = s1.split("; ");
    		String[] strs2 = strs[0].split("=");
//    		System.out.println(strs2[0]); 
//    		System.out.println(strs[0]);
    		login_cookie = strs2[1];
    	
   
//    		//从字符串中提取指定的字符串
//    		String s="ssfsfhshfsfjjs13293016789yfdiyifdsafyasif";
//    		//书写正则表达式
//    		String regex="[1][34579]\\d{9}";
//    		//将正则表达式转成正则对象
//    		Pattern pattern =Pattern.compile(regex);
//    		//正则对象与字符串匹配
//    		Matcher matcher=pattern.matcher(s);
//    		//匹配成功后打印出找到的结果            
//    		
//    		while(matcher.find()){
//    			System.out.println(matcher.group());
//    		}
    		
    			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
		       try {
					httpClient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return login_cookie;
    	
    }
    
    
    public static void main(String[] args) {
//      String json = "{'data':{'phone':'13076220975','password':'a9825c9802e96ee7422a93f387fa864fb0f740f7'}";	
    	String order_url="https://jdev.bhsgd.net/integral/order/begin/63";
    	System.out.print(arrayList.size());
    	if (arrayList.size()==0){
    		System.out.println("我艹 我没有东西");
    	}
    	
    	//第一次登录下单
        Test2.read_txt();
        Test2.get_pwd();
        pwd= Test2.getsha1(phone);
        Test2.login();
        Test2.save_cookie();

        //下单
//        Test2.Action(order_url);
        if (arrayList.size()==0){
    		System.out.println("我艹 我出不来了");
    	}
        
        
        //第二次登录下单
        Test2.read_txt();
        Test2.get_pwd();
        pwd= Test2.getsha1(phone);
        Test2.login();
        Test2.save_cookie();
        
        Test2.read_cookie();
        Test2.get_cookie();
        Test2.Action(order_url);
        Test2.read_cookie();
        Test2.get_cookie();
        Test2.Action(order_url);
        //下单
//        Test2.Action(order_url);
        


    }
}