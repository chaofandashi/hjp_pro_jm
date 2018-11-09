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
	//���Ƶ�¼����
	static int login_num=0;
	static int run_times=1;
	//���Ƶ�¼����
	static int run_user=10;
	static String phone;
	static String pwd;
	static String login_cookie="��¼cookie";
	static ArrayList<String> arrayList = new ArrayList<String>(); 
	static ArrayList<String> cookie_list = new ArrayList<String>(); 
	
	//����1
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// ������ת����ʮ�����Ƶ��ַ�����ʽ
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
	//����2
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
    //��cookie
    public static void save_cookie(){
    	FileWriter write =null;
    	String file_name="d:/login_cookie.txt";
    	try{
    		write = new FileWriter(file_name,true);//true ��ʾ������ false ��ʾ����
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
    //��ȡcookie
    public static void read_cookie(){
        try {  
            BufferedReader reader = new BufferedReader(new FileReader("d:/login_cookie.txt"));//��������ļ��� 
//            reader.readLine();//��һ����Ϣ��Ϊ������Ϣ������,�����Ҫ��ע�͵� 
            String line = null;  
            while((line=reader.readLine())!=null){  
            	
				cookie_list.add(line);
            } 
            reader.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    //��ȡcookie��ֵ
    public static void get_cookie(){
    	String[] cookieArrayTrue = (String[]) cookie_list.toArray(new String[0]);
    	login_cookie=cookieArrayTrue[cookie_num];
    	cookie_num+=1;
//    	System.out.println(login_cookie);
    }
	//��ȡtxt�˺�
    public static void read_txt(){
        try {  
            BufferedReader reader = new BufferedReader(new FileReader("D:\\login.txt"));//��������ļ��� 
//            reader.readLine();//��һ����Ϣ��Ϊ������Ϣ������,�����Ҫ��ע�͵� 
            String line = null;  
            while((line=reader.readLine())!=null){  
                String item[] = line.split(",");//CSV��ʽ�ļ�Ϊ���ŷָ����ļ���������ݶ����з� 
                String user =item[0];
                String pwd =item[1];
                arrayList.add(user);  
                arrayList.add(pwd); 
//                String last = item[item.length-1];//�������Ҫ�������� 
                //int value = Integer.parseInt(last);//�������ֵ������ת��Ϊ��ֵ  
            } 
            reader.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    //��ȡ�˺����봫ֵ
    public static void get_pwd(){
    	String[] strArrayTrue = (String[]) arrayList.toArray(new String[0]);
    	phone=strArrayTrue[num0];
    	pwd=strArrayTrue[num1];
    	num0+=2;
    	num1+=2;
    	System.out.println(phone);
    	System.out.println(pwd);
    }  
   	//Ĭ�ϳ�ʼ������
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
		
		//���ܺ������
		pwd=baohushen_login.getsha1(phone);
		String json="{\"data\":{\"phone\":\""+phone+"\",\"password\": \""+pwd+"\"}}";
		String url="https://jdev.bhsgd.net/user/login/h5";
		//��һ������HttpClient����
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//�ڶ�������httpPost����
		HttpPost httpPost = new HttpPost(url);
		//����������ͷ��Ϣ
		httpPost.setHeader("Accept", "application/json"); 
		httpPost.setHeader("Content-Type", "application/json");
		//���Ĳ���httpPost����JSON��ʽ��utf-8����
		StringEntity entity = new StringEntity(json, "utf-8");
		//��POST������UTF-8���벢��װ�ɱ�ʵ�����
		entity.setContentEncoding("utf-8");
		//Я��json
		httpPost.setEntity(entity);
		//������Ӧ�����������������Ӧ����
		//ResponseHandler<String> responseHandler = new BasicResponseHandler();
		//returnValue = httpClient.execute(httpPost,responseHandler); //���ӿڻ�ȡ����ֵʱ�������ô˷���
		
		try {
			//��ȡͷ��Ϣcookie
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
    	System.out.println("���Ѿ���ʼ����,�����ҵ�"+run_times+"��");
		run_times++;
		//��ȡֵcookie��Ϊ10 �ͱ��0 ����ȡֵ
    	if (cookie_num==run_user){
    		cookie_num=0;
    	}
    }
	//ʵ������ʱÿ���߳̽�ִ��һ�Σ��ڲ��Է�������ǰִ�У�������LR��init�ķ���
	public void setupTest(JavaSamplerContext arg0) {
		System.out.println("��Ҫ��ʼ��½��");
		while (login_num<10){
			baohushen_login.login();
			login_num+=1;
		}
	}
	//action
	public SampleResult runTest(JavaSamplerContext arg0) {
		//��ȡ��¼��cookie
		baohushen_login.aciton();
		if (cookie_list.size()==0){
			baohushen_login.read_cookie();
			System.out.println(cookie_list);
		}
		baohushen_login.get_cookie();
		// TODO Auto-generated method stub
		//���ò�������
		SampleResult results = null;//����࣬��ʾ�ڲ鿴����� 
		results =new SampleResult();//������Ӧ�������
		//�µ���ַ
		String order_url="https://jdev.bhsgd.net/integral/order/begin/63";
		String txt_json = "{\"data\":{\"input_contents\":[{\"content_id\":\"57\",\"value\":\"�����ı�����\"}],\"pic_contents\":[]}}";
		//��һ������HttpClient����
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//�ڶ�������httpPost����
		HttpPost httpPost = new HttpPost(order_url);
		//����������ͷ��Ϣ
		httpPost.setHeader("Accept", "application/json"); 
		httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Cookie","sid="+login_cookie);
//		System.out.println(login_cookie);
		//���Ĳ���httpPost����JSON��ʽ��utf-8����
		StringEntity entity = new StringEntity(txt_json, "utf-8");
		//��POST������UTF-8���벢��װ�ɱ�ʵ�����
		entity.setContentEncoding("utf-8");
		//Я��json
		httpPost.setEntity(entity);
		//������Ӧ�����������������Ӧ����
		//ResponseHandler<String> responseHandler = new BasicResponseHandler();
		//returnValue = httpClient.execute(httpPost,responseHandler); //���ӿڻ�ȡ����ֵʱ�������ô˷���
		//��������ֵ��������
		String resStr = "���ؽ��";
		try {
			//����Ŀ�ʼ���ھۺϱ�����ʾsample
			results.sampleStart();
			//������Ӧ�����������������Ӧ����
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			resStr = httpClient.execute(httpPost,responseHandler); //���ӿڻ�ȡ����ֵʱ�������ô˷���
			//������oldjunping����
			JSONObject json1 = JSONObject.fromObject(resStr);
			//����ʾ�ڽ��������Ӧ������  encoding:null
			results.setResponseData("�����:\n"+json1,null);
			results.setDataType(SampleResult.TEXT);
			//�������Jmeter�������������
			System.out.println("���������̨:"+json1);
			//����json  ����  �����ֱ��json.getString("origin")
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
		System.out.println("�����������ټ�");
	}
}
