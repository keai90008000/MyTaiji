package chenchi1502l20170417.bawei.com.mytaiji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;

/**
 * Created by 陈驰 on 2017/4/17.
 */

public class TJview extends View {
    private static final String TAG = "TJView";
    private int padding = 8; //画笔工具
    private Paint mPaint; //圆心坐标
    private float currentX = 0;
    private float currentY = 0;
    // 大圆半径
     private float radiusBig = 80;
    // 中圆半径
     private float radiusCenter = radiusBig / 2;
    //小圆半径
    private float radiusSmall = radiusCenter / 3;

    public TJview(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        setAnimation();
    }

    public TJview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        setAnimation();
    }

    public TJview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        setAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawLeftHalfCirle(canvas);
        drawTBCirle(canvas);
    }

    private void drawTBCirle(Canvas canvas) {
        //画上面的白中圆
         mPaint.setColor(Color.WHITE);
        canvas.drawCircle(currentX, currentY - radiusBig / 2, radiusCenter, mPaint);
        // 画上面的黑小圆
       mPaint.setColor(Color.BLACK);
        canvas.drawCircle(currentX, currentY - radiusBig / 2, radiusSmall, mPaint);
        // 画下面的黑中圆
         mPaint.setColor(Color.BLACK);
        canvas.drawCircle(currentX, currentY + radiusBig / 2, radiusCenter, mPaint);
        // 画下面的白小圆
        mPaint.setColor(Color.WHITE);
       canvas.drawCircle(currentX, currentY + radiusBig / 2, radiusSmall, mPaint);
    }

    private void drawLeftHalfCirle(Canvas canvas) {
            mPaint.setColor(Color.WHITE);
        canvas.drawArc(new RectF(currentX - radiusBig, currentY - radiusBig, currentX + radiusBig, currentY + radiusBig), 90, 180, true, mPaint);
        //90度开始画180度
    }

    private void drawBg(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(currentX, currentY, radiusBig + padding, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        currentX = MeasureSpec.getSize(widthMeasureSpec) / 2;
        currentY = MeasureSpec.getSize(heightMeasureSpec) / 2;
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_POINTER_DOWN:
                if(event.getPointerCount() >= 2) {
                    float x = event.getX(0);
                    float y = event.getY(0);
                    float x1 = event.getX(1);
                    float y1 = event.getY(1);
                    double sqrt = Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
                    radiusBig = (float) (radiusBig*sqrt);
                    radiusCenter = radiusBig / 2;
                    radiusSmall = radiusCenter / 3;
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                currentY = event.getY();
                break;
        }
        postInvalidate();
        return true;
    }

    private void setAnimation() {
       ScaleAnimation scaleAnimation2 = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, ScaleAnimation.RELATIVE_TO_PARENT, 0.5f, ScaleAnimation.RELATIVE_TO_PARENT, 0.5f);
       scaleAnimation2.setDuration(1000);// 设置持续时间
       scaleAnimation2.setRepeatCount(99999);// 设置重复次数
       scaleAnimation2.setFillAfter(true);// 保持动画结束时的状态
       startAnimation(scaleAnimation2);
    }
}
