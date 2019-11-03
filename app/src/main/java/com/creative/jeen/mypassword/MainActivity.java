package com.creative.jeen.mypassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Intent passwordListActivity = new Intent(getApplicationContext(), PasswordListActivity.class);
//        startActivity(passwordListActivity);
//        finish();

        Button btnForShowList = (Button) findViewById(R.id.btn_for_show_list);

        btnForShowList.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent passwordListActivity = new Intent(getApplicationContext(), PasswordListActivity.class);
                startActivity(passwordListActivity);
                finish();
            }
        });


    }
}
