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

public class CarBarWindow {
    // declaring required variables
    private final Context context;
    private final View mView;
    private final WindowManager mWindowManager;
    private final WindowManager.LayoutParams mParams;
    //Replace with user selected apps later
    public String musicApp = "it.vfsfitvnm.vimusic";
    public String mapsApp = "net.osmand.plus";
    public String textsApp = "org.thoughtcrime.securesms";
    public String homeApp = "com.wave.carbar";

    public void startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void minimizeCarBar(Context context) {
        minimizedCarBar minimizedCarBar = new minimizedCarBar(context);
        minimizedCarBar.open();
        close();
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
            Log.d("Error1a",e.toString());
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

    @SuppressLint("InflateParams")
    public CarBarWindow(Context context){
        this.context=context;
        // set the layout parameters of the window
        mParams = new WindowManager.LayoutParams(
                // Shrink the window to wrap the content rather
                // than filling the screen
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
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
        mView = layoutInflater.inflate(R.layout.carbar_window, null);
        // set onClickListener on the remove button, which removes
        // the view from the window
        //Music button func
       mView.findViewById(R.id.open_music).setOnClickListener(view -> startNewActivity(context, musicApp));
        //Maps button func
        mView.findViewById(R.id.open_maps).setOnClickListener(view -> startNewActivity(context, mapsApp));
        //Texts button func
        mView.findViewById(R.id.open_texts).setOnClickListener(view -> startNewActivity(context, textsApp));
        //Minimize func
        mView.findViewById(R.id.homeButton).setOnClickListener(view -> startNewActivity(context, homeApp));
        //Minimize func
        mView.findViewById(R.id.minimizeButton).setOnClickListener(view -> minimizeCarBar(context));
        //Close func
      mView.findViewById(R.id.closeButton).setOnClickListener(view -> close());      // Define the position of the
        // window within the screen
        mParams.gravity = Gravity.BOTTOM;
        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);
    }
}
