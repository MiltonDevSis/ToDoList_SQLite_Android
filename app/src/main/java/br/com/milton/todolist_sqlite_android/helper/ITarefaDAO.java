package br.com.milton.todolist_sqlite_android.helper;

import java.util.List;
import br.com.milton.todolist_sqlite_android.model.Tarefa;

public interface ITarefaDAO {

    public boolean salvar(Tarefa tarefa);
    public boolean atualizar(Tarefa tarefa);
    public  boolean deletar(Tarefa tarefa);
    public List<Tarefa> listar();
}
