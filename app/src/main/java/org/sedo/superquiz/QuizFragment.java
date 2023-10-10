package org.sedo.superquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.sedo.superquiz.databinding.FragmentQuizBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

	private FragmentQuizBinding binding;
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentQuizBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	private void onResponseTap(View view){
		binding.quizResPanel.setVisibility(ViewGroup.VISIBLE);
		Button[] buttons = new Button[]{binding.answerA, binding.answerB, binding.answerC, binding.answerD};
		for (Button button : buttons){
			button.setEnabled(false);
		}
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Button[] buttons = new Button[]{binding.answerA, binding.answerB, binding.answerC, binding.answerD};
		for (Button button : buttons){
			button.setOnClickListener(this::onResponseTap);
		}
	}
}