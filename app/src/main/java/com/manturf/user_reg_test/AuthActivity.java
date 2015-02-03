package com.manturf.user_reg_test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AuthActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String TAG = AuthActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        BootstrapButton signup = (BootstrapButton) findViewById(R.id.signup);
        signup.setOnClickListener(AuthActivity.this);
    }

    @Override
    public void onClick(View v) {
        final UserReg userReg = new UserReg();

        EditText email = (EditText) findViewById(R.id.edit_email);
        EditText password = (EditText) findViewById(R.id.edit_password);
        EditText password_c = (EditText) findViewById(R.id.edit_password_c);

        final String eEmail = email.getText().toString();
        final String ePassword = password.getText().toString();
        final String ePassword_c = password_c.getText().toString();

        try {
            UserReg.UserInfoConv(eEmail, ePassword, ePassword_c);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //queue
        RequestQueue queue = Volley.newRequestQueue(this);

        //url
        final String url = "http://manturf2.herokuapp.com/api/registrations";

        //Request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            RespMsg respMsg = JsonConverter.toObject(s,RespMsg.class);
                            Log.i(TAG,respMsg.getStatus());
                            /*Log.i(TAG,respMsg.getPassword());*/
                            if (respMsg.getStatus().equals("ng")){
                                Toast.makeText(getApplication(),"まちがってるで！",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplication(),"成功や！楽しんでや！",Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
/*                        Toast.makeText(getApplication(),"RESPONSE" + s,Toast.LENGTH_LONG).show();*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(),"ERROR" + error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("user[email]",eEmail);
                params.put("user[password]",ePassword);
                params.put("user[password_confirmation]",ePassword_c);
                return params;
            }
        };
        queue.add(postRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auth, menu);
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
}
