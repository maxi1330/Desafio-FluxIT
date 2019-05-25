package com.maxignov.desafiofluxit.Views.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.maxignov.desafiofluxit.Adapters.AdapterPetsList;
import com.maxignov.desafiofluxit.Constants;
import com.maxignov.desafiofluxit.Controller.ControllerPets;
import com.maxignov.desafiofluxit.ListenerCustom;
import com.maxignov.desafiofluxit.Model.Pet;
import com.maxignov.desafiofluxit.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterPetsList.PetSelectedInterface {
    private RecyclerView recyclerViewPetsList;
    private AdapterPetsList adapterPetsList;
    private Toolbar toolbar;
    private ControllerPets controllerPets;
    private View parent_view;
    private ProgressBar progressBarMainActivity;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        controllerPets = new ControllerPets(this);

        adapterPetsList = new AdapterPetsList(this);
        recyclerViewPetsList.setAdapter(adapterPetsList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewPetsList.setLayoutManager(layoutManager);
        recyclerViewPetsList.setHasFixedSize(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(getResources().getString(R.string.titleDefaultToolbar));

        updatePetList();

        swipeRefreshLayout.setOnRefreshListener(refreshListener);

    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            updatePetList();
        }
    };

    private void updatePetList() {
        progressBarMainActivity.setVisibility(View.VISIBLE);
        ListenerCustom<List<Pet>> listenerView = new ListenerCustom<List<Pet>>() {
            @Override
            public void finish(List<Pet> response) {
                progressBarMainActivity.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                if (response != null) {
                    if (response.size() <= 0) {
                        Snackbar.make(parent_view, getResources().getString(R.string.messageTryAgain), Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(parent_view, getResources().getString(R.string.messageSuccess), Snackbar.LENGTH_SHORT).show();
                        adapterPetsList.updatePetsList(response);
                    }
                } else {
                    Snackbar.make(parent_view, getResources().getString(R.string.messageTryAgain), Snackbar.LENGTH_LONG).show();
                }
            }
        };
        controllerPets.requestPetFindByStatus(listenerView, Constants.STATUS_PET_AVAILABLE);
    }

    private void initViews(){
        recyclerViewPetsList = findViewById(R.id.recyclerViewPetsList);
        toolbar = findViewById(R.id.toolbar);
        parent_view = findViewById(android.R.id.content);
        progressBarMainActivity = findViewById(R.id.progressBarMainActivity);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    public void petClick(String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.KEY_PET_ID,id);
        startActivity(intent);
    }
}
