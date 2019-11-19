package me.andika.lockscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static me.andika.lockscreen.MainActivity.saved_password;

public class PasswordActivity extends AppCompatActivity {
    String password;
    TextView editText ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        editText = (TextView) findViewById(R.id.editText);

        if(saved_password == null){
            Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        Button button = (Button) findViewById(R.id.check);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                password = editText.getText().toString();

                if(password.equals( saved_password )){
                    Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(PasswordActivity.this,"Please enter correct password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
