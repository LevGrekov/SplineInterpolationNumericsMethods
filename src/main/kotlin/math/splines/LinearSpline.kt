package math.splines

import math.polynomials.NewtonPolynomial
import java.util.*

class LinearSpline(points: Map<Double, Double>) : InterpolationSpline(points) {

    override val segments: List<SplineSegment> = buildSegments()

    private fun buildSegments(): List<SplineSegment> {
        require(points.isNotEmpty()) { "Points map cannot be empty." }

        val segments = mutableListOf<SplineSegment>()
        val iterator = points.iterator()

        if (iterator.hasNext()) {
            var (x1, y1) = iterator.next().toPair()
            var segmentIndex = 0

            while (iterator.hasNext()) {
                val (x2, y2) = iterator.next().toPair()

                val polynomial = NewtonPolynomial(mapOf(x1 to y1, x2 to y2))
                segments.add(SplineSegment(x1, x2, polynomial))

                x1 = x2
                y1 = y2
                segmentIndex++
            }
        }
        return segments
    }
}