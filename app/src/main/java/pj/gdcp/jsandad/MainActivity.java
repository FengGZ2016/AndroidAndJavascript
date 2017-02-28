package pj.gdcp.jsandad;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btn_one;
    private Button btn_two;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_one = (Button) findViewById(R.id.button);
        btn_two = (Button) findViewById(R.id.button2);
        mWebView = (WebView) findViewById(R.id.webview);
        //启用JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        mWebView.loadUrl("file:///android_asset/web.html");

        //实现JS调用Android方法，需要在Java代码中添加下面这句，webview绑定javascriptInterface
        //第二个参数"android"是别名，JS脚本通过这个别名来调用java的方法，这个别名跟HTML代码中也是对应的。
        mWebView.addJavascriptInterface(MainActivity.this,"android");

        /**
         *调用无参JS
         * */
        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:javacalljs()");
            }
        });

        /**
         * 调用有参JS
         * */
        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        // 传递参数调用JS的方法
                mWebView.loadUrl("javascript:javacalljswith(" + "'http://blog.csdn.net/Leejizhou'" + ")");

            }
        });
    }

    @JavascriptInterface
    public void startFunction(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"JS调用了startFunction()",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //新建一个对话框
                new AlertDialog.Builder(MainActivity.this).setMessage(text).show();

            }
        });

    }
}
