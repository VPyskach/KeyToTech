package com.test.keytotechtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.test.keytotechtest.view.comments.CommentsActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editFieldStart;
    private EditText editFieldEnd;

    private int startNumber = -1;
    private int endNumber = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void initViews(){
        editFieldStart = findViewById(R.id.editNumberStart);
        editFieldEnd = findViewById(R.id.editNumberEnd);
    }


    public void buttonContinueOnClick(View view) {

        if(isValidate()){
            goToNextActivity();
        }
    }

    private boolean isValidate(){
        if(!editFieldStart.getText().toString().equals("")){
            startNumber = Integer.valueOf(editFieldStart.getText().toString());
        }else{
            editFieldStart.setError(getResources().getString(R.string.edit_eror_start_index));
            return false;
        }

        if(!editFieldEnd.getText().toString().equals("")){
            endNumber = Integer.valueOf(editFieldEnd.getText().toString());
        }else{
            editFieldEnd.setError(getResources().getString(R.string.edit_error_end_index));
            return false;
        }

        if(startNumber < 0){
            editFieldStart.setError(getResources().getString(R.string.edit_error_must_be_positive));
            return false;
        }

        if(endNumber < 0){
            editFieldEnd.setError(getResources().getString(R.string.edit_error_must_be_positive));
            return false;
        }

        if(startNumber == endNumber){
            editFieldEnd.setError(getResources().getString(R.string.edit_error_must_be_different));
            return false;
        }
        return true;
    }

    private void goToNextActivity(){
        Intent intent = new Intent(MainActivity.this, CommentsActivity.class);
        intent.putExtra(Constans.START_INDEX, Math.min(startNumber, endNumber));
        intent.putExtra(Constans.END_INDEX, Math.max(startNumber, endNumber));
        startActivity(intent);
    }
}
