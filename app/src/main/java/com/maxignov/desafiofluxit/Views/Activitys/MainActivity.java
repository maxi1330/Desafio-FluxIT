package com.maxignov.desafiofluxit.Views.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.maxignov.desafiofluxit.Adapters.AdapterPetsList;
import com.maxignov.desafiofluxit.Utils.Constants;
import com.maxignov.desafiofluxit.Controller.ControllerPets;
import com.maxignov.desafiofluxit.Utils.ListenerCustom;
import com.maxignov.desafiofluxit.Model.PojoModel.Pet;
import com.maxignov.desafiofluxit.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterPetsList.PetSelectedInterface {
    private RecyclerView recyclerViewPetsList;
    private AdapterPetsList adapterPetsList;
    private Toolbar toolbar;
    private ControllerPets controllerPets;
    private View parent_view;
    private MenuItem mSearch;
    private SearchView mSearchView;
    private ProgressBar progressBarMainActivity;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        controllerPets = new ControllerPets(this);
        initRecyclerView();

        setToolbar();
        updatePetList();

        swipeRefreshLayout.setOnRefreshListener(refreshListener);
    }

    //Listener del searchView
    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        //Cuando cambia el texto, se lo envio al adapter
        @Override
        public boolean onQueryTextChange(String s) {
            adapterPetsList.filterList(s);
            return true;
        }
    };

    //Inicializa el recyclerView
    private void initRecyclerView(){
        adapterPetsList = new AdapterPetsList(this,this);
        recyclerViewPetsList.setAdapter(adapterPetsList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewPetsList.setLayoutManager(layoutManager);
        recyclerViewPetsList.setHasFixedSize(true);
    }

    //Setea los datos por defecto de la toolbar
    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(getResources().getString(R.string.titleDefaultToolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items,menu);
        mSearch = menu.findItem(R.id.searchView);
        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(onQueryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

    //Listener del swipeRefresh, al accionarlo pido llamo al servicio nuevamente y actualizo la lista
    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            updatePetList();
        }
    };

    //Llamo al controlador, solicitando la lista de mascotas y actualizo la lista
    private void updatePetList() {
        progressBarMainActivity.setVisibility(View.VISIBLE);
        ListenerCustom<List<Pet>> listenerView = new ListenerCustom<List<Pet>>() {
            @Override
            public void finish(List<Pet> response) {
                //Si se esta mostrando la progressBar la oculto
                if(progressBarMainActivity.isShown()){
                    progressBarMainActivity.setVisibility(View.GONE);
                }
                //Si se esta mostrando el swipeResfresh la oculto
                if(swipeRefreshLayout.isShown()){
                    swipeRefreshLayout.setRefreshing(false);
                }

                if (response != null) {
                    if (response.size() <= 0) {
                        Snackbar.make(parent_view, getResources().getString(R.string.messageTryAgain), Snackbar.LENGTH_LONG).show();
                        Log.w("MainActivity","Listado vacio");
                    } else {
                        Snackbar.make(parent_view, getResources().getString(R.string.messageSuccess), Snackbar.LENGTH_SHORT).show();
                        adapterPetsList.updatePetsList(response);
                    }
                } else {
                    Snackbar.make(parent_view, getResources().getString(R.string.messageTryAgain), Snackbar.LENGTH_LONG).show();
                    Log.w("MainActivity","Listado nulo");
                }
            }
        };
        controllerPets.requestPetFindByStatus(listenerView, Constants.STATUS_PET_AVAILABLE);
    }

    //Inicia las vistas
    private void initViews(){
        recyclerViewPetsList = findViewById(R.id.recyclerViewPetsList);
        toolbar = findViewById(R.id.toolbar);
        parent_view = findViewById(android.R.id.content);
        progressBarMainActivity = findViewById(R.id.progressBarMainActivity);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    /*Implementacion de la interfaz del adaptador del recycler.
      Inicia la actividad detalle y envia el id de la mascota seleccionada*/
    @Override
    public void petClick(String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.KEY_PET_ID,id);
        startActivity(intent);
    }
}

