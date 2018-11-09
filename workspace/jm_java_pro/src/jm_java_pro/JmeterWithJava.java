
package jm_java_pro;
import java.util.HashMap;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;


public class JmeterWithJava extends AbstractJavaSamplerClient{
	
	//��ʼ��Ĭ�ϲ���
	public Arguments getDefaultParameters(){
		Arguments params = new Arguments();
		params.addArgument("firstname","firstname");
		params.addArgument("lastname","lastname");
		params.addArgument("url","http://45.32.225.217:8080/examples/servlets/servlet/RequestParamExample");
		return params;
	}
	//��ʼ��������ʵ������ʱÿ���߳̽�ִ��һ�Σ��ڲ��Է�������ǰִ�У�������LR��init�ķ���
	public void setupTest(JavaSamplerContext arg0){
		
	}
	//ѹ�ⲿ�֣�����lr��action
	public SampleResult runTest(JavaSamplerContext arg0){
		
		//���ò�������
		SampleResult results = null;//����࣬��ʾ�ڲ鿴����� 
		String firstname="";
		String lastname = "";
		String url = "";
		HashMap<String,String> myParams = new HashMap<String,String>();
		HttpRespons hr = new HttpRespons();
		HttpRequester request = new HttpRequester();
		
		//��ȡ���������������ҳ������ key
		firstname = arg0.getParameter("firstname");
		lastname = arg0.getParameter("lastname");
		url = arg0.getParameter("url");
		//��������ֵ��������
		String resStr=null;

		//������Ӧ�������
		results =new SampleResult();

		//��װ�����ͷ�������
		try {
			//��������HashMap
			myParams.put("lastname", lastname);
			myParams.put("firstname", firstname);

			//����Ŀ�ʼ���ھۺϱ�����ʾsample
			results.sampleStart();

			//��������
			hr = request.sendPost(url,myParams,null);
			//��ȡ����ֵ
			resStr=hr.getContent();

			//����ʾ�ڽ��������Ӧ������  encoding:null
			results.setResponseData("�����:"+resStr,null);
			results.setDataType(SampleResult.TEXT);

			//�������Jmeter�������������
			System.out.println("���������̨:"+resStr);
			results.setSuccessful(true);
		} catch (Exception e) {
			// TODO: handle exception
			results.setSuccessful(false);
		}finally{
			//�������
			results.sampleEnd();
		}
		//�������
		firstname=null;
		myParams=null;
		hr=null;
		request=null;
		url =null;
		resStr=null;
		return results;
		
	}
	//���ܷ�����ʵ������ʱÿ���߳̽�ִ��һ�Σ��ڲ��Է������н�����ִ�У�������LR��end����
	public void teardownTest(JavaSamplerContext arg0){
		
	}
}
