package com.smartapps.super_pos.Utils.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.smartapps.super_pos.Items.NavItem;
import com.smartapps.super_pos.MainActivity;
import com.smartapps.super_pos.R;

import java.util.Objects;

public class CustomTabLayout extends TabLayout {

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public FragmentLifecycle getFragmentLifecycle() {
        return fragmentLifecycle;
    }

    public static void setFragmentLifecycle(FragmentLifecycle fragmentLifecycle) {
        CustomTabLayout.fragmentLifecycle = fragmentLifecycle;
    }

    private static FragmentLifecycle fragmentLifecycle;
    public interface FragmentLifecycle {

        void onPauseFragment();
        void onResumeFragment(NavItem navItem);

    }

    public void initCustomView(MainActivity.ViewPagerAdapter viewPagerAdapter, ViewPager viewPager){
        for (int i = 0; i < getTabCount(); i++) {
            // inflate the Parent LinearLayout Container for the tab
            // from the layout nav_tab.xml file that we created 'R.layout.nav_tab
            LinearLayout tab = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.nav_tab, null, false);
            if(tab != null) {
                // get child TextView and ImageView from this layout for the icon and label
                TextView tab_label = tab.findViewById(R.id.nav_label);
                ImageView tab_icon = tab.findViewById(R.id.nav_icon);

                // set the label text by getting the actual string value by its id
                // by getting the actual resource value `getResources().getString(string_id)`
                tab_label.setText(viewPagerAdapter.getNavItem(i).getTitle());

                // set the home to be active at first
                if (i == getSelectedTabPosition()) {
                    tab_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tab_icon.setImageResource(viewPagerAdapter.getNavItem(i).getPressed_icon_id());
                } else {
                    tab_icon.setImageResource(viewPagerAdapter.getNavItem(i).getIcon_id());
                }

                // finally publish this custom view to navigation tab
                Objects.requireNonNull(getTabAt(i)).setCustomView(tab);
            }
        }

        addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        if(fragmentLifecycle != null){
                            fragmentLifecycle.onResumeFragment(viewPagerAdapter.getNavItem(tab.getPosition()));
                        }
                        // 1. get the custom View you've added
                        View tabView = tab.getCustomView();
                        if(tabView != null) {
                            // get inflated children Views the icon and the label by their id
                            TextView tab_label = tabView.findViewById(R.id.nav_label);
                            ImageView tab_icon = tabView.findViewById(R.id.nav_icon);

                            // change the label color, by getting the color resource value
                            tab_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                            // change the image Resource
                            // i defined all icons in an array ordered in order of tabs appearances
                            // call tab.getPosition() to get active tab index.
                            tab_icon.setImageResource(viewPagerAdapter.getNavItem(tab.getPosition()).getPressed_icon_id());
                        }
                    }

                    // do as the above the opposite way to reset tab when state is changed
                    // as it not the active one any more
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        View tabView = tab.getCustomView();
                        if(fragmentLifecycle != null){
                            fragmentLifecycle.onPauseFragment();
                        }
                        if(tabView != null) {
                            TextView tab_label = tabView.findViewById(R.id.nav_label);
                            ImageView tab_icon = tabView.findViewById(R.id.nav_icon);

                            // back to the black color
                            tab_label.setTextColor(getResources().getColor(R.color.font_color));
                            // and the icon resouce to the old black image
                            // also via array that holds the icon resources in order
                            // and get the one of this tab's position
                            tab_icon.setImageResource(viewPagerAdapter.getNavItem(tab.getPosition()).getIcon_id());
                        }
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

    }
    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager, boolean autoRefresh) {
        super.setupWithViewPager(viewPager, autoRefresh);
        MainActivity.ViewPagerAdapter viewPagerAdapter = (MainActivity.ViewPagerAdapter) viewPager.getAdapter();
        initCustomView(viewPagerAdapter,viewPager);

    }

}