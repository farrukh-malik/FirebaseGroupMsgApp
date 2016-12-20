package com.example.user.mychatappfinal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by user on 12/6/2016.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

       switch (position){
           case 0:
               return new ChatFragment();
           case 1:
               return new OtherFragment();
           default:
               return null;
       }

    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {


        switch (position){
            case 0:
                return "Chats";
            case 1:
                return "Others";
            default:
                return null;
        }
    }
}
