package com.example.asdf.myoschina.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/11.
 */

public class DbHelper extends SQLiteOpenHelper {
    public ArrayList<Integer>list;

    public DbHelper(Context context) {
        super(context, "info", null, 1);
        list=new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info (id integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //插入数据的方法
    public void  add(int id,SQLiteDatabase db){
        ContentValues values = new ContentValues();//是用map封装的对象，用来存放值
        values.put("id",id);
        db.insert("info",null,values);
    }
    //删除数据
    public void delete(int id,SQLiteDatabase db) {
        db.execSQL("delete from info where id=?", new Object[] { id });
    }



    //查询所有的数据的方法

    public ArrayList<Integer> query(SQLiteDatabase db){

        Cursor cursor = db.query("info",null, null, null, null, null, null);

        //解析Cursor中的数据
        if(cursor != null && cursor.getCount() >0){//判断cursor中是否存在数据

            //循环遍历结果集，获取每一行的内容
            while(cursor.moveToNext()){//条件，游标能否定位到下一行
                //获取数据
                int id = cursor.getInt(0);
                list.add(id);
            }
        }
        return list;
    }




}
