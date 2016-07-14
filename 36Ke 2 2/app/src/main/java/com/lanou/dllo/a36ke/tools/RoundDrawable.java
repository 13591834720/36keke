package com.lanou.dllo.a36ke.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by dllo on 16/7/4.
 */
public class RoundDrawable extends Drawable {
    private Bitmap bitmap;
    private Paint paint;
    private RectF rectF;
    private float x;
    private float y;

    public RoundDrawable(Bitmap bitmap) {
        this.bitmap = bitmap;
        paint = new Paint();
        //设置画笔的着色器,可以理解成画笔的花纹,设置好后 ,用该画笔画任何东西,都是这张Bitmap
        //CLAMP是将Bitmap周围一圈像素拉伸
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
    }

    //核心方法,可以将我们需要的内容绘制到屏幕上
    @Override
    public void draw(Canvas canvas) {
        //画圆角矩形
        //canvas.drawRoundRect(rectF,200,200,paint);
        float min = Math.min(x,y);
        canvas.drawCircle(min/2f,min/2f,min/2f,paint);
    }


    //该方法确定Drawable范围
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rectF = new RectF(left,top,right,bottom);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }
    //当ImageView宽高自适应 ,来告诉Drawerable他的宽高
    @Override
    public int getIntrinsicWidth() {
        x=bitmap.getWidth();
        return bitmap.getWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        y=bitmap.getHeight();
        return bitmap.getHeight();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return 0;
    }
}

