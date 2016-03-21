package com.example.barry215.microwall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by barry215 on 2016/3/19.
 */
public class LoginFragment extends DialogFragment {

    @Bind(R.id.usernameWrapper) TextInputLayout usernameWrapper;
    @Bind(R.id.passwordWrapper) TextInputLayout passwordWrapper;
    @Bind(R.id.tv_login) TextView tv_login;
    @Bind(R.id.ed_username) EditText ed_username;
    @Bind(R.id.ed_password) EditText ed_password;

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static final String NUMBER_PATTERN = "^[0-9]*$";
    private Pattern pattern_1 = Pattern.compile(EMAIL_PATTERN);
    private Pattern pattern_2 = Pattern.compile(NUMBER_PATTERN);
    private Matcher matcher1;
    private Matcher matcher2;
    private boolean flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.activity_login, container);
        ButterKnife.bind(this, view);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                String username = ed_username.getText().toString();
                String password = ed_password.getText().toString();
                if ((!validateEmail(username))&&(!validatePhone(username))) {
                    usernameWrapper.setErrorEnabled(true);
                    usernameWrapper.setError("好像写错了哟~");
                    flag = false;
                } else {
                    usernameWrapper.setErrorEnabled(false);
                    flag = true;
                }
                if (!validatePassword(password)) {
                    passwordWrapper.setErrorEnabled(true);
                    passwordWrapper.setError("密码不能小于6位哟~");
                    flag = false;
                } else {
                    passwordWrapper.setErrorEnabled(false);
                    flag = true;
                }
                if (flag) {
                    ed_username.setFocusable(false);
                    ed_password.setFocusable(false);
                    doLogin();
                }else {
                    Toast.makeText(getActivity(),"全部的判断是错的", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    //隐藏键盘
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean validateEmail(String email) {
        matcher1 = pattern_1.matcher(email);
        return matcher1.matches();
    }

    public boolean validatePhone(String num){
        matcher2 = pattern_2.matcher(num);
        return matcher2.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 6;
    }

    public void doLogin(){
        Intent intent = new Intent(getActivity(),MainActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
