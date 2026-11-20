package com.example.voloshenko;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;

public class NotifyFragment extends Fragment {

    private static final String CHANNEL_ID = "lab_channel";
    private static final int NOTIFY_ID = 1;

    private final ActivityResultLauncher<String> askNotification =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (!isAdded()) {
                    return;
                }
                if (granted) {
                    showNotification();
                } else {
                    Toast.makeText(requireContext(), R.string.notify_denied, Toast.LENGTH_LONG).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnNotify).setOnClickListener(v -> onBellClick());
    }

    @Override
    public void onResume() {
        super.onResume();
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.notify_title);
    }

    private void onBellClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            askNotification.launch(Manifest.permission.POST_NOTIFICATIONS);
            return;
        }
        showNotification();
    }

    private void showNotification() {
        Context context = requireContext();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // канал нужен с Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    getString(R.string.channel_name),
                    NotificationManager.IMPORTANCE_HIGH
            );
            manager.createNotificationChannel(channel);
        }

        Intent openApp = new Intent(context, MainActivity.class);
        openApp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending = PendingIntent.getActivity(
                context,
                0,
                openApp,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        String fio = getString(R.string.fio);
        String group = getString(R.string.group);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle(fio)
                .setContentText(group)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(fio + "\n" + group))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pending);

        manager.notify(NOTIFY_ID, builder.build());
        Toast.makeText(context, R.string.notify_sent, Toast.LENGTH_SHORT).show();
    }
}
