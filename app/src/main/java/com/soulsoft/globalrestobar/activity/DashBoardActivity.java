package com.soulsoft.globalrestobar.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.soulsoft.globalrestobar.BaseActivity;
import com.soulsoft.globalrestobar.R;
import com.soulsoft.globalrestobar.fragment.TakeOrderFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import butterknife.BindView;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class DashBoardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navView)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawer;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.bottomNavigationView)
    AnimatedBottomBar animatedBottomBar;

    public static TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //basic intialisation...
        initViews();

        //load take order fragment..
        loadFragment(new TakeOrderFragment());
    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.activity_dash_board;
    }

    private void initViews(){
        tvTitle = findViewById(R.id.tv_title);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(view -> drawer.openDrawer(Gravity.LEFT));
        navigationView.setNavigationItemSelectedListener(this);

        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
                switch (i){
                    case 0://take order
                        tvTitle.setText(getResources().getString(R.string.take_order1));
                        loadFragment(new TakeOrderFragment());
                        break;

                    case 1://bill fragment..
                        tvTitle.setText(getResources().getString(R.string.billing));
                        break;

                    case 2://Cancel order....
                        tvTitle.setText(getResources().getString(R.string.cancel_order1));
                        break;
                }
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            //logout();
        } else if (id == R.id.nav_take_order_id) {
            tvTitle.setText(getResources().getString(R.string.take_order1));
            loadFragment(new TakeOrderFragment());
        } else if (id == R.id.nav_order_taken_id) {

        } else if (id == R.id.nav_cancel_order_id) {
            tvTitle.setText(getResources().getString(R.string.cancel_order1));
        } else if (id == R.id.nav_shift_table_id) {

        } else if (id == R.id.nav_generate_bill) {
            tvTitle.setText(getResources().getString(R.string.billing));
        }else if (id == R.id.nav_aboutus) {
            tvTitle.setText(getResources().getString(R.string.about_us));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

}
