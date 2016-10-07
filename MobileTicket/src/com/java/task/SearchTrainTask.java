package com.java.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.DefaultedHttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.java.activity.SearchTrainListActivity;
import com.java.bean.Train;
import com.java.utils.MyServer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.widget.TextView;
import android.widget.Toast;

public class SearchTrainTask extends AsyncTask<TextView, Void, String>{
    private Context ctx;
    private ProgressDialog pd;
    public SearchTrainTask(Context ctx,ProgressDialog pd){
    	this.ctx=ctx;
    	this.pd=pd;
    }
	
	@Override
	protected String doInBackground(TextView... params) {
		String fromcity=params[0].getText().toString();
		String tocity=params[1].getText().toString();
		String fromDate=params[2].getText().toString();
		String fromTime=params[3].getText().toString();
		
		String url=MyServer.url+"SearchTrainList.jsp";
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(url);
		List<BasicNameValuePair> ls=new ArrayList<BasicNameValuePair>();
		ls.add(new BasicNameValuePair("fromcity",fromcity));
		ls.add(new BasicNameValuePair("tocity",tocity));
		ls.add(new BasicNameValuePair("fromDate",fromDate));
		ls.add(new BasicNameValuePair("fromTime",fromTime));
		
		StringBuilder sb=new StringBuilder();
		String s="";
		try {
			UrlEncodedFormEntity entity=new UrlEncodedFormEntity(ls,"utf-8");
			post.setEntity(entity);
			HttpResponse response=client.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity httpEntity =response.getEntity();
				InputStream is=httpEntity.getContent();
				InputStreamReader isr=new InputStreamReader(is);
				BufferedReader br=new BufferedReader(isr);
				while((s=br.readLine())!=null){
					sb.append(s);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	@Override
	protected void onPostExecute(String result) {
	//	Toast.makeText(ctx, result, 1).show();
		pd.dismiss();
		List<Train> ls=new ArrayList<Train>();
		try {
			JSONArray ja=new JSONArray(result);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jobj=ja.getJSONObject(i);
				int id=jobj.getInt("id");
				String num=jobj.getString("num");
				String start_date=jobj.getString("start_date");
				String start_time=jobj.getString("start_time");
				String from_city=jobj.getString("from_city");
				String to_city=jobj.getString("to_city");
				String from_mycity=jobj.getString("from_mycity");
				String to_mycity=jobj.getString("to_mycity");
				
				Train train=new Train();
				train.setId(id);
				train.setNum(num);
				train.setStart_date(start_date);
				train.setStart_time(start_time);
				train.setFrom_city(from_city);
				train.setTo_city(to_city);
				train.setFrom_mycity(from_mycity);
				train.setTo_mycity(to_mycity);
				ls.add(train);
				
			}
			Intent it=new Intent(ctx,SearchTrainListActivity.class);
			Bundle bundle=new Bundle();
			bundle.putSerializable("trainlist", (Serializable)ls);
			it.putExtras(bundle);
			ctx.startActivity(it);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	

}
