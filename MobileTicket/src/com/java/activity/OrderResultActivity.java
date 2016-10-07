package com.java.activity;

import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.java.ticket.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class OrderResultActivity extends Activity {
    private String username,idcard,orderTime,fromDate,fromTime;
	private int trainId;
	private String trainNum,fromMycity,toMycity,seat_num,price;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_result);
		
		String ticketInfo = getIntent().getStringExtra("ticketInfo");		
		try {
		JSONArray ja = new JSONArray(ticketInfo);
		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = ja.getJSONObject(i);
			username = jo.getString("username");
			idcard = jo.getString("idcard");
			orderTime = jo.getString("orderTime");
			fromDate = jo.getString("fromDate");
			fromTime = jo.getString("fromTime");
			trainId = jo.getInt("trainId");
			trainNum = jo.getString("trainNum");
			fromMycity = jo.getString("fromMycity");
			toMycity = jo.getString("toMycity");
			seat_num = jo.getString("seat_num");
			price = jo.getString("price");
		}
		String contents= "BEGIN:TICKET\nVERSION:1.0\n"+"Name:"+username+"\nIdcard:"+idcard+"\nOrdertime:"+orderTime+"\nFromDate:"+fromDate+ "\nFromTime:"+fromTime+"\nTrainId:"+trainId+"\nTrainNum:"+trainNum+"\nFromMycity:"+fromMycity+"\nToMycity:"+toMycity+"\nSeatNum:"+seat_num+"\nPrice:"+price+"\nEND:TICKET";
		try {
			Bitmap bm= qr_code(contents, BarcodeFormat.QR_CODE);			
	        ImageView img=(ImageView)findViewById(R.id.order_result_img) ;	
	        img.setImageBitmap(bm);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	} catch (JSONException e) {
		e.printStackTrace();
	}
	}
	//
	public Bitmap qr_code(String string, BarcodeFormat format)
			throws WriterException {
		MultiFormatWriter writer = new MultiFormatWriter();
		Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
		hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix matrix = writer.encode(string, format, 400, 400, hst);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}

			}
		}
	Bitmap bitmap = Bitmap.createBitmap(width, height,
			Bitmap.Config.ARGB_8888);
	// 通过像素数组生成bitmap,具体参考api
	bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	return bitmap;
    }
}
