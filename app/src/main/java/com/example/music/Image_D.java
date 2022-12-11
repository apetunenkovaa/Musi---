package com.example.music;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Image_D {
    Context mContext;

    public Image_D(Context mContext) {
        this.mContext = mContext;
    }

    public Bitmap getUserImage(String encodedImg)
    {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
            return BitmapFactory.decodeResource(Image_D.this.mContext.getResources(), R.drawable.img);
    }
}
