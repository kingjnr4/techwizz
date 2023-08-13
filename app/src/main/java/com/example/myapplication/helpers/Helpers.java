package com.example.myapplication.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.*;
import android.widget.*;
import com.example.myapplication.R;

public class Helpers {
    public static void makeShortToast(Activity activity,String message){
        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show();
    }

    public static void makeLongToast(Activity activity,String message){
        Toast.makeText(activity,message, Toast.LENGTH_LONG).show();
    }

    public static void showPopupWindow(Activity activity, String message, int autoCloseDelayMillis,int color,int textColor) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);

        TextView popupMessageTextView = popupView.findViewById(R.id.text_id);
        LinearLayout linearLayout = popupView.findViewById(R.id.dialog_id);
        popupMessageTextView.setText(message);
        popupMessageTextView.setTextColor(textColor);
        linearLayout.setBackgroundColor(color);

        int width = 1000;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setBackgroundDrawable(null);

        // Show the popup window at the center of the screen
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 120);

        // Close the popup window after a delay
        new Handler().postDelayed(popupWindow::dismiss, autoCloseDelayMillis);
    }
}
