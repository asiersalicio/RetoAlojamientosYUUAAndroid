package com.yuua.alojamientosyuua.activitys;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.yuua.alojamientosyuua.DatosApp;
import com.yuua.alojamientosyuua.R;
import com.yuua.alojamientosyuua.entidades.Usuario;
import com.yuua.alojamientosyuua.net.Cliente;
import com.yuua.alojamientosyuua.net.Consultas;
import com.yuua.reto.net.Request;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Register extends AppCompatActivity {

    EditText fechaNacimiento;
    final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private EditText campoNombre, campoApellidos, campoDNI, campoEmail, campoNombreUsuario, campoTelefono, campoContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DatosApp.currentContext=this;
        getSupportActionBar().hide();

        inizializar();
    }

    private void inizializar() {

        fechaNacimiento=findViewById(R.id.RegFechaNacimiento);
        campoNombre=findViewById(R.id.RegNombre);
        campoApellidos=findViewById(R.id.RegApellidos);
        campoDNI=findViewById(R.id.regDNI);
        campoEmail=findViewById(R.id.regEmail);
        campoNombreUsuario=findViewById(R.id.regUsername);
        campoTelefono=findViewById(R.id.regTelefono);
        campoContrasena=findViewById(R.id.regContrasena);

        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTimeField();
                datePickerDialog.show();
            }
        });

    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                fechaNacimiento.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        Calendar calmax18year = Calendar.getInstance();
        calmax18year.setTime(new Date());
        calmax18year.add(Calendar.YEAR, -18);
        Date datemax18years = calmax18year.getTime();

        datePickerDialog.getDatePicker().setMaxDate(datemax18years.getTime());
        datePickerDialog.show();

    }

    private void actualizarCampos() {
        String myFormat = getResources().getString(R.string.dateFormat);
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        fechaNacimiento.setText(sdf.format(myCalendar.getTime()));

    }

    public void btnRegistrarse(View view)
    {
        if(validarCampos())
        {
            Usuario usuario = new Usuario(campoDNI.getText().toString(), campoNombre.getText().toString(),campoApellidos.getText().toString(),"cliente",campoNombreUsuario.getText().toString(),campoContrasena.getText().toString(), new Date(),campoEmail.getText().toString(),Long.parseLong(campoTelefono.getText().toString()),null);
            Consultas consultas = new Consultas();
            Request peticion= consultas.prepararInsertHibernate(Usuario.class, new Object[]{usuario});
            consultas.devolverResultadoPeticionBoolean(peticion);
        }
        else
        {
            Toast.makeText(this,R.string.theDataIsNotValid,Toast.LENGTH_LONG).show();
        }

    }

    private boolean validarCampos() {

        boolean valido=true;



        if(campoNombreUsuario.getText().toString().length()<2)
        {
            valido=false;
            campoNombreUsuario.setError(getString(R.string.usernameIsNotValid));
            campoNombreUsuario.requestFocus();
        }

        // TODO Falta hacer el DNI y el email

        if(campoApellidos.getText().toString().length()<2)
        {
            valido=false;
            campoApellidos.setError(getString(R.string.surnameIsNotValid));
            campoApellidos.requestFocus();
        }
        if(campoNombre.getText().toString().length()<2)
        {
            valido=false;
            campoNombre.setError(getString(R.string.nameIsNotValid));
            campoNombre.requestFocus();
        }

        return valido;
    }


}
