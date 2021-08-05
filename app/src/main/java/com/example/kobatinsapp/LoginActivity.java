package com.example.kobatinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btn_login;

    BaseApiService baseApiService;
    Context context;
    Sesion sesion;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        baseApiService = UtilsApi.getAPIService();
        context = this;
        sesion = new Sesion(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseApiService.loginRequest(username.getText().toString(), password.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){

                                    String id_user = jsonRESULTS.getJSONObject("tbl_user").getString("id_user");
                                    String name = jsonRESULTS.getJSONObject("tbl_user").getString("name");
                                    String username = jsonRESULTS.getJSONObject("tbl_user").getString("username");
                                    String password = jsonRESULTS.getJSONObject("tbl_user").getString("password");
                                    String handphone = jsonRESULTS.getJSONObject("tbl_user").getString("handphone");

                                    sesion.saveSPString(Sesion.IDUSER, id_user);
                                    sesion.saveSPString(Sesion.NMUSER, name);
                                    sesion.saveSPString(Sesion.USRUSER, username);
                                    sesion.saveSPString(Sesion.PASSUSER, password);
                                    sesion.saveSPString(Sesion.HPUSER, handphone);
                                    sesion.saveSPBoolean(Sesion.SUDAH_LOGIN, true);

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();

                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(getApplication(), error_message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }  catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });
    }

    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}
