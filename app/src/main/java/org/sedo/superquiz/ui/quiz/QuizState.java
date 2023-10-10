package org.sedo.superquiz.ui.quiz;

import org.sedo.superquiz.data.Question;

import java.util.List;

/**
 * Wraps all data which define the state of the Fragment.
 * */
public class QuizState {
	private Question question;
	private Integer score;
	private int questionIndex;
	private List<Question> questions;

	public QuizState() {
		score = 0;
	}

	public boolean isLast() {
		return questions == null || (questions.size() == questionIndex + 1);
	}

	public QuizState clone(){
		QuizState state = new QuizState();
		state.question = question;
		state.score = score;
		state.questionIndex = questionIndex;
		state.questions = questions;
		return state;
	}

	public Question getQuestion() {
		return question;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public int getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
		this.question = questions.get(questionIndex);
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}