package practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@GetMapping
	public String test2() {
		return "test2";
	}
	
	@GetMapping("/attempt1")
	public String attemp1() {
		return "test1";
	}
}
