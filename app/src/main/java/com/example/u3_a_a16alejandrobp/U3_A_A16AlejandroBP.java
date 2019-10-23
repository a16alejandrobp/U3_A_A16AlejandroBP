package com.example.u3_a_a16alejandrobp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class U3_A_A16AlejandroBP extends AppCompatActivity {
    Bundle b = new Bundle();
  private static final int COD_TLF=1;
  private static final int COD_PETICION = 33;
  AlertDialog.Builder venta;
    Intent intent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent segunda = new Intent(U3_A_A16AlejandroBP.this,Secundaria.class);
                //startActivity(segunda);
                startActivityForResult(segunda, COD_PETICION);

            }
        });
        btn1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog(v.getId());
                return true;
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == COD_PETICION) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("cadea")){
                    b.putString("cadea",data.getExtras().getString("cadea"));
                }
                if (data.hasExtra("telefono")){
                    b.putString("telefono",data.getExtras().getString("telefono"));
                }

            } else
                Toast.makeText(this, "Saíches da actividade secundaria sen premer o botón Pechar", Toast.LENGTH_SHORT).show();
        }

    }


    public void mostrarDatos(View v){

        Toast.makeText(this, "Cadea: "+b.getString("cadea")+"\nTeléfono: "+b.getString("telefono"),Toast.LENGTH_LONG).show();
    }
    protected Dialog onCreateDialog(int id) {
        venta = new AlertDialog.Builder(this);
        venta.setTitle("Selecciona unha opción");
        venta.setCancelable(true);
        venta.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                if(!b.containsKey("cadea") || b.getString("cadea").isEmpty()){
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.es/search?q=casa"));
                    startActivity(intent);
                }else {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.es/search?q="+b.getString("cadea")));
                    startActivity(intent);
                }
            }
        });
        venta.setNegativeButton("Chamar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                if (Build.VERSION.SDK_INT>=23){
                    int permiso = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    if (permiso == PackageManager.PERMISSION_GRANTED){
                        chamarTelefono();
                    }
                    else{
                        U3_A_A16AlejandroBP.this.requestPermissions( new String[]{Manifest.permission.CALL_PHONE},COD_TLF);
                    }

                }
                else{
                    int permiso = checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE);
                    if (permiso == PackageManager.PERMISSION_GRANTED){
                        chamarTelefono();
                    }
                }

            }
        });
        return venta.create();
    }
    private void chamarTelefono(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        if(!b.containsKey("telefono") || b.getString("telefono").isEmpty()){
            Toast.makeText(this,"Non se introduciu ningún número de teléfono",Toast.LENGTH_LONG).show();
        }else {
            callIntent.setData(Uri.parse("tel:" +b.getString("telefono")));
            startActivity(callIntent);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle estado) {
        estado.putString("cadea",b.getString("cadea"));
        estado.putString("telefono",b.getString("telefono"));
        super.onSaveInstanceState(estado);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        b = savedInstanceState;
    }
}
