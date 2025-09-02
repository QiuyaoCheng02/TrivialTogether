package com.example.user2.classes.dialogs;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user2.User2ConnectionService;
import com.example.user2.classes.DBhelper;
import com.example.user1.R;
import com.example.user2.classes.Logger;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */


public class CustomDialogFragment extends DialogFragment {

    private ImageView imageView;
    private TextView name;
    private TextView time;
    private PointF pointF;
    private ImageButton addButton;
    private ImageButton minusButton;
    private Button confirmButton;
    private Button cancelButton;
    private Spinner spinnerVisitOrder;
    private CustomSpinnerAdapter spinnerAdapter;
    private List<String> spinnerDataList = new ArrayList<>();
    private AlertDialog alertDialog;
    private User2ConnectionService BoundService;
    private DBhelper dbHelper;
    private boolean mServiceBound = false;
    private double allocatedTime;
    private double remainingTime;
    private Logger logger;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Nullable
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 这个方法在 Service 与 Fragment 建立连接时调用
            BoundService = ((User2ConnectionService.LocalBinder)  service).getService();
            mServiceBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 这个方法在 Service 与 Fragment 断开连接时调用
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_dialog, container, false);
        Intent intent = new Intent(getActivity(), User2ConnectionService.class);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        dbHelper = new DBhelper(getActivity());
        imageView = view.findViewById(R.id.mainImage);
        name = view.findViewById(R.id.name);
        time = view.findViewById(R.id.time);
        addButton = view.findViewById(R.id.addButton);
        minusButton = view.findViewById(R.id.minusButton);
        DialogInfo dialogInfo = getArguments().getParcelable("dialogInfo");
        String name1=dialogInfo.getTag();

        String log="on click "+name1;
        try {
            Logger.logToFile(log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DBhelper dbHelper = new DBhelper(getContext()); // 实例化DBHelper对象
        int currentOrder= dbHelper.getPlaceOrder(name1);
        boolean b = currentOrder == -1;
        double remainingTime=getRemainingTime();
        if(remainingTime<Double.parseDouble(dialogInfo.getTime())&&b)
        {
            addButton.setImageResource(R.drawable.add_gray);
        }

        // Receive DialogInfo object from arguments

        if (dialogInfo != null) {
            imageView.setImageResource(dialogInfo.getImageResId());
            name.setText(dialogInfo.getName());
            if(dialogInfo.getTime().equals("1.0")){
                time.setText(dialogInfo.getTime()+" hour");
            }else{
                time.setText(dialogInfo.getTime()+" hours");
            }

            pointF = dialogInfo.getPointF();
        }
        if(currentOrder!=-1){
            minusButton.setVisibility(View.VISIBLE);
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建并显示一个新的Dialog
                if(remainingTime<Double.parseDouble(dialogInfo.getTime())&&b)
                {
                    Toast.makeText(requireContext(), "Not enough time", Toast.LENGTH_SHORT).show();
                }
                else{
                    showDialog();

                    String log="check add "+name1;
                    try {
                        Logger.logToFile(log);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建并显示一个新的Dialog
                dbHelper.deletePlaceOrder(name1);
                String label="update2";
                String content=name1+";"+"delete ; delete";
                BoundService.sendMessageWithLabel(label,content);

                String log="delete "+name1;
                try {
                    Logger.logToFile(log);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                dismiss();


            }
        });
        return view;
    }
    public double getRemainingTime(){
        allocatedTime=dbHelper.calculateAllocatedTime();
        remainingTime=7.0-allocatedTime;

        return remainingTime;
    }
    private void showDialog() {
        // 加载新的Dialog布局文件
        View dialogView = getLayoutInflater().inflate(R.layout.add_dialog, null);
        DBhelper dbHelper = new DBhelper(getContext()); // 实例化DBHelper对象
        // 初始化Spinner和适配器
        spinnerVisitOrder = dialogView.findViewById(R.id.spinner_visit_order);
        confirmButton = dialogView.findViewById(R.id.btn_confirm);
        cancelButton = dialogView.findViewById(R.id.btn_cancel);
        spinnerAdapter = new CustomSpinnerAdapter(getActivity(), spinnerDataList);
        spinnerVisitOrder.setAdapter(spinnerAdapter);
        // Receive DialogInfo object from arguments
        DialogInfo dialogInfo = getArguments().getParcelable("dialogInfo");
        String name=dialogInfo.getTag();
        // 获取FragmentManager并显示CustomDialogFragment

        int currentOrder= dbHelper.getPlaceOrder(name);
        int allOrder=dbHelper.getValidPlaceCount();
        if(allOrder==0){
            updateSpinnerDataList(1);
            updateSpinnerSelection(1);
        }
        else if(currentOrder==-1){
            updateSpinnerDataList(allOrder+1);
            updateSpinnerSelection(allOrder+1);
        }else{
            updateSpinnerSelection(currentOrder);
            updateSpinnerDataList(allOrder);
        }


        // 创建Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOrder = spinnerVisitOrder.getSelectedItemPosition() + 1; // 获取Spinner当前选择的顺序，加1是因为Spinner的索引是从0开始的
                int currentOrder = dbHelper.getPlaceOrder(name); // 获取当前景点的顺序
                if (currentOrder == -1) {
                    // >=n 的部分 +1
                    dbHelper.increaseOrders(selectedOrder,10);
                }
                else if (selectedOrder > currentOrder) {

                    // 如果新选择的顺序大于当前顺序，则将数据库中大于当前顺序，小于等于新选择顺序的所有顺序都减1
                    dbHelper.decreaseOrders(currentOrder + 1, selectedOrder);
                } else if (selectedOrder < currentOrder) {
                    // 如果新选择的顺序小于当前顺序，则将数据库中大于等于新选择顺序，小于当前顺序的所有顺序都加1
                    dbHelper.increaseOrders(selectedOrder, currentOrder - 1);
                }

                // 更新当前景点的顺序为新选择的顺序
                dbHelper.updatePlaceOrder(name, selectedOrder);

                String label="update2";
                String content=name+";"+selectedOrder;

                String log="change "+name+" from "+currentOrder+" to "+selectedOrder;
                try {
                    Logger.logToFile(log);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // 关闭Dialog
                BoundService.sendMessageWithLabel(label,content);

                alertDialog.dismiss();
                CustomDialogFragment.super.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log="cancel change "+name+" from "+currentOrder;
                try {
                    Logger.logToFile(log);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                alertDialog.dismiss();
                CustomDialogFragment.super.dismiss();
            }
        });

        // 创建并显示Dialog
        alertDialog = builder.create();
        alertDialog.show();
    }


    public  void fetchDataAndUpdateSpinner(){
        DBhelper dbHelper = new DBhelper(getContext()); // 实例化DBHelper对象
        int validPlaceCount = dbHelper.getValidPlaceCount(); // 调用实例化对象上的方法
        updateSpinnerDataList(validPlaceCount);
    }
    // 更新Spinner的数据源
    public void updateSpinnerDataList(int itemCount) {
        List<String> newDataList = new ArrayList<>();
        for (int i = 1; i <= itemCount; i++) {
            newDataList.add(String.valueOf(i));
        }
        spinnerDataList.clear();
        spinnerDataList.addAll(newDataList);
        spinnerAdapter.notifyDataSetChanged();

        // 更新Spinner的选项数量
        updateSpinnerItemCount(itemCount);
    }

    // 更新Spinner的默认选项
    private void updateSpinnerSelection(int order) {
        spinnerVisitOrder.setSelection(order - 1); // 默认显示order
    }

    // 更新Spinner的选项数量
    private void updateSpinnerItemCount(int itemCount) {
            spinnerAdapter.setCount(itemCount);
            spinnerAdapter.notifyDataSetChanged();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 清空图片资源
        imageView.setImageDrawable(null);
        imageView=null;
        // 清空按钮资源和点击事件监听器
        if (addButton != null) {
            addButton.setImageDrawable(null);
            addButton.setOnClickListener(null);
        }
        if (minusButton != null) {
            minusButton.setImageDrawable(null);
            minusButton.setOnClickListener(null);
        }
        if (cancelButton != null) {
            cancelButton.setBackground(null);
            cancelButton.setOnClickListener(null);
        }
        if (confirmButton != null) {
            confirmButton.setBackground(null);
            confirmButton.setOnClickListener(null);
        }

        time=null;
        name=null;
        // 清空 Spinner 的适配器和选择监听器
        if (spinnerVisitOrder != null) {

            spinnerVisitOrder.setAdapter(null);
            spinnerVisitOrder.setOnItemSelectedListener(null);
            spinnerAdapter=null;
        }
        // 解绑服务
        if (mServiceBound) {
            getActivity().unbindService(serviceConnection);
            mServiceBound = false;
        }
        // 关闭数据库连接
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null; // 将 dbHelper 置为 null
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
