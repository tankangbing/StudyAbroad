package com.app.studyabroad.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.studyabroad.entity.PlayHistory;
import com.app.studyabroad.util.FinalStr;
import com.app.studyabroad.util.SysUtil;

/**
 * ���ż�¼�����
 * @author Amao
 *
 */
public class DBManagerToPlayHistory {
	 private DatabaseHelper helper;
	    private SQLiteDatabase db;

	    public DBManagerToPlayHistory(Context context)
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> oncreate");
	        helper = new DatabaseHelper(context);
	        // ��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0,
	        // mFactory);
	        // ����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate��
	        db = helper.getWritableDatabase();
	    }

	    /**
	     * ������Ӳ�����ʷ��¼
	     * 
	     * @param playhistoryList
	     */
	    public void add(List<PlayHistory> playhistoryList)
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> add");
	        // ����������ȷ������������
	        db.beginTransaction(); // ��ʼ����
	        try
	        {
	            for (PlayHistory phl : playhistoryList)
	            {
	                db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_NAME
	                        + " VALUES(null, ?, ?, ?)", new Object[] { phl.getMateid(),
	                        phl.getUserid(), phl.getTime() });
	                // ������������execSQL()����������ռλ�����������Ѳ���ֵ���ں��棬˳���Ӧ
	                // һ��������execSQL()�����У��û����������ַ�ʱ��Ҫת��
	                // ʹ��ռλ����Ч�������������
	            }
	            db.setTransactionSuccessful(); // ��������ɹ����
	        }
	        finally
	        {
	            db.endTransaction(); // ��������
	        }
	    }
	    
	    /**
	     * �������ż�¼
	     * 
	     * @param playhistoryList
	     */
	    public void savePlayHis(PlayHistory plh)
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> savePlayHis");
	        // ����������ȷ������������
	        db.beginTransaction(); // ��ʼ����
	        try
	        {
	        	if(null != plh){
	        		db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_NAME
	                        + " VALUES(null, ?, ?, ?)", new Object[] { plh.getMateid(),
	                		plh.getUserid(), plh.getTime() });
		            db.setTransactionSuccessful(); // ��������ɹ����
	        	}
	        }
	        finally
	        {
	            db.endTransaction(); // ��������
	        }
	    }

	    /**
	     * �޸�
	     * 
	     * @param  plh
	     */
	    public void updatePlayHis(PlayHistory plh)
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> updatePlayHis");
	        ContentValues cv = new ContentValues();
	        cv.put("time", plh.getTime());
	        db.update(DatabaseHelper.TABLE_NAME, cv, "mateid = ? and userid = ?",
	                new String[] { plh.getMateid(),plh.getUserid() });
	    }

	    /**
	     * ɾ��
	     * 
	     * @param plh
	     */
	    public void deleteOldPerson(PlayHistory plh)
	    {
	      //todo
	    }

	    /**
	     * ��ѯ
	     * 
	     * @return List<PlayHistory>
	     */
	    public List<PlayHistory> query()
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> query");
	        ArrayList<PlayHistory> plhList = new ArrayList<PlayHistory>(0);
	        Cursor c = queryTheCursor();
	        while (c.moveToNext())
	        {
	        	PlayHistory plh = new PlayHistory();
	        	plh.setId(c.getInt(c.getColumnIndex("id"))); //id
	        	plh.setMateid(c.getString(c.getColumnIndex("mateid"))); //����id
	        	plh.setUserid(c.getString(c.getColumnIndex("userid"))); //�û�id
	        	plh.setTime(c.getInt(c.getColumnIndex("time"))); //ʱ��
	        	plhList.add(plh);
	        }
	        c.close();
	        return plhList;
	    }

	    /**
	     * ��ѯ���м�¼
	     * 
	     * @return Cursor
	     */
	    public Cursor queryTheCursor()
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> selete *");
	        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME,
	                null);
	        return c;
	    }
	    
	    /**
	     * ������Ϣ���Ҳ��ż�¼
	     * @return
	     */
	    public Cursor queryByMap(Map<String,Object> map)
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> queryByMap");
	        StringBuffer sql = new StringBuffer("SELECT * FROM " + DatabaseHelper.TABLE_NAME);
	        if(!SysUtil.isEmpty(map)){
        		sql.append(" where 1 = 1");
	        	for (String key : map.keySet()) {
	        		sql.append(" and "+key+" = '"+map.get(key)+"'");
	        	}
	        }
	        Cursor c = db.rawQuery(sql.toString(),
	                null);
	        return c;
	    } 

	    /**
	     * �ر���������
	     */
	    public void closeDB()
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> closeDB");
	        // �ͷ����ݿ���Դ
	        db.close();
	    }

}
