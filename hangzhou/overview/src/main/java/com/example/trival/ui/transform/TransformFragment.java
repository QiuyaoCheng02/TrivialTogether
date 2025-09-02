package com.example.trival.ui.transform;

import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trival.R;
import com.example.trival.databinding.FragmentTransformBinding;
import com.example.trival.OverviewConnectionService;
import com.example.trival.ThumbnailView;

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
import android.os.HandlerThread;

import org.jetbrains.annotations.Nullable;

public class TransformFragment extends Fragment {

    private ThumbnailView thumbnailView;
    private FragmentTransformBinding binding;
    private HandlerThread handlerThread;
    private Handler mHandler;

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(OverviewConnectionService.ACTION_MESSAGE_RECEIVED)) {
                String label = intent.getStringExtra(OverviewConnectionService.EXTRA_LABEL);
                String content = intent.getStringExtra(OverviewConnectionService.EXTRA_CONTENT);
                if (label.equals("newCenter1")) {
                    drawFrameInBackground(content);
                }
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTransformBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_transform, container,false);

        thumbnailView= root.findViewById(R.id.frame_view);
        thumbnailView.refreshFrame(100,100,100,100);
        handlerThread = new HandlerThread("BroadcastThread");
        handlerThread.start();

        mHandler = new Handler(handlerThread.getLooper());

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thumbnailView = view.findViewById(R.id.frame_view);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (handlerThread != null) {
            handlerThread.quit();
        }
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(OverviewConnectionService.ACTION_MESSAGE_RECEIVED);
        requireActivity().registerReceiver(messageReceiver, filter, null, mHandler);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().unregisterReceiver(messageReceiver);
    }

    private void drawFrameInBackground(final String content) {
        drawFrame(content);
    }

    private void drawFrame(final String content) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] parts = content.split(",");
                float left = Float.parseFloat(parts[0]);
                float top = Float.parseFloat(parts[1]);
                float right = Float.parseFloat(parts[2]);
                float bottom = Float.parseFloat(parts[3]);

                thumbnailView.refreshFrame(left, top, right, bottom);

            }
        });
    }
}
