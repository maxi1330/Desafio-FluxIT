package com.maxignov.desafiofluxit.Views.Activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.maxignov.desafiofluxit.Utils.Constants;
import com.maxignov.desafiofluxit.Controller.ControllerPets;
import com.maxignov.desafiofluxit.Utils.ListenerCustom;
import com.maxignov.desafiofluxit.Model.PojoModel.Pet;
import com.maxignov.desafiofluxit.R;
import com.maxignov.desafiofluxit.Views.Fragments.FragmentBusiness;
import com.maxignov.desafiofluxit.Views.Fragments.FragmentPetDetail;


public class DetailActivity extends AppCompatActivity {
    private ControllerPets controllerPets;
    private View parent_view;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentPetDetail fragmentPetDetail;
    private FragmentBusiness fragmentBusiness;
    private String id;
    private ProgressBar progressBarDetailActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
        setToolbar();

        controllerPets = new ControllerPets(this);
        fragmentBusiness = new FragmentBusiness();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            id = bundle.getString(Constants.KEY_PET_ID);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(listenerBottomNavigation);

        requestFindByID(id);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Llamo al controlador, solicitando la mascota, creo el fragmentPetDetail y lo muestro.
    private void requestFindByID(String id){
        progressBarDetailActivity.setVisibility(View.VISIBLE);
        ListenerCustom<Pet> listenerView = new ListenerCustom<Pet>() {
            @Override
            public void finish(Pet response) {
                progressBarDetailActivity.setVisibility(View.GONE);
                if (response != null) {
                        Snackbar.make(parent_view, getResources().getString(R.string.messageSuccess), Snackbar.LENGTH_SHORT).show();
                        fragmentPetDetail = FragmentPetDetail.buildFragmentPetDetail(response);
                        setFragment(fragmentPetDetail);
                } else {
                    Snackbar.make(parent_view, getResources().getString(R.string.messageTryAgain), Snackbar.LENGTH_LONG).show();
                }
            }
        };
        controllerPets.requestPetFindById(listenerView,id);
    }

    //Setea el fragmento que llega por parametro al contenedor de fragmentos
    private void setFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    //Setea la toolbar con los valores por defecto
    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setDataInToolbar(R.drawable.ic_pets_black_24dp,(getResources().getString(R.string.titleDetail)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //Setea nuevos datos en la toolbar (logo y titulo)
    private void setDataInToolbar(int logo, String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(logo);
        toolbar.setTitle(title);
    }

    //Inicia las vistas
    private void initViews(){
        toolbar = findViewById(R.id.my_toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        parent_view = findViewById(android.R.id.content);
        progressBarDetailActivity = findViewById(R.id.progressBarDetailActivity);
    }

    //Listener del BottomNavigationView
    private BottomNavigationView.OnNavigationItemSelectedListener listenerBottomNavigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigationIconPetDetail:
                    setDataInToolbar(R.drawable.ic_pets_black_24dp,(getResources().getString(R.string.titleDetail)));
                    setFragment(fragmentPetDetail);
                    break;

                case R.id.navigationIconBusiness:
                    setDataInToolbar(R.drawable.ic_store_mall_directory_black_24dp,(getResources().getString(R.string.titleBussines)));
                    setFragment(new FragmentBusiness());
                    break;
            }
            return false;
        }
    };
}
