package syj.upc.edu.handleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final int UPDATE = 0x1;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        begin();//开启倒计时并跳转页面的方法
    }
    //消息处理者,创建一个Handler的子类对象,目的是重写Handler的处理消息的方法(handleMessage())
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE:
                    tv.setText(String.valueOf(msg.arg1));
                    break;
            }
        }
    };

    public void begin(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=5;i>0;i--){
                    Message msg = new Message();
                    msg.what = UPDATE;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);//休眠1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //打印log
                    Log.i("tag",MainActivity.this+"-"+ i);
                }
                //计时结束后跳转到其他界面
                startActivity(new Intent(MainActivity.this,Main3Activity.class));
                //添加finish方法在任务栈中销毁倒计时界面，使新开界面在回退时直接退出而不是再次返回该界面
                finish();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //log打印用于测试activity销毁
        Log.i("tag","destory");
    }
}