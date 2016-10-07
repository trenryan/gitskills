package com.java.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class RegActivity extends Activity implements OnClickListener{
    private String uri=MyServer.url+"Reg.jsp";
	private Button saveBtn; 
	private EditText userNameTxt,pswTxt,nameTxt,telTxt,emailTxt,idcardTxt;
	private Handler handler;
	private String receiveStr;
	private ProgressDialog pd;
	private SharedPreferences sp;
	private String filename="myfile";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		initUI();
		handler=new Handler(){
			public void handleMessage(Message msg){
				pd.dismiss();
				if(receiveStr.equals("success")){
					sp=getSharedPreferences(filename,Context.MODE_PRIVATE);
					Editor editor=sp.edit();
					editor.putString("username", userNameTxt.getEditableText().toString());
					editor.putString("password", pswTxt.getEditableText().toString());
					editor.putString("name", nameTxt.getEditableText().toString());
					editor.putString("tel", telTxt.getEditableText().toString());
					editor.putString("email", emailTxt.getEditableText().toString());
					editor.putString("idcard", idcardTxt.getEditableText().toString());
					editor.commit();
					
					Intent it=new Intent(RegActivity.this,LoginActivity.class);
					startActivity(it);
					RegActivity.this.finish();
				}else if(receiveStr.equals("error")){
					Toast.makeText(RegActivity.this, "用户名已经存在", 1).show();
				}				
			}
		};
	}
	private void initUI(){
		saveBtn=(Button)findViewById(R.id.savebtn_id);
		userNameTxt=(EditText)findViewById(R.id.usertxt_id);
		pswTxt=(EditText)findViewById(R.id.pswtxt_id);
		nameTxt=(EditText)findViewById(R.id.nametxt_id);
		telTxt=(EditText)findViewById(R.id.teltxt_id);
		emailTxt=(EditText)findViewById(R.id.emailtxt_id);
		idcardTxt=(EditText)findViewById(R.id.idcardtxt_id);
		saveBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		pd=new ProgressDialog(this);
		pd.setMessage("正在注册......");
		pd.setCancelable(false);
		pd.show();
		// 执行线程
		MyThread mythread=new MyThread();
		new Thread(mythread).start();	
	}
	//内部类：线程
	private class MyThread implements Runnable{

		@Override
		public void run() {
			HttpClient client=new DefaultHttpClient();
			HttpPost post=new HttpPost(uri);
			List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("username", userNameTxt.getEditableText().toString()));
			params.add(new BasicNameValuePair("password", pswTxt.getEditableText().toString()));
			params.add(new BasicNameValuePair("name", nameTxt.getEditableText().toString()));
			params.add(new BasicNameValuePair("tel", telTxt.getEditableText().toString()));
			params.add(new BasicNameValuePair("email", emailTxt.getEditableText().toString()));
			params.add(new BasicNameValuePair("idcard", idcardTxt.getEditableText().toString()));			
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
