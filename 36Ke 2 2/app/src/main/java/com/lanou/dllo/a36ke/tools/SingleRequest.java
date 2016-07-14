package com.lanou.dllo.a36ke.tools;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by dllo on 16/6/21.
 */
public class SingleRequest {
    private  static  SingleRequest singleRequest;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    private SingleRequest(Context context){
        requestQueue= Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new MemoryCache());
    }

    public  static  SingleRequest getSingleRequest(Context context){
        if (singleRequest==null){
            synchronized (SingleRequest.class){
                if (singleRequest==null){
                    singleRequest=new SingleRequest(context);
                }
            }
        }return  singleRequest;
    }

}
