package com.java.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.java.activity.OrderResultActivity;
import com.java.utils.MyServer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class BuyTicketTask extends AsyncTask<String, Void, String>{   
	private Context ctx;
	public BuyTicketTask(Context ctx){
		this.ctx=ctx;
	}
	
	@Override
	protected String doInBackground(String... params) {
		String url=MyServer.url+"BuyTicket.jsp";
		HttpClient client =new DefaultHttpClient();
		HttpPost post=new HttpPost(url);
		List<BasicNameValuePair> ls=new ArrayList<BasicNameValuePair>();
		ls.add(new BasicNameValuePair("username", params[0]));
		ls.add(new BasicNameValuePair("name", params[1]));
		ls.add(new BasicNameValuePair("idcard", params[2]));
		ls.add(new BasicNameValuePair("trainId", params[3]));
		ls.add(new BasicNameValuePair("trainNum", params[4]));
		ls.add(new BasicNameValuePair("fromMycity", params[5]));
		ls.add(new BasicNameValuePair("toMycity", params[6]));
		
		StringBuilder sb= new StringBuilder();
		try {
			UrlEncodedFormEntity entity= new UrlEncodedFormEntity(ls,"utf-8");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				InputStream is =response.getEntity().getContent();
				InputStreamReader isr=new InputStreamReader(is);
				BufferedReader br= new BufferedReader(isr);			
				String str="";
				while((str = br.readLine())!=null){
					sb.append(str);
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
		Intent it = new Intent(ctx,OrderResultActivity.class);
		it.putExtra("ticketInfo", result);
		ctx.startActivity(it);
		
	}

}
