package br.com.milton.todolist_sqlite_android.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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
        return false;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        return false;
    }

    @Override
    public List<Tarefa> listar() {
        return null;
    }
}
