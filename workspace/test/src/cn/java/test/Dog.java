package cn.java.test;

public class Dog {
	String breed;
	int age;
	String color;
	public Dog(){
		System.out.println("我是dog的构造函数");
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



