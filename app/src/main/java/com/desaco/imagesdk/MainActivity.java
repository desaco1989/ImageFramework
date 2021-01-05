package com.desaco.imagesdk;

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

import java.io.File;


public class MainActivity extends AppCompatActivity {

    View parent_view;
    TextView text;
    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        parent_view = this.findViewById(R.id.parent_view);
//        text = this.findViewById(R.id.text);
//        button = this.findViewById(R.id.button);
        imageView = findViewById(R.id.pic_iv);

//        button.setOnClickListener(view -> initSelectPictureManager());//这是lambda的使用方式

        String path;
//      path = "https://upload-images.jianshu.io/upload_images/5207488-9b7d8d755f83092b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp";
        path = "https://t-static-shopping.cxzx10086.cn/img/shopping/%E7%BB%84%20233_1609316610960.png";
//        path = "https://www.baidu.com";

        showImage(imageView, path);
    }

    /**
     * 展示图片
     *
     * @param path
     */
    void showImage(ImageView imageView, String path) {
        ImageLoadBaseTool.display(this, imageView, path,
                new ImageConfig(R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, 25),
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
}
