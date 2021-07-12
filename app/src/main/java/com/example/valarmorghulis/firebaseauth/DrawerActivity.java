package com.example.valarmorghulis.firebaseauth;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;


public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private ImageView imageView;
//    private static int SPLASH_TIME_OUT = 4000;
NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent SplashIntent = new Intent(DrawerActivity.this,Activity_splash_screen.class);
                startActivity(SplashIntent);
                finish();
            }
        },SPLASH_TIME_OUT);*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        imageView = (ImageView) headerLayout.findViewById(R.id.drawer_image);
//        Picasso.with(this)
//                .load(R.drawable.a_l)
//                .fit()
//                .centerInside()
//                .into(imageView);
        Glide.with(this)
                .load(R.drawable.a_l)
                .into(imageView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

//        Toast.makeText(DrawerActivity.this,menuItem.getItemId(),Toast.LENGTH_LONG).show();
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).commit();

                break;
            case R.id.nav_userItems:getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new UserItems()).commit();
                break;
            case R.id.nav_my_profile:getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new ProfileFragment()).commit();
                break;

            case R.id.nav_sell:getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new SellFragment()).commit();
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new AboutFragment()).addToBackStack(null).commit();
                break;

//            case R.id.nav_feedback:
//                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new FeedbackFragment()).commit();
//                break;


        }

//        menuItem.setChecked(false);
        drawer.closeDrawer(GravityCompat.START);
//        removeColor();
        return true;
    }
    private void removeColor() {
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            MenuItem item = navigationView.getMenu().getItem(i);
            item.setChecked(false);
        }
    }
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
       else if(getSupportFragmentManager().getBackStackEntryCount()==0 && fragment != null && !(fragment instanceof HomeFragment) )
        {             getSupportFragmentManager().popBackStack();
//            Toast.makeText(DrawerActivity.this,"2",Toast.LENGTH_LONG).show();

            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        else {
//            Toast.makeText(DrawerActivity.this,"3",Toast.LENGTH_LONG).show();
//            getSupportFragmentManager().popBackStack();
            super.onBackPressed();

        }
    }

}

