package br.com.milton.todolist_sqlite_android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import br.com.milton.todolist_sqlite_android.R;
import br.com.milton.todolist_sqlite_android.helper.TarefaDAO;
import br.com.milton.todolist_sqlite_android.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText txtTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        txtTarefa = findViewById(R.id.txtTarefas);

        // recupera tarefa, caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        // configurar tarefa na caixa de texto
        if(tarefaAtual != null){
            txtTarefa.setText( tarefaAtual.getNomeTarefa() );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ){
            case R.id.itemSalvar:
                // executa ação para o item salvar
                TarefaDAO tarefaDAO = new TarefaDAO( getApplicationContext() );

                if ( tarefaAtual != null ){// edição

                    String nomeTarefa = txtTarefa.getText().toString();
                    if(!nomeTarefa.isEmpty()){

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa( nomeTarefa );
                        tarefa.setId( tarefaAtual.getId() );

                        // atualizar no banco de dados
                        if( tarefaDAO.atualizar( tarefa ) ){
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao atualizar a tarefa", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao atualizar a tarefa", Toast.LENGTH_LONG).show();
                        }
                    }
                }else{// salvar

                    String nomeTarefa = txtTarefa.getText().toString();
                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa( nomeTarefa );

                        if ( tarefaDAO.salvar( tarefa ) ){
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao salvar a tarefa", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao salvar a tarefa", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}