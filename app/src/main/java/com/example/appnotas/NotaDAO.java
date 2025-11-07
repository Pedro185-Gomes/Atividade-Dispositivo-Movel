package com.example.appnotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    private DBHelper dbHelper;

    public NotaDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void inserir(Nota nota) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", nota.getTitulo());
        values.put("conteudo", nota.getConteudo());
        db.insert("notas", null, values);
        db.close();
    }

    public void atualizar(Nota nota) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", nota.getTitulo());
        values.put("conteudo", nota.getConteudo());
        db.update("notas", values, "_id=?", new String[]{String.valueOf(nota.getId())});
        db.close();
    }

    public void excluir(Long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("notas", "_id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Nota> listar() {
        List<Nota> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notas ORDER BY _id DESC", null);
        while (cursor.moveToNext()) {
            Nota nota = new Nota();
            nota.setId(cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
            nota.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            nota.setConteudo(cursor.getString(cursor.getColumnIndexOrThrow("conteudo")));
            lista.add(nota);
        }
        cursor.close();
        db.close();
        return lista;
    }
}
