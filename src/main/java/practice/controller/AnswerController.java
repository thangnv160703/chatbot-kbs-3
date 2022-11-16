package practice.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import practice.model.Answer;
import practice.model.NgheNghiep;
import practice.model.Result;
import practice.service.CareerService;

@RestController
@RequestMapping("/answer")
@Slf4j
public class AnswerController {
	@Autowired
	CareerService careerService;

	@PostMapping
	public ArrayList<Answer> getCareer(@RequestBody String body) {
		log.info("Received");
		ArrayList<Answer> answer_list = careerService.findBestCareer(body);
		log.info(answer_list.toString());
		// vi du, tam thoi de day
//		NgheNghiep x = new NgheNghiep();
//		x.setId((long) 1000);
//		x.setTen("IT Engineer");
//		x.setCode("RIA");
//		x.setDoKho(2);
//		ArrayList<NgheNghiep> list2 = new ArrayList<>();
//		list2.add(x);
		return answer_list;
	}
}
