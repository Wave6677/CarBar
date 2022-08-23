package com.wave.carbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;

public class FirstFragment {
    private final View mView;

    //Opens a new instance of the CarBar and closes this instance of the Minimized bar.
    public void expandCarBar(Context context) {
        CarBarWindow CarBar = new CarBarWindow(context);
        CarBar.open();
    }

    public FirstFragment(Context context, View mView){
        this.mView = mView;
        String text_label = "hello";
        // Expands the CarBar by calling the exapndCarBar function (See above)
        View contextView = null;
        mView.findViewById(R.id.tesButton).setOnClickListener(view -> {
            Snackbar.make(contextView, R.string.text_label, Snackbar.LENGTH_SHORT)
                    .show();
        });
        //Align to the primary side of the screen set by the user (May change later to allow user defined area)
    }
}