package cn.java.test;

public class Dog {
	String breed;
	int age;
	String color;
	public Dog(){
		System.out.println("����dog�Ĺ��캯��");
	}
	void barking(){
		System.out.println("1");
	}
	public void setAge(int agee){
		age=agee;
	}
	public int getAge(){
		System.out.println("age:"+age);
		return age;
	}
}


