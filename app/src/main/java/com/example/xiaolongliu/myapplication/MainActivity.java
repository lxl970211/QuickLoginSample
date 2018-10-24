package com.example.xiaolongliu.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.energysh.drawshow.R;
import com.energysh.quicklogin.bean.ResultBean;
import com.energysh.quicklogin.interfaces.Result;
import com.energysh.quicklogin.login.LoginManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginManager.getInstance().initSdk(this);

        final TextView textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().login(MainActivity.this, new Result<ResultBean>() {
                    @Override
                    public void onResult(ResultBean resultBean) {
                        textView.setText(resultBean.getCode() + "  "+resultBean.getMsg() + "  "+ resultBean.getPhoneNumber());
                    }
                });
            }
        });
    }
}
