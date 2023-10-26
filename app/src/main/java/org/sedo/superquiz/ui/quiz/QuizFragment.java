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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.sedo.superquiz.R;
import org.sedo.superquiz.data.Question;
import org.sedo.superquiz.databinding.FragmentQuizBinding;
import org.sedo.superquiz.injection.ViewModelFactory;
import org.sedo.superquiz.ui.home.WelcomeFragment;

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
		return new QuizFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		model = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(QuizViewModel.class);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentQuizBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	private void onResponseTap(View view) {
		try {
			int index = Integer.parseInt(view.getTag().toString());
			model.onResponseTap(index);
		} catch (Exception ignored) {
		}
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		model.startQuiz();
		model.getQuizState().observe(getViewLifecycleOwner(), this::loadState);
		Button[] buttons = new Button[]{binding.answerA, binding.answerB, binding.answerC, binding.answerD};
		for (Button button : buttons) {
			button.setOnClickListener(this::onResponseTap);
		}
		binding.quizNextButton.setOnClickListener(v -> {
			if (model.isLastQuestion()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(R.string.quiz_end_alert_title);
				builder.setMessage(String.format(getString(R.string.quiz_end_alert_body), model.getScore()));

				builder.setPositiveButton(R.string.quiz_end_positive_button, (dialog, which) -> {
					//Catch click on positive button
					getParentFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_container_view, WelcomeFragment.newInstance(null, null))
							.commit();
				});

				AlertDialog dialog = builder.create();
				dialog.setCancelable(false);
				dialog.show();
			} else {
				model.nextQuestion();
			}
		});
	}

	private void loadState(QuizState state) {
		Question question = state.getQuestion();
		binding.quizQstView.setText(question.getQuestion());
		Button[] buttons = new Button[]{binding.answerA, binding.answerB, binding.answerC, binding.answerD};
		for (int i = 0; i < buttons.length; i++) {
			updateButton(buttons[i], i, question, state);
		}
		switch (state.getViewLifeState()) {
			case QuizState.WAIT_TO_CONTINUE:
				binding.quizResPanel.setVisibility(ViewGroup.VISIBLE);
				int responseIndex = state.getSelectedResponse();
				updateOnTap(buttons[responseIndex], responseIndex, state);
				break;
			case QuizState.VIEW_CREATED:
			case QuizState.WAITING_FOR_RESPONSE:
			default:
				binding.quizResPanel.setVisibility(ViewGroup.GONE);
		}
	}

	/**
	 * Updates button on view displaying
	 */
	private void updateButton(Button button, int index, Question question, QuizState quizState) {
		button.setText(question.getChoice(index));
		button.setEnabled(quizState.isButtonEnabled());
		button.setBackgroundColor(getResources().getColor(R.color.button_def, null));
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
			if (model.isLastQuestion()) {
				binding.quizNextButton.setText(R.string.finish);
			}
			binding.quizResPanel.setVisibility(ViewGroup.VISIBLE);
		}
	}
}