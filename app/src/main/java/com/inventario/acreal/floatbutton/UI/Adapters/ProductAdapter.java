package com.inventario.acreal.floatbutton.UI.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.Events.RecyclerViewOnItemClickListener;
import com.inventario.acreal.floatbutton.Models.Picking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielme.com
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.PaletteViewHolder> {
    private List<Picking> data, data2;
    int valor ;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public ProductAdapter(@NonNull List<Picking> data,
                          @NonNull RecyclerViewOnItemClickListener
                                      recyclerViewOnItemClickListener) {
        this.data = new ArrayList<>();
        this.data = data;

        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }





    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.listarticulo, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        Picking guardar;
        guardar = this.data.get(position);
        holder.gettxtlote().setText(guardar.getLOTE());
        holder.gettxtUbicacion().setText(guardar.getUBICACION());
        holder.gettxtArticulo().setText(guardar.getARTICULO());
        holder.gettxtdescripcion().setText(guardar.getDESCRIPCION());
        holder.getDispatch().setText(guardar.getNCARGA());
       /* GradientDrawable gradientDrawable = (GradientDrawable) holder.getCircleView().getBackground();
        int colorId = android.graphics.Color.parseColor(guardar.getColor());
        gradientDrawable.setColor(colorId);*/
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public void setFilter(List<Picking> countryModels){
        data = new ArrayList<>();
        data.addAll(countryModels);
        notifyDataSetChanged();
    }
    class PaletteViewHolder extends RecyclerView.ViewHolder implements View
        .OnClickListener {
       // private View circleView;
        private TextView txtlote;
        private TextView txtUbicacion, txtArticulo,txtdescripcion, tvDispatch;


        public PaletteViewHolder(View itemView) {
            super(itemView);
            //circleView = itemView.findViewById(R.id.circleView);
            txtlote = (TextView) itemView.findViewById(R.id.tv_lote_name);
            txtUbicacion = (TextView) itemView.findViewById(R.id.tv_name_location);
            txtArticulo = (TextView) itemView.findViewById(R.id.tv_id_product);
            txtdescripcion = (TextView)itemView.findViewById(R.id.tv_name);
            tvDispatch = (TextView)itemView.findViewById(R.id.tv_dispatch_name);

            itemView.setOnClickListener(this);
        }

        public TextView gettxtlote() {
            return txtlote;
        }

        public TextView gettxtUbicacion() {
            return txtUbicacion;
        }

        public TextView gettxtArticulo() {
            return txtArticulo;
        }

        public TextView gettxtdescripcion() {
            return txtdescripcion;
        }

        public TextView getDispatch() {
            return tvDispatch;
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

