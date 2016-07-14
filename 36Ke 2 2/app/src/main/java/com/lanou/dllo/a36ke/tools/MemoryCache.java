package com.lanou.dllo.a36ke.tools;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by dllo on 16/6/21.
 */
public class MemoryCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> lruCache;

    public MemoryCache() {
        int maxSize= (int) (Runtime.getRuntime().maxMemory()/1024/8);

        lruCache=new LruCache<String, Bitmap>(maxSize){
            //告诉LruCache每一个bitmap占用多大内存
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;

            }
        };


    }
    @Override
    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url,bitmap);

    }
}
