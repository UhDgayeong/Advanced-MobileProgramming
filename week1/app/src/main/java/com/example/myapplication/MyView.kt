package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.*

class MyView : View {
    private var paint = Paint()
    var rect = Rect(10, 10, 110, 110)
    var xx = 0F
    var yy = 0F
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        paint.color = Color.RED

        var rand = Random().nextInt(3) + 1

        if (rand == 1) { //사각형 그리기
            println("사각형그리기")
            canvas.drawRect(rect, paint)
        }
        else if (rand == 2) { //삼각형 그리기
            println("삼각형그리기")
            paint.strokeWidth = 5F
            canvas.drawLine(xx+0, yy-60, xx-50, yy+20, paint)
            canvas.drawLine(xx-50, yy+20, xx+50, yy+20, paint)
            canvas.drawLine(xx+50, yy+20, xx+0, yy-60, paint)
        }
        else { //원 그리기
            println("원그리기")
            canvas.drawCircle(xx, yy, 50F, paint)
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            println("${event.x}, ${event.y}")

            xx = event.x.toFloat()
            yy = event.y.toFloat()
            rect.left = event.x.toInt()
            rect.top = event.y.toInt()
            rect.right = rect.left + 100
            rect.bottom = rect.top + 100

            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }
}