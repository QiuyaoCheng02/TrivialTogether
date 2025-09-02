package com.example.user1.classes.button;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.user1.classes.AttractionsInfo;
import com.example.user1.classes.dialogs.CustomDialogFragment;
import com.example.user1.classes.dialogs.DialogInfo;
import com.example.user1.classes.dialogs.DialogInfoProvider;

import java.util.List;

public class ButtonClickListener implements View.OnClickListener {
    private CustomDialogFragment dialogFragment;
    private DialogInfo dialogInfo;
    private FragmentManager fragmentManager;

    public ButtonClickListener(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof PinButton) {
            PinButton button = (PinButton) v.getTag();
            dialogInfo = getDialogInfo(button.getName());
            if (dialogInfo != null) {
                if (dialogFragment == null) {
                    // 如果对话框实例不存在，则创建新的对话框实例
                    dialogFragment = new CustomDialogFragment();
                    Bundle args = new Bundle();
                    args.putParcelable("dialogInfo", dialogInfo);
                    dialogFragment.setArguments(args);
                } else {
                    // 如果对话框实例已存在，则更新参数
                    Bundle args = dialogFragment.getArguments();
                    args.putParcelable("dialogInfo", dialogInfo);
                    dialogFragment.setArguments(args);
                }

                // 显示对话框
                if (!dialogFragment.isAdded()) {
                    dialogFragment.show(fragmentManager, "custom_dialog");
                }
            }
        }
    }

    private DialogInfo getDialogInfo(String buttonName) {
        List<DialogInfo> dialogInfoList = DialogInfoProvider.getDialogInfoList();
        for (DialogInfo dialogInfo : dialogInfoList) {
            if (dialogInfo.getTag().equals(buttonName)) {
                return dialogInfo;
            }
        }
        return null;
    }
}
