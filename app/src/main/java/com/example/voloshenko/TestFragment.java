package com.example.voloshenko;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;

public class TestFragment extends Fragment {

    // правильный пункт — BottomNavigationView
    private static final int RIGHT = 2;

    private final String[] variants = {
            "FloatingActionButton",
            "ScrollView",
            "BottomNavigationView",
            "CheckBox"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnAnswer).setOnClickListener(v -> showVariants());
    }

    @Override
    public void onResume() {
        super.onResume();
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.test_title);
    }

    private void showVariants() {
        final int[] picked = {-1};

        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.choose_title)
                .setSingleChoiceItems(variants, -1, (dialog, which) -> picked[0] = which)
                .setPositiveButton(R.string.btn_ready, (dialog, which) -> checkAnswer(picked[0]))
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }

    private void checkAnswer(int picked) {
        if (picked < 0) {
            Toast.makeText(requireContext(), R.string.choose_first, Toast.LENGTH_SHORT).show();
            return;
        }
        int message = picked == RIGHT ? R.string.answer_ok : R.string.answer_bad;
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }
}
