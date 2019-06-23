package cn.xiaokai.xg.search;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private Main main;
	private ArrayList<HashMap<String, Object>> All;
	private AssetManager ac;

	public AssetManager getAc() {
		return ac;
	}

	public MyAdapter(Main main) {
		this.main = main;
		ac = main.getAssets();
	}

	public void setAll(ArrayList<HashMap<String, Object>> all) {
		this.All = all;
	}

	@Override
	public int getCount() {
		return All.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		SBData data;
		if (arg1 == null) {
			arg1 = View.inflate(main, R.layout.m_l_i, null);
			data = new SBData();
			data.imageView = (ImageView) arg1.findViewById(R.id.imageView1);
			data.textView1 = (TextView) arg1.findViewById(R.id.textView1);
			data.textView2 = (TextView) arg1.findViewById(R.id.textView2);
			arg1.setTag(data);
		}
		data = (SBData) arg1.getTag();
		HashMap<String, Object> map = All.get(arg0);
		data.map = map;
		data.textView1.setText(map.get("Name") + "[" + map.get("ID") + ":" + map.get("Damage") + "]");
		data.textView2.setText(map.get("Path").toString());
		InputStream in;
		try {
			in = ac.open(map.get("Path").toString());
			Bitmap bmp = BitmapFactory.decodeStream(in);
			data.imageView.setImageBitmap(bmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arg1;
	}

	public ArrayList<HashMap<String, Object>> getAll() {
		return All;
	}
}
