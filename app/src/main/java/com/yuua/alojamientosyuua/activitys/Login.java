package com.yuua.alojamientosyuua.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.yuua.alojamientosyuua.DatosApp;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.entidades.Usuario;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient apiClient;
    private SignInButton signInButton;
    private static int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        inizializar();


    }

    private void inizializar() {
        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        apiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        signInButton=(SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            gotoProfile(result);
        }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }
    private void gotoProfile(GoogleSignInResult result){
        GoogleSignInAccount account=result.getSignInAccount();
        DatosApp.user = new Usuario(account.getFamilyName(),account.getGivenName(),account.getDisplayName(),"usuario",account.getDisplayName(),null,null,account.getEmail(),0,account.getPhotoUrl());
        finish();
    }





    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Error de conexion!", Toast.LENGTH_SHORT).show();
        Log.e("GoogleSignIn", "OnConnectionFailed: " + connectionResult);
    }

    public void btnRegisterWithEmail(View view)
    {
        Intent intentRegistro = new Intent(this, Register.class);
        startActivity(intentRegistro);
        finish();
    }


}
