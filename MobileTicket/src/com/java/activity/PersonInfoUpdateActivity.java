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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonInfoUpdateActivity extends Activity implements OnClickListener{
	private String uri=MyServer.url+"PersonInfoUpdate.jsp";
	private Handler handler;
	private String username;
	private String receiveStr;
//	private ProgressDialog pd;
	private Button saveBtn;
	private EditText oldNameTxt,newNameTxt,oldIdcardTxt,newIdcardTxt;
	private SharedPreferences sp;
	private String filename="myfile";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_info_update);
		
		oldNameTxt=(EditText) findViewById(R.id.old_name_txt_id);
		newNameTxt=(EditText) findViewById(R.id.new_name_txt_id);
		oldIdcardTxt=(EditText) findViewById(R.id.old_idcard_txt_id);
		newIdcardTxt=(EditText) findViewById(R.id.new_idcard_txt_id);
		saveBtn=(Button) findViewById(R.id.save_personinfo_btn_id);
		saveBtn.setOnClickListener(this);
		
		sp=getSharedPreferences(filename, Context.MODE_PRIVATE);
		String name=sp.getString("name", "");
		String idcard=sp.getString("idcard", "");
		username=sp.getString("username", "");
		oldNameTxt.setText(name);
		oldIdcardTxt.setText(idcard);
		
		handler=new Handler(){
			public void handleMessage(Message msg){
				if(receiveStr.equals("ok")){
					Toast.makeText(PersonInfoUpdateActivity.this, "修改成功！", 1).show();
					Intent it=new Intent(PersonInfoUpdateActivity.this,MainActivity.class);
					startActivity(it);
				}else{
					Toast.makeText(PersonInfoUpdateActivity.this, "修改失败！", 1).show();
				}
			}
		};
	}
	@Override
	public void onClick(View v) {
		MyThread my=new MyThread();
		new Thread(my).start();
		
	}
	//内部类：线程
	private class MyThread implements Runnable{

		@Override
		public void run() {
			HttpClient client=new DefaultHttpClient();
			HttpPost post=new HttpPost(uri);
			List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("name", newNameTxt.getEditableText().toString()));
			params.add(new BasicNameValuePair("idcard", newIdcardTxt.getEditableText().toString()));
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
