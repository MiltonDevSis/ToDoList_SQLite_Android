package br.com.milton.todolist_sqlite_android.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.milton.todolist_sqlite_android.R;
import br.com.milton.todolist_sqlite_android.adapter.TarefaAdapter;
import br.com.milton.todolist_sqlite_android.helper.DbHelper;
import br.com.milton.todolist_sqlite_android.helper.RecyclerItemClickListener;
import br.com.milton.todolist_sqlite_android.helper.TarefaDAO;
import br.com.milton.todolist_sqlite_android.model.Tarefa;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);

        // adicionar evento de clique
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // recuperar tarefa para edição
                Tarefa tarefaSelecionada = listaTarefas.get( position );

                Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                startActivity( intent );
            }

            @Override
            public void onLongItemClick(View view, int position) {

                // recupera tarefa para deletar
                tarefaSelecionada = listaTarefas.get( position );

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                // configura titulo em mensagem
                dialog.setTitle("Confimar exclusão");
                dialog.setMessage("Deseja excluir a tarefa?\nTarefa: " + tarefaSelecionada.getNomeTarefa() + " ?");
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                        if( tarefaDAO.deletar( tarefaSelecionada ) ){

                            carregarListaDeTarefas();
                            Toast.makeText(getApplicationContext(), "Sucesso ao excluir tarefa", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao excluir tarefa", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                dialog.setNegativeButton("Não", null);

                //exibir dialog
                dialog.create();
                dialog.show();
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



            }
        }) {
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity( intent );
            }
        });
    }

    public void carregarListaDeTarefas(){
        // listar tarefas
        TarefaDAO tarefaDAO = new TarefaDAO( getApplicationContext() );
        listaTarefas = tarefaDAO.listar();

        // configurar adapter
        tarefaAdapter = new TarefaAdapter( listaTarefas );

        // configurar recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize( true );
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter( tarefaAdapter );
    }

    @Override
    protected void onStart() {
        carregarListaDeTarefas();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}