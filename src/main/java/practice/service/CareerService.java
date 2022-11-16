package practice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import practice.model.Answer;
import practice.model.NgheNghiep;
import practice.model.Result;
import practice.repository.NgheNghiepRepository;

@Service
@Slf4j
public class CareerService {
	@Autowired
	private NgheNghiepRepository ngheNghiepRepo;

	public ArrayList<Answer> findBestCareer(String body) {
		// Tach chuoi json
		String[] json_array = body.substring(1, body.length() - 2).split("},");
		JsonParser springParser = JsonParserFactory.getJsonParser();
		Map<String, Object> map;
		Map<String, Integer> chiSo_list = new HashMap<>();

		for (int i = 0; i < json_array.length; ++i) {
			map = springParser.parseMap(json_array[i] + "}");
			chiSo_list.put(map.get("name").toString(), Integer.parseInt(map.get("kq").toString()));

		}

		// lay nhung chi so khac 0
		ArrayList<Result> result_list = new ArrayList<>();
		for (String chiSo : chiSo_list.keySet()) {
			if (chiSo_list.get(chiSo) != 0) {
				result_list.add(new Result(chiSo, chiSo_list.get(chiSo)));
			}
		}
		Collections.sort(result_list); // sap xep

		ArrayList<String> code_list = getHollandCode(result_list);
		log.info("Ket qua: " + code_list.toString());

		ArrayList<Answer> answer_list = new ArrayList<>();
		for (String code : code_list) {
			answer_list.add(getAnswer(code));
		}

//		log.info("Answer_list:");
//		for (Answer answer : answer_list) {
//			log.info(answer.toString());
//		}

		return answer_list;
	}

	public Answer getAnswer(String hollandCode) {
		Answer answer = new Answer(hollandCode);
		ArrayList<NgheNghiep> job_list = (ArrayList<NgheNghiep>) ngheNghiepRepo.findByCode(hollandCode);
		for (NgheNghiep job : job_list) {
			answer.addNghe(job);
		}
		return answer;
	}

	public ArrayList<String> getHollandCode(ArrayList<Result> result_list) {
		for(Result re:result_list) {
			log.info(re.toString());
		}
		
		ArrayList<String> hollandCode_list = new ArrayList<>();
		if (result_list.isEmpty()) {

		} else if (result_list.size() == 1) {
			hollandCode_list.add(result_list.get(0).getId());
		} else if (result_list.size() == 2) {
			if (result_list.get(0).getScore() == result_list.get(1).getScore()) {
				hollandCode_list.add(result_list.get(0).getId() + result_list.get(1).getId());
				hollandCode_list.add(result_list.get(1).getId() + result_list.get(0).getId());
			} else {
				hollandCode_list.add(result_list.get(0).getId() + result_list.get(1).getId());
			}
		} else {
			int loc = 3;
			while (loc < result_list.size() && (result_list.get(2).getScore() == result_list.get(loc).getScore())) {
				loc += 1;
			}
			if (result_list.get(0).getScore() == result_list.get(1).getScore()) {
				if (result_list.get(1).getScore() == result_list.get(2).getScore()) {
					String str = "";
					for (int i = 0; i < loc; ++i) {
						str += result_list.get(i).getId();
					}
					ArrayList<String> dsChinhHop = new ArrayList<>();
					getChinhHop(str, "", 3, dsChinhHop);
					for (String x : dsChinhHop) {
						hollandCode_list.add(x);
					}
				} else {
					String str1 = result_list.get(0).getId() + result_list.get(1).getId();
					String str2 = result_list.get(1).getId() + result_list.get(0).getId();
					for (int i = 2; i < loc; ++i) {
						hollandCode_list.add(str1 + result_list.get(i).getId());
						hollandCode_list.add(str2 + result_list.get(i).getId());
					}
				}
			} else {
				if (result_list.get(1).getScore() == result_list.get(2).getScore()) {
					String str = "";
					for (int i = 1; i < loc; ++i) {
						str += result_list.get(i).getId();
					}
					ArrayList<String> dsChinhHop = new ArrayList<>();
					getChinhHop(str, "", 2, dsChinhHop);
					for (String x : dsChinhHop) {
						hollandCode_list.add(result_list.get(0).getId() + x);
					}
				} else {
					String str1 = result_list.get(0).getId() + result_list.get(1).getId();
					for (int i = 2; i < loc; ++i) {
						hollandCode_list.add(str1 + result_list.get(i).getId());
					}
				}
			}
		}

		if (result_list.size() < 3) {
			return hollandCode_list;
		} else {
			return getUuTien(hollandCode_list);
		}
	}

	public ArrayList<String> getUuTien(ArrayList<String> result_list) {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("R", 0);
		map.put("I", 1);
		map.put("A", 2);
		map.put("S", 3);
		map.put("E", 4);
		map.put("C", 5);
		
		int[][] matrix = { { 0, 1, 0, -1, 0, 1 }, { 1, 0, 1, 0, -1, 0 }, { 0, 1, 0, 1, 0, -1 }, { -1, 0, 1, 0, 1, 0 },
				{ 0, -1, 0, 1, 0, 1 }, { 1, 0, -1, 0, 1, 0 }, };

		int max_value = -10;
		HashMap<String, Integer> priority = new HashMap<>();
		for (String result : result_list) {
			int first = (int) map.get(result.substring(0, 1));
			int second = (int) map.get(result.substring(1, 2));
			int third = (int) map.get(result.substring(2));
			int value = 3 * matrix[first][second] + 2 * matrix[first][third] + matrix[second][third];
			log.info(result + ":" + value);
			if (max_value < value) {
				max_value = value;
				priority.clear();
				priority.put(result, value);
			} else if (max_value == value) {
				priority.put(result, value);
			}
		}
		ArrayList<String> final_codes = new ArrayList<>();

		for (String s : priority.keySet()) {
			final_codes.add(s);
		}
		return final_codes;
	}
	
	public void getChinhHop(String a, String sub, int len, ArrayList<String> list) {
        if(sub.length() == len){
            list.add(sub);
        }
        for (int i = 0; i < a.length(); ++i) {
            if(sub.indexOf(a.charAt(i)) == -1){
                getChinhHop(a, sub + a.charAt(i), len, list);
            }
        }
    }
}
