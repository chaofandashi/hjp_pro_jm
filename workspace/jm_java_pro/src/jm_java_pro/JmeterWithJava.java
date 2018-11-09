
package jm_java_pro;
import java.util.HashMap;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;


public class JmeterWithJava extends AbstractJavaSamplerClient{
	
	//初始化默认参数
	public Arguments getDefaultParameters(){
		Arguments params = new Arguments();
		params.addArgument("firstname","firstname");
		params.addArgument("lastname","lastname");
		params.addArgument("url","http://45.32.225.217:8080/examples/servlets/servlet/RequestParamExample");
		return params;
	}
	//初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LR中init的方法
	public void setupTest(JavaSamplerContext arg0){
		
	}
	//压测部分，就是lr中action
	public SampleResult runTest(JavaSamplerContext arg0){
		
		//设置参数变量
		SampleResult results = null;//结果类，显示在查看结果树 
		String firstname="";
		String lastname = "";
		String url = "";
		HashMap<String,String> myParams = new HashMap<String,String>();
		HttpRespons hr = new HttpRespons();
		HttpRequester request = new HttpRequester();
		
		//获取请求参数变量，从页面上来 key
		firstname = arg0.getParameter("firstname");
		lastname = arg0.getParameter("lastname");
		url = arg0.getParameter("url");
		//声明返回值参数变量
		String resStr=null;

		//生成响应结果对象
		results =new SampleResult();

		//组装参数和发送请求
		try {
			//参数存入HashMap
			myParams.put("lastname", lastname);
			myParams.put("firstname", firstname);

			//事物的开始，在聚合报告显示sample
			results.sampleStart();

			//发送请求
			hr = request.sendPost(url,myParams,null);
			//获取返回值
			resStr=hr.getContent();

			//会显示在结果树的响应数据里  encoding:null
			results.setResponseData("结果是:"+resStr,null);
			results.setDataType(SampleResult.TEXT);

			//会输出在Jmeter启动的命令窗口中
			System.out.println("输出到控制台:"+resStr);
			results.setSuccessful(true);
		} catch (Exception e) {
			// TODO: handle exception
			results.setSuccessful(false);
		}finally{
			//事物结束
			results.sampleEnd();
		}
		//数据清除
		firstname=null;
		myParams=null;
		hr=null;
		request=null;
		url =null;
		resStr=null;
		return results;
		
	}
	//介绍方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LR的end方法
	public void teardownTest(JavaSamplerContext arg0){
		
	}
}
