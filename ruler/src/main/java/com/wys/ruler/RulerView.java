package com.wys.ruler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * 封装的标尺控件
 * 使用该控件替换 StraightedgeView 和 TriangleRulerView
 * @author wys
 * @update at 2018年5月28日
 * */
public class RulerView extends RelativeLayout {

    private StraightedgeView lineRuler;
    private TriangleRulerView triangleRuler;
    private ImageView image_hor, image_ver;

    private int ruler_type = 0;
    private int backgroundResource;
    private int scaleLength = dp2dx(15);
    private int scaleColor = Color.BLACK;
    private int scaleTextSize = dp2dx(12);

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrRes(context, attrs);
        buildView(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        invalidate();
    }

    private void buildView(Context context) {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        switch (ruler_type) {
            case 0:
                lineRuler = new StraightedgeView(context);
                image_hor = new ImageView(context);
                lineRuler.setLineLength(scaleLength);
                lineRuler.setTextSize(scaleTextSize);
                image_hor.setLayoutParams(lp);
                lineRuler.setLayoutParams(lp);
                image_hor.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image_hor.setImageResource(backgroundResource);
                lineRuler.setScaleColor(scaleColor);
                lineRuler.build();
                this.addView(image_hor);
                this.addView(lineRuler);
                break;
            case 1:
                triangleRuler = new TriangleRulerView(context);
                image_hor = new ImageView(context);
                image_ver = new ImageView(context);
                triangleRuler.setLineLength(scaleLength);
                triangleRuler.setTextSize(scaleTextSize);
                triangleRuler.setScaleColor(scaleColor);
                triangleRuler.setLayoutParams(lp);
                lp = new LayoutParams(LayoutParams.MATCH_PARENT, scaleLength * 6);
                image_hor.setLayoutParams(lp);
                lp = new LayoutParams(scaleLength * 6, LayoutParams.MATCH_PARENT);
                image_ver.setLayoutParams(lp);
                image_hor.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image_ver.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image_ver.setImageResource(backgroundResource);
                image_hor.setImageResource(backgroundResource);
                triangleRuler.build();
                this.addView(image_hor);
                this.addView(image_ver);
                this.addView(triangleRuler);
                break;
            default:
                break;
        }
    }

    private void readAttrRes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RulerView);
        backgroundResource = typedArray.getResourceId(R.styleable.RulerView_ruler_backgroundImage, R.drawable.wood);
        ruler_type = typedArray.getInteger(R.styleable.RulerView_ruler_type, 0);
        scaleLength = (int) typedArray.getDimension(R.styleable.RulerView_ruler_scaleLength, scaleLength);
        scaleColor = typedArray.getInteger(R.styleable.RulerView_ruler_scaleColor, scaleColor);
        typedArray.recycle();
    }

    private int dp2dx(int dpValue) {
        int dxValue = (int) getResources().getDisplayMetrics().density * dpValue;
        return dxValue;
    }

}
