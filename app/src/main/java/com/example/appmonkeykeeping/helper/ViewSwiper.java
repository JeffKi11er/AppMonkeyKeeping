package com.example.appmonkeykeeping.helper;

import android.animation.ArgbEvaluator;
import android.content.Context;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.appmonkeykeeping.adapter.PageAdapter;
import com.example.appmonkeykeeping.model.ModelPage;

import java.util.ArrayList;
import java.util.List;

public class ViewSwiper implements PageAdapter.PageChoosing{
    PageAdapter pagerAdapter;
    List<ModelPage>pages = new ArrayList<>();
    Integer[]color=null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    PageClickNotification listener;
    public void addListPage(ModelPage page){
        pages.add(page);
    }

    public void setListener(PageClickNotification listener) {
        this.listener = listener;
    }

    public PageAdapter initializeAdapter(Context context){
        pagerAdapter = new PageAdapter(pages,context);
        pagerAdapter.setListener(this);
        return pagerAdapter;
    }
    public Integer setUpPaging(Integer[]dataColors,
                            int position,
                            float possitionOffset,
                            int positionOffsetPixels){
        color = dataColors;
        if (position < (pagerAdapter.getCount() -1) && position < (color.length - 1)) {
            return (Integer) argbEvaluator.evaluate(
                    possitionOffset,
                    color[position],
                    color[position + 1]
            );
        }
        else {
            return color[color.length - 1];
        }
    }
    public String showTargetPage(int position){
        return pages.get(position).getTitle();
    }
    @Override
    public void onClick(int position) {
        if(listener!=null){
            listener.onClickPaging(position);
        }
    }
    public interface PageClickNotification{
        void onClickPaging(int position);
    }
}
