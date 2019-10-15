package com.smartapps.super_pos.Fragments;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.smartapps.super_pos.Items.NavItem;
import com.smartapps.super_pos.ProjectActivity;
import com.smartapps.super_pos.Utils.Views.CustomTabLayout;

public class ProjectFragment extends Fragment implements CustomTabLayout.FragmentLifecycle {
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    public ProjectActivity getProjectActivity(){
        return (ProjectActivity)getActivity();
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment(NavItem navItem) {
//        Toast.makeText(getContext(),"Class " + this.getClass().getName(), Toast.LENGTH_SHORT).show();
    }

}
