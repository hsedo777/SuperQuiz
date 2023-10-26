package org.sedo.superquiz.ui.quiz;

import org.sedo.superquiz.data.Question;

import java.util.List;

/**
 * Wraps all data which define the state of the Fragment.
 */
public class QuizState {

	/**
	 * Value of view state at first come on the fragment
	 */
	public static final int VIEW_CREATED = 0;
	/**
	 * Value of view state when a new question is displayed and we are waiting to user to tap response
	 */
	public static final int WAITING_FOR_RESPONSE = 1;
	/**
	 * Value of the view state when user has submit an answer and we are now waiting for click on button {@code Next} or {@code finish}
	 */
	public static final int WAIT_TO_CONTINUE = 2;


	private Question question;
	private Integer score;
	private int questionIndex;
	private List<Question> questions;
	private boolean buttonEnabled;
	private Integer selectedResponse;
	private int viewLifeState = VIEW_CREATED;

	public QuizState() {
		score = 0;
	}

	public boolean isLast() {
		return questions == null || (questions.size() == questionIndex + 1);
	}

	public Question getQuestion() {
		return question;
	}

	public Integer getScore() {
		return score;
	}

	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
		this.question = questions.get(questionIndex);
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	/**
	 * Stars the quiz
	 */
	void onQuizStart() {
		setQuestionIndex(0);
		viewLifeState = WAITING_FOR_RESPONSE;
		buttonEnabled = true;
	}

	/**
	 * Processes user answer submitting.
	 */
	void onResponseTap(int responseIndex) {
		boolean check = responseIndex == getQuestion().getAnswerIndex();
		if (check) {
			score = score + 10;
		}
		selectedResponse = responseIndex;
		buttonEnabled = false;
		viewLifeState = WAIT_TO_CONTINUE;
	}

	/**
	 * Passes to the next question if possible.
	 */
	boolean nextQuestion() {
		if (isLast()) {
			// Something to do
			return false;
		} else {
			selectedResponse = null;
			buttonEnabled = true;
			setQuestionIndex(questionIndex + 1);
			viewLifeState = WAITING_FOR_RESPONSE;
			return true;
		}
	}

	/**
	 * Tests if the answer buttons are enabled or not.
	 */
	public boolean isButtonEnabled() {
		return buttonEnabled;
	}

	/**
	 * Gets, if defined, the index of the submitted answer
	 */
	public Integer getSelectedResponse() {
		return selectedResponse;
	}

	/**
	 * Tests if there is currently a defined value for the submitted answer
	 */
	public boolean isAnswerSubmit() {
		return selectedResponse != null;
	}

	/**
	 * Gets the constant which defines the state of the view.
	 */
	public int getViewLifeState() {
		return viewLifeState;
	}
}