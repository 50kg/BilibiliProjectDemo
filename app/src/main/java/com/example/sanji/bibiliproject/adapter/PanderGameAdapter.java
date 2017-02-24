package com.example.sanji.bibiliproject.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.bean.PanderGameBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by sanji on 2017/2/21.
 */

public class PanderGameAdapter extends SuperAdapter<PanderGameBean.DataBean> {
    public PanderGameAdapter(Context context, List<PanderGameBean.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(final SuperViewHolder holder, int viewType, final int layoutPosition, PanderGameBean.DataBean item) {
        holder.setText(R.id.pander_gmme_tv,item.getCname());

        Glide.with(mContext).load(item.getImg())
                .crossFade()//淡入
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存
                .placeholder(R.drawable.ic_next_video_placeholder)//站位
                .error(R.drawable.img_tips_error_banner_tv)//错误
                .into((ImageView) holder.getView(R.id.pander_gmme_img));

        final View view = holder.getView(R.id.item_pander_game);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(view, layoutPosition);
            }
        });
    }

    public interface OnItemClickListener {
        //单击
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
