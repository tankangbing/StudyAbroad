package com.app.studyabroad.jdbc;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.studyabroad.entity.User;
import com.app.studyabroad.util.FinalStr;

/**
 * �û���
 * @author Amao
 *
 */
public class DBManagerToUser {
	 private DatabaseHelper helper;
	    private SQLiteDatabase db;

	    public DBManagerToUser(Context context)
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
	    public void saveUser(User user)
	    {
	        Log.d(FinalStr.LOG_TAG, "�����û���Ϣ");
	        // ����������ȷ������������
	        db.beginTransaction(); // ��ʼ����
	        try
	        {
	        	if(null != user){
	        		db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_NAME_USER
	                        + " VALUES(?,?,?)", new Object[] {"1",user.getUserName(),user.getUserPsw()}); //���������ֻ��һ����¼
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
	    public void updateUser(int id,String userName,String Psw)
	    {
	        Log.d(FinalStr.LOG_TAG, "�޸��û���Ϣ");
	        ContentValues cv = new ContentValues();
	        cv.put("userName", userName);
	        cv.put("userPsw", Psw);
	        db.update(DatabaseHelper.TABLE_NAME_USER, cv, "id = ?",
	                new String[] {id+""});
	    }

	    /**
	     * ɾ��
	     * 
	     * @param plh
	     */
	    public void deleteUser()
	    {
	    	 Log.d(FinalStr.LOG_TAG, "ɾ���û���Ϣ");
		        // ����������ȷ������������
		        db.beginTransaction(); // ��ʼ����
		        try
		        {
	        		db.execSQL("delete from "+DatabaseHelper.TABLE_NAME_USER); //ɾ��
		            db.setTransactionSuccessful(); // ��������ɹ����
		        }
		        finally
		        {
		            db.endTransaction(); // ��������
		        }
	    }

	    /**
	     * ��ѯȫ��
	     * 
	     * @return List<Rsa>
	     */
	    public List<User> queryUser()
	    {
	        Log.d(FinalStr.LOG_TAG, "��ѯȫ���û���Ϣ");
	        ArrayList<User> userList = new ArrayList<User>(0);
	        Cursor c = queryTheCursor();
	        while (c.moveToNext())
	        {
	        	User user = new User();
	        	user.setId(c.getInt(c.getColumnIndex("id")));
	        	user.setUserName(c.getString(c.getColumnIndex("userName")));
	        	user.setUserPsw(c.getString(c.getColumnIndex("userPsw")));
	        	userList.add(user);
	        }
	        c.close();
	        return userList;
	    }

	    /**
	     * ��ѯ���м�¼
	     * 
	     * @return Cursor
	     */
	    public Cursor queryTheCursor()
	    {
	        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME_USER,
	                null);
	        return c;
	    }
	    
	    /**
	     * �ر���������
	     */
	    public void closeDB()
	    {
	        Log.d(FinalStr.LOG_TAG, "�ر����ݿ�����");
	        // �ͷ����ݿ���Դ
	        db.close();
	    }

}
