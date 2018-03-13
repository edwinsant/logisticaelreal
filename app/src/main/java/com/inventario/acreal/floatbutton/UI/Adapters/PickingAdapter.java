package com.inventario.acreal.floatbutton.UI.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inventario.acreal.floatbutton.Models.Picking;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.Events.RecyclerViewOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielme.com
 */
public class PickingAdapter extends RecyclerView.Adapter<PickingAdapter.PaletteViewHolder> {
    private List<Picking> data, data2;
    int valor ;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public PickingAdapter(@NonNull List<Picking> data,
                          @NonNull RecyclerViewOnItemClickListener
                                      recyclerViewOnItemClickListener) {
        this.data = new ArrayList<>();
        this.data = data;
       //valor = this.data.size();
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }



    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picking, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        Picking guardar;
        guardar = this.data.get(position);
        holder.gettxtcarga().setText(String.valueOf(guardar.getNCARGA()));
        holder.gettxtNombreCho().setText(guardar.getTRANSPORTISTA());
        holder.gettxtBodega().setText(guardar.getBODEGA());
        holder.gettxtFecha().setText(guardar.getFECHA());
       /* GradientDrawable gradientDrawable = (GradientDrawable) holder.getCircleView().getBackground();
        int colorId = android.graphics.Color.parseColor(guardar.getColor());
        gradientDrawable.setColor(colorId);*/
    }

    @Override
    public int getItemCount() {
        int valor = this.data.size();
        return valor;
    }

    public void setFilter(List<Picking> countryModels){
        data = new ArrayList<>();
        data.addAll(countryModels);
        notifyDataSetChanged();
    }
    class PaletteViewHolder extends RecyclerView.ViewHolder implements View
        .OnClickListener {
       // private View circleView;
        private TextView txtcarga;
        private TextView txtNombreCho, txtBodega,txtFecha;


        public PaletteViewHolder(View itemView) {
            super(itemView);
            //circleView = itemView.findViewById(R.id.circleView);
            txtcarga = (TextView) itemView.findViewById(R.id.tv_carry);
            txtNombreCho = (TextView) itemView.findViewById(R.id.tv_name);
            txtBodega = (TextView) itemView.findViewById(R.id.tv_warehouse);
            txtFecha = (TextView)itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(this);
        }

        public TextView gettxtcarga() {
            return txtcarga;
        }

        public TextView gettxtNombreCho() {
            return txtNombreCho;
        }

        public TextView gettxtBodega() {
            return txtBodega;
        }

        public TextView gettxtFecha() {
            return txtFecha;
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

