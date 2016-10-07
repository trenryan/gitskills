package com.java.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.task.SearchTrainTask;
import com.java.ticket.R;
import com.java.utils.MyDateTime;
import com.liucanwen.citylist.CityListActivity;

public class ReserverFragment extends Fragment implements View.OnClickListener {
	private ProgressDialog pd;
	private ImageView changeView;
	private TextView fromTxtView, toTxtView, searchTxtView, OrderTxtView,
			personTxtView;
	private TextView fromDateTxt, fromTimeTxt;
	private String time;
	private Button searchBtn;

	private Context mContext;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
	}

	// 处理fragment布局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.activity_main, null);
		initUI(view);
		return view;
	}

	private void initUI(View view) {
		fromTxtView = (TextView) view.findViewById(R.id.from_id);
		toTxtView = (TextView) view.findViewById(R.id.to_id);
		fromDateTxt = (TextView) view.findViewById(R.id.from_date_id);
		fromTimeTxt = (TextView) view.findViewById(R.id.from_time_id);

		searchBtn = (Button) view.findViewById(R.id.search_btn_id);
		changeView = (ImageView) view.findViewById(R.id.change_id);

		fromTxtView.setOnClickListener(this);
		toTxtView.setOnClickListener(this);
		fromDateTxt.setOnClickListener(this);
		fromTimeTxt.setOnClickListener(this);
		changeView.setOnClickListener(this);
		searchBtn.setOnClickListener(this);
	}

	// 依附activity创建完成
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.from_id:
			Intent intent1 = new Intent(mContext, CityListActivity.class);
			intent1.putExtra("fromcity", "fromcity");
			startActivityForResult(intent1, 1);
			break;

		case R.id.to_id:
			Intent intent2 = new Intent(mContext, CityListActivity.class);
			intent2.putExtra("tocity", "tocity");
			startActivityForResult(intent2, 2);
			break;

		case R.id.from_date_id:
			DatePickerDialog dp = new DatePickerDialog(mContext,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							int month = monthOfYear + 1;
							String s = null;
							if (month < 10 && dayOfMonth < 10)
								s = year + "-0" + month + "-0" + dayOfMonth;
							else if (month < 10 && dayOfMonth > 10)
								s = year + "-0" + month + "-" + dayOfMonth;
							else if (month > 10 && dayOfMonth < 10)
								s = year + "-" + month + "-0" + dayOfMonth;
							else
								s = year + "-" + month + "-" + dayOfMonth;
							fromDateTxt.setText(s);
						}
					}, MyDateTime.getYear(), MyDateTime.getMonth(),
					MyDateTime.getDay());
			dp.show();
			break;

		case R.id.from_time_id:
			final String[] times = { "00:00-06:00", "06:00-12:00",
					"12:00-18:00", "18:00-24:00" };
			AlertDialog.Builder b = new AlertDialog.Builder(mContext);
			b.setSingleChoiceItems(times, 0,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							time = times[which];

						}
					})
					.setTitle("请选择时间段")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									fromTimeTxt.setText(time);

								}
							}).create().show();
			break;

		case R.id.change_id:
			String fromStr = fromTxtView.getText().toString();
			String toStr = toTxtView.getText().toString();
			fromTxtView.setText(toStr);
			toTxtView.setText(fromStr);
			break;

		case R.id.search_btn_id:
			pd = new ProgressDialog(mContext);
			pd.setMessage("正在加载中...");
			pd.setCancelable(false);
			pd.show();
			SearchTrainTask stt = new SearchTrainTask(mContext, pd);
			stt.execute(fromTxtView, toTxtView, fromDateTxt, fromTimeTxt);
			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == 1) {
			String fromcity = data.getStringExtra("fromcity");
			fromTxtView.setText(fromcity);
		}
		if (requestCode == 2 && resultCode == 2) {
			String tocity = data.getStringExtra("tocity");
			toTxtView.setText(tocity);
		}
	}
}
