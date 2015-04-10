package com.mall.intel.rnh;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager.OnCancelListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button btn;
    String res="";
    String res1="";
    ListView prdList;
    ArrayList<String> products = new ArrayList<String>();
    MyCustomAdapter myCustomAdapter;
    String [] list ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.Scanbutton);
        btn.setOnClickListener(this);
        //myCustomAdapter = new MyCustomAdapter(products, this);
        /*prdList = (ListView)findViewById(R.id.ProductList);
        prdList.setAdapter(myCustomAdapter);
    */
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v)
    {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();




    }

    private class GetData extends AsyncTask<Void, Void, Void>

    {

        String result = "";

        GetData(TextView view) {

        }


        protected void onPostExecute(Void s) {
            //	tv1.setText(blood[0]);


            try {
                products.add(res1);
                //ArrayAdapter<String> myAdp = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, products);
                //prdList.setAdapter(myAdp);
                myCustomAdapter = new MyCustomAdapter(products, getApplicationContext());
                prdList = (ListView)findViewById(R.id.ProductList);
                prdList.setAdapter(myCustomAdapter);

            }
            catch (Exception fndn)
            {
                // result=res1;
                Log.d("Intererror","error in adding to list");
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
           HttpClient httpclient;
            Log.d("In Doin", "asasasas");
            HttpGet request;
            HttpResponse response = null;


            try {
                httpclient = new DefaultHttpClient();

                request = new HttpGet("http://imall.harshnv.me/intell.php?bcode=" + res);
                response = httpclient.execute(request);
                Log.d("Response", response.toString());
                Log.d("Res", res);
            } catch (Exception e) {

                result = "error1";
            }

            try {

                BufferedReader rd = null;
                if (response != null) {
                    rd = new BufferedReader(new InputStreamReader(
                            response.getEntity().getContent()));
                }


                res1 = rd.readLine();


            } catch (Exception e) {
                result = "error2";
                e.printStackTrace();
            }

            Log.d("Final Result", result);
            return null;
        }

    }
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
try{
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
//we have a result
            res=scanningResult.getContents();
            new GetData(null).execute(null,null,null);
          //  products.add(scanningResult.getContents());
         //   Log.d("fuck", scanningResult.getContents());

        } else {
       //     Log.d("fuck2", "No result");
            showToast();
        }
}
catch (Exception er)
{
    showToast();
}
    }
    private void showToast(){
        Toast.makeText(this, "No Data Read", Toast.LENGTH_SHORT).show();
    }

}
