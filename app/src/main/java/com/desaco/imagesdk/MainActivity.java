package com.desaco.imagesdk;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.desaco.imageloader.image.ImageConfig;
import com.desaco.imageloader.image.ImageLoadBaseTool;
import com.desaco.imageloader.image.ImageLoadProcessInterface;
import com.desaco.imageloader.utils.LogTagUtil;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        mImageView = findViewById(R.id.pic_iv);

//        button.setOnClickListener(view -> initSelectPictureManager());//这是lambda的使用方式

        String path;
//      path = "https://upload-images.jianshu.io/upload_images/5207488-9b7d8d755f83092b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp";
//        path = "https://t-static-shopping.cxzx10086.cn/img/shopping/封面图01_1601192723146.png";
//        path = "https://www.baidu.com";
        path = "https://t-static-shopping.cxzx10086.cn/img/shopping/index_del_list3_1607481816933.png";

        showImage(mImageView, path);

        LogTagUtil.e("desaco", "平板返回 True，手机返回 False , isTablet= " + isTablet(this));


//        tryCatch();

    }

    /**
     * 展示图片
     *
     * @param path
     */
    private void showImage(ImageView imageView, String path) {
        ImageLoadBaseTool.setPrintLog(true); // 打印日志

        ImageConfig config = new ImageConfig();
        config.setDefaultRes(R.mipmap.p2);
        config.setFailRes(R.mipmap.p2);
        config.setRadius(15);
        config.setScaleType(ImageView.ScaleType.CENTER_CROP); // CENTER_CROP
        config.setWidth(300);
        config.setHeight(300);

        ImageLoadBaseTool.getInstance(false).display(this, imageView, path,
                config, // new ImageConfig(R.mipmap.p2, R.mipmap.p2, 20)
                new ImageLoadProcessInterface() {

                    @Override
                    public void onLoadStarted() {
                        LogTagUtil.e("desaco", "onLoadStarted");
                    }

                    @Override
                    public void onResourceReady() {
                        LogTagUtil.e("desaco", "onResourceReady");
                    }

                    @Override
                    public void onLoadCleared() {
                        LogTagUtil.e("desaco", "onLoadCleared");
                    }

                    @Override
                    public void onLoadFailed() {
                        LogTagUtil.e("desaco", "onLoadFailed");
                    }
                });
    }

    public void tryCatch() {
        System.out.println("try block");
        System.exit(0);

        try {
            LogTagUtil.e("desaco", "try");
            int a = 3 / 0;
            return;
        } catch (Exception ex) {
            LogTagUtil.e("desaco", "catch");
            return;
        } finally {
            LogTagUtil.e("deaco", "finally");
        }
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
