package cn.java.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class file_input {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		InputStream f=new FileInputStream("D:/hello.txt");
//		System.out.println(f);
//		f.close();
//		File f = new File("D:/hello.txt");
//		InputStream out = new FileInputStream(f);
//		System.out.println(f);
		
		
		File f=new File("D:/hello.txt");
		FileOutputStream fop =new FileOutputStream(f);
		OutputStreamWriter write = new OutputStreamWriter(fop, "UTF-8");
	}

}
