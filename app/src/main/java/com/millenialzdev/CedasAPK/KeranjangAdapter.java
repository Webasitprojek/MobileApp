package com.millenialzdev.CedasAPK;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {
    private List<KeranjangItem> keranjangItems;
    private EditText totalFeeTxt, namaBarangTxt, hargaBarangTxt, jumlahBarangTxt, idkeranjang;
    private RecyclerView recyclerView;
    private OnItemClickListener mListener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(KeranjangItem item);
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public KeranjangAdapter(List<KeranjangItem> keranjangItems, EditText totalFeeTxt, RecyclerView recyclerView) {
        this.keranjangItems = keranjangItems;
        this.totalFeeTxt = totalFeeTxt;
        this.recyclerView = recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText namaBarangTxt, hargaBarangTxt, jumlahBarangTxt, idkeranjang;
        ImageView minusCartBtn, plusCartBtn;
        TextView numberItemsTxt;

        public ViewHolder(View view) {
            super(view);
            namaBarangTxt = view.findViewById(R.id.Namakeranjang);
            hargaBarangTxt = view.findViewById(R.id.Hargakeranjang);
            jumlahBarangTxt = view.findViewById(R.id.numberItemsTxt);
            minusCartBtn = view.findViewById(R.id.minusCartBtn);
            plusCartBtn = view.findViewById(R.id.plusCartBtn);
            numberItemsTxt = view.findViewById(R.id.numberItemsTxt);
            idkeranjang = view.findViewById(R.id.Idkeranjang);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemkeranjang_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KeranjangItem keranjangItem = keranjangItems.get(position);

        holder.namaBarangTxt.setText(keranjangItem.getNama());
        holder.idkeranjang.setText(keranjangItem.getId());

        int harga = Integer.parseInt(keranjangItem.getHarga());
        int jumlah = Integer.parseInt(keranjangItem.getJumlah());
        int totalHarga = harga * jumlah;

        holder.hargaBarangTxt.setText(String.valueOf(totalHarga));
        holder.jumlahBarangTxt.setText(keranjangItem.getJumlah());

        updateTotalFee2();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousSelectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedPosition);

                // Rest of your click logic
            }
        });



        holder.plusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(holder.numberItemsTxt.getText().toString());
                currentQuantity++;
                holder.numberItemsTxt.setText(String.valueOf(currentQuantity));

                int totalFee = currentQuantity * harga;
                holder.hargaBarangTxt.setText(String.valueOf(totalFee));

                updateTotalFee();
                updateTotalFee2();
            }
        });

        holder.minusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(holder.numberItemsTxt.getText().toString());
                if (currentQuantity > 0) {
                    currentQuantity--;
                    holder.numberItemsTxt.setText(String.valueOf(currentQuantity));

                    int totalFee = currentQuantity * harga;
                    holder.hargaBarangTxt.setText(String.valueOf(totalFee));

                    updateTotalFee();
                    updateTotalFee2();
                }
            }
        });

        // Menambahkan onClickListener ke itemView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(keranjangItem);
                }
            }
        });
    }

    private void updateTotalFee() {
        int totalFee = 0;

        for (KeranjangItem item : keranjangItems) {
            int harga = Integer.parseInt(item.getHarga());
            int jumlah = Integer.parseInt(item.getJumlah());
            totalFee += harga * jumlah;
        }
    }

    private void updateTotalFee2() {
        int totalFee2 = 0;

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View itemView = recyclerView.getChildAt(i);
            TextView hargaBarangTxt = itemView.findViewById(R.id.Hargakeranjang);

            int harga = Integer.parseInt(hargaBarangTxt.getText().toString());
            totalFee2 += harga;
        }

        totalFeeTxt.setText(String.valueOf(totalFee2));
    }

    public void setItems(List<KeranjangItem> items) {
        this.keranjangItems = items;
        notifyDataSetChanged();
    }

    public void onItemClick(int position) {
        selectedPosition = position;
        notifyDataSetChanged(); // Ensure the adapter refreshes the views
    }

    // Example method in KeranjangAdapter to get selected position


    @Override
    public int getItemCount() {
        return keranjangItems.size();
    }
}
