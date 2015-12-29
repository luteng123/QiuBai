package com.luteng.qiubai.Utils;

import android.graphics.*;
import com.squareup.picasso.Transformation;

/**
 * Created by John on 2015/12/29.
 */
public class CircleTransform implements Transformation{
    public Bitmap transform(Bitmap source){
        Bitmap result = Bitmap.createBitmap(source.getWidth(),source.getHeight()
                                    ,Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        new Canvas(result).drawCircle(source.getWidth()/2,source.getHeight()/2,
                                    Math.min(source.getHeight(),source.getWidth())/2,paint);
        //释放原图
        source.recycle();
        return result;
    }

    @Override
    public String key() {
        return "circle";
    }
}
