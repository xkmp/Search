package cn.xiaokai.xg.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class Main extends Activity implements OnItemClickListener, OnItemLongClickListener, OnLongClickListener {
	public ArrayList<HashMap<String, Object>> AllItems, Ab;
	public ArrayList<HashMap<String, Object>> NotAllItems;
	public MyAdapter Adapter;
	public ProgressDialog pd;
	public ListView lv;
	public EditText ed;
	public ImageButton bt, b2;
	public MT mt1;
	public Md md1;
	public static Main main;
	public boolean bool = false;

	@Override
	public boolean onLongClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.imageButton3:
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
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			p.put("FF", p.get("FF").equals("SB") ? "DB" : "SB");
			try {
				p.storeToXML(new FileOutputStream(file), null);
				if (p.get("FF").equals("SB"))
					Toast.makeText(this, "已设置快速启动", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(this, "已取消快速启动", Toast.LENGTH_SHORT).show();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		case R.id.editText1:
			ed.setText("");
			return true;
		}
		return false;
	}

	public void onXiaokai(View view) {
		Intent intent = new Intent(this, Author.class);
		startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		HashMap<String, Object> map = Ab.get(arg2);
		ClipData mClipData = ClipData.newPlainText("Label", map.get("Path").toString());
		cm.setPrimaryClip(mClipData);
		Toast.makeText(this, "已将该项贴图路径复制到粘贴板", Toast.LENGTH_LONG).show();
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg1 == null)
			return;
		InputStream in;
		View view = View.inflate(this, R.layout.builder, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
		TextView textView = (TextView) view.findViewById(R.id.textView1);
		final HashMap<String, Object> map = Ab.get(arg2);
		final String s = "物品名称：" + map.get("Name") + "\n物品ID：" + map.get("ID") + ":" + map.get("Damage") + "\n物品贴图："
				+ map.get("Path");
		textView.setText(s);
		try {
			in = Adapter.getAc().open(map.get("Path").toString());
			Bitmap bmp = BitmapFactory.decodeStream(in);
			imageView.setImageBitmap(bmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Builder builder = new Builder(this);
		builder.setTitle(map.get("Name").toString());
		builder.setView(view);
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		builder.setNeutralButton("复制贴图路径", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData mClipData = ClipData.newPlainText("Label", map.get("Path").toString());
				cm.setPrimaryClip(mClipData);
				Toast.makeText(Main.this, "已将该项贴图路径复制到粘贴板", Toast.LENGTH_LONG).show();
			}
		});
		builder.setPositiveButton("复制全部数据", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData mClipData = ClipData.newPlainText("Label", s);
				cm.setPrimaryClip(mClipData);
				Toast.makeText(Main.this, "已将该项数据复制到粘贴板", Toast.LENGTH_LONG).show();
			}
		});
		builder.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		main = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		load();
		Adapter = new MyAdapter(this);
		AllItems = ItemIDSunName.getAll();
		isSc();
	}

	protected void load() {
		md1 = new Md();
		lv = (ListView) findViewById(R.id.listView1);
		ed = (EditText) findViewById(R.id.editText1);
		bt = (ImageButton) findViewById(R.id.imageButton1);
		ed.addTextChangedListener(new Edc(this));
		lv.setOnItemClickListener(this);
		lv.setOnItemLongClickListener(this);
		bt.setOnLongClickListener(this);
		b2 = (ImageButton) findViewById(R.id.imageButton3);
		b2.setOnLongClickListener(this);
	}

	public void isSc() {
		pd = new ProgressDialog(this);
		pd.setTitle("提示");
		pd.setMessage("正在检查数据.....");
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setProgress(0);
		pd.setCancelable(false);
		pd.setMax(Path.path.length);
		pd.show();
		mt1 = new MT(this, 0);
		mt1.start();
	}

	public void onToClick(View view) {
		Adapter.setAll(bool ? AllItems : NotAllItems);
		Ab = bool ? AllItems : NotAllItems;
		bool = !bool;
		Adapter.notifyDataSetChanged();
		onSeekClick(null);
	}

	public void onSeekClick(View view) {
		String arg0 = ed.getText().toString();
		ArrayList<HashMap<String, Object>> All = main.Ab, s = new ArrayList<HashMap<String, Object>>();
		if (arg0.toString().isEmpty()) {
			s = main.Ab;
		} else {
			All = main.Ab;
			s = new ArrayList<HashMap<String, Object>>();
			for (HashMap<String, Object> map : All)
				if (String.valueOf(map.get("Name")).contains(arg0.toString())
						|| String.valueOf(map.get("ID")).contains(arg0)
						|| String.valueOf(map.get("Damage")).contains(arg0)
						|| String.valueOf(map.get("Path")).contains(arg0))
					s.add(map);
		}
		main.Adapter.setAll(s);
		main.Adapter.notifyDataSetChanged();
	}

	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				pd.setMessage(md1.msg);
				break;
			case 1:
				pd.setProgress(md1.i);
				break;
			case 2:
				pd.setMax(md1.i);
				break;
			case 3:
				pd.cancel();
				Ab = AllItems;
				Adapter.setAll(AllItems);
				lv.setAdapter(Adapter);
				break;
			case -1:
				finish();
				break;
			}
		}
	};
}
