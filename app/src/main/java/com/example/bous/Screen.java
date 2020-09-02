package com.example.bous;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Screen {
    public static void display(String message, Context context) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity((Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL), 1, 5);
        toast.show();
    }

}
