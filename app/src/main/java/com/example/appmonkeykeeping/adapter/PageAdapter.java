package com.example.appmonkeykeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.model.ModelPage;

import java.util.List;

public class PageAdapter extends PagerAdapter {
    private List<ModelPage>models;
    private Context context;
    private LayoutInflater layoutInflater;
    private PageChoosing listener;

    public PageAdapter(List<ModelPage> models, Context context) {
        this.models = models;
        this.context = context;
    }
    public void setListener(PageChoosing listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_pager,container,false);
        ImageView imgPage = view.findViewById(R.id.img_page);
        TextView tvTitlePage = view.findViewById(R.id.tv_title_page);
        TextView tvDescPage = view.findViewById(R.id.tv_desc_page);
        TextView tvProgress = view.findViewById(R.id.tv_progress_pager);
        ProgressBar progressBar = view.findViewById(R.id.progress_pager);
        int progressCheck = models.get(position).getProgress();
        if(progressCheck==0){
            tvProgress.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }else {
            tvProgress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(progressCheck);
            tvProgress.setText(progressCheck+"%");
        }
        imgPage.setImageResource(models.get(position).getImage());
        tvTitlePage.setText(models.get(position).getTitle());
        tvDescPage.setText(models.get(position).getDesc());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(position);
                }
            }
        });
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
    public interface PageChoosing{
        void onClick(int position);
    }
}
