package com.example.appnotas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NotaViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Nota nota);
        void onItemLongClick(Nota nota); // novo método para remover
    }

    private List<Nota> lista;
    private OnItemClickListener listener;

    public NotaAdapter(List<Nota> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_anotacao, parent, false);
        return new NotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        Nota nota = lista.get(position);
        holder.txtTitulo.setText(nota.getTitulo());
        holder.txtConteudo.setText(nota.getConteudo());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(nota));

        // Novo: clique longo para remover
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(nota);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class NotaViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtConteudo;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtConteudo = itemView.findViewById(R.id.txtConteudo);
        }
    }
}
