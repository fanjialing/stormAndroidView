package com.laanto.it.app.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.*;

import com.laanto.it.app.manager.LogManager;

/**
 * DB  tools
 * 
 * @author fanjialing
 *
 * @date 2015年12月3日 下午5:49:12
 *
 */
@SuppressWarnings("all")
public class DBHelper extends SQLiteOpenHelper {
    //private SQLiteDatabase sqLiteDatabase;
    //private static DBHelper helper = null;
	private String TAG = "DBHelper";

    /**
     * 构造函数
     * @param context
     * @param dataBaseName 数据库名
     * @param version 数据库版本
     */
    public DBHelper(Context context, String dataBaseName, int version) {
        super(context, dataBaseName, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建表
       // this.sqLiteDatabase = sqLiteDatabase;
        LogManager.i("create-db","join DBHelper onCreate...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    /**
     * 查询一条记录
     * @param sql　select 语句，该语句里的参数使用?占位符.可以是多表联查语句
     * @param params　参数
     * @return　返回一个map集合，该集合里key是查询的列名,value是列对应的值。有几个列就有几个key和value
     * 注意：如果select语句返回的记录条数有多个，该方法将产生RuntimeException。
     * 如果没有任何记录，则返回一个size为０的map对象，而非null
     * sql示例:
     * select * from user wehre id=?,name=?;
     */
    public Map selectOne(String sql, String... params) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Map map = new HashMap();
        Cursor c = sqLiteDatabase.rawQuery(sql,params);
        if(c.getCount() > 1) {
            throw new RuntimeException("查询结果存在多条数据....,不符合要求!");
        } else if(c.getCount() == 0) {
            return map;
        }
        String [] columns = c.getColumnNames();

        while (c.moveToNext()) {
            for(String s : columns) {
                Object data = c.getString(c.getColumnIndex(s));
                map.put(s,data);
            }
        }
        return map;
    }
    /**
     * 查询多条记录
     * @param sql　select 语句，该语句里的参数使用?占位符，可以是多表联查语句。
     * @param params　参数
     * @return　返回一个List<Map>集合，
     * 该集合里每个Map就是一条记录，集合里每个Map的key是查询的列名,value是列对应的值。有几个列就有几个key和value
     * 注意：如果select语句返回的记录数为０则返回一个size为0的map对象而非null
     * sql示例:
     * select * from user wehre id=?,name=?;
     * select u.name,u.password,c.email,c.login from user as u,cata as c where c.id = u.id
     */
    public List<Map> select(String sql, String... params) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        List datas = new ArrayList();
        Cursor c = sqLiteDatabase.rawQuery(sql,params);
        if(c.getCount() == 0) {
            return datas;
        }
        String [] columns = c.getColumnNames();
        Map map = null;
        while (c.moveToNext()) {
            map = new HashMap();
            for(String s : columns) {
                Object data = c.getString(c.getColumnIndex(s));
                map.put(s,data);
            }
            datas.add(map);
        }
        return datas;
    }

    /**
     * dml操作
     * @param sql insert、update、delete,drop等语句.
     * @param params　参数
     * @return 操作成功返回true,否则返回false
     * 查询语句请使用select或selectOne,本方法不支持查询语句的结果返回.
     * 也可以使用本类提供的
     *            delete(),update(),insert(),drop()方法进行相关处理.这些方法调用execDMLSQL()作为底层处理
     */
    public boolean execDMLSQL(String sql, String... params) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            sqLiteDatabase.execSQL(sql, params);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 新增操作
     * @param insertSql
     * @param params
     * @return
     */
    public boolean insert(String insertSql, String... params) {

        return execDMLSQL(insertSql,params);
    }

    /**
     * 删除操作
     * @param deleteSql
     * @param params
     * @return
     */
    public boolean delete(String deleteSql, String... params) {

        return execDMLSQL(deleteSql,params);
    }

    /**
     * 更新操作
     * @param updateSql
     * @param params
     * @return
     */
    public boolean update(String updateSql, String... params) {

        return execDMLSQL(updateSql,params);
    }

    /**
     * drop操作
     * @param dropSql
     * @param params
     * @return
     */
    public boolean drop(String dropSql, String... params) {
        return execDMLSQL(dropSql,params);
    }

}
