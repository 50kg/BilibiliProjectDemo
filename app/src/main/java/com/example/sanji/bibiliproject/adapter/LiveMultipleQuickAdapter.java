package com.example.sanji.bibiliproject.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.bean.LiveContnetBean;
import com.example.sanji.bibiliproject.utils.Utils;

import java.util.List;

/**
 * Created by sanji on 2017/2/17.
 */

public class LiveMultipleQuickAdapter extends BaseMultiItemQuickAdapter<LiveContnetBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public LiveMultipleQuickAdapter(List<LiveContnetBean> data) {
        super(data);
        addItemType(LiveContnetBean.TITLE, R.layout.item_live_title);
        addItemType(LiveContnetBean.CONTNET, R.layout.item_live_contnet);
        addItemType(LiveContnetBean.MORE, R.layout.item_live_more);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveContnetBean item) {
        switch (helper.getItemViewType()) {
            case LiveContnetBean.TITLE:
                helper.setText(R.id.titleBig, item.getTitleName());
                Glide.with(mContext).load(item.getTitleSrc()).crossFade().into((ImageView) helper.getView(R.id.titleIcon));
                helper.setText(R.id.count, Utils.getNumWan(item.getCount()));
                break;
            case LiveContnetBean.CONTNET:
                helper.setText(R.id.tv_userName, item.getContentName());
                Glide.with(mContext).load(item.getContentSrc())
                        .crossFade()//淡入
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存
                        .placeholder(R.drawable.ic_next_video_placeholder)//站位
                        .error(R.drawable.img_tips_error_banner_tv)//错误
                        .into((ImageView) helper.getView(R.id.imgFace));
                helper.setText(R.id.tv_contnet_title, item.getTitle());
                helper.setText(R.id.guankancount, String.valueOf(item.getOnline()));
                break;
            case 0:
                //加载更多没有数据
                break;
        }
    }
}
