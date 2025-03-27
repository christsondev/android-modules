package com.christsondev.components.bottombar

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

internal class CurvedShape(
    private val positionOffset: Float,
    private val circleRadius: Float,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val width = size.width
        val height = size.height

        val offset = positionOffset

        val path = Path().apply {
            // Calculate points and control points
            val firstCurveStartPoint = Offset(
                x = offset - (circleRadius * 2) - (circleRadius / 3),
                y = 0f
            )
            val firstCurveEndPoint = Offset(
                x = offset,
                y = circleRadius + (circleRadius / 4)
            )
            val secondCurveStartPoint = firstCurveEndPoint
            val secondCurveEndPoint = Offset(
                x = offset + (circleRadius * 2) + (circleRadius / 3),
                y = 0f
            )

            val firstCurveControlPoint1 = Offset(
                x = firstCurveStartPoint.x + circleRadius + (circleRadius / 4),
                y = firstCurveStartPoint.y
            )
            val firstCurveControlPoint2 = Offset(
                x = firstCurveEndPoint.x - (circleRadius * 2) + circleRadius,
                y = firstCurveEndPoint.y
            )

            val secondCurveControlPoint1 = Offset(
                x = secondCurveStartPoint.x + (circleRadius * 2) - circleRadius,
                y = secondCurveStartPoint.y
            )
            val secondCurveControlPoint2 = Offset(
                x = secondCurveEndPoint.x - (circleRadius + (circleRadius / 4)),
                y = secondCurveEndPoint.y
            )

            // Draw the path
            moveTo(0f, 0f) // Start from top-left corner
            lineTo(firstCurveStartPoint.x, firstCurveStartPoint.y)
            cubicTo(
                firstCurveControlPoint1.x, firstCurveControlPoint1.y,
                firstCurveControlPoint2.x, firstCurveControlPoint2.y,
                firstCurveEndPoint.x, firstCurveEndPoint.y
            )
            cubicTo(
                secondCurveControlPoint1.x, secondCurveControlPoint1.y,
                secondCurveControlPoint2.x, secondCurveControlPoint2.y,
                secondCurveEndPoint.x, secondCurveEndPoint.y
            )
            lineTo(width, 0f) // Line to top-right corner
            lineTo(width, height) // Line to bottom-right corner
            lineTo(0f, height) // Line to bottom-left corner
            close() // Close the path
        }

        return Outline.Generic(path = path)
    }
}