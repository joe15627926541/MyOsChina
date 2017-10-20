package move.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/13.
 */

public class ClickHelper extends SQLiteOpenHelper {

    public ArrayList<Integer> list;
    public ArrayList<Integer> list1;

    public ClickHelper(Context context) {
        super(context, "click", null, 1);
        list=new ArrayList<>();
        list1=new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table click (id integer,isclick integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //插入数据的方法
    public void  add(int id,int isclick,SQLiteDatabase db){
        ContentValues values = new ContentValues();//是用map封装的对象，用来存放值
        values.put("id",id);
        values.put("isclick",isclick);
        db.insert("click",null,values);
    }
    //删除数据
    public void delete(int id,SQLiteDatabase db) {
        db.execSQL("delete from click where id=?", new Object[] { id });
    }



    //查询所有的点击过的id的数据的方法

    public ArrayList<Integer> query(SQLiteDatabase db){

        Cursor cursor = db.query("click",null, null, null, null, null, null);

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


    //查询所有的点击过的Boolean的数据的方法

    public ArrayList<Integer> query1(SQLiteDatabase db){

        Cursor cursor = db.query("click",null, null, null, null, null, null);

        //解析Cursor中的数据
        if(cursor != null && cursor.getCount() >0){//判断cursor中是否存在数据

            //循环遍历结果集，获取每一行的内容
            while(cursor.moveToNext()){//条件，游标能否定位到下一行
                //获取数据
                int isclick = cursor.getInt(1);
                list1.add(isclick);
            }
        }
        return list1;
    }



}
