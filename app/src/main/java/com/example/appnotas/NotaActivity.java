package com.example.appnotas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NotaActivity extends AppCompatActivity {

    private EditText editTitulo, editConteudo;
    private Button btnSalvar;
    private NotaDAO dao;
    private Nota nota = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);


        editTitulo = findViewById(R.id.editTitulo);
        editConteudo = findViewById(R.id.editConteudo);
        btnSalvar = findViewById(R.id.btnSalvar);

        dao = new NotaDAO(this);

        if (getIntent().hasExtra("nota")) {
            nota = (Nota) getIntent().getSerializableExtra("nota");
            editTitulo.setText(nota.getTitulo());
            editConteudo.setText(nota.getConteudo());
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = editTitulo.getText().toString();
                String conteudo = editConteudo.getText().toString();

                if (nota == null) {
                    Nota nova = new Nota(titulo, conteudo);
                    dao.inserir(nova);
                } else {
                    nota.setTitulo(titulo);
                    nota.setConteudo(conteudo);
                    dao.atualizar(nota);
                }
                finish();
            }
        });
    }
}
