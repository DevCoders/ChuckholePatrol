package com.project.chuckholepatrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.util.Log;

public class SendServer {

	
	 private static String ServerURL = "http://chuckholepatrol.dyndns.org/chuckhole_api/set.php"; 
	   // private static String registerURL = "http://10.0.2.2/ah_login_api/"; 
	   // private JSONParser jsonParser;   
	    private JSONArray jsonarray;
	    private JSONObject json;
	    JSONObject jObj;
	    private String jso;
	   // private static String login_tag = "login"; 
	   // private static String register_tag = "register"; 
	  
	    // constructor 
	/*    public SendServer(){ 
	        jsonParser = new JSONParser(); 
	    } */
	  
	    /** 
	     * function make Login Request 
	     * @param email 
	     * @param password 
	     * */
	    public JSONObject send(Cursor cs){ 
	        // Building Parameters   
	        if(cs!=null)  
	        {  
	             cs.moveToFirst();  
	             jsonarray = new JSONArray();  
	             while (cs.isAfterLast() == false) {  
	             json = new JSONObject();  
	        try {  
	             json.put("lat",cs.getString(cs.getColumnIndex("lat")));  
	             json.put("lng",cs.getString(cs.getColumnIndex("lng")));  
	             json.put("time",cs.getString(cs.getColumnIndex("time")));   
	             json.put("x",cs.getString(cs.getColumnIndex("x")));
	             json.put("y",cs.getString(cs.getColumnIndex("y")));
	             json.put("z",cs.getString(cs.getColumnIndex("z")));
	             jsonarray.put(json);  
	             cs.moveToNext();  
	        }  
	        catch (Exception e) {  
	             Log.d("Android", "JSON Error");  
	        }  
	             }  
	             

	             try {  
	                 // Create a new HttpClient and Post Header  
	                 HttpClient httpclient = new DefaultHttpClient();  
	                 HttpPost httppost = new HttpPost(ServerURL);  
	                 // Post the data:  
	                 Log.d("JSON", jsonarray.toString());
	                 StringEntity se = new StringEntity(jsonarray.toString());  
	                 httppost.setEntity(se);  
	                 httppost.setHeader("Accept", "application/json");  
	                 httppost.setHeader("Content-type", "application/json");  
	                 // Execute HTTP Post Request  
	                 System.out.print(json);  
	                 HttpResponse response = httpclient.execute(httppost);  
	                 //HttpEntity httpEntity = HttpResponse.getEntity(); 
	                // is = httpEntity.getContent(); 
	                 // for JSON:  
	                 if(response != null)  
	                 {  
	                      InputStream is = response.getEntity().getContent();  
	                      BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);  
	                      StringBuilder sb = new StringBuilder();  
	                      String line = null;  
	                      try {  
	                           while ((line = reader.readLine()) != null) {  
	                           sb.append(line + "n");  
	                           }  
	                           
	                           jso = sb.toString();
	                           Log.e("JSON", jso); 
	                      } catch (IOException e) {  
	                           e.printStackTrace();  
	                           }   
	                      finally {  
	                           try {  
	                                is.close();  
	                           } catch (IOException e) {  
	                                               e.printStackTrace();  
	                                          }  
	                                     }  
	                                }  
	                 }  
	                 catch (ClientProtocolException e) {                 
	                 } catch (IOException e) {   
	              }            
	            }  
	           // cs.close();     
	    
	         
				// try parse the string to a JSON object 
	            try { 
	                jObj = new JSONObject(jso); 
	            } catch (JSONException e) { 
	                Log.e("JSON Parser", "Error parsing data " + e.toString()); 
	            } 
	      
	            // return JSON String 
	            return jObj; 

	      /*  List<NameValuePair> params = new ArrayList<NameValuePair>(); 
	        params.add(new BasicNameValuePair("tag", login_tag)); 
	        params.add(new BasicNameValuePair("email", email)); 
	        params.add(new BasicNameValuePair("password", password)); 
	      //  JSONObject json = jsonParser.getJSONFromUrl(loginURL, params); 
	        // return json 
	        // Log.e("JSON", json.toString()); 
	        return json; */
	    } 

	        
	    
	
}
