import java.util.HashMap;

import lrapi.lr;

public class Actions {
	String url="http://45.32.225.217:8080/examples/servlets/servlet/RequestParamExample";
	
	public void sendMsg(){
		HttpRespons hr = new HttpRespons();//������
		HttpRequester request = new HttpRequester();//������
		//�������� �������������
		HashMap<String,String> headers = new HashMap<String,String>();
		HashMap<String,String> param = new HashMap<String,String>();
		//body�������
		param.put("lastname", "aa");
		param.put("firstname", "bb");
		String resStr = "";//������Ӧ���
		String execptStr = "aa";
		
//		lr.start_transaction("send_msg_str");//����ʼ  ����ʼǰд
		try {
			hr = request.sendPost(url,param,null);//��������
			resStr= hr.getContent();//�õ���Ӧ����
			System.out.println(hr.getContent());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		if(resStr.contains(execptStr)){//�����Ӧ���ذ�������ֵ������������ɹ���
			System.out.println("success");
//			lr.end_transaction("send_msg_str",lr.PASS);
		}else{
			System.out.println("fail");//������� ʧ��
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
