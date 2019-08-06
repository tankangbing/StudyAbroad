package com.app.studyabroad.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

public class FloatingFunc {
	 /**
     * 浮动窗口在屏幕中的x坐标
     */
    private static float x = 0;
    /**
     * 浮动窗口在屏幕中的y坐标
     */
    private static float y = 150;
    /**
     * 屏幕触摸状态，暂时未使用
     */
    private static float state = 0;
    /**
     * 鼠标触摸开始位置
     */
    private static float mTouchStartX = 0;
    /**
     * 鼠标触摸结束位置
     */
    private static float mTouchStartY = 0;
    /**
     * windows 窗口管理器
     */
    private static WindowManager wm = null;

    /**
     * 浮动显示对象
     */
    private static View floatingViewObj = null;

    /**
     * 参数设定类
     */
    public static LayoutParams params = new LayoutParams();
    public static int TOOL_BAR_HIGH = 0;
    /**
     * 要显示在窗口最前面的对象
     */
    private static View view_obj = null;

    /**
     * 要显示在窗口最前面的方法
     *
     * @param context
     *            调用对象Context getApplicationContext()
     * @param wm2
     *            调用对象 Window getWindow()
     * @param floatingViewObj
     *            要显示的浮动对象 View
     */
    public static void show(Context context, WindowManager wm2, LinearLayout floatingViewObj,DisplayMetrics dm) {
        // 加载xml文件中样式例子代码
        // ********************************Start**************************
        // LayoutInflater inflater =
        // LayoutInflater.from(getApplicationContext());
        // View view = inflater.inflate(R.layout.topframe, null);
        // wm =
        // (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        // 加载xml文件中样式例子代码
        // *********************************End***************************
        //
        // 关闭浮动显示对象然后再显示
        close(context);
        FloatingFunc.floatingViewObj = floatingViewObj;

        view_obj = floatingViewObj;
       // Rect frame = new Rect();
        // 这一句是关键，让其在top 层显示
        // getWindow()
       // wm2.getDecorView().getWindowVisibleDisplayFrame(frame);
       // TOOL_BAR_HIGH = frame.top;

//        wm = (WindowManager) context// getApplicationContext()
//                .getSystemService(Activity.WINDOW_SERVICE);
        wm = wm2;
        params.type = LayoutParams.TYPE_SYSTEM_ALERT
                | LayoutParams.TYPE_SYSTEM_OVERLAY;
        params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                | LayoutParams.FLAG_NOT_FOCUSABLE;
        
      //  params.type =  2003;//LayoutParams.TYPE_PHONE; // 设置window type  
        params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明  

      // 设置Window flag  
//      params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL  
//              | LayoutParams.FLAG_NOT_FOCUSABLE;  

        // 设置悬浮窗口长宽数据
        params.width = LayoutParams.WRAP_CONTENT;
        params.height = LayoutParams.WRAP_CONTENT;
        // 设定透明度
        params.alpha = 80;
        // 设定内部文字对齐方式
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.TOP;
        
      //获取屏幕
      // 设置悬浮窗口长宽数据  
      //android.view.ViewGroup.LayoutParams.WRAP_CONTENT;// 40;
//      int width = dm.widthPixels;
//      int height = dm.heightPixels;
//      if(width<height){
//      	height = 270;
//      }
//      params.height = height;//android.app.ActionBar.LayoutParams.WRAP_CONTENT;// 40;  
//      params.width =  width;

        // 以屏幕左上角为原点，设置x、y初始值ֵ
        params.x = (int) x;
        params.y = (int) y;
        // tv = new MyTextView(TopFrame.this);
        wm.addView(floatingViewObj, params);

    }

    /**
     * 跟谁滑动移动
     *
     * @param event
     *            事件对象
     * @param view
     *            弹出对象实例（View）
     * @return
     */
    public static boolean onTouchEvent(MotionEvent event, View view) {

        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY() - 25; // 25是系统状态栏的高度
        Log.i("currP", "currX" + x + "====currY" + y);// 调试信息
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            state = MotionEvent.ACTION_DOWN;
            // panTime();
            // 获取相对View的坐标，即以此View左上角为原点
            mTouchStartX = event.getX();
            mTouchStartY = event.getY();
            Log.i("startP", "startX" + mTouchStartX + "====startY"
                    + mTouchStartY);// 调试信息

            break;
        case MotionEvent.ACTION_MOVE:
            state = MotionEvent.ACTION_MOVE;
            updateViewPosition(view);
            break;

        case MotionEvent.ACTION_UP:
            state = MotionEvent.ACTION_UP;
            updateViewPosition(view);
            mTouchStartX = mTouchStartY = 0;
            break;
        }
        return true;
    }

    /**
     * 关闭浮动显示对象
     */
    public static void close(Context context) {

        if (view_obj != null && view_obj.isShown() && null != wm) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Activity.WINDOW_SERVICE);
            wm.removeView(view_obj);
        }
    }

    /**
     * 更新弹出窗口位置
     */
    private static void updateViewPosition(View view) {
        // 更新浮动窗口位置参数
        params.x = (int) (x - mTouchStartX);
        params.y = (int) (y - mTouchStartY);
        wm.updateViewLayout(FloatingFunc.floatingViewObj, params);
    }

}
