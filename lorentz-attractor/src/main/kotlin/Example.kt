import org.openrndr.*
import org.openrndr.math.Vector2
import org.openrndr.color.ColorRGBa

class Example : Program() {
    private var a = -1.0 + 2.0 * Math.random()
    private val b = 1.0
    private val scaleFactor = 8.0
    private val bgColor = ColorRGBa(0.98, 0.98, 1.0)
    private val color = ColorRGBa(0.0, 0.0, 0.2, 0.01)

    override fun setup() {
        window.presentationMode = PresentationMode.MANUAL
        mouse.clicked.listen {
            a = -1.0 + 2.0 * Math.random()
            window.requestDraw()
        }
    }

    private fun sq(v: Double): Double {
        return v * v
    }

    override fun draw() {
        var last = Vector2(-20.0 + Math.random() * 40.0, -20.0 + Math.random() * 40.0)
        val positions2D: ArrayList<Vector2> = ArrayList()
        positions2D.add(last.times(scaleFactor))

        fun f(x: Double): Double {
            return a * x + 2.0 * (1.0 - a) * sq(x) / (1 + sq(x))
        }

        for (i in 0..100000) {
            val nextX = b * last.y + f(last.x)
            val nextY = f(nextX) - last.x
            last = Vector2(nextX, nextY)
            positions2D.add(last.times(scaleFactor))
        }

        drawer.background(bgColor)
        drawer.translate(window.size.x / 2, window.size.y / 2)

        drawer.stroke = null
        drawer.fill = color

//        drawer.rectangles(positions2D, 1.0, 1.0)
        drawer.circles(positions2D, 3.0)

//        val c = contour {
//            moveTo(positions2D[0])
//            for (i in 1..positions2D.count() - 1) {
//                lineTo(positions2D[i])
//            }
//        }
//        drawer.contour(c)
    }
}

fun main(args: Array<String>) {
    application(Example(),
            configuration {
                width = 600
                height = 450
            })
}