package cn.xiaokai.xg.search;

public class MsbT extends Thread {
	private MainActivity activity;
	private int key;

	public MsbT(MainActivity activity, int key) {
		this.activity = activity;
		this.key = key;
	}

	@Override
	public void run() {
		switch (key) {
		case 0:
			try {
				sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			for (int i = 0; i < 1300; i++)
				activity.handler.sendEmptyMessage(0);
			for (int i = 0; i > -200; i--) {
				activity.w = i;
				activity.handler.sendEmptyMessage(1);
			}
			for (int i = -200; i < 0; i++) {
				activity.w = i;
				activity.handler.sendEmptyMessage(1);
				try {
					sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i > -100; i--) {
				activity.w = i;
				activity.handler.sendEmptyMessage(1);
				try {
					sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int i = -100; i < 0; i++) {
				activity.w = i;
				activity.handler.sendEmptyMessage(1);
				try {
					sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i > -50; i--) {
				activity.w = i;
				activity.handler.sendEmptyMessage(1);
			}
			for (int i = -50; i < 0; i++) {
				activity.w = i;
				activity.handler.sendEmptyMessage(1);
			}
			break;
		case 1:
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			activity.handler.sendEmptyMessage(2);
			break;
		}
		super.run();
	}
}
