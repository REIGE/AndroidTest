package com.example.tx1;

import java.util.ArrayList;

import com.example.tx1.bean.InfoBean;
import com.example.tx1.dao.InfoDao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener, OnItemClickListener {

	public static final int LOGIN = 0;
	public static final int REGISTER = 1;

	Context mContext;
	private EditText et_id;
	private EditText et_password;
	private EditText et_rid;
	private EditText et_rpassword;
	private EditText et_rpassword2;
	private EditText et_remail;
	private EditText et_rphonenum;
	private EditText et_rnum;
	private TabHost tabhost;
	private ListView lv_showid;

	private ArrayList<InfoBean> arrayList = new ArrayList<InfoBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mContext = this;

		tabhost = (TabHost) findViewById(R.id.tabhost);
		tabhost.setup();

		LayoutInflater i = LayoutInflater.from(this);
		i.inflate(R.layout.tab_login, tabhost.getTabContentView());
		i.inflate(R.layout.tab_register, tabhost.getTabContentView());// 动态载入XML，而不需要Activity

		tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("登录").setContent(R.id.tab_login));
		tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("注册").setContent(R.id.tab_register));
		// 设置其实标签
		tabhost.setCurrentTab(0);

		findViewById(R.id.bt_cancle).setOnClickListener(this);
		findViewById(R.id.bt_confirm).setOnClickListener(this);
		findViewById(R.id.bt_rcancle).setOnClickListener(this);
		findViewById(R.id.bt_rconfirm).setOnClickListener(this);
		findViewById(R.id.btn_show_id).setOnClickListener(this);

		et_id = (EditText) findViewById(R.id.et_id);
		et_password = (EditText) findViewById(R.id.et_password);
		et_rid = (EditText) findViewById(R.id.et_rid);
		et_rpassword = (EditText) findViewById(R.id.et_rpassword);
		et_rpassword2 = (EditText) findViewById(R.id.et_rpassword2);
		et_remail = (EditText) findViewById(R.id.et_remail);
		et_rphonenum = (EditText) findViewById(R.id.et_rphonenum);
		et_rnum = (EditText) findViewById(R.id.et_rnum);

		lv_showid = (ListView) findViewById(R.id.lv_showid);

		MylistAdapter mylistAdapter = new MylistAdapter();
		lv_showid.setAdapter(mylistAdapter);
		lv_showid.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_confirm:
			login();
			break;
		case R.id.bt_cancle:
			// TODO 退出应用
			break;
		case R.id.bt_rconfirm:
			register();

			break;
		case R.id.bt_rcancle:
			// 跳转回login
			tabhost.setCurrentTab(LOGIN);
			break;
		case R.id.btn_show_id:
			showId();
		default:
			break;
		}
	}

	private void login() {
		// 检查空
		String id = et_id.getText().toString().trim();
		String password = et_password.getText().toString().trim();
		if (TextUtils.isEmpty(id) || TextUtils.isEmpty(password)) {
			Toast.makeText(mContext, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		// 检查用户名密码是否正确
		InfoDao infoDao = new InfoDao(mContext);
		ArrayList<InfoBean> list = infoDao.query(id);
		// 因为id是主键 所以list中只有一个bean对象
		InfoBean bean = list.get(0);
		if (id.equals(bean.id) && password.equals(bean.password)) {
			Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
			EnterHome();
		} else {
			Toast.makeText(mContext, "用户名或密码错误", Toast.LENGTH_SHORT).show();
		}

	}

	private void EnterHome() {
		
		Intent intent = new Intent();
		intent.setClass(mContext, activity_home.class);
		startActivity(intent);
		
	}

	private void register() {
		InfoDao infoDao = new InfoDao(mContext);
		InfoBean bean = new InfoBean();
		bean.id = et_rid.getText().toString().trim();
		bean.password = et_rpassword.getText().toString().trim();
		bean.email = et_remail.getText().toString().trim();
		bean.phone = et_rphonenum.getText().toString().trim();
		boolean result = infoDao.add(bean);
		if (result) {
			Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
			tabhost.setCurrentTab(LOGIN);
		} else {
			Toast.makeText(mContext, "注册失败", Toast.LENGTH_SHORT).show();
		}

	}

	private void showId() {
		arrayList.clear();
		MylistAdapter mylistAdapter = new MylistAdapter();
		lv_showid.setAdapter(mylistAdapter);
		int visibility = lv_showid.getVisibility();
		if (visibility == View.VISIBLE) {
			lv_showid.setVisibility(View.INVISIBLE);
		} else {

			InfoDao infoDao = new InfoDao(mContext);

			ArrayList<InfoBean> list = infoDao.query(null);
			Toast.makeText(mContext, list.size() + "", Toast.LENGTH_SHORT).show();
			for (int i = 0; i < list.size(); i++) {
				InfoBean bean = list.get(i);
				arrayList.add(bean);
			}

			lv_showid.setVisibility(View.VISIBLE);
		}

	}

	class MylistAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return arrayList.size();
		}

		@Override
		public Object getItem(int position) {
			return arrayList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// 复用convertView
			View view = null;
			if (convertView != null) {
				view = convertView;
			} else {
				view = View.inflate(mContext, R.layout.item_id_layout, null);
			}

			ImageView item_id_layout = (ImageView) view.findViewById(R.id.iv_item_logo);
			TextView tv_item_id = (TextView) view.findViewById(R.id.tv_item_id);

			item_id_layout.setImageResource(R.drawable.ic_launcher);
			tv_item_id.setText(arrayList.get(position).id);

			parent.setBackgroundColor(Color.WHITE);
			// TextView textView = new TextView(mContext);
			// textView.setText(id[position]);

			return view;
		}

	}

	// parent代表listview view点击的条目上的view对象 position条目的位置 id条目的id
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long _id) {
		et_id.setText(arrayList.get(position).id);
		lv_showid.setVisibility(View.INVISIBLE);
	}

}
