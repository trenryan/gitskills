package com.java.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.adapter.SearchTrainListAdapter;
import com.java.bean.Train;
import com.java.ticket.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SearchTrainListActivity extends Activity {
    private ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_train_list);
		
		final List<Train> ls=(List<Train>)getIntent().getExtras().getSerializable("trainlist");
		lv=(ListView)findViewById(R.id.trains_lv_id);
//		List<Map<String,String>> data=new ArrayList<Map<String,String>>(); 
//		
//		for(Train train :ls){
//			Map<String, String> map=new HashMap<String, String>();
//			map.put("train_num", train.getNum());
//			map.put("from_city", train.getFrom_city());
//			map.put("tocity", train.getTo_city());	
//			data.add(map);
//		}
//		
//		SimpleAdapter sa=new SimpleAdapter(this,
//				                           data,
//				                           R.layout.train_list_item,
//				                           new String[]{"train_num","from_city","tocity"},
//				                           new int[]{R.id.train_num_id,R.id.from_city_id,R.id.to_city_id});
		SearchTrainListAdapter sa=new SearchTrainListAdapter(this,ls);
		lv.setAdapter(sa);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it=new Intent(SearchTrainListActivity.this, BuyTicketActivity.class);
				it.putExtra("train", ls.get(position));
				startActivity(it);
				
			}
		});
	}
}
