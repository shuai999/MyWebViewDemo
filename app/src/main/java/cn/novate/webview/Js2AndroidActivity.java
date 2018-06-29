package cn.novate.webview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Email: 2185134304@qq.com
 * Created by Novate 2018/6/29 11:20
 * Version 1.0
 * Params:
 * Description:    Js 调用 Android 中的方法，并且给 js 有返回值
 *
 *                  有时候我们需要监听html中控件的一些事件，比如点击 html中的 某个按钮，跳转到别的 Activity
 *
 *
 *                  1>：js调用android方法使用场景：
 *                      js调用android方法可以复制信息、获取点击事件
 *
 *                  2>：js 调用 android 有3 种方法：
 *                      a：通过 webview的 addJavascriptInterface() ；
 *                      b：通过 WebViewClient 的 shouldOverrideUrlLoading ()方法 回调拦截 url ；
 *                      c：通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（） 消息
 *
 *
*/

public class Js2AndroidActivity extends AppCompatActivity {

    private WebView web_view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js2android);

        initView() ;
    }


    private void initView() {
        web_view = (WebView) findViewById(R.id.web_view);
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


        // JsInterface是自己创建的类；
        // android是给JsInterface这个类随便取一个名字，
        // 但是必须和html代码中 javascript:android.onSum(result)、javascript:android.showToast(money); 的 android 一样，是同一个名字
        web_view.addJavascriptInterface(new JsInterface() , "android");
    }


    public class JsInterface{
        /**
         *  点击 WebView中的（即就是 Html中的） 点击事件
         *  <input type="button" value="结算" onclick="showToast('12')">
         */
        @JavascriptInterface
        public void showToast(String toast){
            Toast.makeText(Js2AndroidActivity.this , "点击webview的html代码中的按钮，给html返回的结果是：￥" + toast , Toast.LENGTH_SHORT).show();
        }
    }

}
