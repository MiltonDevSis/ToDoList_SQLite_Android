package br.com.milton.todolist_sqlite_android.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import br.com.milton.todolist_sqlite_android.model.Tarefa;

public class TarefaDAO implements ITarefaDAO{

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    public TarefaDAO(Context context){
        DbHelper dbHelper = new DbHelper( context );
        escrever = dbHelper.getWritableDatabase();
        ler = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        try {
            escrever.insert( DbHelper.TABELA_TAREFAS, null, cv);
            Log.e("INFO", "Erro ao salvar tarefa");
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar tarefa" + e.getMessage());
           return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try {
            String[] args = {tarefa.getId().toString()};
            escrever.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.i("INFO", "Erro ao atualizar a tarefa");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar a tarefa" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = {tarefa.getId().toString()};
            escrever.delete(DbHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO", "Erro ao deletar a tarefa");
        }catch (Exception e){
            Log.e("INFO", "Erro ao deletar a tarefa" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> listaTarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;";

        Cursor c = ler.rawQuery(sql, null);

        while ( c.moveToNext() ){
            Tarefa tarefa = new Tarefa();

            Long id = c.getLong( c.getColumnIndex("id") );
            String nomeTarefa = c.getString( c.getColumnIndex("nome") );

            tarefa.setId( id );
            tarefa.setNomeTarefa( nomeTarefa );

            listaTarefas.add( tarefa );
        }

        return listaTarefas;
    }
}
