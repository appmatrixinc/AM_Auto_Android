package com.appmatrixinc.amauto;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;

/**
 * Created by jennaharris on 4/18/14.
 */
public class ContactUs extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact_us, container, false);

        ImageButton callNow = (ImageButton) view.findViewById(R.id.call_now);
        ImageButton emailCS = (ImageButton) view.findViewById(R.id.email_cs);
        ImageButton emplDir = (ImageButton) view.findViewById(R.id.employee_dir);
        ImageButton mapDir = (ImageButton) view.findViewById(R.id.map_dir);
        final WebView webviewContact = (WebView) getActivity().findViewById(R.id.webview_contact);

        View header = view.findViewById(R.id.header);
        ImageButton menu = (ImageButton) header.findViewById(R.id.menu_button);
        menu.setOnClickListener(MenuButton.menuToggle());

        callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String phone_cs = getResources().getString(R.string.phone_customer_service);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone_cs));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });

        emailCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_cs = getResources().getString(R.string.email_customer_service);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email_cs});
                i.putExtra(Intent.EXTRA_SUBJECT, "AM Auto Customer Service");
                //i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mapDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://www.maps.google.com/maps?saddr="
                + URLEncoder.encode("37.78583526611328")
                + ","
                + URLEncoder.encode("-122.40641784667969")
                + "&daddr="
                + URLEncoder.encode("230 Automall Dr Roseville CA 95661");
                //new MyAsyncTask().execute(url);
                openWebView(url);

            }
        });

        return view;
    }

    private void openWebView(String url) {
        WebView webviewContact = (WebView) getActivity().findViewById(R.id.webview_contact);

        webviewContact.setWebViewClient(new WebViewClient());
        webviewContact.getSettings().setJavaScriptEnabled(true);
        webviewContact.loadUrl(url);
        webviewContact.setVisibility(View.VISIBLE);
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {

            get(getActivity(), urls[0]);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(), "MyAsynchTask Complete", Toast.LENGTH_LONG);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        private void get(final Context context, final String url) {
            //new Thread() {
               // @Override
                //public void run() {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet(url);
                    setProxyIfNecessary(context, request);

                    try {
                        HttpResponse response = client.execute(request);
                        Log.v("Test", "StatusCode: " + response.getStatusLine().getStatusCode() + ", Entity: " + EntityUtils.toString(response.getEntity()));

                    } catch (Exception e) {
                        // Oh, crash
                    }
                //}
           // }.start();
        }

        private void setProxyIfNecessary(Context context, HttpUriRequest request) {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivity == null ? null : connectivity.getActiveNetworkInfo();
            if (networkInfo == null || networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return;
            }

            String proxyHost = Proxy.getHost(context);
            if (proxyHost == null) {
                return;
            }

            int proxyPort = Proxy.getPort(context);
            if (proxyPort < 0) {
                return;
            }

            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            ConnRouteParams.setDefaultProxy(request.getParams(), proxy);
        }
    }
}
