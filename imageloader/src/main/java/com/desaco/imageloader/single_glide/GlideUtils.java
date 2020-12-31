package com.desaco.imageloader.single_glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.desaco.imageloader.R;
import com.desaco.imageloader.transformations.BlurTransformation;
import com.desaco.imageloader.transformations.CropCircleTransformation;
import com.desaco.imageloader.transformations.GrayscaleTransformation;
import com.desaco.imageloader.transformations.RoundedCornersTransformation;
import com.desaco.imageloader.utils.LogTagUtil;

import java.io.File;

/**
 * Fresco在天猫盒子上出现加载图片黑屏现象，Glide框架加载图片不会出现
 *
 * @author dengwen
 * @date 2020/12/31.
 * <p>
 * https://blog.csdn.net/blogrecord/article/details/102830454 ; https://github.com/afkT/DevUtils
 * cloneImageOptions	克隆图片加载配置
 * defaultOptions	获取默认加载配置
 * emptyOptions	获取空白加载配置
 * skipCacheOptions	获取跳过缓存 ( 每次都从服务端获取最新 ) 加载配置
 * getLoadResOptions	获取自定义图片加载配置
 * transformationOptions	获取图片处理效果加载配置
 * clearDiskCache	清除磁盘缓存
 * clearMemoryCache	清除内存缓存
 * onLowMemory	低内存通知
 * getDiskCache	获取 SDCard 缓存空间
 * preload	预加载图片
 * displayImage	图片显示
 * displayImageToGif	图片显示
 * loadImageBitmap	图片加载
 * loadImageDrawable	图片加载
 * loadImageFile	图片加载
 * loadImageGif	图片加载
 * cancelDisplayTask	取消图片显示任务
 * destroy	销毁操作
 * pause	暂停图片加载
 * resume	恢复图片加载
 * stop	停止图片加载
 * start	开始图片加载
 * <p>
 * https://www.jianshu.com/p/92070f357068
 * loadSD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
 * loadassets资源：load("file:///android_asset/f003.gif")
 * loadraw资源：load("Android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
 * loaddrawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
 * loadContentProvider资源：load("content://media/external/images/media/139469")
 * loadhttp资源：load("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg")
 * loadhttps资源：load("https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp")
 * <p>
 * load(Uri uri)，load(File file)，load(Integer resourceId)，load(URL url)，load(byte[] model)，load(T model)，loadFromMediaStore(Uri uri)。
 * load的资源也可以是本地视频，如果想要load网络视频或更高级的操作可以使用VideoView等其它控件完成
 * <p>
 * 1、thumbnail(float sizeMultiplier). 请求给定系数的缩略图。如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示。系数sizeMultiplier必须在(0,1)之间，可以递归调用该方法。
 * 2、sizeMultiplier(float sizeMultiplier). 在加载资源之前给Target大小设置系数。
 * 3、diskCacheStrategy(DiskCacheStrategy strategy).设置缓存策略。DiskCacheStrategy.SOURCE：缓存原始数据，DiskCacheStrategy.RESULT：缓存变换(如缩放、裁剪等)后的资源数据，DiskCacheStrategy.NONE：什么都不缓存，DiskCacheStrategy.ALL：缓存SOURC和RESULT。默认采用DiskCacheStrategy.RESULT策略，对于download only操作要使用DiskCacheStrategy.SOURCE。
 * 4、priority(Priority priority). 指定加载的优先级，优先级越高越优先加载，但不保证所有图片都按序加载。枚举Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW。默认为Priority.NORMAL。
 * ５、dontAnimate(). 移除所有的动画。
 * ６、animate(int animationId). 在异步加载资源完成时会执行该动画。
 * ７、animate(ViewPropertyAnimation.Animator animator). 在异步加载资源完成时会执行该动画。
 * ８、placeholder(int resourceId). 设置资源加载过程中的占位Drawable。
 * ９、placeholder(Drawable drawable). 设置资源加载过程中的占位Drawable。
 * 10、fallback(int resourceId). 设置model为空时要显示的Drawable。如果没设置fallback，model为空时将显示error的Drawable，如果error的Drawable也没设置，就显示placeholder的Drawable。
 * 11、fallback(Drawable drawable).设置model为空时显示的Drawable。
 * 12、error(int resourceId).设置load失败时显示的Drawable。
 * 13、error(Drawable drawable).设置load失败时显示的Drawable。
 * 14、listener(RequestListener<? super ModelType, TranscodeType> requestListener). 监听资源加载的请求状态，可以使用两个回调：onResourceReady(R resource, T model, Target<R> target, boolean isFromMemoryCache, boolean isFirstResource)
 * 和onException(Exception e, T model, Target<R> target, boolean isFirstResource)
 * ，但不要每次请求都使用新的监听器，要避免不必要的内存申请，可以使用单例进行统一的异常监听和处理。
 * 15、skipMemoryCache(boolean skip). 设置是否跳过内存缓存，但不保证一定不被缓存（比如请求已经在加载资源且没设置跳过内存缓存，这个资源就会被缓存在内存中）。
 * 16、override(int width, int height). 重新设置Target的宽高值（单位为pixel）。
 * 17、into(Y target).设置资源将被加载到的Target。
 * 18、into(ImageView view). 设置资源将被加载到的ImageView。取消该ImageView之前所有的加载并释放资源。
 * 19、into(int width, int height). 后台线程加载时要加载资源的宽高值（单位为pixel）。
 * 20、preload(int width, int height). 预加载resource到缓存中（单位为pixel）。
 * 21、asBitmap(). 无论资源是不是gif动画，都作为Bitmap对待。如果是gif动画会停在第一帧。
 * 22、asGif().把资源作为GifDrawable对待。如果资源不是gif动画将会失败，会回调.error()
 */
public class GlideUtils {
    private GlideUtils() {
    }

    // 日志 TAG
    private static final String TAG = GlideUtils.class.getSimpleName();
    // GlideLoader
    private static GlideLoader sGlideLoader;
    // 图片默认加载配置
    private static RequestOptions DF_OPTIONS = defaultOptions();
    // 全局 Context
    private static Context sContext;
    // 图片加载中
    private static int sImageLoadingRes = 0;
    // 图片地址异常
    private static int sImageUriErrorRes = 0;
    // 图片 ( 加载 / 解码 ) 失败
    private static int sImageFailRes = 0;

    // ================================
    // =  GlideLoader(RequestManager) =
    // ================================

    public static GlideLoader with(@NonNull Context context) {
        return new GlideLoader(Glide.with(context));
    }

    public static GlideLoader with(@NonNull Activity activity) {
        return new GlideLoader(Glide.with(activity));
    }

    @NonNull
    public static GlideLoader with(@NonNull FragmentActivity activity) {
        return new GlideLoader(Glide.with(activity));
    }

    @NonNull
    public static GlideLoader with(@NonNull android.app.Fragment fragment) {
        return new GlideLoader(Glide.with(fragment));
    }

    @NonNull
    public static GlideLoader with(@NonNull Fragment fragment) {
        return new GlideLoader(Glide.with(fragment));
    }

    @NonNull
    public static GlideLoader with(@NonNull View view) {
        return new GlideLoader(Glide.with(view));
    }

    /**
     * 获取全局 Context Glide
     *
     * @return {@link GlideLoader}
     */
    public static GlideLoader with() {
        if (sGlideLoader == null) {
            try {
                sGlideLoader = new GlideLoader(Glide.with(sContext));
            } catch (Exception e) {
                LogTagUtil.d(TAG, "with, Exception=" + e.toString());
            }
        }
        return sGlideLoader;
    }

    // ==========
    // = 初始化 =
    // ==========

    /**
     * 初始化方法
     *
     * @param context {@link Context}
     */
    public static void init(final Context context) {
        if (sContext == null && context != null) {
            // 设置全局 Context
            sContext = context.getApplicationContext();
            // 默认进行初始化
            with();
        }
    }

    // ==================
    // = RequestOptions =
    // ==================

    /**
     * 克隆图片加载配置
     *
     * @param options 待克隆加载配置
     * @return {@link RequestOptions}
     */
    public static RequestOptions cloneImageOptions(final RequestOptions options) {
        return (options != null) ? options.clone() : null;
    }

    /**
     * 获取默认加载配置
     * <pre>
     *     优先级:
     *     Priority.LOW 低
     *     Priority.NORMAL 默认正常
     *     Priority.HIGH 高 / 优先
     *     Priority.IMMEDIATE 立即加载
     *     缓存:
     *     DiskCacheStrategy.NONE 不做磁盘缓存
     *     DiskCacheStrategy.SOURCE 只缓存图像原图
     *     DiskCacheStrategy.RESULT 只缓存加载后的图像, 即处理后最终显示时的图像
     *     DiskCacheStrategy.ALL 缓存所有版本的图像 ( 默认行为 )
     * </pre>
     *
     * @return {@link RequestOptions}
     */
    public static RequestOptions defaultOptions() {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置全缓存
                .placeholder(sImageLoadingRes) // 设置图片在下载期间显示的图片
                .fallback(sImageUriErrorRes) // 设置图片 Uri 为空或是错误的时候显示的图片
                .error(sImageFailRes) // 设置图片 ( 加载 / 解码 ) 过程中错误时候显示的图片
//                .format(DecodeFormat.PREFER_ARGB_8888) // 设置图片解码格式, 默认 8888
                .priority(Priority.HIGH);
        return requestOptions;
    }

    /**
     * 获取空白加载配置
     *
     * @return {@link RequestOptions}
     */
    public static RequestOptions emptyOptions() {
        return new RequestOptions();
    }

    /**
     * 获取跳过缓存 ( 每次都从服务端获取最新 ) 加载配置
     *
     * @return {@link RequestOptions}
     */
    public static RequestOptions skipCacheOptions() {
        return skipCacheOptions(cloneImageOptions(DF_OPTIONS));
    }

    /**
     * 获取跳过缓存 ( 每次都从服务端获取最新 ) 加载配置
     *
     * @param options {@link RequestOptions}
     * @return {@link RequestOptions}
     */
    public static RequestOptions skipCacheOptions(final RequestOptions options) {
        if (options != null) {
            return options.diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
        }
        return options;
    }

    /**
     * 获取自定义图片加载配置
     *
     * @param loadingRes 设置加载中显示的图片
     * @return {@link RequestOptions}
     */
    public static RequestOptions getLoadResOptions(@DrawableRes final int loadingRes) {
        return getLoadResOptions(cloneImageOptions(DF_OPTIONS), loadingRes);
    }

    /**
     * 获取自定义图片加载配置
     *
     * @param options    {@link RequestOptions}
     * @param loadingRes 设置加载中显示的图片
     * @return {@link RequestOptions}
     */
    public static RequestOptions getLoadResOptions(final RequestOptions options, @DrawableRes final int loadingRes) {
        if (options != null && loadingRes != 0) {
            options.placeholder(loadingRes) // 设置图片在下载期间显示的图片
                    .fallback(loadingRes) // 设置图片 Uri 为空或是错误的时候显示的图片
                    .error(loadingRes); // 设置图片 ( 加载 / 解码 ) 过程中错误时候显示的图片
        }
        return options;
    }

    /**
     * 获取图片处理效果加载配置
     *
     * @param transformation {@link Transformation} 图形效果
     * @return {@link RequestOptions}
     */
    public static RequestOptions transformationOptions(final Transformation transformation) {
        return transformationOptions(cloneImageOptions(DF_OPTIONS), transformation);
    }

    /**
     * 获取图片处理效果加载配置
     *
     * @param options        {@link RequestOptions}
     * @param transformation {@link Transformation} 图形效果
     * @return {@link RequestOptions}
     */
    public static RequestOptions transformationOptions(final RequestOptions options, final Transformation transformation) {
        if (options != null) {
            try {
                options.transform(transformation);
            } catch (Exception e) {
                LogTagUtil.d(TAG, "图片处理效果加载配置transformationOptions, Exception=" + e.toString());
            }
        }
        return options;
    }

    // ==============
    // = 内部方法类 =
    // ==============

    /**
     * detail: Glide Loader 封装内部类
     *
     * @author Ttt
     */
    public final static class GlideLoader {

        // RequestManager
        private RequestManager mRequestManager;

        /**
         * 构造函数
         *
         * @param requestManager {@link RequestManager}
         */
        public GlideLoader(RequestManager requestManager) {
            this.mRequestManager = requestManager;
            // 设置加载配置
            if (requestManager != null) requestManager.setDefaultRequestOptions(DF_OPTIONS);
        }

        // ==============
        // = 预加载处理 =
        // ==============

        /**
         * 预加载图片
         *
         * @param uri Image Uri
         */
        public void preload(final String uri) {
            preload(uri, null);
        }

        /**
         * 预加载图片
         * <pre>
         *     先加载图片, 不显示, 等到需要显示的时候, 直接拿缓存用
         * </pre>
         *
         * @param uri     Image Uri
         * @param options {@link RequestOptions}
         */
        public void preload(final String uri, final RequestOptions options) {
            if (mRequestManager != null) {
                if (options != null) {
                    mRequestManager.asBitmap().load(uri).apply(options).preload();
                } else {
                    mRequestManager.asBitmap().load(uri).preload();
                }
            }
        }

        // ============
        // = 图片显示 =
        // ============

        /**
         * 图片显示
         *
         * @param uri       Image Uri
         * @param imageView ImageView
         */
        public void displayImage(final String uri, final ImageView imageView) {
            displayImage(uri, imageView, null);
        }

        /**
         * 图片显示
         * <pre>
         *     支持显示 Gif 图片第一帧
         * </pre>
         *
         * @param uri       Image Uri
         * @param imageView ImageView
         * @param options   {@link RequestOptions}
         */
        public void displayImage(final String uri, final ImageView imageView, final RequestOptions options) {
            if (mRequestManager != null && imageView != null) {
                if (options != null) {
                    mRequestManager.asBitmap().load(uri).apply(options).into(imageView);
                } else {
                    mRequestManager.asBitmap().load(uri).into(imageView);
                }
            }
        }

        /**
         * 先显示缩略图，再显示原图：用原图的1/10作为缩略图
         *
         * @param uri
         * @param imageView
         * @param options
         */
        public void displayThumbnailImage(final String uri, final ImageView imageView, final RequestOptions options) {
            if (mRequestManager != null && imageView != null) {
                if (options != null) {
                    mRequestManager.asBitmap().load(uri).apply(options).thumbnail(0.1f).into(imageView);
                } else {
                    mRequestManager.asBitmap().load(uri).thumbnail(0.1f).into(imageView);
                }
            }
        }

        /**
         * 图片处理
         */
        public void displayProcessImage( final String uri, final ImageView imageView, final RequestOptions options) {
            // 圆形裁剪
            Glide.with(sContext)
                    .load("http://inthecheesefactory.com/uploads/source/nestedfragment/fragments.png")
                    .transform(new CropCircleTransformation())
                    .into(imageView);
            // Glide圆角处理
            Glide.with(sContext)
                    .load("http://inthecheesefactory.com/uploads/source/nestedfragment/fragments.png")
                    .transform(new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL))
                    .into(imageView);
            // 灰度处理
            Glide.with(sContext)
                    .load("http://inthecheesefactory.com/uploads/source/nestedfragment/fragments.png")
                    .transform(new GrayscaleTransformation())
                    .into(imageView);

            // Glide中使用（如高斯模糊）  R.drawable.demo
            Glide.with(sContext).load("http://inthecheesefactory.com/uploads/source/nestedfragment/fragments.png")
                    .transform(new BlurTransformation())
                    .into(imageView);
           // 添加多种滤镜效果 R.drawable.demo
            Glide.with(sContext).load("http://inthecheesefactory.com/uploads/source/nestedfragment/fragments.png")
                    .transform(new BlurTransformation(25), new CropCircleTransformation())
                    .into(imageView);
        }

        /**
         * 先显示缩略图，再显示原图：用其它图片作为缩略图
         * @param uri
         * @param imageView
         * @param options
         */
//        public void displayImageWithOtherUrl(final String uri, final ImageView imageView, final RequestOptions options) {
//            DrawableRequestBuilder<Integer> thumbnailRequest = Glide
//                    .with(sContext)
//                    .load(R.drawable.news);
//            if (mRequestManager != null && imageView != null) {
//                if (options != null) {
//                    mRequestManager.asBitmap().load(uri).apply(options).thumbnail(thumbnailRequest).into(imageView);
//                } else {
//                    mRequestManager.asBitmap().load(uri).thumbnail(thumbnailRequest).into(imageView);
//                }
//            }
//        }
        // =

        /**
         * 图片显示
         *
         * @param uri       Image Uri
         * @param imageView ImageView
         */
        public void displayImageToGif(final String uri, final ImageView imageView) {
            displayImageToGif(uri, imageView, null);
        }

        /**
         * 图片显示
         *
         * @param uri       Image Uri
         * @param imageView ImageView
         * @param options   {@link RequestOptions}
         */
        public void displayImageToGif(final String uri, final ImageView imageView, final RequestOptions options) {
            if (mRequestManager != null && imageView != null) {
                if (options != null) {
                    mRequestManager.asGif().load(uri).apply(options).into(imageView);
                } else {
                    mRequestManager.asGif().load(uri).into(imageView);
                }
            }
        }

        // ============
        // = 图片加载 =
        // ============

        /**
         * 图片加载
         *
         * @param uri    Image Uri
         * @param target {@link Target}
         */
        public void loadImageBitmap(final String uri, final Target<Bitmap> target) {
            loadImageBitmap(uri, target, null);
        }

        /**
         * 图片加载
         *
         * @param uri     Image Uri
         * @param target  {@link Target}
         * @param options {@link RequestOptions}
         */
        public void loadImageBitmap(final String uri, final Target<Bitmap> target, final RequestOptions options) {
            if (mRequestManager != null) {
                if (options != null) {
                    mRequestManager.asBitmap().load(uri).apply(options).into(target);
                } else {
                    mRequestManager.asBitmap().load(uri).into(target);
                }
            }
        }

        // =

        /**
         * 图片加载
         *
         * @param uri    Image Uri
         * @param target {@link Target}
         */
        public void loadImageDrawable(final String uri, final Target<Drawable> target) {
            loadImageDrawable(uri, target, null);
        }

        /**
         * 图片加载
         *
         * @param uri     Image Uri
         * @param target  {@link Target}
         * @param options {@link RequestOptions}
         */
        public void loadImageDrawable(final String uri, final Target<Drawable> target, final RequestOptions options) {
            if (mRequestManager != null) {
                if (options != null) {
                    mRequestManager.asDrawable().load(uri).apply(options).into(target);
                } else {
                    mRequestManager.asDrawable().load(uri).into(target);
                }
            }
        }

        // =

        /**
         * 图片加载
         *
         * @param uri    Image Uri
         * @param target {@link Target}
         */
        public void loadImageFile(final String uri, final Target<File> target) {
            loadImageFile(uri, target, null);
        }

        /**
         * 图片加载
         *
         * @param uri     Image Uri
         * @param target  {@link Target}
         * @param options {@link RequestOptions}
         */
        public void loadImageFile(final String uri, final Target<File> target, final RequestOptions options) {
            if (mRequestManager != null) {
                if (options != null) {
                    mRequestManager.asFile().load(uri).apply(options).into(target);
                } else {
                    mRequestManager.asFile().load(uri).into(target);
                }
            }
        }

        /**
         * 图片加载
         *
         * @param uri    Image Uri
         * @param target {@link Target}
         */
        public void loadImageGif(final String uri, final Target<GifDrawable> target) {
            loadImageGif(uri, target, null);
        }

        /**
         * 图片加载
         *
         * @param uri     Image Uri
         * @param target  {@link Target}
         * @param options {@link RequestOptions}
         */
        public void loadImageGif(final String uri, final Target<GifDrawable> target, final RequestOptions options) {
            if (mRequestManager != null) {
                if (options != null) {
                    mRequestManager.asGif().load(uri).apply(options).into(target);
                } else {
                    mRequestManager.asGif().load(uri).into(target);
                }
            }
        }

        // ============
        // = 其他操作 =
        // ============

        /**
         * 取消图片显示任务
         *
         * @param view {@link View}
         */
        public void cancelDisplayTask(final View view) {
            if (mRequestManager != null && view != null) {
                mRequestManager.clear(view);
            }
        }

        /**
         * 取消图片显示任务
         *
         * @param target {@link Target}
         */
        public void cancelDisplayTask(final Target target) {
            if (mRequestManager != null && target != null) {
                mRequestManager.clear(target);
            }
        }

        // =

        /**
         * 销毁操作
         */
        public void destroy() {
            if (mRequestManager != null) {
                mRequestManager.onDestroy();
            }
        }

        /**
         * 暂停图片加载
         */
        public void pause() {
            if (mRequestManager != null) {
                mRequestManager.pauseAllRequests();
            }
        }

        /**
         * 恢复图片加载
         */
        public void resume() {
            if (mRequestManager != null) {
                mRequestManager.resumeRequests();
            }
        }

        /**
         * 停止图片加载
         */
        public void stop() {
            if (mRequestManager != null) {
                mRequestManager.onStop();
            }
        }

        /**
         * 开始图片加载
         */
        public void start() {
            if (mRequestManager != null) {
                mRequestManager.onStart();
            }
        }
    }

    // ============
    // = 其他操作 =
    // ============

    /**
     * 清除磁盘缓存
     */
    public static void clearDiskCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // This method must be called on a background thread.
                    Glide.get(sContext).clearDiskCache();
                } catch (Exception e) {
                    LogTagUtil.d(TAG, "清除磁盘缓存clearDiskCache, Exception=" + e.toString());
                }
            }
        }).start();
    }

    /**
     * 清除内存缓存
     */
    public static void clearMemoryCache() {
        try {
            // This method must be called on the main thread.
            Glide.get(sContext).clearMemory(); // 必须在主线程上调用该方法
        } catch (Exception e) {
            LogTagUtil.d(TAG, "清除内存缓存clearMemoryCache, Exception=" + e.toString());
        }
    }

    /**
     * 低内存通知
     */
    public static void onLowMemory() {
        try {
            Glide.get(sContext).onLowMemory();
        } catch (Exception e) {
            LogTagUtil.d(TAG, "低内存通知onLowMemory, Exception=" + e.toString());
        }
    }

    /**
     * 获取 SDCard 缓存空间
     *
     * @return SDCard 缓存空间 File
     */
    public static File getDiskCache() {
        try {
            return Glide.getPhotoCacheDir(sContext);
        } catch (Exception e) {
            LogTagUtil.d(TAG, "获取 SDCard 缓存空间,getDiskCache, Exception=" + e.toString());
        }
        return null;
    }
}
