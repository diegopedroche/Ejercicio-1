package com.example.ejercicio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ejercicio1.Modelos.Producto;
import com.example.ejercicio1.databinding.ActivityAddProductoBinding;

public class AddProductoActivity extends AppCompatActivity {

    private ActivityAddProductoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCrearProductoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = binding.txtNombreProductoAdd.getText().toString();
                String cantidadS = binding.txtCantidadProductoAdd.getText().toString();
                String precioS = binding.txtPrecioProductoAdd.getText().toString();

                if (!nombre.isEmpty() && !cantidadS.isEmpty() && !precioS.isEmpty()){
                    Producto p = new Producto(nombre,Float.parseFloat(precioS),Integer.parseInt(cantidadS));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PROD",p);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(AddProductoActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}