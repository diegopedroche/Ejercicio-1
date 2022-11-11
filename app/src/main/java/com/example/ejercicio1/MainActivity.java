package com.example.ejercicio1;

import android.content.Intent;
import android.os.Bundle;

import com.example.ejercicio1.Adapter.ProductosAdapter;
import com.example.ejercicio1.Modelos.Producto;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.ejercicio1.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Producto> productosList;

    private ActivityResultLauncher<Intent> launcherAddProducto;

    //Recycler
    private ProductosAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private NumberFormat nf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        productosList = new ArrayList<>();
        nf = NumberFormat.getCurrencyInstance();

        calculaValoresFinales();
        adapter = new ProductosAdapter(productosList,R.layout.producto_view_holder,this);
        layoutManager = new GridLayoutManager(this, 1);
        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        inicializaLaunchers();


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherAddProducto.launch(new Intent(MainActivity.this, AddProductoActivity.class));
            }
        });
    }

    private void inicializaLaunchers() {
        launcherAddProducto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    if (result.getData() != null && result.getData().getExtras() != null){
                        Producto p = (Producto) result.getData().getExtras().getSerializable("PROD");
                        productosList.add(p);
                        adapter.notifyItemInserted(productosList.size()-1);
                        calculaValoresFinales();
                    }
                }
            }
        });
    }

    public void calculaValoresFinales(){
        int cantidadTotal = 0;
        float imoorteTotal = 0f;

        for (Producto p:productosList) {
            cantidadTotal += p.getCantidad();
            imoorteTotal += p.getCantidad() * p.getPrecio();
        }

        binding.contentMain.lbCantidadTotalMain.setText(String.valueOf(cantidadTotal));
        binding.contentMain.lbPrecioTotalMain.setText(nf.format(imoorteTotal));
    }
}