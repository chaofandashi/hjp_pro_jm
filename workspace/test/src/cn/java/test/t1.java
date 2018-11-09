package cn.java.test;
import cn.java.test.Dog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class t1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=1;i<=9;i++){
			for(int j=1;j<=i;j++){
				System.out.print(j+"*"+i+"="+i*j+'\t');
				}
			System.out.println();
		}
		
		Date date = new Date();
		SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String s="start time ";
		System.out.println(s+ft.format(date));
		
		Calendar c1 = Calendar.getInstance();
		c1.set(2009, 6-1,12);
		System.out.println();
	}

}
