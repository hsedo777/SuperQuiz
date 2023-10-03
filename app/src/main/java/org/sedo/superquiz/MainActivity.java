package org.sedo.superquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import org.sedo.superquiz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		View root = binding.getRoot();
		setContentView(root);
	}
}