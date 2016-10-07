package com.java.activity;

import com.java.bean.Train;
import com.java.task.BuyTicketTask;
import com.java.ticket.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BuyTicketActivity extends Activity {
    private TextView from_mycity_txtView;
    private TextView to_mycity_txtView;
    private TextView trainNum_txtView;
    private Button submitBtn;
    private SharedPreferences sp;
    private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_ticket);
		
		final Train train = (Train) getIntent().getExtras().getSerializable("train");
		
		sp=getSharedPreferences("myfile", Context.MODE_PRIVATE);
		final String username=sp.getString("username", "");
		final String name=sp.getString("name", "");
		final String idcard=sp.getString("idcard", "");
		
		final String trainId=String.valueOf(train.getId());
		from_mycity_txtView=(TextView) findViewById(R.id.from_mycity_txt_id);
		to_mycity_txtView=(TextView) findViewById(R.id.to_mycity_txt_id);
		trainNum_txtView=(TextView) findViewById(R.id.train_num_txt_id);
		from_mycity_txtView.setText(train.getFrom_mycity());
		to_mycity_txtView.setText(train.getTo_mycity());
		trainNum_txtView.setText(train.getNum());
		
		submitBtn=(Button) findViewById(R.id.submit_order_btn_id);
		submitBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				BuyTicketTask task=new BuyTicketTask(BuyTicketActivity.this);
				task.execute(username,name,idcard,trainId,train.getNum(),train.getFrom_mycity(),train.getTo_mycity());
			}
		});
	}
}
