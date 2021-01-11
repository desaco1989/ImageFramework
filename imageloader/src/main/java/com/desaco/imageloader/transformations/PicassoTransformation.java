package com.desaco.imageloader.transformations;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.desaco.imageloader.utils.LogTagUtil;
import com.squareup.picasso.Transformation;

import java.lang.ref.WeakReference;

/**
 * @author dengwen
 * @date 2021/1/11.
 * <p>
 * Android 使用Picasso加载网络图片等比例缩放- https://huangxiaoguo.blog.csdn.net/article/details/73551686
 * <p>
 * Picasso.with(mContext)
 * 　　.load(imageUrl)
 * 　　.placeholder(R.mipmap.zhanwei)
 * 　　.error(R.mipmap.zhanwei)
 * 　　.transform(transformation)
 * 　　.into(viewHolder.mImageView);
 * <p>
 * PicassoTransformation转换图片的宽和高
 */
public class PicassoTransformation implements Transformation {

    private WeakReference<ImageView> weakReferenceIv;

    public PicassoTransformation(ImageView iv) {
        super();
        weakReferenceIv = new WeakReference<ImageView>(iv);
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth = weakReferenceIv.get().getWidth();
        if (source.getWidth() == 0) {
            return source;
        }

//        LogTagUtil.i("PicassoTransformation", "source.getHeight()=" + source.getHeight());
//        LogTagUtil.i("PicassoTransformation", "source.getWidth()=" + source.getWidth());

        // 如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
        int targetHeight = (int) (targetWidth * aspectRatio);
        LogTagUtil.i("PicassoTransformation", "targetWidth=" + targetWidth);
        LogTagUtil.i("PicassoTransformation", "targetHeight=" + targetHeight);
        if (targetHeight != 0 && targetWidth != 0) {
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                // Same bitmap is returned if sizes are the same
                source.recycle();
            }
            return result;
        } else {
            return source;
        }

    }

    @Override
    public String key() {
        return "transformation" + " desiredWidth";
    }

}
