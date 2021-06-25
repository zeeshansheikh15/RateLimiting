package com.test.header.controller;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/listHeaders")
	public void listAllHeaders( HttpServletRequest request) 
	{
		ArrayList<String> headersNames=Collections.list(request.getHeaderNames());
		headersNames.forEach(name ->{
			System.out.println("Name: "+name);
		});

	
}}
