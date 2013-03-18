package com.project.chuckholepatrol;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v4.app.NavUtils;


public class View_graph extends Activity {

	private static final String MAP_URL = "http://chuckholepatrol.dyndns.org";  
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_graph);
		setupWebView();    
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// Show the Up button in the action bar.
	//	getActionBar().setDisplayHomeAsUpEnabled(true);
    	//Intent lineIntent = line.getIntent(this);
       // startActivity(lineIntent);
		
	}

	private void setupWebView() {       
	webView = (WebView) findViewById(R.id.webview);    
	webView.getSettings().setLoadWithOverviewMode(true);
    webView.getSettings().setUseWideViewPort(true);
	webView.getSettings().setJavaScriptEnabled(true);    
	//Wait for the page to load then send the location information    
	webView.setWebViewClient(new WebViewClient());    
	webView.loadUrl(MAP_URL);  
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_graph, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		//view = line.getView(this);
		//setContentView(view);
	}


}
