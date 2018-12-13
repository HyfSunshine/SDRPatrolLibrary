package com.sdr.patrollib.base.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.sdr.patrollib.base.fragment.PatrolBaseSimpleFragment;

import java.util.List;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolBaseFragmentPagerAdapter<F extends PatrolBaseSimpleFragment> extends FragmentPagerAdapter {

    private List<F> fragmentList;

    public PatrolBaseFragmentPagerAdapter(FragmentManager fm, List<F> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getFragmentTitle();
    }
}
