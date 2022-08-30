package com.karikari.goodpinkeypad

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import java.util.*


class GoodPinKeyPad : LinearLayout {
    companion object {
        private val TAG = GoodPinKeyPad::class.java.simpleName

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    private val keypads: MutableList<TextView> = ArrayList()
    private val indicators = Stack<String>()
    private var listener: KeyPadListerner? = null
    private var mBackRight: ImageButton? = null
    private var mBackPressView: View? = null
    private var mCancelAllView: View? = null
    private var mBackLeft: ImageButton? = null
    private var numberOfPins = 0
    private var indicator1: FrameLayout? = null
    private var indicator2: FrameLayout? = null
    private var indicator3: FrameLayout? = null
    private var indicator4: FrameLayout? = null
    private var indicator5: FrameLayout? = null
    private var indicator6: FrameLayout? = null
    private var mRootLayout: LinearLayout? = null
    private var keyPadContainer: LinearLayout? = null
    private val themeMap = HashMap<Int, Drawable>()
    private val solidMap = HashMap<Int, Drawable>()
    private val holoMap = HashMap<Int, Drawable>()
    private val canCelMap = HashMap<Int, Drawable>()
    private val backMap = HashMap<Int, Drawable>()
    private var themeId = 0
    private var theme: Drawable? = null
    private var solid: Drawable? = null
    private var hollow: Drawable? = null
    private var keypadCallAllIcon: Drawable? = null
    private var keypadBackPressIcon: Drawable? = null
    private var back: Drawable? = null
    private var cancel: Drawable? = null
    private var marginTop = 0f
    private var marginLeft = 0f
    private var marginRight = 0f
    private var marginBottom = 0f
    private var keySize = 0f
    private var keyTextSize = 0f
    private var errorTextSize = 0f
    private var background_color = 0
    private var keypadTextColor = 0
    private var backPressVisibility = 0
    private var keyBackground: Drawable? = null
    private var selectedIndicator: Drawable? = null
    private var defaultIndicator: Drawable? = null
    private var errorIndicator: Drawable? = null
    private var errorText: String = ""
    private var errorTextInt: Int = 0
    private var errorTextView: TextView? = null
    private var font: Typeface? = null


    constructor(context: Context) : super(context) {
        initViews(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttributes(context, attrs)
        initViews(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttributes(context, attrs)
        initViews(context)
    }

    fun setKeyPadListener(listerner: KeyPadListerner?) {
        this.listener = listerner
    }

    fun setBackPressView(backView: View?) {
        mBackPressView = backView
        mBackPressView!!.setOnClickListener { doClearPin() }
    }

    fun setCallAllView(callAllView: View?) {
        mCancelAllView = callAllView
        mCancelAllView!!.setOnClickListener { clearAll() }
    }

    fun setErrorIndicators(isError: Boolean) {
        if (isError) {
            setIndicators(-1)
            indicators.clear()
        }
    }

    fun setErrorText(value: String) {
        this.errorText = value
    }

    fun setTypeFace(typeface: Typeface?) {
        this.font = typeface
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initAttributes(context: Context, attrs: AttributeSet?) {
        //themeMap.clear_dark();
        themeMap[1] = context.resources.getDrawable(R.drawable.keys_white_theme, context.theme)
        themeMap[2] =
            context.resources.getDrawable(R.drawable.keys_black_theme, context.theme)
        themeMap[3] =
            context.resources.getDrawable(R.drawable.keys_white_border_theme, context.theme)
        themeMap[4] =
            context.resources.getDrawable(R.drawable.keys_black_border_theme, context.theme)
        solidMap[1] = context.resources.getDrawable(R.drawable.white_solid_dot, context.theme)
        solidMap[2] = context.resources.getDrawable(R.drawable.black_solid_dot, context.theme)
        solidMap[3] = context.resources.getDrawable(R.drawable.white_solid_dot, context.theme)
        solidMap[4] = context.resources.getDrawable(R.drawable.black_solid_dot, context.theme)
        holoMap[1] = context.resources.getDrawable(R.drawable.white_hollow_dot, context.theme)
        holoMap[2] =
            context.resources.getDrawable(R.drawable.black_hollow_dot, context.theme)
        holoMap[3] =
            context.resources.getDrawable(R.drawable.white_hollow_dot, context.theme)
        holoMap[4] = context.resources.getDrawable(R.drawable.black_hollow_dot, context.theme)
        backMap[1] = context.resources.getDrawable(R.drawable.back_white, context.theme)
        backMap[2] = context.resources.getDrawable(R.drawable.back_dark, context.theme)
        backMap[3] = context.resources.getDrawable(R.drawable.back_white, context.theme)
        backMap[4] = context.resources.getDrawable(R.drawable.back_dark, context.theme)
        canCelMap[1] =
            context.resources.getDrawable(R.drawable.clear_white, context.theme)
        canCelMap[2] = context.resources.getDrawable(R.drawable.clear_dark, context.theme)
        canCelMap[3] = context.resources.getDrawable(R.drawable.clear_white, context.theme)
        canCelMap[4] = context.resources.getDrawable(R.drawable.clear_dark, context.theme)
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.GoodPinKeyPad, 0, 0)
        try {
            background_color = typedArray.getColor(
                R.styleable.GoodPinKeyPad_backgroundColor,
                ContextCompat.getColor(context, R.color.transparent)
            )

            numberOfPins = typedArray.getInt(R.styleable.GoodPinKeyPad_pinEntry, 4)
            themeId = typedArray.getInt(R.styleable.GoodPinKeyPad_keyPadStyle, 1)
            theme = themeMap[themeId]
            solid = solidMap[themeId]
            hollow = holoMap[themeId]
            back = backMap[themeId]
            cancel = canCelMap[themeId]
            keypadTextColor = typedArray.getColor(
                R.styleable.GoodPinKeyPad_textColor,
                ContextCompat.getColor(context, R.color.transparent)
            )
            keypadBackPressIcon = typedArray.getDrawable(R.styleable.GoodPinKeyPad_backPressIcon)
            keypadCallAllIcon = typedArray.getDrawable(R.styleable.GoodPinKeyPad_cancelIcon)
            marginLeft =
                typedArray.getDimension(R.styleable.GoodPinKeyPad_marginLeft, dpToPx(0f).toFloat())
            marginTop =
                typedArray.getDimension(R.styleable.GoodPinKeyPad_marginTop, dpToPx(0f).toFloat())
            marginBottom = typedArray.getDimension(
                R.styleable.GoodPinKeyPad_marginBottom,
                dpToPx(0f).toFloat()
            )
            marginRight =
                typedArray.getDimension(R.styleable.GoodPinKeyPad_marginRight, dpToPx(0f).toFloat())
            keySize =
                typedArray.getDimension(R.styleable.GoodPinKeyPad_keySize, dpToPx(0f).toFloat())
            backPressVisibility =
                typedArray.getInteger(R.styleable.GoodPinKeyPad_controlsPressVisibility, 1)
            keyBackground = typedArray.getDrawable(R.styleable.GoodPinKeyPad_keyBackground)

            selectedIndicator = typedArray.getDrawable(R.styleable.GoodPinKeyPad_selectedIndicator)

            defaultIndicator = typedArray.getDrawable(R.styleable.GoodPinKeyPad_defaultIndicator)

            errorIndicator = typedArray.getDrawable(R.styleable.GoodPinKeyPad_errorIndicator)

            keyTextSize =
                typedArray.getFloat(R.styleable.GoodPinKeyPad_keyTextSize, 0f)

            errorTextSize =
                typedArray.getDimension(
                    R.styleable.GoodPinKeyPad_errorTextSize,
                    dpToPx(0f).toFloat()
                )

            /**
             *  This sets the font type at the XML
             *  if SDK version is less than 26 you can use the setTypeFace Function
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                font = typedArray.getFont(R.styleable.GoodPinKeyPad_font)
            }

        } finally {
            typedArray.recycle()
        }
    }

    private fun setThemes() {


        for (textView in keypads) {
            textView.background = theme
            textView.setTextColor(keypadTextColor)

            if (keyBackground != null) {
                textView.background = keyBackground
            }

            if (font != null) {
                textView.typeface = font
            }

            if (keyTextSize != 0f) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, keyTextSize)
            }

            if (keySize != 0f) {
                val textViewParam = textView.layoutParams as ViewGroup.LayoutParams
                textViewParam.height = keySize.toInt()
                textViewParam.width =  keySize.toInt()
                textView.layoutParams = textViewParam
            }

        }
        if (keypadBackPressIcon != null) {
            //mBackRight.setImageResource(this.keypadBackPressIcon);
            mBackRight!!.setImageDrawable(keypadBackPressIcon)
        }
        if (keypadCallAllIcon != null) {
            //mBackLeft.setImageResource(this.keypadCallAllIcon);
            mBackLeft!!.setImageDrawable(keypadCallAllIcon)
        }
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(
            dpToPx(marginLeft),
            dpToPx(marginTop),
            dpToPx(marginRight),
            dpToPx(marginBottom)
        )
        keyPadContainer!!.layoutParams = params
        if (backPressVisibility == 0) {
            mBackLeft!!.visibility = GONE
            mBackRight!!.visibility = GONE
        } else {
            mBackLeft!!.visibility = VISIBLE
            mBackRight!!.visibility = VISIBLE
            if (keypadBackPressIcon == null && keypadCallAllIcon == null) {
                mBackLeft!!.setImageDrawable(cancel)
                mBackRight!!.setImageDrawable(back)
            }
        }

        if (selectedIndicator != null) {
            solid = selectedIndicator
        }

        if (defaultIndicator != null) {
            hollow = defaultIndicator
        }

        if (font != null) {
            errorTextView!!.typeface = font
        }
    }

    private fun initViews(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.key_pad_layout, this)
        mBackRight = view.findViewById(R.id.back_right)
        mBackLeft = view.findViewById(R.id.back_left)
        errorTextView = view.findViewById(R.id.mErrorText)
        keypads.add(view.findViewById<View>(R.id.key0) as TextView)
        keypads.add(view.findViewById<View>(R.id.key1) as TextView)
        keypads.add(view.findViewById<View>(R.id.key2) as TextView)
        keypads.add(view.findViewById<View>(R.id.key3) as TextView)
        keypads.add(view.findViewById<View>(R.id.key4) as TextView)
        keypads.add(view.findViewById<View>(R.id.key5) as TextView)
        keypads.add(view.findViewById<View>(R.id.key6) as TextView)
        keypads.add(view.findViewById<View>(R.id.key7) as TextView)
        keypads.add(view.findViewById<View>(R.id.key8) as TextView)
        keypads.add(view.findViewById<View>(R.id.key9) as TextView)
        keypads.add(view.findViewById<View>(R.id.key11) as TextView)
        indicator1 = view.findViewById(R.id.indicator_1)
        indicator2 = view.findViewById(R.id.indicator_2)
        indicator3 = view.findViewById(R.id.indicator_3)
        indicator4 = view.findViewById(R.id.indicator_4)
        indicator5 = view.findViewById(R.id.indicator_5)
        indicator6 = view.findViewById(R.id.indicator_6)
        mRootLayout = view.findViewById(R.id.root_layout)
        keyPadContainer = view.findViewById(R.id.key_pad_container)
        setListener()
        setThemes()
        if (numberOfPins == 4) {
            indicator5!!.visibility = GONE
            indicator6!!.visibility = GONE
        }
        if (numberOfPins == 5) {
            indicator6!!.visibility = GONE
        }
        mRootLayout!!.setBackgroundColor(background_color)
        indicator1!!.background = hollow
        indicator2!!.background = hollow
        indicator3!!.background = hollow
        indicator4!!.background = hollow
        indicator5!!.background = hollow
        indicator6!!.background = hollow

        errorTextView!!.text = errorText
    }

    private fun setListener() {
        for (keypad in keypads) {
            keypad.setOnClickListener {
                if (listener != null) {
                    indicators.push(keypad.text.toString())
                    if (indicators.size <= numberOfPins) {
                        setIndicators(indicators.size)
                        if (indicators.size == numberOfPins) {
                            listener!!.onKeyPadPressed(pinToString(indicators))
                        }
                    } else {
                        indicators.pop()
                    }
                } else {
                    indicators.push(keypad.text.toString())
                    if (indicators.size <= numberOfPins) {
                        setIndicators(indicators.size)
                    } else {
                        indicators.pop()
                    }
                }
            }
        }
        mBackRight!!.setOnClickListener { doClearPin() }
        mBackLeft!!.setOnClickListener { clearAll() }
    }

    private fun clearAll() {
        if (listener != null) {
            listener!!.onClear()
            try {
                indicators.clear()
                setIndicators(indicators.size)
                // listener!!.onKeyPadPressed(pinToString(indicators))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun doClearPin() {
        if (listener != null) {
            listener!!.onKeyBackPressed()
            try {
                if (indicators.size != 0) {
                    indicators.pop()
                    setIndicators(indicators.size)
                } else {
                    setIndicators(indicators.size)
                }
                // listener!!.onKeyPadPressed(pinToString(indicators))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setError() {
        if (!TextUtils.isEmpty(errorText)) {
            errorTextView!!.visibility = VISIBLE
            errorTextView!!.text = errorText
        }

        if (errorTextSize != 0f) {
            errorTextView!!.textSize = errorTextSize
        }
    }

    private fun setIndicators(size: Int) {
        when (size) {
            0 -> {
                indicator1!!.background = hollow
                indicator2!!.background = hollow
                indicator3!!.background = hollow
                indicator4!!.background = hollow
                indicator5!!.background = hollow
                indicator6!!.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            1 -> {
                indicator1!!.background = solid
                indicator2!!.background = hollow
                indicator3!!.background = hollow
                indicator4!!.background = hollow
                indicator5!!.background = hollow
                indicator6!!.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            2 -> {
                indicator1!!.background = solid
                indicator2!!.background = solid
                indicator3!!.background = hollow
                indicator4!!.background = hollow
                indicator5!!.background = hollow
                indicator6!!.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            3 -> {
                indicator1!!.background = solid
                indicator2!!.background = solid
                indicator3!!.background = solid
                indicator4!!.background = hollow
                indicator5!!.background = hollow
                indicator6!!.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            4 -> {
                indicator1!!.background = solid
                indicator2!!.background = solid
                indicator3!!.background = solid
                indicator4!!.background = solid
                indicator5!!.background = hollow
                indicator6!!.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            5 -> {
                indicator1!!.background = solid
                indicator2!!.background = solid
                indicator3!!.background = solid
                indicator4!!.background = solid
                indicator5!!.background = solid
                indicator6!!.background = hollow
                errorTextView!!.visibility = INVISIBLE
            }
            6 -> {
                indicator1!!.background = solid
                indicator2!!.background = solid
                indicator3!!.background = solid
                indicator4!!.background = solid
                indicator5!!.background = solid
                indicator6!!.background = solid
                errorTextView!!.visibility = INVISIBLE
            }
            -1 -> {
                indicator1!!.background = errorIndicator
                indicator2!!.background = errorIndicator
                indicator3!!.background = errorIndicator
                indicator4!!.background = errorIndicator
                indicator5!!.background = errorIndicator
                indicator6!!.background = errorIndicator
                setError()
            }
        }
    }

    private fun pinToString(indicators: Stack<String>): String {
        var str = ""
        for (s in indicators) {
            str += s
        }
        return str
    }

    fun dipToPixels(context: Context, dipValue: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
    }

    private fun dpToPx(valueInDp: Float): Int {
        return TypedValue.applyDimension(1, valueInDp, this.resources.displayMetrics)
            .toInt()
    }

    private fun dpToPxF(valueInDp: Float): Float {
        return TypedValue.applyDimension(1, valueInDp, this.resources.displayMetrics)
    }

    fun dpToSp(dp: Float, context: Context): Int {
        return (dpToPxx(dp, context) / context.resources.displayMetrics.scaledDensity).toInt()
    }

    fun dpToPxx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
            .toInt()
    }

    fun spToPx(sp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.resources.displayMetrics
        )
            .toInt()
    }
}