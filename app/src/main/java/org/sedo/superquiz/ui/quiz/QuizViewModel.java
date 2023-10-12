package org.sedo.superquiz.ui.quiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.sedo.superquiz.data.QuestionRepository;

public class QuizViewModel extends ViewModel {
	private final QuestionRepository repository;
	private final MutableLiveData<QuizState> currentQuestion = new MutableLiveData<>();

	public QuizViewModel(QuestionRepository repository) {
		this.repository = repository;
	}

	/**
	 * Gets the {@code LiveData} of the current question.
	 * */
	public MutableLiveData<QuizState> getCurrentQuestion(){
		return currentQuestion;
	}

	/**
	 * Calls on first come to the fragment to start th quiz.
	 */
	public void startQuiz() {
		QuizState state = new QuizState();
		state.setQuestions(repository.getQuestions());
		state.setQuestionIndex(0);
		currentQuestion.postValue(state);
	}

	/**
	 * Cals when user clicks on one response button.
	 *
	 * @return {@code true} if and only if the response index is the good.
	 */
	public boolean onResponseTap(int responseIndex) {
		QuizState state = currentQuestion.getValue();
		boolean check = responseIndex == state.getQuestion().getAnswerIndex();
		if (check) {
			state.setScore(state.getScore() + 10);
			currentQuestion.postValue(state.clone());
		}
		return check;
	}

	/**
	 * Calls when user tap on button `Next` to request the next
	 * question.
	 *
	 * @return {@code true} if and only if there is at least one new question to display.
	 */
	public boolean nextQuestion() {
		QuizState state = currentQuestion.getValue();
		if (state.isLast()) {
			// Something to do
			return false;
		} else {
			state.setQuestionIndex(state.getQuestionIndex() + 1);
			currentQuestion.postValue(state.clone());
			return true;
		}
	}
}