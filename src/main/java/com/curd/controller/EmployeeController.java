package com.curd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curd.exception.ResourceNotFoundException;
import com.curd.model.Employee;
import com.curd.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employee
	@GetMapping("/employee")
	public List<Employee> getAllEmp(){
		return employeeRepository.findAll();
	}
	//create employee
	@PostMapping("/employee")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	//employee get by id
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Long id){
		Employee employee=employeeRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("employee not exist with id"+id));
		return ResponseEntity.ok(employee);
	}
	//update employee
	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id ,
			@RequestBody Employee employeeUpdate){
		Employee employee=employeeRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("employee not exist with id"+id));
		employee.setFirstName(employeeUpdate.getFirstName());
		employee.setLastName(employeeUpdate.getLastName());
		employee.setEmailId(employeeUpdate.getEmailId());
		
		Employee updatemp = employeeRepository.save(employee);
		return ResponseEntity.ok(updatemp);
		
	}
	
	//Delete employee
	@DeleteMapping("/employee/{id}")
	public ResponseEntity< Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee=employeeRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("employee not exist with id"+id));
		employeeRepository.delete(employee);
		Map<String,Boolean> response=new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}

}
