package com.desaco.imageloader.image;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.desaco.imageloader.utils.LogTagUtil;


/**
 * 图片加载基础的工具类
 */
public class ImageLoadBaseTool {

    private static final String TAG = "ImageLoadBaseTool";
    private static ImageLoadInterface imageLoad = null;

    private static ImageLoadBaseTool mImageLoadBaseTool;

    private ImageLoadBaseTool() {

    }

    private ImageLoadBaseTool(boolean isNeedGlide) { // TODO 在这里切换 glide 或 picasso
        if (imageLoad == null) {
            imageLoad = new ImageLoadByGlide(); // 目前只使用glide加载图片；picasso暂无需求
        }

//        if (isNeedGlide) {
//            if (imageLoad == null) {
//                imageLoad = new ImageLoadByGlide(); // glide
//            }
//        } else {
//            if (imageLoad == null) {
//                imageLoad = new ImageLoadByPicasso(); // picasso
//            }
//        }

    }

    public static ImageLoadBaseTool getInstance(boolean isNeedGlide) {
        if (mImageLoadBaseTool == null) {
            synchronized (ImageLoadBaseTool.class) {
                if (mImageLoadBaseTool == null) {
                    mImageLoadBaseTool = new ImageLoadBaseTool(isNeedGlide);
                }
            }
        }
        return mImageLoadBaseTool;
    }

    public static void setPrintLog(boolean isPrintLog) {
        if (isPrintLog) {
            LogTagUtil.isCommonLog = true;
        }
    }

    /**
     * imageView中加载项目内资源
     *
     * @param mContext
     * @param view
     * @param resId
     */
    public void display(Context mContext, final ImageView view, @DrawableRes int resId) {
        display(mContext, view, null, resId);
    }

    /**
     * 加载网络图片/本地图片
     *
     * @param mContext
     * @param view
     * @param url
     */
    public void display(Context mContext, ImageView view, String url) {
        display(mContext, view, url, -1);
    }

    /**
     * 加载图片
     *
     * @param mContext     上下文
     * @param view         imageview
     * @param url          图片地址
     * @param defaultImage 默认显示内容
     */
    public void display(Context mContext, ImageView view, String url, int defaultImage) {
        display(mContext, view, url, defaultImage, null);
    }

    /**
     * @param mContext
     * @param view
     * @param url
     * @param imageLoadProcessInterface
     */
    public void display(Context mContext, ImageView view, String url,
                        ImageLoadProcessInterface imageLoadProcessInterface) {
        display(mContext, view, url, -1, imageLoadProcessInterface);
    }

    /**
     * @param mContext                  上下文
     * @param view                      imageview
     * @param url                       地址
     * @param defaultImage              默认图片
     * @param imageLoadProcessInterface 监听
     */
    public void display(Context mContext, ImageView view, String url, int defaultImage,
                        ImageLoadProcessInterface imageLoadProcessInterface) {
        display(mContext, view, url, defaultImage, -1, imageLoadProcessInterface);
    }

    public void display(Context mContext, ImageView view, String url, int defaultImage, int failImage,
                        ImageLoadProcessInterface imageLoadProcessInterface) {
        display(mContext, view, url, new ImageConfig(defaultImage, failImage, 0), imageLoadProcessInterface);
    }

    public void display(Context mContext, ImageView view, String url, ImageConfig config) {
        display(mContext, view, url, config, null);
    }

    public void display(Context mContext, ImageView view, String url,
                        ImageConfig config,
                        ImageLoadProcessInterface imageLoadProcessInterface) {
        displayUrl(mContext, view, url, config, imageLoadProcessInterface);
    }

    /**
     * glide加载图片
     *
     * @param imageView view
     * @param url       url
     */
    private void displayUrl(Context mContext, final ImageView imageView, final String url,
                            final ImageConfig config,
                            final ImageLoadProcessInterface imageLoadProcessInterface) {
        try {
            imageLoad.display(mContext, imageView, url, config, imageLoadProcessInterface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复加载图片
     *
     * @param context
     */
    public void resumeLoad(Context context, String url) {
        if (imageLoad != null) {
            imageLoad.resumeLoad(context, url);
        }
    }

    /**
     * 清除一个资源的加载
     *
     * @param context
     */
    public void clearImageView(Context context, ImageView imageView, String url) {
        if (imageLoad != null) {
            imageLoad.clearImageView(context, imageView, url);
        }
    }

    /**
     * 暂停加载图片
     *
     * @param context
     */
    public void pauseLoad(Context context, String url) {
        if (imageLoad != null) {
            imageLoad.pauseLoad(context, url);
        }
    }
}
