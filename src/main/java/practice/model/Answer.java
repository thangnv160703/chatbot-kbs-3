package practice.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Answer {
	private String hollandCode;
	private ArrayList<NgheNghiep> lvl1;
	private ArrayList<NgheNghiep> lvl2;
	private ArrayList<NgheNghiep> lvl3;
	private ArrayList<NgheNghiep> lvl4;
	private ArrayList<NgheNghiep> lvl5;
	
	public Answer(String hollandCode) {
		this.hollandCode = hollandCode;
		this.lvl1 = new ArrayList<>();
		this.lvl2 = new ArrayList<>();
		this.lvl3 = new ArrayList<>();
		this.lvl4 = new ArrayList<>();
		this.lvl5 = new ArrayList<>();
	}
	
	public void addNghe(NgheNghiep nghe) {
		int x = nghe.getDoKho();
		switch (x) {
		case 1:
			lvl1.add(nghe);
			break;
		case 2:
			lvl2.add(nghe);
			break;
		case 3:
			lvl3.add(nghe);
			break;
		case 4:
			lvl4.add(nghe);
			break;
		default:
			lvl5.add(nghe);
			break;
		}
	}
	
	public String toString() {
		String str = this.hollandCode + " ";
		str += lvl1.toString() + lvl2.toString() + lvl3.toString() + lvl4.toString() + lvl5.toString();
		return str;
	}
}
