package com.app.studyabroad.util;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;



/**
 * 自定义圆
 * Created by DELL on 2018/1/22.
 */

public class CircleImageView extends View{
    /**
     * 图片
     */
    private Bitmap mSrc;

    /**
     * 控件的宽度
     */
    private int mWidth;
    /**
     * 控件的高度
     */
    private int mHeight;
    /**
     *内颜色
     */
    private int inColor;
    /**
     * 边框颜色
     */
    private int outColor;
    /**
     *边框粗细
     */
    private int outStrokeWidth;
    /**
     * 文字
     */
    private String mText;
    /**
     * 文字颜色
     */
    private int mTextColor;
    /**
     * 文字大小
     */
    private float mTextSize;

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context) {
        this(context, null);
        inColor = Color.WHITE;
        mTextColor = Color.BLACK;
        outColor = Color.GRAY;
        outStrokeWidth = 10;
        mWidth = 130;
        mHeight = 130;
        mText = "";
        mTextSize = 50;
    }

    /**
     * 初始化一些自定义的参数
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StockManage);
//        mSrc = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.StockManage_icon, -1));
//        if (mSrc == null) {
//            inColor = array.getColor(R.styleable.StockManage_inColor, Color.WHITE);
//        }
//        mTextColor = array.getColor(R.styleable.StockManage_textColor, Color.BLACK);
//        outColor = array.getColor(R.styleable.StockManage_outColor, -1);
//        outStrokeWidth = array.getDimensionPixelSize(R.styleable.StockManage_stroke, 0);
//        mWidth = array.getDimensionPixelSize(R.styleable.StockManage_width, 0);
//        mHeight = array.getDimensionPixelSize(R.styleable.StockManage_height, 0);
//        mText = array.getString(R.styleable.StockManage_text);
//        mTextSize = array.getDimension(R.styleable.StockManage_textSize,R.dimen.text_size_sub);// 如果设置为DP等单位，会做像素转换
//        //释放资源
//        array.recycle();

    }

    /**
     * 计算控件的高度和宽度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHeight);
    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {

        int min = Math.min(mWidth, mHeight);
        /**
         * 长度如果不一致，按小的值进行压缩
         */
        if (mSrc != null) {
            mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);
            canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
        } else {
            canvas.drawBitmap(createCircleImage(null, min), 0, 0, null);
        }
    }

    /**
     * 根据原图和边长绘制圆形图片
     *
     * @param source
     *            color 这两个参数只能取一个
     * @param min
     * @return
     */
    private Bitmap createCircleImage(Bitmap source, int min) {

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);

        /**
         * 使用SRC_IN，参考上面的说明
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        if (source != null)// 画图片
            canvas.drawBitmap(source, 0, 0, paint);
        else { // 画圆
            paint.setColor(inColor);
            canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        }

        if (outColor != 0) {
            // 让画出的图形是空心的
            paint.setStyle(Paint.Style.STROKE);
            // 设置画出的线的 粗细程度
            paint.setStrokeWidth(outStrokeWidth);
            paint.setColor(outColor);
            canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        }
        if(mText != null){
            // 让画出的图形是实心的
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
            if(mTextColor != 0 ){
                paint.setColor(mTextColor);
            }
            if(mTextSize != 0 ){
                paint.setTextSize(mTextSize);
            }
            paint.setTextAlign(Paint.Align.CENTER);
            FontMetrics fm = paint.getFontMetrics();
            //得到文字的高度
            float fFontHeight = (float)Math.ceil(fm.descent - fm.ascent);

            canvas.drawText(mText, mWidth/2, mHeight/2+fFontHeight/4, paint);
        }
        return target;
    }

    //设置文字
    public void setText(String string) {
        mText = string;
        //重绘
        invalidate();
        requestLayout();
    }

    //设置内颜色
    public void setInColor(int color) {
        inColor = color;
        //重绘
        invalidate();
        requestLayout();
    }
    public String getText(){
        return mText;
    }
}

