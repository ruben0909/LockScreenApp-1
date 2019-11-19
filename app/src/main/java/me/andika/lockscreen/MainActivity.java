package me.andika.lockscreen;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import me.andika.lockscreen.utils.LockScreen;
import me.andika.lockscreen.utils.LockWindowAccessibilityService;

public class MainActivity extends AppCompatActivity {

    ToggleButton toggleButton;
    RadioButton easy, difficult, medium, auto, division, multiply, plus, minus;
    protected static String operator = "+";
    protected static String level;
    protected static String saved_password;
    EditText editPassword;
    TextView showValue;
    protected static int counter =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // conunter
        editPassword  = (EditText) findViewById(R.id.edit_password);
        editPassword.setText(saved_password);
        Button button  = (Button) findViewById(R.id.set_password);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                saved_password = editPassword.getText().toString();
                Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // conunter
        showValue =  (TextView) findViewById(R.id.textView);
        String s1 =  showValue.getText().toString();
        counter = new Integer(Integer.parseInt(s1));

        plus = (RadioButton)findViewById(R.id.plus);
        minus = (RadioButton)findViewById(R.id.minus);
        multiply = (RadioButton)findViewById(R.id.multiply);
        division = (RadioButton)findViewById(R.id.division);
        easy = (RadioButton)findViewById(R.id.easy);
        difficult = (RadioButton)findViewById(R.id.difficult);
        medium = (RadioButton)findViewById(R.id.medium);
        auto = (RadioButton)findViewById(R.id.auto);

        Intent intent = getIntent();
        if(intent != null){
            int setLevel = intent.getIntExtra("level_set", 0);
            if(setLevel == 2){
                medium.setChecked(true);
                level = "2";
            }else if(setLevel == 3){
                difficult.setChecked(true);
                level = "3";
            }else if(setLevel == 4){
                level = "4";
                auto.setChecked(true);
            }else{
                level = "1";
                easy.setChecked(true);
            }
        }

        plus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ) {operator = "+";}
            }
        });
        minus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ){operator = "-";}
            }
        });
        multiply.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ){
                    operator = "*";
                }
            }
        });
        division.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ){
                    operator = "/";
                }
            }
        });
        // select the lavel
        easy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    level = "1";
                }
            }
        });
        medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ){level = "2";}
            }
        });
        difficult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ){level = "3";}
            }
        });
        auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ){level = "4";}
            }
        });

        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        LockScreen.getInstance().init(this,true);
        if(LockScreen.getInstance().isActive()){
            toggleButton.setChecked(true);
        }else{
            toggleButton.setChecked(false);
        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    LockScreen.getInstance().active();
                }else{
                    LockScreen.getInstance().deactivate();
                }
            }
        });

    }

    public void countIn(View v){
        counter++;
        showValue.setText(Integer.toString(counter));
    }

    public void countDe(View v){
        counter--;
        if(counter <= 0){
            counter =0;
        }
        showValue.setText(Integer.toString(counter));
    }

    public void resetCount(View v){
        counter = 0;
        showValue.setText(Integer.toString(counter));
    }
}
