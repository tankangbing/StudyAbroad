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
import com.app.studyabroad.entity.Rsa;
import com.app.studyabroad.util.FinalStr;
import com.app.studyabroad.util.SysUtil;

/**
 * Rsa�����㷨�����
 * @author Amao
 *
 */
public class DBManagerToRsa {
	 private DatabaseHelper helper;
	    private SQLiteDatabase db;

	    public DBManagerToRsa(Context context)
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> oncreate");
	        helper = new DatabaseHelper(context);
	        // ��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0,
	        // mFactory);
	        // ����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate��
	        db = helper.getWritableDatabase();
	    }

	    /**
	     * save
	     */
	    public void saveRsa(Rsa rsa)
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> saveRsa");
	        // ����������ȷ������������
	        db.beginTransaction(); // ��ʼ����
	        try
	        {
	        	if(null != rsa){
	        		db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_NAME_RSA
	                        + " VALUES(?,?,?,?,?)", new Object[] {"1",rsa.getAppRsaModulus(),rsa.getSysRsaModulus(),
	        				rsa.getSysRsaPrivateExponent(),rsa.getAppRsaPublicExponent()}); //���������ֻ��һ����¼
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
	     */
	    public void updateRsa(int id,String appRsaModulus,String sysRsaModulus,String sysRsaPrivateExponent,String appRsaPublicExponent)
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> updateRsa");
	        ContentValues cv = new ContentValues();
	        cv.put("appRsaModulus", appRsaModulus);
	        cv.put("sysRsaModulus", sysRsaModulus);
	        cv.put("sysRsaPrivateExponent", sysRsaPrivateExponent);
	        cv.put("appRsaPublicExponent", appRsaPublicExponent);
	        db.update(DatabaseHelper.TABLE_NAME_RSA, cv, "id = ?",
	                new String[] {id+""});
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
	     * ��ѯȫ��
	     * 
	     * @return List<Rsa>
	     */
	    public List<Rsa> queryRsa()
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> query Rsa*");
	        ArrayList<Rsa> rsaList = new ArrayList<Rsa>(0);
	        Cursor c = queryTheCursor();
	        while (c.moveToNext())
	        {
	        	Rsa rsa = new Rsa();
	        	rsa.setId(c.getInt(c.getColumnIndex("id")));
	        	rsa.setAppRsaModulus(c.getString(c.getColumnIndex("appRsaModulus")));
	        	rsa.setSysRsaModulus(c.getString(c.getColumnIndex("sysRsaModulus")));
	        	rsa.setSysRsaPrivateExponent(c.getString(c.getColumnIndex("sysRsaPrivateExponent")));
	        	rsa.setAppRsaPublicExponent(c.getString(c.getColumnIndex("appRsaPublicExponent")));
	        	rsaList.add(rsa);
	        }
	        c.close();
	        return rsaList;
	    }

	    /**
	     * ��ѯ���м�¼
	     * 
	     * @return Cursor
	     */
	    public Cursor queryTheCursor()
	    {
	        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME_RSA,
	                null);
	        return c;
	    }
	    
	    /**
	     * ������Ϣ����
	     * @return
	     */
	    public Cursor queryByMap(Map<String,Object> map)
	    {
	        Log.d(FinalStr.LOG_TAG, "DBManager --> queryByMap Rsa");
	        StringBuffer sql = new StringBuffer("SELECT * FROM " + DatabaseHelper.TABLE_NAME_RSA);
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
