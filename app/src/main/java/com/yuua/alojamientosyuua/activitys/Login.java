package com.yuua.alojamientosyuua.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.yuua.alojamientosyuua.LoginLogOut;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.Sistema;
import com.yuua.alojamientosyuua.entidades.Usuario;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient apiClient;
    private SignInButton signInButton;
    private static int RC_SIGN_IN = 1;
    private CheckBox keekLoged;

    private EditText etLoginUser, etLoginPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        inicializar();
    }

    private void inicializar() {
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
        keekLoged=findViewById(R.id.keepLoggedLogin);


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
        Sistema.user = new Usuario(account.getFamilyName(),account.getGivenName(),account.getDisplayName(),"usuario",account.getDisplayName(),null,null,account.getEmail(),0,account.getPhotoUrl());
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
        ArrayList<Object> resultadosBusqueda;
        String loginPassEncrypted = Register.md5(etLoginPass.getText().toString());
        Consultas consultar = new Consultas();
        Request peticionUsuario = consultar.prepararQueryHibernate(Consultas.QUERY_CON_CONDICIONES, Usuario.class, new String[]{"nombreUsuario","contrasena"}, new String[]{etLoginUser.getText().toString(),loginPassEncrypted});
        resultadosBusqueda = (ArrayList<Object>)consultar.devolverResultadoPeticion(peticionUsuario,Usuario.class);

        if(resultadosBusqueda.size()==1)
        {
            Sistema.user = (Usuario) resultadosBusqueda.get(0);
            Toast.makeText(this, getString(R.string.youHaveBeenLogged), Toast.LENGTH_LONG).show();
            if(keekLoged.isChecked())
            {
                System.out.println("Guardando usuario persistente");
                LoginLogOut.guardarLogin(this, Sistema.user.getIdDni(),Sistema.user.getContrasena());
            }
            finish();
        }
        else
        {
            Toast.makeText(this, getString(R.string.incorrectUserAndPassword), Toast.LENGTH_LONG).show();
        }

    }



}
