package cn.xiaokai.xg.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
	public TextView tv;
	public int w = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		File file = getFilesDir();
		file = new File(file, "sb.xml");
		Properties p = new Properties();
		if (!file.exists())
			try {
				p.put("FF", "DB");
				p.storeToXML(new FileOutputStream(file), null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		try {
			p.loadFromXML(new FileInputStream(file));
			if (p.get("FF").equals("SB")) {
				Intent intent = new Intent(MainActivity.this, Main.class);
				startActivity(intent);
				finish();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.textView1);
		tv.setTop(0);
		(new MsbT(this, 0)).start();
		(new MsbT(this, 1)).start();
	}

	public Handler handler = new Handler() {
		private int he = 1;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:
				Intent intent = new Intent(MainActivity.this, Main.class);
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
				break;
			case 1:
				tv.setTop(he + w);
				break;
			case 0:
				he++;
				tv.setTop(he);
				break;
			}
		}
	};

	@Override
	public void onBackPressed() {
	}
}
