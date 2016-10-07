package com.java.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

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
import android.content.*;
import android.content.SharedPreferences.Editor;
import android.os.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class LoginActivity extends Activity implements OnClickListener{
	private String url=MyServer.url+"LoginCheck.jsp";
	private Handler handler;
	private ProgressDialog pd;
	private String receiveStr;
	private Button loginBtn,regBtn;
	private EditText userTxt,pswTxt;
	private SharedPreferences sp;
	private String filename="myfile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initUI();
		
		handler=new Handler(){
			public void handleMessage(Message msg){
				pd.dismiss();
				if(receiveStr.equals("success")){
					sp=getSharedPreferences(filename,Context.MODE_PRIVATE);
					Editor editor=sp.edit();
					editor.putString("username", userTxt.getEditableText().toString());
					editor.putString("password", pswTxt.getEditableText().toString());
					editor.commit();
					Intent it= new Intent(LoginActivity.this,MainActivity.class);
					startActivity(it);
				}else if(receiveStr.equals("error")){
					Toast.makeText(LoginActivity.this,"用户名或者密码不正确",1).show();
				}
			}
		};
		
	}
	private void initUI(){
		
		userTxt=(EditText)findViewById(R.id.usertxt_id);
		pswTxt=(EditText) findViewById(R.id.pswtxt_id);
		loginBtn=(Button)findViewById(R.id.loginbtn_id);
		regBtn=(Button)findViewById(R.id.regbtn_id);
		loginBtn.setOnClickListener(this);
		regBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.loginbtn_id:
			pd=new ProgressDialog(this);
			pd.setMessage("正在登录......");
			pd.setCancelable(false);
			pd.show();
			//启动线程
			MyThread my=new MyThread();
			new Thread(my).start();
			break;
		case R.id.regbtn_id:
			Intent it=new Intent(this,RegActivity.class);
			startActivity(it);
			break;
			
			default:
				break;		
		}
	}
	
	//内部类：线程
	private class MyThread implements Runnable{

		@Override
		public void run() {
			HttpClient client =new DefaultHttpClient();
			HttpPost post=new HttpPost(url);
			List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("username", userTxt.getEditableText().toString()));
			params.add(new BasicNameValuePair("password", pswTxt.getEditableText().toString()));
			try {
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"utf-8");
				post.setEntity(entity);
				HttpResponse response= client.execute(post);
				if(response.getStatusLine().getStatusCode()==200){
					HttpEntity httpentity= response.getEntity();
					InputStream is=httpentity.getContent();
					InputStreamReader isr=new InputStreamReader(is);
					BufferedReader br=new BufferedReader(isr);
					receiveStr=br.readLine();
					handler.sendEmptyMessage(1);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
