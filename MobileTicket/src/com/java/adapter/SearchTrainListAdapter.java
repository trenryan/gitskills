package com.java.adapter;

import java.util.List;

import com.java.bean.Train;
import com.java.ticket.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchTrainListAdapter extends BaseAdapter{
    private Context ctx;
    private List<Train> data;
    private boolean flag=false;
	
    public SearchTrainListAdapter(Context ctx,List<Train> data){
    	this.ctx=ctx;
    	this.data=data;
    }
    
	@Override
	public int getCount() {
		return data.size();
	}
	@Override
	public Object getItem(int position) {	
		return data.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	public View getView(int position,View convertView,ViewGroup parent){
		final ViewHolder viewHolder = new ViewHolder();
		if(convertView==null){
			convertView=LayoutInflater.from(ctx).inflate(R.layout.train_list_item, null);
			TextView trainNumTxtView =(TextView)convertView.findViewById(R.id.train_num_id);
			TextView trainFromcityTxtView =(TextView)convertView.findViewById(R.id.from_city_id);
			TextView trainTocityTxtView =(TextView)convertView.findViewById(R.id.to_city_id);
			TextView userFromcityTxtView =(TextView)convertView.findViewById(R.id.from_mycity_id);
			TextView userTocityTxtView =(TextView)convertView.findViewById(R.id.to_mycity_id);
			ImageView downUpImgView =(ImageView)convertView.findViewById(R.id.down_up_id);
			TextView hiddenView =(TextView)convertView.findViewById(R.id.txt_id);
			
			trainNumTxtView.setText(data.get(position).getNum());
			trainFromcityTxtView.setText(data.get(position).getFrom_city());
			trainTocityTxtView.setText(data.get(position).getTo_city());
			userFromcityTxtView.setText(data.get(position).getFrom_mycity());
			userTocityTxtView.setText(data.get(position).getTo_mycity());
			
			viewHolder.trainNumTxtView =trainNumTxtView;
			viewHolder.trainFromcityTxtView =trainFromcityTxtView;
			viewHolder.trainTocityTxtView =trainTocityTxtView;
			viewHolder.userFromcityTxtView =userFromcityTxtView;
			viewHolder.userTocityTxtView =userTocityTxtView;
			viewHolder.downUpImgView =downUpImgView;
			viewHolder.hiddenView =hiddenView;
			
			convertView.setTag(viewHolder);
		}else{
			convertView.getTag();
		}
		viewHolder.downUpImgView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flag=!flag;
				if(flag==false){
					viewHolder.downUpImgView.setImageDrawable(ctx.getResources().getDrawable(R.drawable.icon_down));
					viewHolder.hiddenView.setVisibility(View.GONE);
				}
				if(flag==true){
					viewHolder.downUpImgView.setImageDrawable(ctx.getResources().getDrawable(R.drawable.icon_up));
					viewHolder.hiddenView.setVisibility(View.VISIBLE);
				}
			}
		});
		return convertView;
	}
	class ViewHolder{
	    public TextView trainNumTxtView;
	    public TextView trainFromcityTxtView;
	    public TextView trainTocityTxtView;
	    public TextView userFromcityTxtView;
	    public TextView userTocityTxtView;
	    public ImageView downUpImgView;
	    public TextView hiddenView;
	}
}
	
