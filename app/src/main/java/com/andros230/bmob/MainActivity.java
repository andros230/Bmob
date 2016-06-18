package com.andros230.bmob;

import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends Activity {
    BmobRealTimeData data = new BmobRealTimeData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "5b9353d27ae18dc5aafb5bf57b85a06b");
        delete();
    }

    //保存数据
    public void save() {
        Person person = new Person();
        person.setName("lucky");
        person.setAddress("北京");
        person.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.d("保存数据---", "onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("保存数据---", "onFailure");
            }
        });
    }

    //查询数据
    public void query() {
        BmobQuery<Person> query = new BmobQuery<>();
        //增加查询条件
        query.addWhereEqualTo("name", "lucky");
        query.findObjects(MainActivity.this, new FindListener<Person>() {
            @Override
            public void onSuccess(List<Person> list) {
                for (Person person1 : list) {
                    Log.d("查询数据---", person1.getName() + person1.getAddress());
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e("查询数据---", "onError");
            }
        });
    }

    public void update() {
        final Person person = new Person();
        person.setAddress("北京");
        person.update(this, "snNHbbbe", new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.d("更新成功---", "onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("更新失败---", "onFailure");
            }
        });
    }

    public void delete() {
        Person person = new Person();
        person.setObjectId("snNHbbbe");
        person.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                Log.d("删除成功---", "onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("删除失败---", "onFailure");
            }
        });
    }

    //实时数据
    public void realTimeData() {
        data.start(this, new ValueEventListener() {
            @Override
            public void onDataChange(JSONObject arg0) {
                // TODO Auto-generated method stub\
                JSONObject data = arg0.optJSONObject("data");
                Log.d("name---", data.optString("name"));
                Log.d("content---", data.optString("content"));
            }

            @Override
            public void onConnectCompleted() {
                // TODO Auto-generated method stub
                if (data.isConnected()) {
                    data.subTableUpdate("Chat");
                }
            }
        });
    }

}
