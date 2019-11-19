package me.andika.lockscreen;

import android.app.Activity;
//import android.content.Intent;
//import android.os.Build;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.Toast;
//import android.graphics.PixelFormat;
//import android.view.WindowManager;

//import java.util.List;
//import java.util.Random;
//import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
//import me.andika.lockscreen.HomeKeyLocker;
import java.util.Timer;
import java.util.TimerTask;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import me.andika.lockscreen.helpers.LockscreenUnlocker;

import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
import static me.andika.lockscreen.MainActivity.counter;
import static me.andika.lockscreen.MainActivity.level;
import static me.andika.lockscreen.MainActivity.operator;


public class LockScreenActivity extends Activity  implements View.OnTouchListener{

    Button button, button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonClear,buttonMinus;
    String result,result11,result22, pre_result1;
    TextView resultTextView , alert_my;
    TextView first, second, operation;
    public WindowManager windowManager;
    int count = 0;
    int pre_result = 0;
    int first_num = 0;
    int second_num = 0;
    private final int OVERLAY_PERMISSION_REQUEST = 8776;
    private HomeKeyLocker homeKeyLocker = new HomeKeyLocker();
    public RelativeLayout lockscreenContainer;
    private static int strong_save1 = 0;
    private LockscreenUnlocker unlocker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setType(TYPE_SYSTEM_OVERLAY);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        try {
//               finish();
               lockHome();
        }
        catch (WindowManager.BadTokenException e) {
            // On Marshmallow and above this is an issue. We don't have the permission to draw over other applications even though it is declared in the manifest
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkDrawOverlayPermission();
            }
        }
    }

    private void checkDrawOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST);
            finish();
        }
    }

    public void initUI(){
        first = (TextView) lockscreenContainer.findViewById(R.id.first);
        second = (TextView) lockscreenContainer.findViewById(R.id.second);
        operation = (TextView) lockscreenContainer.findViewById(R.id.operation);
        alert_my = (TextView) lockscreenContainer.findViewById(R.id.my_alert);
        operation.setText("+");
        initControl();
        initControlListener();
        initSetting();

        button = (Button) lockscreenContainer.findViewById(R.id.unlock);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                caculate();
            }
        });
    }

    private void caculate() {
        if(result == null || Integer.parseInt(pre_result1) != Integer.parseInt(result)){
            Toast.makeText(LockScreenActivity.this, "You must type the correct number.", Toast.LENGTH_SHORT).show();
            count = count + 1;
            strong_save1 = 0;
            if(count == counter){
                count = 0;
                resultTextView.setFocusable(false);
                button.setEnabled(false);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
                buttonClear.setEnabled(false);
                buttonMinus.setEnabled(false);
                Toast.makeText(LockScreenActivity.this, "You are a poor child,  You must weit for 30 seconds", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        resultTextView.setFocusable(true);
                        button.setEnabled(true);
                        button0.setEnabled(true);
                        button1.setEnabled(true);
                        button2.setEnabled(true);
                        button3.setEnabled(true);
                        button4.setEnabled(true);
                        button5.setEnabled(true);
                        button6.setEnabled(true);
                        button7.setEnabled(true);
                        button8.setEnabled(true);
                        button9.setEnabled(true);
                        buttonMinus.setEnabled(true);
                        buttonClear.setEnabled(true);
                    }
                }, 20000);
            }
        }else {
            strong_save1 = strong_save1 + 1;
            if(strong_save1 == 20){
                Intent intent = new Intent(LockScreenActivity.this, MainActivity.class);
                intent.putExtra("level_set", 2);
                startActivity(intent);
            }
            if(strong_save1 == 40){
                Intent intent = new Intent(LockScreenActivity.this, MainActivity.class);
                intent.putExtra("level_set", 3);
                startActivity(intent);
            }
            if(strong_save1 >= 60){
                Intent intent = new Intent(LockScreenActivity.this, MainActivity.class);
                intent.putExtra("level_set", 4);
                startActivity(intent);
            }
            if (strong_save1 >= 80){
                strong_save1 = 60;
            }

            dismissLockscreenAndFinish();
            finish();
        }
    }

    private void initSetting(){
        // Generate random int
        if(level == "1"){
            first_num = (int)(Math.random() * 9 + 1);
            second_num = (int)(Math.random() * 9 + 1);

        }else if(level == "2"){
            first_num = (int)(Math.random() * 99 + 10);
            second_num = (int)(Math.random() * 9 + 1);
        }else if(level == "3"){
            first_num = (int)(Math.random() * 99 + 10);
            second_num = (int)(Math.random() * 99 + 10);
        }else if(level == "4"){
            first_num = (int)(Math.random() * 99 + 1);
            second_num = (int)(Math.random() * 99 + 1);
        }else{
            first_num = (int)(Math.random() * 9 + 1);
            second_num = (int)(Math.random() * 9 + 1);
        }

        // determine the operation level
        if(operator == "+"){
            pre_result = first_num + second_num;
            operation.setText("+");
        }else if(operator == "-"){
            operation.setText("-");
            pre_result = first_num - second_num;
        }else if(operator == "*"){
            operation.setText("*");
            pre_result = first_num * second_num;
        }else if(operator == "/"){
            for(int i=1; i<1000; i++){
                if(level == "1"){
                    first_num = (int)(Math.random() * 9 + 1);
                    second_num = (int)(Math.random() * 9 + 1);

                }else if(level == "2"){
                    first_num = (int)(Math.random() * 99 + 10);
                    second_num = (int)(Math.random() * 9 + 1);
                }else if(level == "3"){
                    first_num = (int)(Math.random() * 99 + 10);
                    second_num = (int)(Math.random() * 99 + 10);
                }else{
                    first_num = (int)(Math.random() * 99 + 1);
                    second_num = (int)(Math.random() * 99 + 1);
                }

                if (first_num % second_num ==0){
                    break;
                }
            }
            operation.setText("/");
            pre_result = first_num / second_num;
        }

        Integer intInstance1 = new Integer(first_num);
        result11 = intInstance1.toString();
        Integer intInstance2 = new Integer(second_num);
        result22 = intInstance2.toString();

        first.setText(result11);
        second.setText(result22);
        Integer intInstance = new Integer(pre_result);
        pre_result1 = intInstance.toString();
    }

    private void lockHome(){
        lockscreenContainer = new RelativeLayout(getBaseContext());
        windowManager = ((WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE));

        if (lockscreenContainer == null || windowManager == null) {
            Toast.makeText(getBaseContext(), R.string.error_lockscreen_not_shown, Toast.LENGTH_LONG)
                    .show();
            return;
        }

        View.inflate(this, R.layout.lock_screen, lockscreenContainer);
        initUI();
        lockscreenContainer.setOnTouchListener(this);
        windowManager.addView(lockscreenContainer, constructWindowParams());
    }

    private WindowManager.LayoutParams constructWindowParams() {
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = getWindowType();
        localLayoutParams.format = PixelFormat.TRANSLUCENT;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;

        return localLayoutParams;
    }
    private int getWindowType() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? TYPE_APPLICATION_OVERLAY : TYPE_SYSTEM_ALERT;
    }
    private void initControlListener() {

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("0");
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("2");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("3");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("4");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("5");
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("6");
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("7");
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("8");
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("9");
            }
        });
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("-");
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearButtonClicked();
            }
        });
    }

    private void onClearButtonClicked() {
        result = "";
        resultTextView.setText("");
    }

    private void onNumberButtonClicked(String pos) {
        result = resultTextView.getText().toString();
        result = result + pos;
        resultTextView.setText(result);
    }

    private void initControl() {
        button0 = (Button)lockscreenContainer.findViewById(R.id.button0);
        button1 = (Button)lockscreenContainer.findViewById(R.id.button1);
        button2 = (Button)lockscreenContainer.findViewById(R.id.button2);
        button3 = (Button)lockscreenContainer.findViewById(R.id.button3);
        button4 = (Button)lockscreenContainer.findViewById(R.id.button4);
        button5 = (Button)lockscreenContainer.findViewById(R.id.button5);
        button6 = (Button)lockscreenContainer.findViewById(R.id.button6);
        button7 = (Button)lockscreenContainer.findViewById(R.id.button7);
        button8 = (Button)lockscreenContainer.findViewById(R.id.button8);
        button9 = (Button)lockscreenContainer.findViewById(R.id.button9);
        buttonMinus = (Button)lockscreenContainer.findViewById(R.id.button_minus);
        buttonClear = (Button)lockscreenContainer.findViewById(R.id.buttonClear);
        resultTextView = (TextView)lockscreenContainer.findViewById(R.id.text_view_result);
    }

//    @Override
//    public void onBackPressed() {
//        // do nothing.
//    }
//
////     delete left device button
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (unlocker == null) {
            return false;
        }

        // Send motion event to unlocker
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                unlocker.onUserTouchDown(motionEvent);
                break;
            case MotionEvent.ACTION_UP:
                unlocker.onUserTouchUp(motionEvent);
                break;
            case MotionEvent.ACTION_MOVE:
                unlocker.onUserTouchMove(motionEvent);
                break;
        }

        return true;
    }

    private void dismissLockscreenAndFinish() {
        try {
            homeKeyLocker.unlock();


            windowManager.removeView(lockscreenContainer);
            lockscreenContainer.removeAllViews();
            finishAffinity();
            overridePendingTransition(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

}
