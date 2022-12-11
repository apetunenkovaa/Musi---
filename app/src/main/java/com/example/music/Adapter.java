package com.example.music;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter  extends BaseAdapter {
    private Context mContext;
    String Image="";
    List<Mask> maskList;

    public Adapter(Context mContext, List<Mask> list) {
        this.mContext = mContext;
        this.maskList = list;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getID();
    }

    private Bitmap getUserImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
        {
            return null;

        }
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        @SuppressLint("ViewHolder") View v = View.inflate(mContext,R.layout.mask,null);
        TextView Name = v.findViewById(R.id.tx_Name);
        TextView Executor = v.findViewById(R.id.tx_Executor);
        TextView Genre = v.findViewById(R.id.tx_Genre);
        TextView Duration = v.findViewById(R.id.tx_Duration);
        ImageView Image = v.findViewById(R.id.image);

        Mask mask = maskList.get(i);
        Name.setText(mask.getName());
        Executor.setText(mask.getExecutor());
        Genre.setText(mask.getGenre());
        Duration.setText(mask.getDuration());


        Image_D DI = new Image_D(mContext);
        Image.setImageBitmap(DI.getUserImage(mask.getImage()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Edit.class);
                intent.putExtra(Mask.class.getSimpleName(), mask);
                mContext.startActivity(intent);
            }
        });

        return  v;

    }


}

