package com.app.studyabroad.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/* 
 * �������������Ƿ񳬳���󳤶ȣ������ù��λ�� 
 * */  
public class MaxLengthWatcher implements TextWatcher {  
  
    private int maxLen = 0;  
    private EditText editText = null;
    private Context context;
      
      
    public MaxLengthWatcher(int maxLen, EditText editText,Context c) {  
        this.maxLen = maxLen;  
        this.editText = editText;
        this.context = c;
    }  
  
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,  
            int arg3) {  
        // TODO Auto-generated method stub  
          
    }  
  
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {  
        // TODO Auto-generated method stub  
        Editable editable = editText.getText();  
        int len = editable.length();  
          
        if(len > maxLen)  
        {  
            int selEndIndex = Selection.getSelectionEnd(editable);  
            String str = editable.toString();  
            //��ȡ���ַ���  
            String newStr = str.substring(0,maxLen);  
            editText.setText(newStr);  
            editable = editText.getText();  
              
            //���ַ����ĳ���  
            int newLen = editable.length();  
            //�ɹ��λ�ó����ַ�������  
            if(selEndIndex > newLen)  
            {  
            	Toast.makeText(context, "��������۲��ܳ���100���ַ���", Toast.LENGTH_LONG).show();
                selEndIndex = editable.length();  
            }  
            //�����¹�����ڵ�λ��  
            Selection.setSelection(editable, selEndIndex);  
              
        }  
    }

	@Override
	public void afterTextChanged(Editable arg0) {	
		
	}  
}
