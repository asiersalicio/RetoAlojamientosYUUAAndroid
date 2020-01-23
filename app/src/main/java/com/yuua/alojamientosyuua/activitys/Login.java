package com.yuua.alojamientosyuua.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient apiClient;
    private SignInButton signInButton;
    private static int RC_SIGN_IN = 1;

    private EditText etLoginUser, etLoginPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        DatosApp.currentContext=this;
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

        etLoginUser=findViewById(R.id.loginUser);
        etLoginPass=findViewById(R.id.loginPass);
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


    public void login(View view){
        String loginPassEncrypted = Register.md5(etLoginPass.getText().toString());

        Consultas consultar = new Consultas();
        Request peticionUsuario = consultar.prepararQueryHibernate(Consultas.QUERY_CON_CONDICIONES, Usuario.class, new String[]{"nombreUsuario","contrasena"}, new String[]{etLoginUser.getText().toString(),loginPassEncrypted});

        if(peticionUsuario != null){
            Toast.makeText(this, "Te has logeado", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Base.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "¿Creiste que iba a funcionar?", Toast.LENGTH_SHORT).show();
        }
    }


}
