package com.santos.tiago.coord;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculatorCoordinate extends AppCompatActivity {

    private EditText edtFuros;
    private EditText edtCirferencia;
    private EditText edtAnguloPartida;
    private ListView lstResultado;
    private Button btnCalcular;
    private Button btnLimpar;
    private double grau = 0.0;
    private double grauTotal = 0.0;
    private int furos = 0;
    private double coordX = 0.0;
    private double coordY = 0.0;
    private double circunferencia = 0.0;
    private double raio = 0.0;
    private double anguloPartida = 0.0;
    private List<String> list = new ArrayList<String>();
    private DecimalFormat dcf = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtCirferencia = (EditText) findViewById(R.id.edtCircunferencia);
        edtFuros = (EditText) findViewById(R.id.edtFuros);
        edtAnguloPartida = (EditText) findViewById(R.id.edtAnguloPartida);
        lstResultado = (ListView) findViewById(R.id.lstResultado);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtCirferencia.getText())) {
                    if (!TextUtils.isEmpty(edtFuros.getText())) {
                        if(!TextUtils.isEmpty(edtAnguloPartida.getText())) {
                            furos = Integer.parseInt(edtFuros.getText().toString());
                            circunferencia = Double.parseDouble(edtCirferencia.getText().toString());
                            anguloPartida = Double.parseDouble(edtAnguloPartida.getText().toString());
                            raio = circunferencia / 2;
                            grau = GetGrau(furos);
                            if(anguloPartida != 0){
                                grauTotal = anguloPartida;
                            }else{
                                grauTotal = grau;
                            }
                            for (int i = 1; i <= furos; i++) {
                                coordX = GetCos(grauTotal) * raio;
                                coordY = GetSen(grauTotal) * raio;
                                list.add(i + ": X" + dcf.format(coordX) + " Y" + dcf.format(coordY) + " Ângulo " +
                                        dcf.format(grauTotal));
                                grauTotal += grau;
                                if(grauTotal > 360)
                                    grauTotal -= 360;
                            }
                            ArrayAdapter<String> adp = new ArrayAdapter<String>(CalculatorCoordinate.this,
                                    android.R.layout.simple_list_item_1, list);
                            lstResultado.setAdapter(adp);
                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(btnCalcular.getWindowToken(), 0);
                        }else{
                            edtAnguloPartida.setError("Coloque o ângulo do primeiro ponto!!!");
                        }
                    } else {
                        edtFuros.setError("Coloque a quantidade de furos!!!");
                    }
                } else {
                    edtCirferencia.setError("Coloque o diâmetro da circunferência!!!");
                }
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtCirferencia.setText("");
                edtFuros.setText("");
                edtAnguloPartida.setText("0");
                lstResultado.setAdapter(null);
                list.clear();
                edtCirferencia.requestFocus();
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(edtCirferencia, 0);
            }
        });
    }

    private double GetSen(double grau) {
        return Math.sin(grau * Math.PI / 180);
    }

    private double GetCos(double grau) {
        return Math.cos(grau * Math.PI / 180);
    }

    private double GetGrau(int furos) {
        return 360.0 / (double) furos;
    }


}
