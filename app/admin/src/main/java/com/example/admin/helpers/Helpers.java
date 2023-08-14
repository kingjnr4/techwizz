package com.example.admin.helpers;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.R;
import org.signal.argon2.Argon2;
import org.signal.argon2.MemoryCost;
import org.signal.argon2.Type;
import org.signal.argon2.Version;
import org.signal.argon2.Argon2Exception;
import org.signal.argon2.UnknownTypeException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public  class Helpers {
    static String salt = "SALTY_SOCCER";
    static Argon2 argon2 = new Argon2.Builder(Version.V13)
            .type(Type.Argon2id)
            .memoryCost(MemoryCost.MiB(32))
            .parallelism(1)
            .iterations(3)
            .build();

    public static String encrypt(String password) throws Argon2Exception {
        Argon2.Result result = argon2.hash(password.getBytes(), salt.getBytes());
        return result.getEncoded();
    }

    public static Boolean verify(String encoded,String password) throws UnknownTypeException {
        return Argon2.verify(encoded,password.getBytes(),Type.Argon2id);
    }

    public static void makeShortToast(Activity activity, String message){
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
    public static String generateRandomImageName() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String randomString = getRandomString(6); // Adjust the length of the random string
        return "image_" + timestamp + "_" + randomString + ".jpg";
    }

    public static String getRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
    }
}