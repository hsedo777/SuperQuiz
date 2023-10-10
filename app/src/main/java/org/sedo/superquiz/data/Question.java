package org.sedo.superquiz.data;

import java.util.List;

public class Question {
	private final String  question;
	private final List<String> choices;
	private final int answerIndex;

	public String getQuestion() {
		return question;
	}

	public List<String> getChoices() {
		return choices;
	}

	public int getAnswerIndex() {
		return answerIndex;
	}

	public Question(String question, List<String> choices, int answerIndex) {
		this.question = question;
		this.choices = choices;
		this.answerIndex = answerIndex;
	}
}