package practice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import practice.model.CauHoi;
import practice.repository.CauHoiRepository;

@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {
	@Autowired
	private CauHoiRepository cauHoiRepo;
	
	@PostMapping
	public List<CauHoi> uploadCH() {
		List<CauHoi> dsCauHoi = (List<CauHoi>) cauHoiRepo.findAll();
//		log.info(dsCauHoi.toString());
		return dsCauHoi;
		
	}
}
