package com.example.appnotas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listNotas;
    private Button btnAdicionar;
    private NotaDAO dao;
    private NotaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNotas = findViewById(R.id.listNotas);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        dao = new NotaDAO(this);
        carregarNotas();

        btnAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotaActivity.class);
            startActivity(intent);
        });
    }

    private void carregarNotas() {
        List<Nota> notas = dao.listar();
        adapter = new NotaAdapter(notas, new NotaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota) {
                Intent intent = new Intent(MainActivity.this, NotaActivity.class);
                intent.putExtra("nota", nota);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(Nota nota) {
                new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("Remover nota")
                        .setMessage("Deseja realmente excluir esta nota?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            dao.excluir(nota.getId());
                            carregarNotas();
                            Toast.makeText(MainActivity.this, "Nota removida com sucesso", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        listNotas.setLayoutManager(new LinearLayoutManager(this));
        listNotas.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarNotas();
    }
}
