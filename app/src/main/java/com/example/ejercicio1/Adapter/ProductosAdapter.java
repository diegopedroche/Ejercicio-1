package com.example.ejercicio1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio1.MainActivity;
import com.example.ejercicio1.Modelos.Producto;
import com.example.ejercicio1.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoVH> {

    private List<Producto> objects;
    private int resource;
    private Context context;
    private NumberFormat nf;
    private MainActivity mainActivity;

    public ProductosAdapter(List<Producto> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
        this.nf = NumberFormat.getCurrencyInstance();
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productoCardView = LayoutInflater.from(context).inflate(resource,null);
        productoCardView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ProductoVH(productoCardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {
        Producto p = objects.get(position);
        holder.lbNombre.setText(p.getNombre());
        holder.lbCantidad.setText(String.valueOf(p.getCantidad()));
        holder.lbPrecio.setText(nf.format(p.getPrecio()));

        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmaDelete(p, holder.getAdapterPosition()).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProducto(p,holder.getAdapterPosition()).show();
            }
        });


    }

    private AlertDialog updateProducto(Producto p, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(p.getNombre());
        builder.setCancelable(false);
        //CUERPO DE ALERT
        View cuerpoAlert = LayoutInflater.from(context).inflate(R.layout.activity_add_producto,null);
        EditText txtNombre = cuerpoAlert.findViewById(R.id.txtNombreProductoAdd);
        txtNombre.setVisibility(View.GONE);
        Button btnCrear = cuerpoAlert.findViewById(R.id.btnCrearProductoAdd);
        btnCrear.setVisibility(View.GONE);
        EditText txtCantidad = cuerpoAlert.findViewById(R.id.txtCantidadProductoAdd);
        txtCantidad.setText(String.valueOf(p.getCantidad()));
        EditText txtPrecio = cuerpoAlert.findViewById(R.id.txtPrecioProductoAdd);
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        builder.setView(cuerpoAlert);
        //FIN DEL CUEERPO
        builder.setNegativeButton("CANCELAR",null);
        builder.setPositiveButton("MODIFICAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtCantidad.getText().toString().isEmpty() && !txtPrecio.getText().toString().isEmpty()){
                    p.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
                    p.setPrecio(Float.parseFloat(txtPrecio.getText().toString()));
                    notifyItemChanged(position);
                    mainActivity.calculaValoresFinales();
                }
            }
        });
        return builder.create();
    }

    private AlertDialog confirmaDelete(Producto p, int posicion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("SEGURO?");
        builder.setCancelable(false);
        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                objects.remove(p);
                notifyItemRemoved(posicion);
                mainActivity.calculaValoresFinales();
            }
        });
        return builder.create();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    public class ProductoVH extends RecyclerView.ViewHolder{
        TextView lbNombre;
        TextView lbCantidad;
        ImageView btnBorrar;
        TextView lbPrecio;
        public ProductoVH(@NonNull View itemView){
            super(itemView);
            lbNombre = itemView.findViewById(R.id.lbNombreProductoCard);
            lbPrecio = itemView.findViewById(R.id.lbPrecioProductoCard);
            lbCantidad = itemView.findViewById(R.id.lbCantidadProductoCard);
            btnBorrar = itemView.findViewById(R.id.btnEliminarProductoCard);
        }
    }
}
