package com.rocky.indexbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author rocky
 * @date 2019/4/26.
 * description：这个是自定 索引条 实现点击快速检索到对应条目
 */
public class IndexBar extends View {

    private int normalColor;
    private int selectColor;
    private int indexSize;
    private Paint mPaint;
    private Rect mRect;
    private int paddingTop;
    private int paddingBottom;
    private int mHeight;
    private int mCellWidth;
    private float mCellHeight;
    private float beginY;

    //布局加载完成后 获得 测量的padding top 和 bottom
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
    }

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //第一步 继承view 设置属性
    public IndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndexBar, defStyleAttr, 0);
        normalColor = a.getColor(R.styleable.IndexBar_normalColor, Color.BLACK);
        selectColor = a.getColor(R.styleable.IndexBar_selectColor, Color.WHITE);
        indexSize = a.getDimensionPixelSize(R.styleable.IndexBar_indexSize, sp2px(14));
        a.recycle();
        init();
    }

    private int sp2px(int sp) {
        float density = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * density + 0.5f);
    }

    //第二部初始化画笔
    private void init() {
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(indexSize);
        mPaint.setColor(normalColor);
        //绘制区域
        mRect = new Rect();
    }

    //第三步，绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //根据数据绘制文字
        //去拿数据 letters
        //拿到数据 作出处理
        if (letters != null) {
            //不null 一步步绘制每个文字
            for (int i = 0; i < letters.size(); i++) {
                //拿到具体的 文字
                String text = letters.get(i);
                //测量文字的宽高
                float textWidth = mPaint.measureText(text);
                //测量绘制文字的边界
                mPaint.getTextBounds(text, 0, text.length(), mRect);
                //拿到文字的高度
                int textHeight = mRect.height();
                //计算绘制文字的坐标
                //文字 居中显示
                float x = mCellWidth * 0.5f - textWidth * 0.5f;
                float y = mCellHeight * 0.5f + textHeight * 0.5f + paddingTop + beginY + mCellHeight * i;
                //设置画笔的颜色  有焦点的时候 为选中  无焦点（点击） 为正常
                //计算当前的点击的文字位置序号 mIndexPos  处理点击事件 第四步
                mPaint.setColor(mIndexPos == i ? selectColor : normalColor);
                canvas.drawText(text, x, y, mPaint);
            }
        }

    }


    //第四步 处理touch事件


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //这里主要是 处理 点击的索引文字的位置 所以处理y坐标即可

        float y;
        //有触摸事件就重绘
        invalidate();
        switch (event.getAction()) {
            //按下
            case MotionEvent.ACTION_DOWN:

                //要求所有事件都传递到该view 由该view 进行处理
                getParent().requestDisallowInterceptTouchEvent(true);
                //点击 刷新drawable 显示点击的动态效果
                refreshState(true);
                //获取y的坐标 计算当前点击位置
                y = event.getY();
                computeMIndex(y);

                break;
            //移动
            case MotionEvent.ACTION_MOVE:
                y = event.getY();
                computeMIndex(y);

                break;
            //抬起
            case MotionEvent.ACTION_UP:
                refreshState(false);
                mIndexPos = -1;
                break;
            default:
                break;
        }


        //直接消费
        return true;
    }

    private void computeMIndex(float y) {
        int currentIndex;
        if (beginY + paddingTop > y) {
            //点击区域无效
            return;
        }

        currentIndex = (int) ((y - paddingTop - beginY) / mCellHeight);

        if (currentIndex != mIndexPos) {
            //表示 新的文字位置  索引改变 此处把位置和文字回调给调用者
            if (indexChangedListener != null) {
                //防止数组越界
                if (letters != null && letters.size() > currentIndex) {
                    indexChangedListener.onIndexChanged(currentIndex, letters.get(currentIndex));
                    mIndexPos = currentIndex;
                }

            }

        }
    }

    private IOnIndexChangedListener indexChangedListener;

    public void setIndexChangedListener(IOnIndexChangedListener indexChangedListener) {
        this.indexChangedListener = indexChangedListener;
    }

    public interface IOnIndexChangedListener {
        void onIndexChanged(int pos, String text);
    }

    private void refreshState(boolean state) {
        if (pressed != state) {
            pressed = state;
            refreshDrawableState();
        }
    }

    private boolean pressed;

    //重写 onCreateDrawableState
    private static final int[] STATE_FOCUSED = new int[]{android.R.attr.state_focused};

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (pressed) {
            mergeDrawableStates(states, STATE_FOCUSED);
        }
        return states;
    }

    private int mIndexPos = -1;


    @Nullable
    private List<String> letters;

    //设置数据
    public void setLetters(@Nullable List<String> letters) {
        if (letters == null) {
            //数据为 null 隐藏
            setVisibility(GONE);
            return;
        }
        this.letters = letters;
        //这时候 布局已经加载完成  可以获得索引条的宽高
        //实际有效的绘制高度
        mHeight = getMeasuredHeight() - paddingBottom - paddingTop;
        //单个文字的所在区域的宽
        mCellWidth = getMeasuredWidth();
        //单个文字的所在区域的高  之所以除以26 是因为不足26个索引的时候从中间开始绘制
        mCellHeight = mHeight / 26.0f;
        //计算开始绘制的 y的坐标
        beginY = (mHeight - mCellHeight * letters.size()) * 0.5f;
        //重绘
        invalidate();
    }

    @Nullable
    public List<String> getLetters() {
        return letters;
    }

    /**
     * onSizeChanged() 在控件大小发生改变时调用。所以这里初始化会被调用一次
     *
     * 作用：获取控件的宽和高度
     */

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getMeasuredHeight()-paddingTop-paddingBottom;
        mCellWidth = getMeasuredWidth();
        mCellHeight = mHeight * 1.0f / 26;
        if (letters != null) {
            beginY = (mHeight - mCellHeight * letters.size()) * 0.5f;
        }
    }
}
