package org.sedo.superquiz.ui.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.sedo.superquiz.R;
import org.sedo.superquiz.data.Question;
import org.sedo.superquiz.databinding.FragmentQuizBinding;
import org.sedo.superquiz.injection.ViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

	private FragmentQuizBinding binding;
	private QuizViewModel model;

	public QuizFragment() {
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment.
	 *
	 * @return A new instance of fragment QuizFragment.
	 */
	public static QuizFragment newInstance() {
		QuizFragment fragment = new QuizFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		model = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(QuizViewModel.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentQuizBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	private void onResponseTap(View view) {
		Button[] buttons = new Button[]{binding.answerA, binding.answerB, binding.answerC, binding.answerD};
		int i = 0;
		QuizState quizState = model.getCurrentQuestion().getValue();
		for (Button button : buttons) {
			button.setEnabled(false);
			if (button == view) {
				model.onResponseTap(i);
				updateOnTap(button, i, quizState);
			}
			i++;
		}
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		model.startQuiz();
		model.getCurrentQuestion().observe(getViewLifecycleOwner(), quizState -> loadState(quizState));
		Button[] buttons = new Button[]{binding.answerA, binding.answerB, binding.answerC, binding.answerD};
		for (Button button : buttons) {
			button.setOnClickListener(this::onResponseTap);
		}
	}

	private void loadState(QuizState state) {
		Question question = state.getQuestion();
		binding.quizQstView.setText(question.getQuestion());
		Button[] buttons = new Button[]{binding.answerA, binding.answerB, binding.answerC, binding.answerD};
		for (int i = 0; i < buttons.length; i++) {
			updateButton(buttons[i], i, question, state);
		}
		if (state.isAnswerSubmit()){
			int responseIndex = state.getSelectedResponse();
			updateOnTap(buttons[responseIndex], responseIndex, state);
		}
	}

	/**
	 * Updates button on view displaying
	 */
	private void updateButton(Button button, int index, Question question, QuizState quizState) {
		button.setText(question.getChoice(index));
		button.setEnabled(quizState.isButtonEnabled());
	}

	/**
	 * Update the button on which user taps
	 */
	private void updateOnTap(Button button, int index, QuizState quizState) {
		if (quizState.isAnswerSubmit()) {
			@ColorRes final int color;
			@StringRes final int answerText;
			if (index == quizState.getQuestion().getAnswerIndex()) {
				color = R.color.answer_good;
				answerText = R.string.answer_good;
			} else {
				color = R.color.answer_bad;
				answerText = R.string.answer_bad;
			}
			button.setBackgroundColor(getResources().getColor(color, null));
			binding.quizPopUpView.setText(answerText);
			// Forces announcing result for accessibility tools
			button.announceForAccessibility(getText(answerText));
			if (model.getCurrentQuestion().getValue().isLast()) {
				binding.quizNextButton.setText(R.string.finish);
			}
			binding.quizResPanel.setVisibility(ViewGroup.VISIBLE);
		}
	}
}