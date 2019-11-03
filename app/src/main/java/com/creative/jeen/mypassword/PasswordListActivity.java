package com.creative.jeen.mypassword;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class PasswordListActivity extends AppCompatActivity implements Button.OnClickListener{

    Context mContext;
    ArrayList<ItemData> PasswordDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mContext = this;
        PasswordDataList = new ArrayList<ItemData>();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.fab :
                final NewInputDialog newInputDialog = new NewInputDialog(this);

                newInputDialog.setTitle("Add Information");

                newInputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                        if ( newInputDialog.isNewData() ) {


                            addPassWordData(newInputDialog.getmSiteName(), newInputDialog.getURL(), newInputDialog.getUserId()
                                    , newInputDialog.getmPassword(), newInputDialog.getmNote());


                        }
                    }
                });

                newInputDialog.show();

                break;

        }
    }

    //입력받은 정보를 ItemData타입으로 arraylist에 추가하는 함수
    public boolean addPassWordData(String sitename, String url, String id, String pw, String note){

        if (sitename != null) {

            ItemData newData = new ItemData(sitename, url, id, pw, note);
            PasswordDataList.add(newData);

            return true;

        } else {

            return false;
        }
    }

}
