package cn.xiaokai.xg.search;

import java.util.ArrayList;
import java.util.HashMap;

import android.text.Editable;
import android.text.TextWatcher;

public class Edc implements TextWatcher {
	private String string = "";
	private Main main;

	public Edc(Main main) {
		this.main = main;
	}

	@Override
	public void afterTextChanged(Editable arg0) {

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if (string.contentEquals(arg0) && !arg0.toString().isEmpty())
			return;
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
		string = arg0.toString();
		main.Adapter.setAll(s);
		main.Adapter.notifyDataSetChanged();
	}

}
