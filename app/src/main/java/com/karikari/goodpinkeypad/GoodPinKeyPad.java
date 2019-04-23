package com.karikari.goodpinkeypad;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import androidx.appcompat.app.AppCompatDelegate;

public class GoodPinKeyPad extends LinearLayout {

    private static final String TAG = GoodPinKeyPad.class.getSimpleName();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private Context context;
    private List<TextView> keypads = new ArrayList<>();
    private Stack<String> indicators = new Stack<>();
    private KeyPadListerner listerner;
    private ImageButton mBackRight;
    private View mBackPressView;
    private ImageButton mBackLeft;
    private int numberOfPins;
    private FrameLayout indicator_1, indicator_2, indicator_3, indicator_4, indicator_5, indicator_6;
    private LinearLayout mRootLayout;
    private HashMap<Integer, Drawable> themeMap = new HashMap<>();
    private HashMap<Integer, Drawable> solidMap = new HashMap<>();
    private HashMap<Integer, Drawable> holoMap = new HashMap<>();

    private int themeId;
    private Drawable theme;
    private Drawable solid;
    private Drawable hollow;

    private int backgroundColorValue, keypadTextColor, keypadBackPressIcon, keypadCallAllIcon;

    public GoodPinKeyPad(Context context) {
        super(context);
        initViews(context);
    }

    public GoodPinKeyPad(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
        initViews(context);
    }

    public GoodPinKeyPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
        initViews(context);
    }

    public void setKeyPadListener(KeyPadListerner listerner) {
        this.listerner = listerner;
    }

    public void setBackPressView(View backView){
        this.mBackPressView = backView;

        mBackPressView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doClearpin();
            }
        });
    }


    private void initAttributes(Context context, AttributeSet attrs) {
        //themeMap.clear();
        themeMap.put(1, context.getResources().getDrawable(R.drawable.keys_white_theme));
        themeMap.put(2, context.getResources().getDrawable(R.drawable.keys_black_theme));
        themeMap.put(3, context.getResources().getDrawable(R.drawable.keys_white_border_theme));
        themeMap.put(4, context.getResources().getDrawable(R.drawable.keys_black_border_theme));

        solidMap.put(1, context.getResources().getDrawable(R.drawable.white_solid_dot));
        solidMap.put(2, context.getResources().getDrawable(R.drawable.black_solid_dot));
        solidMap.put(3, context.getResources().getDrawable(R.drawable.white_solid_dot));
        solidMap.put(4, context.getResources().getDrawable(R.drawable.black_solid_dot));

        holoMap.put(1, context.getResources().getDrawable(R.drawable.white_hollow_dot));
        holoMap.put(2, context.getResources().getDrawable(R.drawable.black_hollow_dot));
        holoMap.put(3, context.getResources().getDrawable(R.drawable.white_hollow_dot));
        holoMap.put(4, context.getResources().getDrawable(R.drawable.black_hollow_dot));

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GoodPinKeyPad, 0, 0);

        try {
            this.backgroundColorValue = typedArray.getColor(R.styleable.GoodPinKeyPad_backgroundColor, context.getResources().getColor(R.color.colorPrimaryDark));
            this.numberOfPins = typedArray.getInt(R.styleable.GoodPinKeyPad_pinEntry, 4);
            this.themeId = typedArray.getInt(R.styleable.GoodPinKeyPad_KeyPadTheme, 1);
            this.theme = themeMap.get(this.themeId);
            this.solid = solidMap.get(this.themeId);
            this.hollow = holoMap.get(this.themeId);

            this.keypadTextColor = typedArray.getColor(R.styleable.GoodPinKeyPad_keyPadTextColor, context.getResources().getColor(R.color.white));
            this.keypadBackPressIcon = typedArray.getResourceId(R.styleable.GoodPinKeyPad_keyPadBackPressIcon, -1);
            this.keypadCallAllIcon = typedArray.getResourceId(R.styleable.GoodPinKeyPad_keyPadCancelAllIcon, -1);


        } finally {
            typedArray.recycle();
        }
    }

    private void setThemes() {
        for (TextView textView : keypads) {
            textView.setBackground(this.theme);
            textView.setTextColor(this.keypadTextColor);
        }

        if (this.keypadBackPressIcon != -1) {
            mBackRight.setImageResource(this.keypadBackPressIcon);
        }

        if (this.keypadCallAllIcon != -1) {
            mBackLeft.setImageResource(this.keypadCallAllIcon);
        }
    }


    private void initViews(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.key_pad_layout, this);
        this.context = context;
        mBackRight = view.findViewById(R.id.back_right);
        mBackLeft = view.findViewById(R.id.back_left);
        keypads.add((TextView) view.findViewById(R.id.key0));
        keypads.add((TextView) view.findViewById(R.id.key1));
        keypads.add((TextView) view.findViewById(R.id.key2));
        keypads.add((TextView) view.findViewById(R.id.key3));
        keypads.add((TextView) view.findViewById(R.id.key4));
        keypads.add((TextView) view.findViewById(R.id.key5));
        keypads.add((TextView) view.findViewById(R.id.key6));
        keypads.add((TextView) view.findViewById(R.id.key7));
        keypads.add((TextView) view.findViewById(R.id.key8));
        keypads.add((TextView) view.findViewById(R.id.key9));
        keypads.add((TextView) view.findViewById(R.id.key11));

        indicator_1 = view.findViewById(R.id.indicator_1);
        indicator_2 = view.findViewById(R.id.indicator_2);
        indicator_3 = view.findViewById(R.id.indicator_3);
        indicator_4 = view.findViewById(R.id.indicator_4);
        indicator_5 = view.findViewById(R.id.indicator_5);
        indicator_6 = view.findViewById(R.id.indicator_6);
        mRootLayout = view.findViewById(R.id.root_layout);
        mBackRight.setBackground(this.theme);
        mBackLeft.setBackground(this.theme);
        setListerner();
        setThemes();

        if (numberOfPins == 4) {
            indicator_5.setVisibility(GONE);
            indicator_6.setVisibility(GONE);
        }

        if (numberOfPins == 5) {
            indicator_5.setVisibility(GONE);
        }

        if (backgroundColorValue != 0) {
            mRootLayout.setBackgroundColor(backgroundColorValue);
        }

        indicator_1.setBackground(hollow);
        indicator_2.setBackground(hollow);
        indicator_3.setBackground(hollow);
        indicator_4.setBackground(hollow);
        indicator_5.setBackground(hollow);
        indicator_6.setBackground(hollow);
    }


    private void setListerner() {
        for (final TextView keypad : keypads) {
            keypad.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (GoodPinKeyPad.this.listerner != null) {
                        indicators.push(keypad.getText().toString());
                        //Log.d(TAG, "indicator Size : " + indicators.size());
                        if (indicators.size() <= numberOfPins) {
                            setIdicators(indicators.size());
                            GoodPinKeyPad.this.listerner.onKeyPadPressed(pinToString(indicators));
                            Log.d(TAG, " Real Value : " + pinToString(indicators));
                        } else {
                            indicators.pop();
                        }
                    }
                }
            });
        }


        mBackRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doClearpin();
            }
        });

        mBackLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GoodPinKeyPad.this.listerner != null) {
                    GoodPinKeyPad.this.listerner.onClear();
                    try {
                        indicators.clear();
                        setIdicators(0);
                        GoodPinKeyPad.this.listerner.onKeyPadPressed(pinToString(indicators));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void doClearpin(){
        if (GoodPinKeyPad.this.listerner != null) {
            GoodPinKeyPad.this.listerner.onKeyBackPressed();
            try {
                if (indicators.size() != 0) {
                    indicators.pop();
                    setIdicators(indicators.size());
                    //Log.d(TAG, "Remove indicators : " + indicators);
                    //Log.d(TAG, " Real Value : " + pinToString(indicators));
                } else {
                    setIdicators(indicators.size());
                    ///Log.d(TAG, " Real Value : " + pinToString(indicators));
                }
                GoodPinKeyPad.this.listerner.onKeyPadPressed(pinToString(indicators));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void setIdicators(int size) {
        Log.d(TAG, "INDICATOR SIZE : " + size);
        switch (size) {
            case 0:
                indicator_1.setBackground(hollow);
                indicator_2.setBackground(hollow);
                indicator_3.setBackground(hollow);
                indicator_4.setBackground(hollow);
                indicator_5.setBackground(hollow);
                indicator_6.setBackground(hollow);
                break;
            case 1:
                indicator_1.setBackground(solid);
                indicator_2.setBackground(hollow);
                indicator_3.setBackground(hollow);
                indicator_4.setBackground(hollow);
                indicator_5.setBackground(hollow);
                indicator_6.setBackground(hollow);
                break;
            case 2:
                indicator_1.setBackground(solid);
                indicator_2.setBackground(solid);
                indicator_3.setBackground(hollow);
                indicator_4.setBackground(hollow);
                indicator_5.setBackground(hollow);
                indicator_6.setBackground(hollow);
                break;
            case 3:
                indicator_1.setBackground(solid);
                indicator_2.setBackground(solid);
                indicator_3.setBackground(solid);
                indicator_4.setBackground(hollow);
                indicator_5.setBackground(hollow);
                indicator_6.setBackground(hollow);
                break;
            case 4:
                indicator_1.setBackground(solid);
                indicator_2.setBackground(solid);
                indicator_3.setBackground(solid);
                indicator_4.setBackground(solid);
                indicator_5.setBackground(hollow);
                indicator_6.setBackground(hollow);
                break;
            case 5:
                indicator_1.setBackground(solid);
                indicator_2.setBackground(solid);
                indicator_3.setBackground(solid);
                indicator_4.setBackground(solid);
                indicator_5.setBackground(solid);
                indicator_6.setBackground(hollow);
                break;
            case 6:
                indicator_1.setBackground(solid);
                indicator_2.setBackground(solid);
                indicator_3.setBackground(solid);
                indicator_4.setBackground(solid);
                indicator_5.setBackground(solid);
                indicator_6.setBackground(solid);
                break;
        }
    }

    private String pinToString(Stack<String> indicators) {
        String str = "";
        for (String s : indicators) {
            str += s;
        }

        return str;
    }


}
