package com.smartapps.super_pos.Items;

import androidx.fragment.app.Fragment;

public class NavItem {

    private String title;
    private int icon_id;
    private int pressed_icon_id;
    private Fragment fragment;

    public NavItem(String title, int icon_id, int pressed_icon_id, Fragment fragment) {
        this.title = title;
        this.icon_id = icon_id;
        this.pressed_icon_id = pressed_icon_id;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }

    public int getPressed_icon_id() {
        return pressed_icon_id;
    }

    public void setPressed_icon_id(int pressed_icon_id) {
        this.pressed_icon_id = pressed_icon_id;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NavItem navItem = (NavItem) o;
        return title.equals(navItem.getTitle());
    }


}
