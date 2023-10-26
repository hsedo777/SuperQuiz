package org.sedo.superquiz.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import org.sedo.superquiz.R;
import org.sedo.superquiz.databinding.FragmentWelcomeBinding;
import org.sedo.superquiz.ui.quiz.QuizFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment extends Fragment {

	private static final String PLAYER_NAME = "PLAYER_NAME";

	private FragmentWelcomeBinding binding;

	private String playerName;

	public WelcomeFragment() {
		// Require default constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param playerName the name of the player.
	 * @return A new instance of fragment WelcomeFragment.
	 */
	public static WelcomeFragment newInstance(String playerName) {
		WelcomeFragment fragment = new WelcomeFragment();
		Bundle args = new Bundle();
		args.putString(PLAYER_NAME, playerName);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			playerName = getArguments().getString(PLAYER_NAME);
		}
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentWelcomeBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//Deactivate the button until the text field become not empty
		binding.playGame.setEnabled(false);
		binding.askNameEditView.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				playerName = s.toString().trim();
				binding.playGame.setEnabled(!playerName.isEmpty());
			}
		});
		binding.askNameEditView.setText(playerName);

		binding.playGame.setOnClickListener(v -> {
			//Hide the keyboard if active
			FragmentActivity fragmentActivity = getActivity();
			if (fragmentActivity != null){
				InputMethodManager input = (InputMethodManager) fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
				View focus = fragmentActivity.getCurrentFocus();
				if (focus != null){
					input.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}

			// Split to next fragment and keep backTo disallowed
			FragmentManager manager = getParentFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			QuizFragment quiz = QuizFragment.newInstance(binding.askNameEditView.getText().toString());
			transaction.add(R.id.fragment_container_view, quiz);
			/*transaction.addToBackStack(null);
			transaction.setReorderingAllowed(true);*/
			transaction.commit();
		});
	}
}