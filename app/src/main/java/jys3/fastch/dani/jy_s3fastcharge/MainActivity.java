package jys3.fastch.dani.jy_s3fastcharge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.lang.Process;
import android.content.*;
import android.support.v4.widget.*;
import android.view.View.*;
import android.text.*;
import android.support.v4.view.*;

public class MainActivity extends AppCompatActivity {

	public static Context context;
	public static EditText etChaB;
	public static EditText etUSBB;
	public static DrawerLayout layout_main;
	
	android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		context = this;
        getSupportFragmentManager().beginTransaction().replace(R.id.flDrawer,DrawerFragment.newInstance()).addToBackStack(null).commit();
		getSupportFragmentManager().beginTransaction().replace(R.id.flMain,MainFragment.newInstance()).addToBackStack(null).commit();
		
		
		etChaB = (EditText)findViewById(R.id.etChaB);
		etUSBB = (EditText)findViewById(R.id.etUSBB);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		layout_main = (DrawerLayout)findViewById(R.id.activity_main);
		layout_main.setDrawerListener(mDrawerToggle);
		
		mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,layout_main, R.string.abc_action_bar_home_description,R.string.abc_action_bar_home_subtitle_description_format){
			public void onDrawerClosed(View view){
				getSupportActionBar().setTitle(getString(R.string.app_name)); //JY-S3 Fast Charge
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View view){
				getSupportActionBar().setTitle(getString(R.string.app_title_drawer_open)); //No init.d?
				supportInvalidateOptionsMenu();
			}


		};
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		layout_main.setDrawerListener(mDrawerToggle);
		
    }

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
        }
        

        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onBackPressed()
	{
		if (layout_main.isDrawerOpen(GravityCompat.START)){
			layout_main.closeDrawers();
		}else{
			finish();
		}
		
	}
}
