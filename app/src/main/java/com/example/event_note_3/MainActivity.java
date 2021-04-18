package com.example.event_note_3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.event_note_3.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    public void onBackPressed() {
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
        // kiểm tra nếu fragment đang show hiện tại là AddEventFragment
        // thì thoát app
        if(homeFragment != null && homeFragment.isVisible()) {
            this.finish();
        } else super.onBackPressed();
    }

    private void initView() {
        // mở app show homeFragment
        loadHomeFragment();
    }

    private void loadHomeFragment() {
        Fragment homeFragment;
        // tìm fragment theo TAG
        // nếu tìm thấy thì pop ra và show lại
        // còn không thì tạo mới và add vào
        homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
        if(homeFragment != null) {
            if(!homeFragment.isVisible())
                getSupportFragmentManager().popBackStack();
        } else {
            homeFragment = HomeFragment.newInstance("","");
            replaceFragment(homeFragment, HomeFragment.TAG);
        }
    }

    private void replaceFragment(Fragment fragment, String tag) {
        // Create transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.frameContainer, fragment, tag);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}