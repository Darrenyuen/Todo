package com.example.yuan.todo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 继承ListView完成自定义的右滑出现删除按钮、可以实现删除的TodoListView
 */
public class TodoListView extends ListView {

    private static final String TAG = TodoListView.class.getSimpleName();

    private int touchSlop;
    private boolean isSliding;
    private int xDown;
    private int yDown;
    private int xMove;
    private int yMove;
    private PopupWindow popupWindow;
    private int mPopupWindowHeight;
    private int mPopupWindowWidth;

    private TextView deleteView;
    private DeleteClickListener deleteClickListener;
    private View mCurrentView;
    private int mCurrentViewPosition;

    public TodoListView(Context context) {
        super(context);
    }

    public TodoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        View view = LayoutInflater.from(context).inflate(R.layout.delete_btn, null);
        deleteView =  view.findViewById(R.id.delete);

        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.getContentView().measure(0, 0); //初始化
        mPopupWindowHeight = popupWindow.getContentView().getMeasuredHeight();
        mPopupWindowWidth = popupWindow.getContentView().getMeasuredWidth();
    }

    public TodoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 触摸事件派发
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;

                if (popupWindow.isShowing()) {
                    dismissPopWindow();
                    return false;
                }
                mCurrentViewPosition = pointToPosition(xDown, yDown);   //根据x,y坐标获取到自己的下标
                View view = getChildAt(mCurrentViewPosition - getFirstVisiblePosition());   //当前可见view的小标减去第一个可见的view的下标就可以找到当前的这个view
                mCurrentView = view;
                break;

            case MotionEvent.ACTION_MOVE:
                xDown = x;
                yDown = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;
                if (xMove < xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop) {
                    isSliding = true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mCurrentView == null) return false;
        int action = ev.getAction();
        if (isSliding) {
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    int[] location = new int[2];
                    mCurrentView.getLocationOnScreen(location);
                    popupWindow.update();

                    deleteView.setHeight(getMeasuredHeight()/getChildCount());

                    popupWindow.showAtLocation(mCurrentView, Gravity.LEFT | Gravity.TOP, location[0] + mCurrentView.getWidth(), location[1] + mCurrentView.getHeight()/2 - mPopupWindowHeight);

                    deleteView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (deleteClickListener != null) {
                                deleteClickListener.onClickDelete(mCurrentViewPosition);
                                popupWindow.dismiss();
                            }
                        }
                    });
                    break;
                case MotionEvent.ACTION_UP:
                    isSliding = false;

                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void dismissPopWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public void setDelButtonClickListener(DeleteClickListener listener) {
        deleteClickListener = listener;
    }

}
