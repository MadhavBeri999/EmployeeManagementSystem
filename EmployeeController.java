package com.madhav.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madhav.model.Employee;
import com.madhav.repository.EmployeeRepository;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeRepository emprepository;

    @PostMapping("/employee")
    public Employee createNewEmployee(@RequestBody Employee employee) {
        return emprepository.save(employee);
    }
    @GetMapping("/employee") 
    public ResponseEntity<List<Employee>>getAllEmployees(){
    	List<Employee> empList = new ArrayList();
    	emprepository.findAll().forEach(empList::add);
    	return new ResponseEntity<List<Employee>>(empList,HttpStatus.OK);
    	
    }
    @GetMapping("/employee/{empid}")
    public ResponseEntity <Employee>getEmployeeByid(@PathVariable Integer empid){
    	Optional <Employee> emp =emprepository.findById(empid);
    	if(emp.isPresent()) {
    		return new ResponseEntity<Employee>(emp.get(),HttpStatus.FOUND);
    	}
    	else {
    		return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
    	}
    	
    }
    @PutMapping("/employee/{empid}")
    public String updateEmployeeById(@PathVariable Integer empid , @RequestBody Employee employee) {
    	Optional<Employee> emp  = emprepository.findById(empid);
    	if(emp.isPresent()) {
    		Employee existemp = emp.get();
    		existemp.setEmpage(employee.getEmpage());
    		existemp.setEmpcity(employee.getEmpcity());
    		existemp.setEmpname(employee.getEmpname());
    		existemp.setEmpsalary(employee.getEmpsalary());
    		emprepository.save(existemp);
    		return "The value reagarding the id "+empid+"has been change";
    		}
    	else {
			return "The value reagarding the id "+empid+"can not be change";
		}
    }
    

    @DeleteMapping("/employee/{empid}")
    public String deletEployeeById(@PathVariable Integer empid) {
    	Optional <Employee> emp =emprepository.findById(empid);
    	if(emp.isPresent()) {
    		emprepository.deleteById(empid);
    		return "The data alongside the id "+empid+" is now deleted";
    	}
    	else {
    		return "id "+empid+" does not exists";
    		
    	}
    }
    @DeleteMapping("employee")
    public String deletefull() {
    	emprepository.deleteAll();
    	return "Full database has been now deleted";
    }
    @GetMapping("/employee/city/{empcity}")
    public ResponseEntity<List<Employee>> findingEmployeeByCity(@PathVariable String empcity) {
        
        List<Employee> empList = emprepository.findByEmpcity(empcity);

        if(empList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(empList, HttpStatus.OK);
    }
    @GetMapping("employee/age/{empage}")
    public ResponseEntity<List<Employee>>findingembyage(@PathVariable Integer empage){
    	List<Employee>empagelist = emprepository.findByEmpageGreaterThan(empage);
    	
    	if(empagelist.isEmpty()) {
    		return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	else {
    		return new ResponseEntity(empagelist ,HttpStatus.FOUND);
    	}
    }

    
    	
    }


