package com.andros230.bmob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "5b9353d27ae18dc5aafb5bf57b85a06b");
        Person person = new Person();
        person.setName("lucky");
        person.setAddress("北京");
        person.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.d("---", "onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("---", "onFailure");
            }
        });
    }
}
