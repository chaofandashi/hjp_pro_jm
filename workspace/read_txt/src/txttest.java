import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class txttest {
	static ArrayList<String> arrayList = new ArrayList<String>();  
	static int num0=0;
//	static int num1=1;
	static String phone;
	static String pwd;
    public static void read_txt(){
        try {  
            BufferedReader reader = new BufferedReader(new FileReader("D:\\errlog.txt"));//��������ļ��� 
//            reader.readLine();//��һ����Ϣ��Ϊ������Ϣ������,�����Ҫ��ע�͵� 
            String line = null;  
            while((line=reader.readLine())!=null){  
//                String item[] = line.split(" ");//CSV��ʽ�ļ�Ϊ���ŷָ����ļ���������ݶ����з� 
                String user =line;
//                System.out.println(line);
//                String pwd =item[1];
                arrayList.add(user);  
//                arrayList.add(pwd); 
//                String last = item[item.length-1];//�������Ҫ�������� 
                //int value = Integer.parseInt(last);//�������ֵ������ת��Ϊ��ֵ  
            } 
            reader.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    public static void get_pwd(){
    	String[] strArrayTrue = (String[]) arrayList.toArray(new String[0]);
    	phone=strArrayTrue[num0];
//    	pwd=strArrayTrue[num1];
    	num0++;
//    	num1+=2;
    }  
    public static void main(String[] args){
    	txttest.read_txt();
    	txttest.get_pwd();
    	System.out.println(phone);
//    	System.out.println(pwd);
    	txttest.get_pwd();
    	System.out.println(phone);
//    	System.out.println(pwd);
    	System.out.println(arrayList);
    	System.out.println(arrayList.size());
    }
}