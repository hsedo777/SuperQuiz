package org.sedo.superquiz.ui.quiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.sedo.superquiz.data.QuestionRepository;

public class QuizViewModel extends ViewModel {
	private final QuestionRepository repository;
	private final MutableLiveData<QuizState> quizState = new MutableLiveData<>();

	public QuizViewModel(QuestionRepository repository) {
		this.repository = repository;
	}

	/**
	 * Gets the {@code LiveData} of the current question.
	 * */
	public MutableLiveData<QuizState> getQuizState(){
		return quizState;
	}

	/**
	 * Calls on first come to the fragment to start th quiz.
	 */
	public void startQuiz() {
		QuizState state = quizState.getValue();
		if (state == null || state.getViewLifeState() == QuizState.VIEW_CREATED) {
			state = state == null ? new QuizState() : state;
			state.setQuestions(repository.getQuestions());
			state.onQuizStart();
			quizState.postValue(state);
		}
	}

	/**
	 * Cals when user clicks on one response button.
	 *
	 * @return {@code true} if and only if the response index is the good.
	 */
	public boolean onResponseTap(int responseIndex) {
		QuizState state = quizState.getValue();
		boolean check = state.onResponseTap(responseIndex);
		quizState.postValue(state);
		return check;
	}

	/**
	 * Calls when user tap on button `Next` to request the next
	 * question.
	 *
	 * @return {@code true} if and only if there is at least one new question to display.
	 */
	public boolean nextQuestion() {
		QuizState state = quizState.getValue();
		boolean success = state.nextQuestion();
		if (success) {
			quizState.postValue(state);
		}
		return success;
	}
}