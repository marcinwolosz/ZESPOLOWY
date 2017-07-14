package com.example.baidMarcinos.kolkoikrzyzyk.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.baidMarcinos.kolkoikrzyzyk.fragment.UsersFragment;

/**
 * Created by MARCIN on 2017-05-27.
 */


public class UsersListAdapter extends FragmentPagerAdapter {
    private static final Fragment[] sFragments = new Fragment[]{/*UsersFragment.newInstance(UsersFragment.TYPE_CHATS),*/
            UsersFragment.newInstance(UsersFragment.TYPE_ALL)};
    private static final String[] sTitles = new String[]{
            "All Users"};

    public UsersListAdapter(FragmentManager fm) {
        super(fm);
    }

  //  @Override
    public Fragment getItem(int position) {
        return sFragments[position];
    }

   // @Override
    public int getCount() {
        return sFragments.length;
    }

   // @Override
    public CharSequence getPageTitle(int position) {
        return sTitles[position];
    }
}
