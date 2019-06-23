package cn.xiaokai.xg.search;

import java.util.ArrayList;
import java.util.HashMap;

public class MT extends Thread {
	private Main main;
	private int Key;

	public MT(Main main, int Key) {
		this.main = main;
		this.Key = Key;
	}

	@Override
	public void run() {
		switch (Key) {
		case 0:
			try {
				sleep(100);
				main.md1.msg = "正在统计数据...";
				main.handler.sendEmptyMessage(0);
				HashMap<String, Object> map;
				int i = 1;
				HashMap<String, String> map2 = ItemIDSunName.getAllPath();
				main.NotAllItems = new ArrayList<HashMap<String, Object>>();
				for (String filePath : Path.path) {
					map = new HashMap<String, Object>();
					String name = map2.get(filePath);
					map.put("Name", name == null ? "Unknown" : name);
					map.put("ID",
							String.valueOf(((name != null && !name.isEmpty()) ? ItemIDSunName.getNameByID(name) : 0)));
					map.put("Damage", String
							.valueOf(((name != null && !name.isEmpty()) ? ItemIDSunName.getNameByDamage(name) : 0)));
					map.put("Path", filePath);
					main.NotAllItems.add(map);
					main.md1.msg = "正在添加" + filePath;
					main.handler.sendEmptyMessage(0);
					main.md1.i = i;
					main.handler.sendEmptyMessage(1);
					i++;
					if (Tool.getRand(1, 6) == 2)
						sleep(1);
				}
				main.md1.msg = "计算完毕。准备装入....";
				main.handler.sendEmptyMessage(0);
				sleep(500);
				main.handler.sendEmptyMessage(3);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
				main.handler.sendEmptyMessage(-1);
			}
			break;
		}
		super.run();
	}
}
