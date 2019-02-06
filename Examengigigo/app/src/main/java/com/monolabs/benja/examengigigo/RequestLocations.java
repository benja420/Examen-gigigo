package com.monolabs.benja.examengigigo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 02/02/2019.
 */

public class RequestLocations
{
    Context contexto;
    ConnectivityManager conMgr;
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public JSONObject Lugares(String url ) {


        // Making HTTP request
        try {

            jObj=null;

            // defaultHttpClient
            HttpParams params1 = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params1, 10000);
            HttpConnectionParams.setSoTimeout(params1, 10000);
            HttpClient httpClient = new DefaultHttpClient(params1);
            HttpPost httpPost = new HttpPost(url);



            //String usuario = txtusuario.getText().toString();
            List<NameValuePair> paramsx = new ArrayList<NameValuePair>();

            httpPost.setEntity(new UrlEncodedFormEntity(paramsx, HTTP.UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            json = "{'conexion':'Revisa tu conexión a internet...'}";
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            json = "{'conexion':'Revisa tu conexión a internet...'}";
        } catch (IOException e) {
            e.printStackTrace();
            json = "{'conexion':'Revisa tu conexión a internet...'}";

        }

        try {

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {

                sb.append(line).append("\n");
            }
            is.close();
            json = sb.toString();

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;



    }
}
