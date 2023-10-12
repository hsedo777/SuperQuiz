package org.sedo.superquiz.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.sedo.superquiz.data.QuestionBank;
import org.sedo.superquiz.data.QuestionRepository;
import org.sedo.superquiz.ui.quiz.QuizViewModel;

/**
 * Provides implementation of factory design for ViewModel {@link QuizViewModel}.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
	private final QuestionRepository repository;
	private static ViewModelFactory factory;

	public static ViewModelFactory getInstance() {
		if (factory == null) {
			synchronized (ViewModelFactory.class) {
				if (factory == null) {
					factory = new ViewModelFactory();
				}
			}
		}
		return factory;
	}

	private ViewModelFactory() {
		repository = new QuestionRepository(QuestionBank.getInstance());
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		if (QuizViewModel.class.isAssignableFrom(modelClass)) {
			final T t = (T) new QuizViewModel(repository);
			return t;
		}
		return ViewModelProvider.Factory.super.create(modelClass);
	}
}