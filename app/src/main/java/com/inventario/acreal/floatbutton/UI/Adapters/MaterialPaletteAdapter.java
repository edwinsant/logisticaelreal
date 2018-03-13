package com.inventario.acreal.floatbutton.UI.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inventario.acreal.floatbutton.Events.RecyclerViewOnItemClickListener;
import com.inventario.acreal.floatbutton.Models.Move;
import com.inventario.acreal.floatbutton.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielme.com
 */
public class MaterialPaletteAdapter extends RecyclerView.Adapter<MaterialPaletteAdapter.PaletteViewHolder> {
    private List<Move> data, data2;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public MaterialPaletteAdapter(@NonNull List<Move> data,
                                  @NonNull RecyclerViewOnItemClickListener
                                      recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }



    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_relocation_item, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        Move guardar;
        guardar = this.data.get(position);
        holder.getPosicion().setText(guardar.getUbicacionO());
        holder.getArticulo().setText(guardar.getArticulo() + " -");
        holder.getDescripcion().setText(guardar.getDescripcion());
        holder.getTarea().setText("# " + guardar.getNo_Tarea());
        holder.getCif().setText(guardar.getCod_cip());
       /* GradientDrawable gradientDrawable = (GradientDrawable) holder.getCircleView().getBackground();
        int colorId = android.graphics.Color.parseColor(guardar.getColor());
        gradientDrawable.setColor(colorId);*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void setFilter(List<Move> countryModels){
        data = new ArrayList<>();
        data.addAll(countryModels);
        notifyDataSetChanged();
    }
    class PaletteViewHolder extends RecyclerView.ViewHolder implements View
        .OnClickListener {
       // private View circleView;
        private TextView Articulo, Descripcion,tarea,cip,posicion;


        public PaletteViewHolder(View itemView) {
            super(itemView);
            //circleView = itemView.findViewById(R.id.circleView);
            Articulo = (TextView) itemView.findViewById(R.id.tv_id_product);
            Descripcion = (TextView) itemView.findViewById(R.id.tv_name_product);
            tarea = (TextView)itemView.findViewById(R.id.tv_task_number);
            cip = (TextView) itemView.findViewById(R.id.pallet_id);
            posicion = (TextView) itemView.findViewById(R.id.tv_name_location);


            itemView.setOnClickListener(this);
        }

        public TextView getPosicion() {
            return posicion;
        }

        public TextView getArticulo() {
            return Articulo;
        }

        public TextView getDescripcion() {
            return Descripcion;
        }

        public TextView getTarea() {
            return tarea;
        }
        public TextView getCif() {
            return cip;
        }
        /*  public View getCircleView() {
            return circleView;
        }*/

        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }


    }
}

