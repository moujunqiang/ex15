package com.example.ex15;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.PersistableBundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends BaseAdapter {

    private List<PersonInfoBean> mStudentDataList;   //创建一个StudentData 类的对象 集合
    private LayoutInflater inflater;

    public MyListAdapter(List<PersonInfoBean> mStudentDataList, Context context) {
        this.mStudentDataList = mStudentDataList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mStudentDataList == null ? 0 : mStudentDataList.size();  //判断有说个Item
    }

    @Override
    public Object getItem(int position) {
        return mStudentDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.listview_item, null);
        PersonInfoBean mStudentData = (PersonInfoBean) getItem(position);

        //在view 视图中查找 组件
        TextView tpid = (TextView) view.findViewById(R.id.tpid);
        TextView sxbq = (TextView) view.findViewById(R.id.sxbq);
        TextView sfzh = (TextView) view.findViewById(R.id.sfzh);
        TextView xm = (TextView) view.findViewById(R.id.xm);
        TextView ryid = (TextView) view.findViewById(R.id.ryid);
        TextView xsd = (TextView) view.findViewById(R.id.xsd);
        TextView xb = (TextView) view.findViewById(R.id.xb);

        tpid.setText(mStudentData.getTpid());
        sxbq.setText(mStudentData.getSxbq());
        sfzh.setText("身份证号：" + mStudentData.getSfzh());
        xm.setText("姓名：" + mStudentData.getXm());
        ryid.setText(mStudentData.getRyid());
        xsd.setText(mStudentData.getXsd() + "");
        xb.setText("性别：" + mStudentData.getXb());
        setImage("data:image/jpg;base64," + mStudentData.getImageBase64(), (ImageView) view.findViewById(R.id.image));
        //返回含有数据的view
        return view;
    }

    //将返回的base64转换成图片
    public static void setImage(String imageStr, ImageView image) {
        byte[] decode = Base64.decode(imageStr.split(",")[1], Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        image.setImageBitmap(bitmap);
    }
}