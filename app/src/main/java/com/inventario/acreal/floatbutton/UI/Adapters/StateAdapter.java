package com.inventario.acreal.floatbutton.UI.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inventario.acreal.floatbutton.Events.RecyclerViewOnItemClickListener;
import com.inventario.acreal.floatbutton.Models.Picking;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.Utils.ExpandAndCollapseViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielme.com
 */
public class StateAdapter extends RecyclerView.Adapter<StateAdapter.PaletteViewHolder> {
    private List<Picking> data, data2;
    int valor ;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public StateAdapter(@NonNull List<Picking> data,
                        @NonNull RecyclerViewOnItemClickListener
                                      recyclerViewOnItemClickListener) {
        this.data = new ArrayList<>();
        this.data = data;
       valor = this.data.size();
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }



    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.listestado, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        Picking guardar;
        guardar = this.data.get(position);
        holder.gettxtlote().setText(guardar.getLOTE());
        holder.gettxtexistencia().setText(guardar.getExistencia());
        holder.gettxtdisponible().setText(guardar.getDisponibles());
        holder.gettxtfecha().setText(guardar.getFECHA());
        holder.gettxtUbicacion().setText(guardar.getUBICACION());
        holder.gettxtsecuencia().setText(guardar.getSECUENCIA());
        holder.gettxtcom_firme_ord_vta().setText(guardar.getCOMP_FIRME_ORD_VTA());
        holder.gettxtcomp_flex_ov_ot().setText(guardar.getCOMP_FLEX_OV_OT());
        holder.gettxtcom_firme_ot().setText(guardar.getCOMP_FIRME_OT());
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
        private TextView txtexistencia, txtdisponible,txtfecha, txtUbicacion, txtsecuencia, txtcom_firme_ord_vta,txtcomp_flex_ov_ot, txtcom_firme_ot;
        public ViewGroup linearLayoutDetails;
        public ImageView imageViewExpand;
        public LinearLayout toggleDetails;
        private static final int DURATION = 250;

        public PaletteViewHolder(View itemView) {
            super(itemView);
            //circleView = itemView.findViewById(R.id.circleView);
            txtlote = (TextView) itemView.findViewById(R.id.txtlote);
            txtUbicacion = (TextView) itemView.findViewById(R.id.txtUbicacion);
            txtexistencia = (TextView) itemView.findViewById(R.id.txtexistencia);
            txtdisponible = (TextView) itemView.findViewById(R.id.txtdisponible);
            txtfecha = (TextView)itemView.findViewById(R.id.txtfecha);
            txtsecuencia = (TextView) itemView.findViewById(R.id.txtsecuencia);
            txtcom_firme_ord_vta = (TextView) itemView.findViewById(R.id.txtcom_firme_ord_vta);
            txtcomp_flex_ov_ot = (TextView) itemView.findViewById(R.id.txtcomp_flex_ov_ot);
            txtcom_firme_ot = (TextView) itemView.findViewById(R.id.txtcom_firme_ot);
            linearLayoutDetails = (ViewGroup) itemView.findViewById(R.id.linearLayoutDetails);
            imageViewExpand = (ImageView) itemView.findViewById(R.id.imageViewExpand);
            toggleDetails = (LinearLayout)itemView.findViewById(R.id.toggleDetails);
            toggleDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (linearLayoutDetails.getVisibility() == View.GONE) {
                        ExpandAndCollapseViewUtil.expand(linearLayoutDetails, DURATION);
                        imageViewExpand.setImageResource(R.mipmap.more);
                        rotate(-180.0f);
                    } else {
                        ExpandAndCollapseViewUtil.collapse(linearLayoutDetails, DURATION);
                        imageViewExpand.setImageResource(R.mipmap.less);
                        rotate(180.0f);
                    }
                }
            });

            itemView.setOnClickListener(this);
        }

        public TextView gettxtlote() {
            return txtlote;
        }
        public TextView gettxtUbicacion() {
            return txtUbicacion;
        }

        public TextView gettxtexistencia() {
            return txtexistencia;
        }

        public TextView gettxtdisponible() {
            return txtdisponible;
        }

        public TextView gettxtfecha() {
            return txtfecha;
        }
        public TextView gettxtsecuencia() {
            return txtsecuencia;
        }
        public TextView gettxtcom_firme_ord_vta() {
            return txtcom_firme_ord_vta;
        }
        public TextView gettxtcomp_flex_ov_ot() {
            return txtcomp_flex_ov_ot;
        }
        public TextView gettxtcom_firme_ot() {
            return txtcom_firme_ot;
        }

        /*  public View getCircleView() {
            return circleView;
        }*/

        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
        private void rotate(float angle) {
            Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setFillAfter(true);
            animation.setDuration(DURATION);
            imageViewExpand.startAnimation(animation);
        }


    }
}

