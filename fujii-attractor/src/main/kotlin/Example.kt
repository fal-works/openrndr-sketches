import org.openrndr.*
import org.openrndr.math.Vector2
import org.openrndr.color.ColorRGBa
import kotlin.math.*

class Example : Program() {
    override fun setup() {
        window.presentationMode = PresentationMode.MANUAL
        mouse.clicked.listen {
            window.requestDraw()
        }
    }

    override fun draw() {
//        val a: Array<Double> = Array(6) { -4.0 + 8.0 * Math.random() }
//        val f: Array<Double> = Array(6) { -4.0 + 8.0 * Math.random() }
//        val v = Math.random()
        val a: Array<Double> = arrayOf(-2.1, 1.4, 1.1, 1.1, 1.2, 0.9)
        val f: Array<Double> = arrayOf(0.4, 1.1, 1.0, 1.1, 1.0, 0.7)
        val v = 0.15

        var x = 0.0
        var y = 0.0
        var t = 0.0

        val positions: List<Vector2> = List(10000000) {
            val pX = x
            val pY = y
            x = a[0] * sin(f[0] * pX) + a[1] * cos(f[1] * pY) + a[2] * sin(f[2] * t)
            y = a[3] * cos(f[3] * pX) + a[4] * sin(f[4] * pY) + a[5] * cos(f[5] * t)
            t += v
            Vector2(x * 130, y * 130)
        }

        drawer.background(ColorRGBa(0.98, 0.98, 1.0))
        drawer.translate(480.0, 480.0)

        val color = ColorRGBa(0.0, 0.0, 0.2, 0.015)
        drawer.fill = color
        drawer.stroke = null

        drawer.rectangles(positions, 1.0, 1.0)
    }
}

fun main(args: Array<String>) {
    application(Example(),
            configuration {
                width = 960
                height = 960
            })
}