package com.xuhuawei.mybutterknife;


import com.xuhuawei.mybutterknife.annotation.ViewInjector;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@ViewInjector(R.id.tv_test)
	protected TextView tv_test;
	
	@ViewInjector(R.id.btn_test)
	protected Button btn_test;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		MyButterKnife.inject(this);
		tv_test.setText("我要成功！");
	}
}
