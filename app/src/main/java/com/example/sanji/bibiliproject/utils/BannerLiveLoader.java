package com.example.sanji.bibiliproject.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.bean.LiveBannerBean;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by sanji on 2017/1/30.
 */
public class BannerLiveLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(((LiveBannerBean) path).getImg())
                .crossFade()
                .placeholder(R.drawable.bili_default_image_tv)//站位
                .error(R.drawable.img_tips_error_banner_tv)//错误
                .into(imageView);
    }
}
