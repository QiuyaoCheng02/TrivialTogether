package com.example.trival.ui.reflow;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.trival.AttractionsInfo;
import com.example.trival.overviewDBHelper;
import com.example.trival.R;
import com.example.trival.arrow.ArrowView;
import com.example.trival.arrow.PositionInfoProvider;
import com.example.trival.databinding.FragmentReflowBinding;
import com.example.trival.OverviewConnectionService;
import com.example.trival.ThumbnailView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Map;


public class ReflowFragment extends Fragment {

    private FragmentReflowBinding binding;
    private ThumbnailView thumbnailView;
    private LargeBitmapView largeBitmapView;
    private ArrowView arrowView;
    private HandlerThread handlerThread;
    private Handler mHandler;
    public double mScale= 0.574;

    private overviewDBHelper overviewDBHelper;
    // 广播接收器
    private void showPopup(String message) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.example.ACTION_DB_DATA")) {

                onDataChange();

            }
           else{
                String label = intent.getStringExtra(OverviewConnectionService.EXTRA_LABEL);
                String content = intent.getStringExtra(OverviewConnectionService.EXTRA_CONTENT);
                if (label.equals("newCenter1")) {
                    drawFrame(content);
                }
                if(label.equals("newCenter2")){
                    drawFrame2(content);
                }
                if(label.equals("popup")){
                    showPopup(content);
                }
                if(label.equals("stop")){
                    //thumbnailView.saveThumbnailImage();
                    thumbnailView.saveLog();
                }
                // 在此处处理接收到的消息
                            }
        }
    };


    public void onDataChange() {
        arrowView.updateArrow(getContext());
        arrowView.updateFlag(getContext());
    }

    private void loadLargeBitmapAsync() {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                // 在后台线程加载大图
                BitmapFactory.Options options = new BitmapFactory.Options();

                return BitmapFactory.decodeResource(getResources(), R.drawable.suzhou_200_a3, options);
            }

            @Override
            protected void
                onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null) {
                    // 加载完成后在UI线程上设置大图
                    largeBitmapView.setImage(ImageSource.bitmap(bitmap));
                }
            }
        }.execute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReflowViewModel reflowViewModel =
                new ViewModelProvider(this).get(ReflowViewModel.class);

        binding = FragmentReflowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        handlerThread = new HandlerThread("BroadcastThread");
        handlerThread.start();

        mHandler = new Handler(handlerThread.getLooper());
        thumbnailView = root.findViewById(R.id.frame_view);
        arrowView = root.findViewById(R.id.arrow_view);
        this.largeBitmapView=root.findViewById(R.id.largeBitmapView);
        loadLargeBitmapAsync();
        largeBitmapView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);
        // 注册广播接收器
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(messageReceiver,
                new IntentFilter(OverviewConnectionService.ACTION_MESSAGE_RECEIVED));
        IntentFilter filter1 = new IntentFilter("com.example.ACTION_DB_DATA");
        getActivity().registerReceiver(messageReceiver, filter1);


        ViewTreeObserver viewTreeObserver = largeBitmapView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Ensure this only runs once by removing the listener
                largeBitmapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // Now you can get the height
                int height = largeBitmapView.getHeight();
                           }
        }); // 添加视图布局完成监听器

        Map<String, PointF> positionInfo = PositionInfoProvider.getPositionInfo();
        arrowView.setPositionInfo(positionInfo);
        //arrowView.addArrow(AttractionsInfo.GUANQIANJIE,AttractionsInfo.HANSHANSI);
        //arrowView.addFlag(AttractionsInfo.CANGLANGTING,1,2);
        overviewDBHelper = new overviewDBHelper(getActivity());
        overviewDBHelper.registerBroadcastReceiver();
        return root;
    }




    private void drawFrame(final String content) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // 在主线程上更新UI
                String[] parts = content.split(",");
                float left = (float) (Float.parseFloat(parts[0]) * mScale);
                float top = (float) (Float.parseFloat(parts[1]) * mScale);
                float right = (float) (Float.parseFloat(parts[2]) * mScale);
                float bottom = (float) (Float.parseFloat(parts[3]) * mScale);
                               thumbnailView.refreshFrame(left, top, right, bottom);
            }
        });
    }

    private void drawFrame2(final String content) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // 在主线程上更新UI
                String[] parts = content.split(",");
                float left = (float) (Float.parseFloat(parts[0]) * mScale);
                float top = (float) (Float.parseFloat(parts[1]) * mScale);
                float right = (float) (Float.parseFloat(parts[2]) * mScale);
                float bottom = (float) (Float.parseFloat(parts[3]) * mScale);
                                thumbnailView.yRrefreshFrame(left, top, right, bottom);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // 取消注册广播接收器
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(messageReceiver);
        binding = null;
    }
}
