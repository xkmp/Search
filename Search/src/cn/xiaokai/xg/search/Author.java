package cn.xiaokai.xg.search;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;

public class Author extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.author);
	}

	public void onClick(View view) {
		Builder vBuilder = new Builder(this);
		String nameString = Tool.doPost("http://tool.epicfx.cn/", "s=qs&qq=2508543202");
		nameString = nameString == null || nameString.isEmpty() ? "帅逼凯" : nameString;
		vBuilder.setTitle(nameString);
		vBuilder.setMessage("感谢使用！\n作者QQ：2508543202\n作者：" + nameString);
		vBuilder.setNegativeButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		vBuilder.setCancelable(false);
		vBuilder.show();
	}
}
