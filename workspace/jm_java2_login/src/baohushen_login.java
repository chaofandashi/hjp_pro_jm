import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import net.sf.json.JSONObject;
import java.io.*;
public class baohushen_login extends AbstractJavaSamplerClient{
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
	static int num0=0;
	static int num1=1;
	static int cookie_num=0;
	//控制登录次数
	static int login_num=0;
	static int run_times=1;
	//控制登录人数
	static int run_user=10;
	static String phone;
	static String pwd;
	static String login_cookie="登录cookie";
	static ArrayList<String> arrayList = new ArrayList<String>(); 
	static ArrayList<String> cookie_list = new ArrayList<String>(); 
	
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
    	String[] cookieArrayTrue = (String[]) cookie_list.toArray(new String[0]);
    	login_cookie=cookieArrayTrue[cookie_num];
    	cookie_num+=1;
//    	System.out.println(login_cookie);
    }
	//读取txt账号
    public static void read_txt(){
        try {  
            BufferedReader reader = new BufferedReader(new FileReader("D:\\login.txt"));//换成你的文件名 
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
    	System.out.println(phone);
    	System.out.println(pwd);
    }  
   	//默认初始化参数
	public Arguments getDefaultParameters(){
		return null;
	}
	public static void login(){
		if (arrayList.size()==0){
			baohushen_login.read_txt();
			System.out.println(arrayList);
		}
//		System.out.println(arrayList);
		baohushen_login.get_pwd();
		
		//加密后的密码
		pwd=baohushen_login.getsha1(phone);
		String json="{\"data\":{\"phone\":\""+phone+"\",\"password\": \""+pwd+"\"}}";
		String url="https://jdev.bhsgd.net/user/login/h5";
		//第一步创建HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//第二步创建httpPost对象
		HttpPost httpPost = new HttpPost(url);
		//第三步设置头信息
		httpPost.setHeader("Accept", "application/json"); 
		httpPost.setHeader("Content-Type", "application/json");
		//第四步给httpPost设置JSON格式的utf-8参数
		StringEntity entity = new StringEntity(json, "utf-8");
		//将POST参数以UTF-8编码并包装成表单实体对象
		entity.setContentEncoding("utf-8");
		//携带json
		httpPost.setEntity(entity);
		//创建响应处理器处理服务器响应内容
		//ResponseHandler<String> responseHandler = new BasicResponseHandler();
		//returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法
		
		try {
			//获取头信息cookie
			CloseableHttpResponse response = null;
			response = httpClient.execute(httpPost);
			String s=response.getFirstHeader("Set-Cookie").toString();
			String[] strs = s.split("; ");
			String[] strs2 = strs[0].split("=");
			login_cookie = strs2[1];
			System.out.println(login_cookie);
			baohushen_login.save_cookie();
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
	}
    public static void aciton(){
    	System.out.println("我已经开始跑了,这是我第"+run_times+"次");
		run_times++;
		//当取值cookie数为10 就变成0 从新取值
    	if (cookie_num==run_user){
    		cookie_num=0;
    	}
    }
	//实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LR中init的方法
	public void setupTest(JavaSamplerContext arg0) {
		System.out.println("我要开始登陆了");
		while (login_num<10){
			baohushen_login.login();
			login_num+=1;
		}
	}
	//action
	public SampleResult runTest(JavaSamplerContext arg0) {
		//获取登录的cookie
		baohushen_login.aciton();
		if (cookie_list.size()==0){
			baohushen_login.read_cookie();
			System.out.println(cookie_list);
		}
		baohushen_login.get_cookie();
		// TODO Auto-generated method stub
		//设置参数变量
		SampleResult results = null;//结果类，显示在查看结果树 
		results =new SampleResult();//生成响应结果对象
		//下单地址
		String order_url="https://jdev.bhsgd.net/integral/order/begin/63";
		String txt_json = "{\"data\":{\"input_contents\":[{\"content_id\":\"57\",\"value\":\"短信文本内容\"}],\"pic_contents\":[]}}";
		//第一步创建HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//第二步创建httpPost对象
		HttpPost httpPost = new HttpPost(order_url);
		//第三步设置头信息
		httpPost.setHeader("Accept", "application/json"); 
		httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Cookie","sid="+login_cookie);
//		System.out.println(login_cookie);
		//第四步给httpPost设置JSON格式的utf-8参数
		StringEntity entity = new StringEntity(txt_json, "utf-8");
		//将POST参数以UTF-8编码并包装成表单实体对象
		entity.setContentEncoding("utf-8");
		//携带json
		httpPost.setEntity(entity);
		//创建响应处理器处理服务器响应内容
		//ResponseHandler<String> responseHandler = new BasicResponseHandler();
		//returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法
		//声明返回值参数变量
		String resStr = "返回结果";
		try {
			//事物的开始，在聚合报告显示sample
			results.sampleStart();
			//创建响应处理器处理服务器响应内容
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			resStr = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法
			//解析成oldjunping数据
			JSONObject json1 = JSONObject.fromObject(resStr);
			//会显示在结果树的响应数据里  encoding:null
			results.setResponseData("结果是:\n"+json1,null);
			results.setDataType(SampleResult.TEXT);
			//会输出在Jmeter启动的命令窗口中
			System.out.println("输出到控制台:"+json1);
			//解析json  多层次  单层就直接json.getString("origin")
			results.setSuccessful(true);
		} catch (Exception e) {
			// TODO: handle exception
			results.setSuccessful(false);
		}finally {
			results.sampleEnd();
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		resStr=null;
		login_cookie=null;
		
		return results;
	}
	//end
	public void teardownTest(JavaSamplerContext arg0){
		System.out.println("我跑完啦，再见");
	}
}
