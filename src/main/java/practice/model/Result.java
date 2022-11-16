package practice.model;


public class Result implements Comparable<Result>{
	private String id;
    private int score;
    
    public Result(String id, int score) {
        this.id = id;
        this.score = score;
    }
    
    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }
    
    @Override
    public String toString() {
        return "Result{" + "id=" + id + ", score=" + score + '}';
    }
    
	@Override
	public int compareTo(Result o) {
		return o.score - this.score;
	}
    
}
