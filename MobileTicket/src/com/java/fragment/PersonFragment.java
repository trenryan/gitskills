package com.java.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.java.activity.LoginActivity;
import com.java.activity.PersonEmailUpdateActivity;
import com.java.activity.PersonInfoUpdateActivity;
import com.java.activity.PersonPswUpdateActivity;
import com.java.activity.PersonTelUpdateActivity;
import com.java.ticket.R;

public class PersonFragment extends Fragment {

	private ListView listView;
	private SharedPreferences sp;
    private String filename="myfile";
	
	private Context mContext;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.activity_person_info, null);
//		sp = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
//		String name = sp.getString("username", "");
//		if (name.equals("")) {
//			Intent it = new Intent(mContext, LoginActivity.class);
//			startActivity(it);
//		} else {
//			
//		}
		initUI(view);
		return view;
	}

	private void initUI(View view) {
		listView=(ListView)view.findViewById(R.id.info_lv_id);
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		final String[] str={"个人资料修改","联系方式修改","密码修改","邮箱修改"};
		for(int i=0;i<str.length;i++){
			Map<String,String> map=new HashMap<String,String>();
			map.put("item",str[i]);
			data.add(map);
		}
		SimpleAdapter sa=new SimpleAdapter(mContext, 
				                           data, 
				                           R.layout.person_info_item, 
				                           new String[]{"item"}, 
				                           new int[]{R.id.person_info_item_id});
		listView.setAdapter(sa);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				switch (position) {
				case 0:
					Intent it1=new Intent(mContext,PersonInfoUpdateActivity.class);
					startActivity(it1);
					break;
				case 1:
					Intent it2=new Intent(mContext,PersonTelUpdateActivity.class);
					startActivity(it2);
					break;
				case 2:
					Intent it3=new Intent(mContext,PersonPswUpdateActivity.class);
					startActivity(it3);
					break;
				case 3:
					Intent it4=new Intent(mContext,PersonEmailUpdateActivity.class);
					startActivity(it4);
					break;
				default:
					break;
				}
		        
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
