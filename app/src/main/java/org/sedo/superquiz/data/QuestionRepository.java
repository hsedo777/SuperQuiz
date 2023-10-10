package org.sedo.superquiz.data;

import java.util.List;

public class QuestionRepository {
	private final QuestionBank bank;

	public List<Question> getQuestions() {
		return bank.getQuestions();
	}

	public QuestionRepository(QuestionBank bank) {
		this.bank = bank;
	}
}
