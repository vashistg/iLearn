package com.gv.web.iLearn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gv.web.iLearn.pojo.Employee;

@Controller
public class TestController {
	
	@RequestMapping(value="/greet")
	public @ResponseBody Employee getGreeting(Employee emp){
		return emp;
	}
	
	@RequestMapping(value="/greet/{name}/{sirname}/{dob}")
	public @ResponseBody String getGreeting(@PathVariable(value="name") String name,@PathVariable(value="sirname") String sirname,@PathVariable(value="dob") String dob){
		return "Hello "+ name + " " + sirname + " dob ::" + dob ;
	}

	
	@RequestMapping(value="/greet/{id}/{salary}/{dob}")
	public @ResponseBody String getambiguous(@PathVariable(value="id") String name,@PathVariable(value="salary") String sirname,@PathVariable(value="dob") String dob){
		return "Hello "+ name + " " + sirname + " dob ::" + dob ;
	}
}
