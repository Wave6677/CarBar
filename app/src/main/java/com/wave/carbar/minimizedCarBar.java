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
import android.net.Uri;
import android.content.Intent;

import static android.content.Context.WINDOW_SERVICE;

public class minimizedCarBar {
    // declaring required variables
    private final Context context;
    private final View mView;
    private final WindowManager mWindowManager;
    private final WindowManager.LayoutParams mParams;
    @SuppressLint("InflateParams")
    public minimizedCarBar(Context context){
        this.context=context;
        // set the layout parameters of the window
        mParams = new WindowManager.LayoutParams(
                // Shrink the window to wrap the content rather
                // than filling the screen
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                // Display it on top of other application windows
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                // Don't let it grab the input focus
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                // Make the underlying application window visible
                // through any transparent parts
                PixelFormat.TRANSLUCENT);
        // getting a LayoutInflater
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflating the view with the custom layout we created
        mView = layoutInflater.inflate(R.layout.minimized_layout, null);
        // set onClickListener on the remove button, which removes
        mParams.gravity = Gravity.START;
        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);
    }

    public void open() {
        try {
            // check if the view is already
            // inflated or present in the window
            if(mView.getWindowToken()==null) {
                if(mView.getParent()==null) {
                    mWindowManager.addView(mView, mParams);
                }
            }
        } catch (Exception e) {
            Log.d("Error1",e.toString());
        }
    }

    public void close() {
        try {
            // remove the view from the window
            ((WindowManager)context.getSystemService(WINDOW_SERVICE)).removeView(mView);
            // invalidate the view
            mView.invalidate();
            // remove all views
            ((ViewGroup)mView.getParent()).removeAllViews();

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (Exception e) {
            Log.d("Error2",e.toString());
        }
    }

}
