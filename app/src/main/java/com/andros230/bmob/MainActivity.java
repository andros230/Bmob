package com.andros230.bmob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        Bmob.initialize(this, "5b9353d27ae18dc5aafb5bf57b85a06b");
        //增加数据
        final Person person = new Person();
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

        //查询数据
        BmobQuery<Person> query = new BmobQuery<>();
        //增加查询条件
        query.addWhereEqualTo("name","lucky2");
        query.findObjects(MainActivity.this, new FindListener<Person>() {
            @Override
            public void onSuccess(List<Person> list) {
                for (Person person1 : list) {
                    Log.d("查询---", person1.getName() + person1.getAddress());
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e("r---", "查询数据onError");
            }
        });


    }
}
