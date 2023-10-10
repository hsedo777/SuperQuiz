package org.sedo.superquiz.ui.quiz;

import androidx.lifecycle.ViewModel;

import org.sedo.superquiz.data.QuestionRepository;

public class QuizViewModel extends ViewModel {
	private final QuestionRepository repository;

	public QuizViewModel(QuestionRepository repository) {
		this.repository = repository;
	}
}
