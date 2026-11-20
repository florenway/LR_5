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
import com.google.android.material.textfield.TextInputEditText;

public class FeedbackFragment extends Fragment {

    private TextInputEditText etFio;
    private TextInputEditText etEmail;
    private String fioValue = "";
    private String emailValue = "";
    private boolean defaultsSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etFio = view.findViewById(R.id.etFio);
        etEmail = view.findViewById(R.id.etEmail);
        if (!defaultsSet) {
            fioValue = getString(R.string.fio);
            emailValue = getString(R.string.email);
            defaultsSet = true;
        }
        etFio.setText(fioValue);
        etEmail.setText(emailValue);
        view.findViewById(R.id.btnSend).setOnClickListener(v -> send());
    }

    @Override
    public void onDestroyView() {
        if (etFio != null && etFio.getText() != null) {
            fioValue = etFio.getText().toString();
        }
        if (etEmail != null && etEmail.getText() != null) {
            emailValue = etEmail.getText().toString();
        }
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.form_title);
    }

    private void send() {
        String fio = etFio.getText() == null ? "" : etFio.getText().toString().trim();
        String email = etEmail.getText() == null ? "" : etEmail.getText().toString().trim();

        if (fio.isEmpty() || email.isEmpty()) {
            Toast.makeText(requireContext(), R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.contains("@") || email.startsWith("@") || !email.contains(".")) {
            Toast.makeText(requireContext(), R.string.bad_email, Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.dialog_title)
                .setMessage("ФИО: " + fio + "\nEmail: " + email)
                .setPositiveButton(R.string.btn_ok, null)
                .show();

        fioValue = "";
        emailValue = "";
        etFio.setText("");
        etEmail.setText("");
    }
}
