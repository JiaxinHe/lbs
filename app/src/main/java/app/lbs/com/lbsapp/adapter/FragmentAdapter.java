package app.lbs.com.lbsapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragmentsList;
    private String[] titles;
    private Drawable[] icons;

    public FragmentAdapter(FragmentManager fm, Context context, List<Fragment> fragmentsList,
                           String[] titles, Drawable[] icons) {
        super(fm);
        this.context = context;
        this.fragmentsList = fragmentsList;
        this.titles = titles;
        this.icons = icons;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

}