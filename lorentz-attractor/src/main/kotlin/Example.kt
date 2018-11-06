import org.openrndr.*
import org.openrndr.math.Vector3
import org.openrndr.math.Vector2
import org.openrndr.color.ColorRGBa
//import org.openrndr.shape.ShapeContour

class LorentzAttractor(
        private val p: Double,
        private val r: Double,
        private val b: Double
) {
    fun calculateNextPoint(cur: Vector3, timeScale: Double): Vector3 {
        val displacement = calculateVelocity(cur).times(timeScale)
        return cur + displacement
    }

    private fun calculateVelocity(cur: Vector3): Vector3 {
        return Vector3(
                -p * cur.x + p * cur.y,
                -cur.x * cur.z + r * cur.x - cur.y,
                cur.x * cur.y - b * cur.z
        )
    }
}

class Example : Program() {
    private val scaleFactor = 10.0
    private val bgColor = ColorRGBa(0.98, 0.98, 1.0)
    private val drawColor = ColorRGBa(0.0, 0.0, 0.2, 0.3)
    private val steps = 50000
    private val initialPoint = Vector3(0.01, 0.0, 0.0)
    private val timeScale = 0.002

    override fun setup() {
        window.presentationMode = PresentationMode.MANUAL
        mouse.clicked.listen {
            window.requestDraw()
        }
    }

    override fun draw() {
        // Initialize drawer
        drawer.background(bgColor)
        drawer.translate(window.size.x / 2, window.size.y / 2)
        drawer.stroke = drawColor
        drawer.fill = null

        // Calculate all vertices
        val attractor = LorentzAttractor(10.0, 28.0, 8.0 / 3.0)
        val lineSegmentListVertices3d: ArrayList<Vector3> = ArrayList()
        val contourVertices2d: ArrayList<Vector2> = ArrayList()   // for omitting the Z coordinate
        var currentPoint = initialPoint
        for (i in 0..steps) {
            // Calculate next point
            val nextPoint = attractor.calculateNextPoint(currentPoint, timeScale)

            // [3d] Add start & end points of line segment
            lineSegmentListVertices3d.add(currentPoint.times(scaleFactor))
            lineSegmentListVertices3d.add(nextPoint.times(scaleFactor))

            // [2d] Add contour vertex
            contourVertices2d.add(Vector2(nextPoint.x, nextPoint.y).times(scaleFactor))

            // Update current point
            currentPoint = nextPoint
        }

        // Draw in 3d with line segments
        drawer.lineSegments3d(lineSegmentListVertices3d)

        // Draw in 2d with contour
//        drawer.contour(ShapeContour.fromPoints(contourVertices2d, false))
    }
}

fun main(args: Array<String>) {
    application(Example(),
            configuration {
                width = 600
                height = 600
            })
}