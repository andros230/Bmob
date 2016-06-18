package com.andros230.bmob;

import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    BmobRealTimeData data = new BmobRealTimeData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "5b9353d27ae18dc5aafb5bf57b85a06b");
        //initAppVersion方法适合开发者调试自动更新功能时使用，一旦AppVersion表在后台创建成功，建议屏蔽或删除此方法，否则会生成多行记录。
       // BmobUpdateAgent.initAppVersion(this);
        BmobUpdateAgent.update(this);

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


    //批量保存数据
    public void insertBatch() {
        List<BmobObject> persons = new ArrayList<>();
        Person person1 = new Person();
        person1.setName("aa");
        person1.setAddress("上海");
        Person person2 = new Person();
        person2.setName("bb");
        person2.setAddress("北京");
        persons.add(person1);
        persons.add(person2);

        new BmobObject().insertBatch(this, persons, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.d("批量保存数据成功---", "onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("批量保存数据失败---", "onFailure");
            }
        });
    }


    //批量修改数据
    public void updateBatch() {
        List<BmobObject> persons = new ArrayList<>();
        Person person1 = new Person();
        person1.setObjectId("49377b7238");
        person1.setAddress("广州");
        Person person2 = new Person();
        person2.setObjectId("72d5c87794");
        person2.setAddress("成都");
        persons.add(person1);
        persons.add(person2);

        new BmobObject().updateBatch(this, persons, new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.d("批量修改数据成功---", "onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("批量修改数据失败", "onFailure");
            }
        });
    }

    //批量删除数据
    public void deleteBatch() {
        List<BmobObject> persons = new ArrayList<>();
        Person person1 = new Person();
        person1.setObjectId("49377b7238");
        Person person2 = new Person();
        person2.setObjectId("72d5c87794");
        persons.add(person1);
        persons.add(person2);

        new BmobObject().deleteBatch(this, persons, new DeleteListener() {
            @Override
            public void onSuccess() {
                Log.d("批量删除数据成功---", "onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("批量删除数据失败---", "onFailure");
            }
        });
    }

}
