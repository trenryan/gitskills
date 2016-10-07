package com.java.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.java.fragment.PersonFragment;
import com.java.fragment.QueryFragment;
import com.java.fragment.ReserverFragment;
import com.java.ticket.R;

public class MainActivity extends FragmentActivity {

	private ViewPager vp_content;
	private RadioGroup rg_group;
	private PersonFragment personFragment;
	private QueryFragment queryFragment;
	private ReserverFragment reserverFragment;
	private List<Fragment> mFragmentList;
	private RadioButton rb_reserver;
	private RadioButton rb_order;
	private RadioButton rb_person;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_main);
		initView();
		initData();

	}

	private void initData() {
		rg_group.check(R.id.rb_reserver);
		reserverFragment = new ReserverFragment();
		queryFragment = new QueryFragment();
		personFragment = new PersonFragment();
		List<Fragment> mFragments = new ArrayList<Fragment>();
		mFragments.add(reserverFragment);
		mFragments.add(queryFragment);
		mFragments.add(personFragment);
		FragmentAdapter fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragments);
		vp_content.setAdapter(fragmentAdapter);
		vp_content.setCurrentItem(0);
		rg_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
					switch (checkedId) {
					case R.id.rb_reserver:
						vp_content.setCurrentItem(0,false);
						break;
					case R.id.rb_order:
						vp_content.setCurrentItem(1,false);
						break;
					case R.id.rb_person:
						vp_content.setCurrentItem(2,false);
						break;
					}
			}
		});
		
	}

	private void initView() {
		vp_content = (ViewPager) findViewById(R.id.vp_content);
		rg_group = (RadioGroup) findViewById(R.id.rg_group);
		rb_reserver = (RadioButton) findViewById(R.id.rb_reserver);
		rb_order = (RadioButton) findViewById(R.id.rb_order);
		rb_person = (RadioButton) findViewById(R.id.rb_person);
	}

	class FragmentAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragmentList = new ArrayList<Fragment>();

		public FragmentAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
			super(fm);
			this.fragmentList = mFragmentList;
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}
	}
}
