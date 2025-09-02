package com.example.user1;

import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.user1.classes.AttractionsInfo;
import com.example.user1.classes.BitmapUtils;
import com.example.user1.classes.ConditionalView;
import com.example.user1.classes.DBhelper;

import com.example.user1.classes.LargeBitmapView;
import com.example.user1.classes.arrow.ArrowManager;
import com.example.user1.classes.arrow.ArrowView;
import com.example.user1.classes.arrow.ArrowView_noSharing;
import com.example.user1.classes.arrow.PositionInfoProvider;
import com.example.user1.classes.button.ButtonClickListener;
import com.example.user1.classes.button.ButtonInfoProvider;
import com.example.user1.classes.button.ButtonManager;
import com.example.user1.classes.button.PinButton;
import com.example.user1.classes.button.PinButtonInfo;
import com.example.user1.classes.button.PinButtonView;
import com.example.user1.classes.ThumbnailView;
import com.example.user1.classes.dialogs.CustomDialogFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirstFragment extends  Fragment {
    private int centerChangedCount = 0;
    private static final double NAVIGATION_SCREEN_WIDTH_SCALE = 0.7;
    private PinButtonView pinButtonView;
    private LargeBitmapView largeBitmapView;
    private ArrowView arrowView;
    private ArrowView_noSharing arrowView_noSharing;
    private ConditionalView conditionalView;

    private DBhelper dBhelper;

    private ViewDragHelper dragHelper;
    private ButtonManager buttonManager;
    private ThumbnailView thumbnailView;
    private HandlerThread handlerThread;
    private Handler mHandler;
    public float mScale;
    private User1ConnectionService BoundService;
    private boolean mServiceBound = false;

    private float left_r;
    private float top_r;
    private float right_r;
    private float bottom_r;

    private float left_y;
    private float top_y;
    private float right_y;
    private float bottom_y;

    public boolean conditional=false;
    public String newCenterPoint2=null;
    public String newScale2= String.valueOf(1.0);

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 这个方法在 Service 与 Fragment 建立连接时调用
            BoundService = ((User1ConnectionService.LocalBinder)  service).getService();
            mServiceBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 这个方法在 Service 与 Fragment 断开连接时调用

        }
    };
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                      if (intent.getAction().equals("com.example.ACTION_DRAW_FRAME")) {
                String label = intent.getStringExtra(User1ConnectionService.EXTRA_LABEL);
                String message = intent.getStringExtra(User1ConnectionService.EXTRA_UPDATED_DATA);

                if(label.equals("newCenter2")){
                    drawFrame2(message);
                }
                if(label.equals("newScale2")){
                    newScale2=message;
                }
                if(label.equals("conditional2")){
                    newCenterPoint2=message;
                                        drawConditional(newCenterPoint2);
                }
                if(label.equals("Conflict")){
                    Toast.makeText(requireContext(), "Conflict", Toast.LENGTH_SHORT).show();
                }

            }
            if ("com.example.ACTION_DB_DATA".equals(intent.getAction())) {

                onDataChange();
            }
        }
    };

    // 数据库内容变化时调用该方法


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        // 这里添加绑定服务的代码
        Intent intent = new Intent(getActivity(), User1ConnectionService.class);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
// 在 onViewCreated 方法中注册广播接收器
        IntentFilter filter1 = new IntentFilter("com.example.ACTION_DB_DATA");
        getActivity().registerReceiver(receiver, filter1);
        IntentFilter filter = new IntentFilter("com.example.ACTION_DRAW_FRAME");
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, filter);
        


        this.largeBitmapView = view.findViewById(R.id.largeBitmapView);
        pinButtonView = view.findViewById(R.id.pinButtonView);
        this.conditionalView=view.findViewById(R.id.conditionalView);
        ButtonClickListener buttonClickListener = new ButtonClickListener(getFragmentManager());
        pinButtonView.setButtonClickListener(buttonClickListener);
        buttonManager = new ButtonManager(pinButtonView);

        // 初始化 DBHelper

        dBhelper= new DBhelper(getActivity());
        dBhelper.registerBroadcastReceiver();

        largeBitmapView.setVisibility(View.VISIBLE);
        arrowView=view.findViewById(R.id.arrowView);
        arrowView_noSharing=view.findViewById(R.id.arrowView_noSharing);

        largeBitmapView.setImage(ImageSource.resource(R.drawable.suzhou_200));
        // 设置缩放选项
        largeBitmapView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);
        largeBitmapView.setMinScale(0.6f);
        largeBitmapView.setMaxScale(5.0f);

        // 设置初始缩放和中心位置
        float initialScale = 1.0f; // 设置初始缩放
        PointF initialCenter = new PointF(0.5f, 0.5f); // 设置初始中心位置 (0.0, 0.0) 到 (1.0, 1.0)

        largeBitmapView.setScaleAndCenter(initialScale, initialCenter);

        // Initialize ViewDragHelper
        dragHelper = ViewDragHelper.create((ViewGroup) largeBitmapView.getParent(), 0.5f, new DragHelperCallback());

        // Set onTouchListener to handle touch events for drag
// 设置 PinButtonView 的触摸事件处理逻辑
        pinButtonView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 处理 PinButtonView 的触摸事件
                // 在需要的时候将触摸事件传递给 LargeBitmapView 处理
                pinButtonView.onTouchEvent(event);
                largeBitmapView.dispatchTouchEvent(event);
                return true; // 返回 true 表示此 View 已处理了触摸事件
            }
        });

//button view
        List<PinButton> buttons = new ArrayList<>();
        pinButtonView.startDrawingTask(buttons);

        // Add layout listener using ViewTreeObserver
        // 从 PositionInfoProvider 中获取位置信息
        Map<String, PointF> positionInfo = PositionInfoProvider.getPositionInfo();
        // 设置位置信息到 ArrowView 中
        arrowView.setPositionInfo(positionInfo);
        arrowView_noSharing.setPositionInfo(positionInfo);
        // 添加箭头到 ArrowView 中
        //arrowView.addArrow(AttractionsInfo.GUANQIANJIE,AttractionsInfo.HANSHANSI);



        largeBitmapView.setOnStateChangedListener(new SubsamplingScaleImageView.OnStateChangedListener() {
            @Override
            public void onScaleChanged(float newScale, int origin) {
                // Do nothing
                pinButtonView.setScale(newScale);
                arrowView.setScale(newScale);
                arrowView_noSharing.setScale(newScale);
                conditionalView.setScale(newScale);
                if(newCenterPoint2!=null){
                    drawConditional(newCenterPoint2);
                }

                //BoundService.sendMessageWithLabel("newScale1", String.valueOf(newScale));
            }

            @Override
            public void onCenterChanged(PointF newCenter, int origin) {
                drawFrame(newCenter);
                pinButtonView.setCenter(newCenter);
                arrowView.setCenter(newCenter);
                arrowView_noSharing.setCenter(newCenter);
                drawToOverview(newCenter);
                conditionalView.setCenter(newCenter);
                if(newCenterPoint2!=null){
                    drawConditional(newCenterPoint2);
                }
                centerChangedCount++;
                if (centerChangedCount % 10 == 0) {
                    drawToOverview(newCenter);
                }
                /*String newCenter1=newCenter.x+","+ newCenter.y;
                BoundService.sendMessageWithLabel("newCenterPoint1",newCenter1);                drawToOverview(newCenter);
*/            }
        });
        for (PinButtonInfo buttonInfo : ButtonInfoProvider.getButtonInfoList()) {
            buttonManager.addButton(buttonInfo);
        }
        return view;

    }


    public void onDataChange() {

        // 更新 Spinner 数据
        CustomDialogFragment customDialogFragment = getCustomDialogFragmentInstance();

        // 调用CustomDialogFragment中的方法来更新数据
        if (customDialogFragment != null) {
            customDialogFragment.fetchDataAndUpdateSpinner();
        }

        if(arrowView_noSharing.getVisibility()==arrowView_noSharing.VISIBLE){
            arrowView_noSharing.updateArrow(getContext());
            arrowView_noSharing.updateFlag(getContext());
        }
        if(arrowView.getVisibility()==arrowView.VISIBLE){
            arrowView.updateArrow(getContext());
            arrowView.updateFlag(getContext());
        }
        if(thumbnailView.getVisibility()== thumbnailView.VISIBLE){
            thumbnailView.updateArrow(getContext());
            thumbnailView.updateFlag(getContext());
            conditional=false;
        }


    }
    // 获取对CustomDialogFragment的实例引用的方法
    private CustomDialogFragment getCustomDialogFragmentInstance() {
        FragmentManager fragmentManager = getChildFragmentManager();
        return (CustomDialogFragment) fragmentManager.findFragmentByTag("CustomDialogFragmentTag");
    }


    public void drawFrame(PointF pointF) {
        // 中点在view位置
        PointF centerInViewPointF = largeBitmapView.sourceToViewCoord(pointF);
        // view的四个点
        float viewLeft = largeBitmapView.getWidth() / 2 - centerInViewPointF.x;
        float viewTop = largeBitmapView.getHeight() / 2 - centerInViewPointF.y;
        float viewRight = viewLeft + largeBitmapView.getWidth();
        float viewBottom = viewTop + largeBitmapView.getHeight();

        // view对应大图的位置
        PointF point1 = largeBitmapView.viewToSourceCoord(viewLeft, viewTop);
        PointF point2 = largeBitmapView.viewToSourceCoord(viewRight, viewTop);
        PointF point3 = largeBitmapView.viewToSourceCoord(viewLeft, viewBottom);
        PointF point4 = largeBitmapView.viewToSourceCoord(viewRight, viewBottom);

        //比例
        float left = point1.x * mScale;
        float top = point1.y * mScale;
        float right = point2.x * mScale;
        float bottom = point3.y * mScale;
        left_r = point1.x;
        top_r = point1.y ;
        right_r = point2.x;
        bottom_r = point3.y;
        if(thumbnailView.getVisibility()==thumbnailView.VISIBLE){
            thumbnailView.refreshFrame(left, top, right, bottom);
        }

        if(conditional){
            boolean overlap = isOverlap(left_r, top_r, right_r, bottom_r, left_y, top_y, right_y, bottom_y);

            isShown(overlap);
            String message =point1.x+","+point1.y+","+point4.x+","+point4.y;
            String label="conditional1";
            BoundService.sendMessageWithLabel(label,message);
        }


    }

    private void drawFrame2(final String content) {
        // 在主线程上更新UI
        String[] parts = content.split(",");
        float left = (float) (Float.parseFloat(parts[0]) * mScale);
        float top = (float) (Float.parseFloat(parts[1]) * mScale);
        float right = (float) (Float.parseFloat(parts[2]) * mScale);
        float bottom = (float) (Float.parseFloat(parts[3]) * mScale);
        left_y = (float) (Float.parseFloat(parts[0]));
        top_y = (float) (Float.parseFloat(parts[1])  );
        right_y = (float) (Float.parseFloat(parts[2]));
        bottom_y= (float) (Float.parseFloat(parts[3]));


        if(thumbnailView.getVisibility()==thumbnailView.VISIBLE){
            thumbnailView.yRrefreshFrame(left, top, right, bottom);
        }

        if(conditional){
            boolean overlap = isOverlap(left_r, top_r, right_r, bottom_r, left_y, top_y, right_y, bottom_y);

            isShown(overlap);
        }

    }
    private void drawConditional(String newCenterPoint2){
        float deviceBScale= Float.parseFloat(newScale2);
        String[] parts = newCenterPoint2.split(",");
        if (parts.length != 4) {

            throw new IllegalArgumentException("Invalid string format for PointF");
        }
        float left = Float.parseFloat(parts[0]);
        float top = Float.parseFloat(parts[1]);
        float right= Float.parseFloat(parts[2]);
        float bottom = Float.parseFloat(parts[3]);

        PointF point1=largeBitmapView.sourceToViewCoord(left,top);
        PointF point2=largeBitmapView.sourceToViewCoord(right,top);
        PointF point3=largeBitmapView.sourceToViewCoord(left, bottom);
        PointF point4=largeBitmapView.sourceToViewCoord(right, bottom);

        PointF viewLeft=largeBitmapView.viewToSourceCoord(point1);
        PointF viewBottom=largeBitmapView.viewToSourceCoord(point4);

        float deviceBViewLeft=viewLeft.x;
        float deviceBViewTop=viewLeft.y;
        float deviceBViewRight=viewBottom.x;
        float deviceBViewBottom=viewBottom.y;


        conditionalView.refreshFrame(deviceBViewLeft, deviceBViewTop, deviceBViewRight, deviceBViewBottom);


    }
    public boolean isOverlap(float left_r, float top_r, float right_r, float bottom_r,
                             float left_y, float top_y, float right_y, float bottom_y) {

        return !(left_r >= right_y || right_r <= left_y || top_r >= bottom_y || bottom_r <= top_y);
    }

    public boolean isShown(boolean isOverlap) {

        if(isOverlap&&conditional){
            onDataChange();
            arrowView_noSharing.setVisibility(View.INVISIBLE);
            arrowView.setVisibility(View.VISIBLE);
        }
        else if(conditional){
            onDataChange();
            arrowView_noSharing.setVisibility(View.VISIBLE);
            arrowView.setVisibility(View.INVISIBLE);

        }
        return true;
    }

    private void drawToOverview(PointF pointF){
        //中点在view位置
        PointF centerInViewPointF = largeBitmapView.sourceToViewCoord(pointF);
        //view的四个点
        float viewLeft = largeBitmapView.getWidth() / 2 - centerInViewPointF.x;
        float viewTop = largeBitmapView.getHeight() / 2 - centerInViewPointF.y;
        float viewRight = viewLeft + largeBitmapView.getWidth();
        float viewBottom = viewTop + largeBitmapView.getHeight();

        //view对应大图的位置
        PointF point1 = largeBitmapView.viewToSourceCoord(viewLeft, viewTop);
        PointF point2 = largeBitmapView.viewToSourceCoord(viewRight, viewTop);
        PointF point3 = largeBitmapView.viewToSourceCoord(viewLeft, viewBottom);
        PointF point4 = largeBitmapView.viewToSourceCoord(viewRight, viewBottom);
        float left1 = point1.x;
        float top1 = point1.y ;
        float right1 = point2.x;
        float bottom1 = point3.y;

        String label = "newCenter1";
        String message=left1+","+top1+","+right1+","+bottom1;

        BoundService.sendMessageWithLabel(label,message);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thumbnailView = view.findViewById(R.id.thumbnailView);
        largeBitmapView=view.findViewById(R.id.largeBitmapView);
        conditionalView=view.findViewById(R.id.conditionalView);
        conditionalView.setVisibility(View.GONE);
        thumbnailView.setVisibility(View.GONE);
        arrowView_noSharing.setVisibility(View.GONE);
        pinButtonView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 解绑服务
        if (mServiceBound) {
            getActivity().unbindService(serviceConnection);
            mServiceBound = false;
        }
        // 在 onDestroyView 方法中取消注册广播接收器，以防止内存泄漏
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver);
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return view == largeBitmapView;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            // 获取图像的宽度
            int imgWidth = largeBitmapView.getSWidth();

            // 计算最大允许的左边界
            int maxLeft = largeBitmapView.getWidth() - imgWidth;

            // 确保 left 不超过边界
            return Math.max(0, Math.min(left, maxLeft));
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            // 获取图像的高度
            int imgHeight = largeBitmapView.getSHeight();

            // 计算最大允许的上边界
            int maxTop = largeBitmapView.getHeight() - imgHeight;

            // 确保 top 不超过边界
            return Math.max(0, Math.min(top, maxTop));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu); // 在片段中创建菜单
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_reload) {
            dBhelper.resetDatabase();
            BoundService.sendMessageWithLabel("reset1", "reset");
            onDataChange();
            return true;
        }
        if (id == R.id.action_refresh) {
            onDataChange();
            if (getActivity() != null) {
                getActivity().stopService(new Intent(getActivity(), User1ConnectionService.class));

                getActivity().startService(new Intent(getActivity(), User1ConnectionService.class));
            }
            return true;
        }
        if (id == R.id.continuousSharing) {
            conditionalView.setVisibility(View.GONE);
            thumbnailView.setVisibility(View.VISIBLE);
            arrowView_noSharing.setVisibility(View.GONE);
            arrowView.setVisibility(View.VISIBLE);
            toggleNavigationVisibility();
            conditional=false;
            onDataChange();
            return true;
        }
        if (id == R.id.noSharing) {
            conditionalView.setVisibility(View.GONE);
            thumbnailView.setVisibility(View.GONE);
            arrowView_noSharing.setVisibility(View.VISIBLE);
            arrowView.setVisibility(View.GONE);
            conditional=false;
            onDataChange();
            return true;
        }
        if (id == R.id.conditionalSharing) {
            thumbnailView.setVisibility(View.GONE);
            conditionalView.setVisibility(View.VISIBLE);

            conditional=true;
            onDataChange();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleNavigationVisibility() {
        if (thumbnailView.getVisibility() == View.VISIBLE) {
            showNavigation();
        } else {
            hideNavigation();
        }
    }

    private void showNavigation() {
        // Load and show navigation image
        thumbnailView.setVisibility(View.VISIBLE);
        Map<String, PointF> positionInfo = PositionInfoProvider.getPositionInfo();
        thumbnailView.setPositionInfo(positionInfo);

        int navigationWidth = (int) (largeBitmapView.getWidth() * NAVIGATION_SCREEN_WIDTH_SCALE);
        mScale = (float) navigationWidth / largeBitmapView.getSWidth();
        int navigationHeight = (int) (largeBitmapView.getSHeight() * mScale);
        // Set the size of the navigation image view
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(navigationWidth, navigationHeight);
        thumbnailView.setLayoutParams(layoutParams);

        // Generate thumbnail
        Bitmap thumbnail = BitmapUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.suzhou_200, navigationWidth, navigationHeight);
        thumbnailView.setScale(mScale);
        // 缩放缩略图到合适的大小
        thumbnail = Bitmap.createScaledBitmap(thumbnail, navigationWidth, navigationHeight, true);

        // Apply thumbnail to thumbnailView
        thumbnailView.setThumbnailBitmap(thumbnail);
        //thumbnailView.addArrow(AttractionsInfo.GUANQIANJIE,AttractionsInfo.HANSHANSI);
    }


    private void hideNavigation() {
        // Hide navigation image
        thumbnailView.setVisibility(View.GONE);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true); // 确保片段拥有选项菜单
    }


}