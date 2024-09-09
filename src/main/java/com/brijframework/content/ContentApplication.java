package com.brijframework.content;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class ContentApplication {

	public static void main(String[] args) throws IOException {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(ContentApplication.class, args);
		
		Map<Employee, String> map=new HashMap<Employee, String>();
		
		Employee e1 = new Employee(25000, "Ram");
		Employee e2 = new Employee(25000, "Ram");
		
		map.put(e1, "First");
		map.put(e2, "Second");
		
		System.out.println(map.size());
	}

}

class Employee{
	int salary;
	String name;
	public Employee(int salary, String name) {
		super();
		this.salary = salary;
		this.name = name;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}