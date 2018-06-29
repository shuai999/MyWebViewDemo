package cn.novate.webview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Email: 2185134304@qq.com
 * Created by Novate 2018/6/29 11:11
 * Version 1.0
 * Params:
 * Description:    Android 调用 js代码中的方法：有3种方式：
 *
 *                  方式一：4.4以下：通过 webview 的 loadUrl("javascript:sum(6,6)") ；
 *                  方式二：4.4以上：通过 webview 的 evaluateJavascript("sumn(6,11)")  ；
 *                  方式三：推荐使用混合方式：判断版本4.4以下、4.4以上；
 *
*/

public class Android2JsActivity extends AppCompatActivity {

    private WebView web_view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android2js);

        initView() ;
    }


    private void initView() {
        web_view = (WebView) findViewById(R.id.web_view);
        // 隐藏 webview
        /*ViewCompat.setAlpha(web_view,0);//修改透明度为最低值
        web_view.setVisibility(View.INVISIBLE);*/
        // 获取WebView的设置
        WebSettings webSettings = web_view.getSettings();
        //设置编码
        webSettings.setDefaultTextEncodingName("utf-8");
        //设置背景颜色 透明
        web_view.setBackgroundColor(Color.argb(0, 0, 0, 0));
        // 将JavaScript设置为可用，这一句话是必须的，不然所做一切都是徒劳的
        webSettings.setJavaScriptEnabled(true);
        // 通过webview加载html页面
        web_view.loadUrl("file:///android_asset/ll.html");



        /************** android调用js方法一：通过WebView的loadUrl（） start *************/

        // new JsInterface()：是自己创建的类；
        // android：是给JsInterface这个类随便取一个别名 ，
        // 但是必须和html代码中 javascript:android.onSum(result)、javascript:android.showToast(money); 的 android 一样，是同一个名字
        // onSum()：是要调用的方法名；
        // 所以：android要与js 商议好 别名和方法名
        web_view.addJavascriptInterface(new JsInterface() , "android");
    }

    public class JsInterface{
        // Android 调用 Js 方法1 中的返回值
        @JavascriptInterface
        public void onSum(int result){
            Toast.makeText(Android2JsActivity.this , "Android调用Js方法有返回值，返回结果是 -> " + result , Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 方法一：
     */
    public void btn_1(View view){
        web_view.loadUrl("javascript:sum(6,6)");
    }


    /************** android调用js方法一：通过WebView的loadUrl（） end *************/




    /************** android调用js方法二：通过WebView的evaluateJavascript（） start *************/
    /**
     * 方法二：
     * Android 调用 Js中的方法，并且有返回值
     *    Android4.4 之后 直接调用evaluateJavascript方法即可
     */
    public void btn_2(View view){
        web_view.evaluateJavascript("sumn(6,11)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Toast.makeText(Android2JsActivity.this, "返回值" + value, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /************** android调用js方法二：通过WebView的evaluateJavascript（） end *************/




    /************** android调用js方法三：推荐使用混合方式，判断4.4以上、4.4以下 start *************/

    public void btn_3(View view){
        // Android版本变量
        final int version = Build.VERSION.SDK_INT;
        // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
        if (version < 18) {
            web_view.loadUrl("javascript:callJS()");
        } else {
            web_view.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                }
            });
        }
    }
    /************** android调用js方法三：推荐使用混合方式，判断4.4以上、4.4以下 end *************/

}
