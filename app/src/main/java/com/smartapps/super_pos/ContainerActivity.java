package com.smartapps.super_pos;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ContainerActivity extends ProjectActivity {
    protected Toolbar mActionBarToolbar;
    protected View v;
    protected Typeface tf;

    public void setContentView(int view) {
        super.setContentView(view);

        v = LayoutInflater
                .from(this)
                .inflate(R.layout.custom_action_bar_layout, null, false);
        tf = ResourcesCompat.getFont(this, R.font.font_super);
        mActionBarToolbar = findViewById(R.id.main_toolbar);
        TextView tabName = v.findViewById(R.id.tab_name);
        tabName.setTypeface(tf);
        v.findViewById(R.id.tab_name).setPadding((int) getResources().getDimension(R.dimen.tab_centering), 0, 0, 0);

        if (mActionBarToolbar != null) {
            setSupportActionBar(mActionBarToolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            androidx.appcompat.app.ActionBar.LayoutParams params = new androidx.appcompat.app.ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT);
            getSupportActionBar().setCustomView(v, params);

            v.findViewById(R.id.back_tabIcon).setOnClickListener(view12 -> onBackPressed());
            v.findViewById(R.id.cart_tabIcon).setVisibility(View.GONE);
            v.findViewById(R.id.account_tabIcon).setVisibility(View.GONE);
        }
    }
}
