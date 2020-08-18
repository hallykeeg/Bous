package com.example.bous;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentAccueil.onButtonAccueilClicked {

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolBarCorrect);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener( this);

//        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
         if(savedInstanceState==null) {
             getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAccueil()).commit();

             navigationView.setCheckedItem(R.id.nav_home);
         }


    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAccueil()).commit();
                toolbar.setTitle("BOUS");
                break;
            case R.id.nav_creance:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCreances()).commit();
                toolbar.setTitle("CREANCES");
                break;
            case R.id.nav_depense:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDepense()).commit();
                toolbar.setTitle("DEPENSES");
                break;
            case R.id.nav_revenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRevenus()).commit();
                toolbar.setTitle("REVENUS");
                break;
            case R.id.nav_dettes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDettes()).commit();
                toolbar.setTitle("DETTES");
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentProfile()).commit();
                toolbar.setTitle("PROFIL");
                break;
            case R.id.nav_securite:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentSecurity()).commit();
                toolbar.setTitle("SECURITE");
                break;
            case R.id.nav_epargne:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentEpargne()).commit();
                toolbar.setTitle("EPARGNE");
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void depensesButtonAction() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDepense()).commit();
        toolbar.setTitle("DEPENSES");
        navigationView.setCheckedItem(R.id.nav_depense);
    }

    @Override
    public void revenusButtonAction() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRevenus()).commit();
        toolbar.setTitle("REVENUS");
        navigationView.setCheckedItem(R.id.nav_revenu);
    }

    @Override
    public void epargneButtonAction() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentEpargne()).commit();
        toolbar.setTitle("EPARGNE");
        navigationView.setCheckedItem(R.id.nav_epargne);
    }

    @Override
    public void dettesButtonAction() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDettes()).commit();
        toolbar.setTitle("DETTES");
        navigationView.setCheckedItem(R.id.nav_dettes);
    }

    @Override
    public void creancesButtonAction() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCreances()).commit();
        toolbar.setTitle("CREANCES");
        navigationView.setCheckedItem(R.id.nav_creance);
    }

    @Override
    public void statsButtonAction() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentStats()).commit();
        toolbar.setTitle("Statistiques");
        navigationView.setCheckedItem(R.id.nav_home);
    }
}