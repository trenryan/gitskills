package com.java.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.java.ticket.R;
import com.java.utils.MyServer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class PersonTelUpdateActivity extends Activity implements OnClickListener{
	private String uri=MyServer.url+"PersonTelUpdate.jsp";
	private Handler handler;
	private String username;
	private String receiveStr;
	private ProgressDialog pd;
	private Button saveBtn;
	private EditText oldTelTxt,newTelTxt;
	private SharedPreferences sp;
	private String filename="myfile";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_tel_update);
		
		oldTelTxt=(EditText) findViewById(R.id.old_tel_txt_id);
		newTelTxt=(EditText) findViewById(R.id.new_tel_txt_id);
		saveBtn=(Button) findViewById(R.id.save_tel_btn_id);
		saveBtn.setOnClickListener(this);
		
		sp=getSharedPreferences(filename, Context.MODE_PRIVATE);
		String tel=sp.getString("tel", "");
		username=sp.getString("username", "");
		oldTelTxt.setText(tel);
		
		handler=new Handler(){
			public void handleMessage(Message msg){
				if(receiveStr.equals("ok")){
					Toast.makeText(PersonTelUpdateActivity.this, "�޸ĳɹ���", 1).show();
					Intent it=new Intent(PersonTelUpdateActivity.this,MainActivity.class);
					startActivity(it);
				}else{
					Toast.makeText(PersonTelUpdateActivity.this, "�޸�ʧ�ܣ�", 1).show();
				}
			}
		};
	}
	@Override
	public void onClick(View v) {
		MyThread my=new MyThread();
		new Thread(my).start();
		
	}
	//�ڲ��ࣺ�߳�
	private class MyThread implements Runnable{

		@Override
		public void run() {
			HttpClient client=new DefaultHttpClient();
			HttpPost post=new HttpPost(uri);
			List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("tel", newTelTxt.getEditableText().toString()));
			params.add(new BasicNameValuePair("username", username));
			try {
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"utf-8");
				post.setEntity(entity);
				HttpResponse response=client.execute(post);
				if(response.getStatusLine().getStatusCode()==200){
					HttpEntity httpEntity= response.getEntity();
					InputStream is= httpEntity.getContent();
		 			InputStreamReader isr=new InputStreamReader(is);
					BufferedReader br=new BufferedReader(isr);
					receiveStr=br.readLine();
					Message msg=new Message();
					msg.what=1;
					handler.sendMessage(msg);
				}
			}catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}catch (ClientProtocolException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
				}			
			}
		}

}
