package sessionFramework.responseUser;

public class QuestionResponse {
	private String question1;
	private String question2;
	
	public QuestionResponse(String question1, String question2) {
		this.question1 = question1;
		this.question2 = question2;
	}	

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}
	
	
}
