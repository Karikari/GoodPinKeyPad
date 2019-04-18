package com.karikari.goodpinkeypad;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GoodPinKeyPad extends ConstraintLayout {

    private static final String TAG = GoodPinKeyPad.class.getSimpleName();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private List<TextView> keypads = new ArrayList<>();
    private KeyPadListerner listerner;
    private TextView mBackRight;
    private TextView mBackLeft;
    private int numberOfPins = 6;
    private FrameLayout indicator_1, indicator_2, indicator_3, indicator_4, indicator_5, indicator_6;
    private Stack<String> indicators = new Stack<>();
    private Context context;

    public GoodPinKeyPad(Context context) {
        super(context);
        initViews(context);
    }

    public GoodPinKeyPad(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public GoodPinKeyPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    public void setKeyPadListener(KeyPadListerner listerner) {
        this.listerner = listerner;
    }

    public void setNumberOfPins(int value) {
        this.numberOfPins = value;

    }


    private void initViews(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.key_pad_layout, this);
        this.context = context;
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
        mBackRight = view.findViewById(R.id.back_right);
        mBackLeft = view.findViewById(R.id.back_left);
        indicator_1 = view.findViewById(R.id.indicator_1);
        indicator_2 = view.findViewById(R.id.indicator_2);
        indicator_3 = view.findViewById(R.id.indicator_3);
        indicator_4 = view.findViewById(R.id.indicator_4);
        indicator_5 = view.findViewById(R.id.indicator_5);
        indicator_6 = view.findViewById(R.id.indicator_6);

        if (numberOfPins == 4) {
            indicator_5.setVisibility(GONE);
            indicator_6.setVisibility(GONE);
        }

        if (numberOfPins == 5) {
            indicator_5.setVisibility(GONE);
        }

        if (numberOfPins == 6) {
            //indicator_5.setVisibility(GONE);
        }
        setListerner();
    }


    private void setListerner() {
        for (View view : keypads) {
            final TextView keypad = (TextView) view;
            keypad.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.d(TAG, "Key Value " + keypad.getText().toString());
                    if (GoodPinKeyPad.this.listerner != null) {
                        indicators.push(keypad.getText().toString());

                        setIdicators(indicators.size());
                        GoodPinKeyPad.this.listerner.onKeyPadPressed(toStringValue(indicators));
                        Log.d(TAG, " Real Value : " + toStringValue(indicators));

                    }
                }
            });
        }


        mBackRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GoodPinKeyPad.this.listerner != null) {
                    GoodPinKeyPad.this.listerner.onKeyBackPressed();
                    try {
                        if (indicators.size() != 0) {
                            indicators.pop();
                            setIdicators(indicators.size());
                            //Log.d(TAG, "Remove indicators : " + indicators);
                            //Log.d(TAG, " Real Value : " + toStringValue(indicators));
                        } else {
                            setIdicators(indicators.size());
                            ///Log.d(TAG, " Real Value : " + toStringValue(indicators));
                        }
                        GoodPinKeyPad.this.listerner.onKeyPadPressed(toStringValue(indicators));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        mBackLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(GoodPinKeyPad.this.listerner!=null){
                    GoodPinKeyPad.this.listerner.onKeyBackPressed();
                }*/
            }
        });
    }

    private void setIdicators(int size) {
        Log.d(TAG, "INDICATOR SIZE : " + size);
        switch (size) {
            case 0:
                indicator_1.setBackgroundResource(R.drawable.hollow_dot);
                indicator_2.setBackgroundResource(R.drawable.hollow_dot);
                indicator_3.setBackgroundResource(R.drawable.hollow_dot);
                indicator_4.setBackgroundResource(R.drawable.hollow_dot);
                indicator_5.setBackgroundResource(R.drawable.hollow_dot);
                indicator_6.setBackgroundResource(R.drawable.hollow_dot);
                break;
            case 1:
                indicator_1.setBackgroundResource(R.drawable.solid_dot);
                indicator_2.setBackgroundResource(R.drawable.hollow_dot);
                indicator_3.setBackgroundResource(R.drawable.hollow_dot);
                indicator_4.setBackgroundResource(R.drawable.hollow_dot);
                indicator_5.setBackgroundResource(R.drawable.hollow_dot);
                indicator_6.setBackgroundResource(R.drawable.hollow_dot);
                break;
            case 2:
                indicator_1.setBackgroundResource(R.drawable.solid_dot);
                indicator_2.setBackgroundResource(R.drawable.solid_dot);
                indicator_3.setBackgroundResource(R.drawable.hollow_dot);
                indicator_4.setBackgroundResource(R.drawable.hollow_dot);
                indicator_5.setBackgroundResource(R.drawable.hollow_dot);
                indicator_6.setBackgroundResource(R.drawable.hollow_dot);
                break;
            case 3:
                indicator_1.setBackgroundResource(R.drawable.solid_dot);
                indicator_2.setBackgroundResource(R.drawable.solid_dot);
                indicator_3.setBackgroundResource(R.drawable.solid_dot);
                indicator_4.setBackgroundResource(R.drawable.hollow_dot);
                indicator_5.setBackgroundResource(R.drawable.hollow_dot);
                indicator_6.setBackgroundResource(R.drawable.hollow_dot);
                break;
            case 4:
                indicator_1.setBackgroundResource(R.drawable.solid_dot);
                indicator_2.setBackgroundResource(R.drawable.solid_dot);
                indicator_3.setBackgroundResource(R.drawable.solid_dot);
                indicator_4.setBackgroundResource(R.drawable.solid_dot);
                indicator_5.setBackgroundResource(R.drawable.hollow_dot);
                indicator_6.setBackgroundResource(R.drawable.hollow_dot);
                break;
            case 5:
                indicator_1.setBackgroundResource(R.drawable.solid_dot);
                indicator_2.setBackgroundResource(R.drawable.solid_dot);
                indicator_3.setBackgroundResource(R.drawable.solid_dot);
                indicator_4.setBackgroundResource(R.drawable.solid_dot);
                indicator_5.setBackgroundResource(R.drawable.solid_dot);
                indicator_6.setBackgroundResource(R.drawable.hollow_dot);
                break;
            case 6:
                indicator_1.setBackgroundResource(R.drawable.solid_dot);
                indicator_2.setBackgroundResource(R.drawable.solid_dot);
                indicator_3.setBackgroundResource(R.drawable.solid_dot);
                indicator_4.setBackgroundResource(R.drawable.solid_dot);
                indicator_5.setBackgroundResource(R.drawable.solid_dot);
                indicator_6.setBackgroundResource(R.drawable.solid_dot);
                break;
        }
    }

    private String toStringValue(Stack<String> indicators) {
        String str = "";
        for (String s : indicators) {
            str += s;
        }

        return str;
    }


}
