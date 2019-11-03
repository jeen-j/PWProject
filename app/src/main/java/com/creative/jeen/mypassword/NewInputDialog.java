package com.creative.jeen.mypassword;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class NewInputDialog extends Dialog implements View.OnClickListener {

    Context mContext;

    private EditText editText_SiteName, editText_URL, editText_UserID, editText_PassWord, editText_Note;
    private Button buttonDoneForDialog, buttonCancelForDialog;
    private Button buttonGeneratePwd, buttonShowPwd;


    private TextView passwordLength;

    private String mSiteName = "";
    private String mUrl = "";
    private String mUserId = "";
    private String mPassword = "";
    private String mNote = "";

    private boolean isNewData = false;


    public NewInputDialog(@NonNull Context context) {

        super(context);

        mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog);

        //add Dialog ----
        editText_SiteName = (EditText) findViewById(R.id.edittext_siteName);
        editText_URL = (EditText) findViewById(R.id.edittext_url);
        editText_UserID = (EditText) findViewById(R.id.edittext_userid);
        editText_PassWord = (EditText) findViewById(R.id.edittext_password);
        editText_Note = (EditText) findViewById(R.id.edittext_note);

        buttonDoneForDialog = (Button) findViewById(R.id.btn_done_for_dialog);
        buttonCancelForDialog = (Button) findViewById(R.id.btn_cancel_for_dialog);
        buttonGeneratePwd = (Button) findViewById(R.id.btn_generate_password);
        buttonShowPwd = (Button) findViewById(R.id.btn_show_password);


        buttonDoneForDialog.setOnClickListener(this);
        buttonCancelForDialog.setOnClickListener(this);


        buttonGeneratePwd.setOnClickListener(this);

        buttonShowPwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    editText_PassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    return true;
                } else {
                    editText_PassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    return true;
                }
            }
        });


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_generate_password:
                //password Dialog 띄우기
                LayoutInflater inflater = (LayoutInflater) (mContext).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout passwordGeneratorView = (LinearLayout) inflater.inflate(R.layout.password_genarate_dialog, null);

                Button buttonIncrease = (Button) passwordGeneratorView.findViewById(R.id.btn_increase_size);
                Button buttonDecrease = (Button) passwordGeneratorView.findViewById(R.id.btn_decrease_size);
                passwordLength = (TextView) passwordGeneratorView.findViewById(R.id.passwordLength);

                View.OnClickListener generateOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pwdLength = Integer.parseInt(passwordLength.getText().toString());

                        switch (view.getId()) {

                            case R.id.btn_increase_size:

                                if (pwdLength < 255) {

                                    pwdLength++;

                                    passwordLength.setText(Integer.toString(pwdLength));

                                } else {

                                    Toast.makeText(mContext, "Max length is 255", Toast.LENGTH_SHORT).show();

                                }
                                break;

                            case R.id.btn_decrease_size:

                                if (pwdLength > 4) {

                                    pwdLength--;

                                    passwordLength.setText(Integer.toString(pwdLength));

                                } else {

                                    Toast.makeText(mContext, "Min length is 4", Toast.LENGTH_SHORT).show();

                                }
                                break;

                        }

                    }
                };

                buttonIncrease.setOnClickListener(generateOnClickListener);
                buttonDecrease.setOnClickListener(generateOnClickListener);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:

                                boolean lower, upper, numbers, symbols;
                                String pwdLength;


                                lower = ((CheckBox) passwordGeneratorView.findViewById(R.id.lowercase)).isChecked();
                                upper = ((CheckBox) passwordGeneratorView.findViewById(R.id.uppercase)).isChecked();
                                numbers = ((CheckBox) passwordGeneratorView.findViewById(R.id.numbers)).isChecked();
                                symbols = ((CheckBox) passwordGeneratorView.findViewById(R.id.symbols)).isChecked();

                                pwdLength = passwordLength.getText().toString();

                                if (!(lower || upper || numbers || symbols))
                                    break;

                                editText_PassWord.setText(generatePassword(lower, upper, numbers, symbols, pwdLength));
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                new AlertDialog.Builder(mContext).setView(passwordGeneratorView).setPositiveButton
                        ("Generate", dialogClickListener).setNegativeButton("Cancel", dialogClickListener).show();

                break;


            case R.id.btn_done_for_dialog:

                mSiteName = editText_SiteName.getText().toString(); // 입력된 사이트 이름을 얻어옴
                mUrl = editText_URL.getText().toString(); // 입력된 사이트 이름을 얻어옴
                mUserId = editText_UserID.getText().toString(); // 입력된 사이트 이름을 얻어옴
                mPassword = editText_PassWord.getText().toString(); // 입력된 사이트 이름을 얻어옴
                mNote = editText_Note.getText().toString(); // 입력된 사이트 이름을 얻어옴

                //새로운 데이터가 들어왔다는 플래그 세팅
                isNewData = true;
                dismiss();// 이후 Activity에서 구현해준 Dismiss 리스너가 작동함

                break;


            case R.id.btn_cancel_for_dialog:

                //새로운 data가 없다는 플래그 세팅
                isNewData = false;
                cancel(); //이후 MainActivity에서 구현해준 Dismiss와 Cancel 리스너가 작동함

                break;

        }
    }


    //password 만드는 함수
    //random으로 들어갈때 체크박스에 네개 모두 체크했음에도 해당 정보가 안들어가는 문제가 있음
    //ex) 특수문자가 포함되지 않고 비밀번호가 생성될 떄가 있음 -> 수정하기를 추천
    public String generatePassword(boolean lower, boolean upper, boolean numbers, boolean symbols, String passwordLength) {

        String password = "";
        String chars = "";

        if (lower)
            chars = chars + "abcdefghijklmnopqrstuvwxyz";

        if (upper)
            chars = chars + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        if (numbers)
            chars = chars + "0123456789";

        if (symbols)
            chars = chars + "<>?\\;:/*-+.#$%^&£!";


        int length = Integer.parseInt(passwordLength);

        for (int i = 0; i < length; i++) {
            int rand = (int) (Math.random() * chars.length());
            password = password + chars.charAt(rand);
        }
        return password;

    }


    public String getmSiteName() {
        return mSiteName;
    }

    public String getURL() {
        return mUrl;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmNote() {
        return mNote;
    }

    public boolean isNewData() {
        return isNewData;
    }

}
