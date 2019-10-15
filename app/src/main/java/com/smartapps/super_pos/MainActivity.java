package com.smartapps.super_pos;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.smartapps.super_pos.Fragments.CurrentOrderFragment;
import com.smartapps.super_pos.Fragments.PreviousOrderFragment;
import com.smartapps.super_pos.Items.NavItem;
import com.smartapps.super_pos.Utils.Views.CustomTabLayout;
import com.smartapps.super_pos.Utils.Views.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ContainerActivity {
    NonSwipeableViewPager viewPager;
    ViewPagerAdapter adapter;
    ArrayList<NavItem> navItems;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    CustomTabLayout navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start service:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("Default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("DefaultDesc");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        //Get FirebaseToken for this app
        FirebaseApp.initializeApp(this);
        getFirebaseAppToken();

        navItems = new ArrayList<>();
        navItems.add(new NavItem(getResources().getString(R.string.nav_current_order), R.drawable.ic_home, R.drawable.ic_home_green, new CurrentOrderFragment()));
        navItems.add(new NavItem(getResources().getString(R.string.nav_previous_order), R.drawable.ic_cats, R.drawable.ic_cats_green,new PreviousOrderFragment() ));
        navItems.add(new NavItem(getResources().getString(R.string.nav_stock), R.drawable.ic_cats, R.drawable.ic_cats_green,new CurrentOrderFragment() ));



        viewPager = findViewById(R.id.content_frame);
        createViewPager(viewPager);
        navigation = findViewById(R.id.tabs);
        navigation.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        navigation.setupWithViewPager(viewPager,true);
        viewPager.setCurrentItem(0);

    }

    //Tabs and ViewPager classes and methods code
    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<NavItem> navItems ;
        ViewPagerAdapter(FragmentManager manager, ArrayList<NavItem> navItems) {
            super(manager);
            this.navItems = navItems;
        }

        @Override
        public Fragment getItem(int position) {
            return navItems.get(position).getFragment();
        }

        public NavItem getNavItem(int position) {
            return navItems.get(position);
        }

        public int getNavItemPosition(String title) {
            NavItem navItem =new NavItem(title,0,0,null);
            if(navItems.contains(navItem)) {
                return navItems.indexOf(navItem);
            }else{
                return 0;
            }

        }
        @Override
        public int getCount() {
            return navItems.size();
        }



        void removeNavItem(String title){
            NavItem removeItem =new NavItem(title,0,0,null);
            if(navItems.contains(removeItem)) {
                int itemIndex = navItems.indexOf(removeItem);
                if(navigation.getSelectedTabPosition() == itemIndex){
                    navigation.clearOnTabSelectedListeners();
                }
                navItems.remove(itemIndex);

                navigation.removeTabAt(itemIndex);
                if(navItems.size() > 0){
                    viewPager.setCurrentItem(0);
                }
                notifyDataSetChanged();
                navigation.initCustomView(this,viewPager);
            }

        }



        @Override
        public CharSequence getPageTitle(int position) {
            return navItems.get(position).getTitle();
        }
    }


    private void createViewPager(ViewPager viewPager){
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),navItems);
        viewPager.setAdapter(adapter);
    }

    public void getFirebaseAppToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult().getToken();
                            //Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("firebaseToken", task.getException().getMessage());
                        }
                    }
                });
    }
}
