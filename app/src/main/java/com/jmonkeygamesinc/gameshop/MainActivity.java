package com.jmonkeygamesinc.gameshop;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.jme3.app.LegacyApplication;
import com.jme3.system.AppSettings;
import com.jme3.view.surfaceview.JmeSurfaceView;
import com.jme3.view.surfaceview.OnExceptionThrown;
import com.jme3.view.surfaceview.OnRendererCompleted;

import java.io.File;
import java.io.FileWriter;

/**
 * <b>NB: Please Open this example <u>root module</u> using Android Studio; because android build scripts are different from java builds.</b>
 * <br/>
 * An Android Example that demonstrates : How to use a simple game#{@link MyGame}
 * on #{@link com.jme3.view.surfaceview.JmeSurfaceView} inside an #{@link androidx.appcompat.app.AppCompatActivity}.
 * <br>
 * <b>Note : use #{@link AppCompatActivity#setRequestedOrientation(int)} and #{@link ActivityInfo#SCREEN_ORIENTATION_LANDSCAPE} for LandScape mode or specify that under the <activity> activity tag xml.</b>
 *
 * @author Lynden Jay Evans of JMonkeyGames Inc.
 */
public final class MainActivity extends AppCompatActivity implements OnRendererCompleted, OnExceptionThrown {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*define the android view with it's id from xml*/
        final JmeSurfaceView jmeSurfaceView = findViewById(R.id.jmeSurfaceView);
        jmeSurfaceView.setDestructionPolicy(JmeSurfaceView.DestructionPolicy.KEEP_WHEN_FINISH);
        /*set the jme game*/
        jmeSurfaceView.setLegacyApplication(new MyGame(this.getApplicationContext()));
        jmeSurfaceView.setOnExceptionThrown(this);
        jmeSurfaceView.setOnRendererCompleted(this);
        /*start the game*/
        jmeSurfaceView.startRenderer(500);
    }

    /**
     * Fired when exception/error/(concretes of #{@link Throwable} class) is thrown.
     *
     * @param e the thrown error or exception
     */
    @Override
    public void onExceptionThrown(Throwable e) {
        Toast.makeText(MainActivity.this, "User's Delay Finished w/ exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    /**
     * Fired when the user delay in ms is up #{@link JmeSurfaceView#startRenderer(int)}.
     *
     * @param application the current jme game instance
     * @param appSettings the current game settings
     */
    @Override
    public void onRenderCompletion(LegacyApplication application, AppSettings appSettings) {
        Toast.makeText(MainActivity.this, "User's Delay Finished w/o errors !" + application.getContext() + " " + appSettings.getFrameRate(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Fired when the screen has/hasNo touch/mouse focus.
     *
     * @param hasFocus specify whether the current screen has focus or not
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        /*get the view from the current activity*/
        final View decorView = MainActivity.this.getWindow().getDecorView();
        /*hide navigation bar, apply fullscreen, hide status bar, immersive sticky to disable the system bars(nav and status) from showing up when user wipes the screen*/
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


}