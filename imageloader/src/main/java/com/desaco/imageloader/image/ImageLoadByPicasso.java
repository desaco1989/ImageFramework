//package com.desaco.imageloader.image;
//
//import android.app.Activity;
//import android.content.Context;
//import android.net.Uri;
//import android.os.Build;
//import android.text.TextUtils;
//import android.widget.ImageView;
//
//import androidx.core.content.FileProvider;
//
//import com.desaco.imageloader.image.transform.PicassoCircleCornerForm;
//import com.desaco.imageloader.utils.LogTagUtil;
//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.RequestCreator;
//
//import java.io.File;
//
///**
// * 图片显示的公共类,使用picasso
// * 已经调试好了，圆角和裁剪
// */
//public class ImageLoadByPicasso implements ImageLoadInterface {
//
//    private static final String TAG = "ImageLoadByPicasso";
//
//    private void baseDisplay(String url, ImageView imageView) { // 设置显示大小和圆角
//        Picasso.get()
//                .load(url)
//                .resize(300, 300)
//                .centerCrop()  // 或者调用
//                .transform(new PicassoCircleCornerForm(15))
//                .into(imageView);
//    }
//
//    /**
//     * glide加载图片
//     *
//     * @param imageView view
//     * @param url       url
//     */
//    public void display(Context mContext, final ImageView imageView, String url,
//                        final ImageConfig config,
//                        final ImageLoadProcessInterface imageLoadProcessInterface) {
//
//        LogTagUtil.e(TAG, "PicassoUtils -> display()");
//
//        if (mContext == null) {
//            LogTagUtil.e(TAG, "PicassoUtils -> display -> mContext is null");
//            return;
//        }
//        // 不能崩
//        if (imageView == null) {
//            LogTagUtil.e(TAG, "PicassoUtils -> display -> imageView is null");
//            return;
//        }
//        Context context = imageView.getContext();
//        // View你还活着吗？
//        if (context instanceof Activity) {
//            if (((Activity) context).isFinishing()) { // activity是否结束
//                return;
//            }
//        }
//        try {
//            if ((config == null || config.defaultRes <= 0) && TextUtils.isEmpty(url)) {
//                LogTagUtil.e(TAG, "PicassoUtils -> display -> url is null and config is null");
//                return;
//            }
//            RequestCreator requestCreator = null;
//            Uri loadUri = null;
//            if (url.startsWith("http") || url.startsWith("https")) {
//                // 网络图片
//                loadUri = Uri.parse(url);
//            } else {
//                // 本地文件
//                if (url.startsWith("file://")) {
//                    // 文件的方式
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
//                        // Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
//                        url = Uri.parse(url).getPath();
//                    }
//                }
//
//                File file = new File(url);
//                if (file != null && file.exists()) {
//                    // 本地文件
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
//                        // Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
//                        loadUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
//                    } else {
//                        loadUri = Uri.fromFile(file);
//                    }
//                } else {
//                    // 可能是资源路径的地址
//                    loadUri = Uri.parse(url);
//                }
//            }
//
//            requestCreator = Picasso.get().load(loadUri);
//            if (config != null) {
//                if (config.defaultRes > 0) {
//                    requestCreator.placeholder(config.defaultRes);
//                }
//                if (config.failRes > 0) {
//                    requestCreator.error(config.failRes);
//                }
//                if (config.width > 0 && config.height > 0) {
//                    requestCreator.resize(config.width, config.height);
//                }
//
//                if (config.getScaleType() != null) { // 设置图片ScaleType
//                    if (config.getScaleType().equals(ImageView.ScaleType.CENTER_CROP)) {
//                        requestCreator.centerCrop();
//                    } else if (config.getScaleType().equals(ImageView.ScaleType.CENTER_INSIDE)) {
//                        requestCreator.centerInside();
//                    }
//                }
//
//                if (config.radius > 0) { // 设置圆角
//                    requestCreator.transform(new PicassoCircleCornerForm(config.radius));
//                }
//            }
//
//            if (imageLoadProcessInterface != null) {
//                requestCreator.tag(url).into(imageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        if (imageLoadProcessInterface != null) {
//                            imageLoadProcessInterface.onResourceReady();
//                            LogTagUtil.e(TAG, "Callback onSuccess");
//                        }
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        if (imageLoadProcessInterface != null) {
//                            imageLoadProcessInterface.onLoadFailed();
//                            LogTagUtil.e(TAG, "Callback onError");
//                        }
//                    }
//                });
//            } else {
//                requestCreator.tag(url).into(imageView);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 恢复加载图片
//     *
//     * @param context
//     */
//    public void resumeLoad(Context context, String url) {
//        if (!TextUtils.isEmpty(url))
//            Picasso.get().resumeTag(url);
//    }
//
//    /**
//     * 清除一个资源的加载
//     *
//     * @param context
//     */
//    public void clearImageView(Context context, ImageView imageView, String url) {
//        if (!TextUtils.isEmpty(url))
//            Picasso.get().invalidate(url);
//    }
//
//    /**
//     * 暂停加载图片
//     *
//     * @param context
//     */
//    public void pauseLoad(Context context, String url) {
//        if (!TextUtils.isEmpty(url))
//            Picasso.get().pauseTag(url);
//    }
//}
