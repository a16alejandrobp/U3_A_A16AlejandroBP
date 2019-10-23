package com.example.u3_a_a16alejandrobp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Secundaria extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secundaria);
    }
    public void onPecharClick(View view) {
        // Forma_Pechar_Activity = " Premendo botón Pechar";
        EditText etsec1 = (EditText) findViewById(R.id.etsec1);
        EditText etsec2 = (EditText) findViewById(R.id.etsec2);

        Intent datos_volta = new Intent();
        datos_volta.putExtra("cadea", etsec1.getText().toString());
        datos_volta.putExtra("telefono", etsec2.getText().toString());
        setResult(RESULT_OK, datos_volta);
        finish();

    }

}
