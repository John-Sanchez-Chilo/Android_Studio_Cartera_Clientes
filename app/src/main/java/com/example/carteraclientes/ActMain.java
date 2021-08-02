package com.example.carteraclientes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.carteraclientes.BaseDatos.DatosOpenHelper;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.carteraclientes.databinding.ActivityActMainBinding;

import java.util.ArrayList;

public class ActMain extends AppCompatActivity {
    //vinieron por defecto
    private AppBarConfiguration appBarConfiguration;
    private ActivityActMainBinding binding;


    //nuevas declaraciones
    private ListView  lstDatos;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> clientes;

    private SQLiteDatabase conexion;
    private DatosOpenHelper datosOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding = ActivityActMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_act_main);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ActMain.this, ActNuevoCliente.class);
                startActivity(i);
            }
        });

        actualizar();
    }

    private void actualizar(){
        lstDatos=(ListView)findViewById(R.id.lstDatos);
        clientes=new ArrayList<String>();

        try{
            datosOpenHelper=new DatosOpenHelper(this);
            conexion=datosOpenHelper.getWritableDatabase();
            StringBuilder sql=new StringBuilder();
            sql.append("SELECT * FROM CLIENTE");
            String sNombre;
            String sTelefono;
            Cursor resultado=conexion.rawQuery(sql.toString(),null);

            if(resultado.getCount()>0){
                resultado.moveToFirst();
                do{
                    sNombre=resultado.getString(resultado.getColumnIndex("NOMBRE"));
                    sTelefono=resultado.getString(resultado.getColumnIndex("TELEFONO"));
                    clientes.add(sNombre+": "+sTelefono);
                }while(resultado.moveToNext());
            }

            adaptador=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,clientes);
            lstDatos.setAdapter(adaptador);


        }catch (Exception ex){
            AlertDialog.Builder dlg=  new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        actualizar();
    }



    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_act_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

     */
}