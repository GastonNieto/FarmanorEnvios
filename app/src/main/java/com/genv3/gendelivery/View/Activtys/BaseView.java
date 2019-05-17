package com.genv3.gendelivery.View.Activtys;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.View.Fragments.EntregaFragment;
import com.genv3.gendelivery.View.Fragments.MisPedidosFragment;
import com.genv3.gendelivery.View.Fragments.PedidosFragmen;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;
import java.util.List;

public class BaseView extends AppCompatActivity {
    public static int navItemIndex = 0;
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_left);
            List<Fragment> listaFragment = getSupportFragmentManager().getFragments();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (listaFragment.isEmpty()) {
                        transaction.replace(R.id.Contenedor, new PedidosFragmen(), "Pedidos").commit();
                        navItemIndex = 0;
                    } else {
                        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                            String tag = fragment.getTag();
                            if (!tag.equals("Pedidos")) {
                                transaction.replace(R.id.Contenedor, new PedidosFragmen(), "Pedidos").commit();
                                navItemIndex = 0;
                            }
                        }
                    }
                    return true;
                case R.id.navigation_dashboard:
                    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                        String tag = fragment.getTag();
                        if (!tag.equals("MisPedidos")) {
                            transaction.replace(R.id.Contenedor, new MisPedidosFragment(), "MisPedidos").commit();
                            navItemIndex = 1;
                        }
                    }
                    return true;
                case R.id.navigation_notifications:
                    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                        String tag = fragment.getTag();
                        if (!tag.equals("Entrega")) {
                            transaction.replace(R.id.Contenedor, new EntregaFragment(), "Entrega").commit();
                            navItemIndex = 1;
                        }
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_view);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.getMenu().getItem(0));
    }

    @Override
    public void onBackPressed() {
        if(navItemIndex!=0){
            navItemIndex = 0;
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.getMenu().getItem(0).setChecked(true));
            return;
        }
        super.onBackPressed();
    }
}
