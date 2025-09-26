package my.first.drawapp

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mDrawPath: CustomPath? = null
    private var mDrawPaint: Paint? = null
    private var mCanvasPaint: Paint? = null
    private var mBrushSize: Float = 0f
    private var color = Color.BLACK
    private val mPaths = ArrayList<CustomPath>()
    private val mundoPaths = ArrayList<CustomPath>()

    init {
        setUpDrawing()
    }
    fun onClickUndo(){
        if (mPaths.size > 0){
            mundoPaths.add(mPaths.removeAt(mPaths.size - 1))
            invalidate()
        }
    }

    private fun setUpDrawing() {
        mDrawPaint = Paint().apply {
            color = this@DrawingView.color
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            isAntiAlias = true
        }
        mCanvasPaint = Paint(Paint.DITHER_FLAG)

        setSizeForBrush(20f)

        mDrawPath = CustomPath(color, mBrushSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (path in mPaths) {
            mDrawPaint?.strokeWidth = path.thickness
            mDrawPaint?.color = path.color
            canvas.drawPath(path, mDrawPaint!!)
        }

        mDrawPath?.let { path ->
            if (!path.isEmpty) {
                mDrawPaint?.strokeWidth = path.thickness
                mDrawPaint?.color = path.color
                canvas.drawPath(path, mDrawPaint!!)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x ?: return false
        val touchY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDrawPaint?.strokeWidth = mBrushSize
                mDrawPaint?.color = color

                mDrawPath = CustomPath(color, mBrushSize)
                mDrawPath?.reset()
                mDrawPath?.moveTo(touchX, touchY)
            }

            MotionEvent.ACTION_MOVE -> {
                mDrawPath?.lineTo(touchX, touchY)
            }
            MotionEvent.ACTION_UP -> {
                mDrawPath?.let { mPaths.add(it) }
                mDrawPath = CustomPath(color, mBrushSize)
            }
            else -> return false
        }
        invalidate()
        return true
    }


    fun setSizeForBrush(newSize: Float) {
        mBrushSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            newSize,
            resources.displayMetrics
        )
        mDrawPaint?.strokeWidth = mBrushSize
        Log.d("BrushSize", "Brush size set to $mBrushSize px")
    }

    fun setColor(newColor : String){
        color = Color.parseColor(newColor)
        mDrawPaint!!.color = color
    }


    internal inner class CustomPath(var color: Int, var thickness: Float) : Path()
}
