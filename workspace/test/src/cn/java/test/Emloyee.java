package cn.java.test;

public class Emloyee {
	static int num=0;
	int num2=1;
	String name;
	int age;
	String designation;
	double salary;
	
	public Emloyee(String name){
		this.name=name;
	}
	public void empAge(int empAge){
		age=empAge;
	}
	public void empDesignation(String empDesig){
	    designation = empDesig;
	}
	public void empSalary(double empSalary){
		salary = empSalary;
	}
	public void printEmployee(){
		System.out.println("����:"+ name );
		System.out.println("����:" + age );
		System.out.println("ְλ:" + designation );
		System.out.println("нˮ:" + salary);
	}
	public static void main(String []args){
		
		
		
		
		 Emloyee a = new Emloyee("ccc");
		 Emloyee.num=2;
		 a.num2=4;
		 System.out.println(Emloyee.num);
		 a.empAge(12);
		 a.empSalary(2400);
		 a.empDesignation("test11111");
		 a.printEmployee();
		 System.out.println();
		 Emloyee b = new Emloyee("ccc");
		 b.empAge(22);
		 b.empSalary(3400);
		 b.empDesignation("test2222");
		 b.printEmployee();
	 }
	
}