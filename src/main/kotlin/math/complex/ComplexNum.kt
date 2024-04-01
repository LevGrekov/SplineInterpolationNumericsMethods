package math.complex

import math.eq
import kotlin.math.*

class ComplexNum(var re: Double = 0.0, var im: Double = 0.0)  {

    fun arg(): Double {
        if (re == 0.0 && im == 0.0) return Double.NaN // Нет угла, если комплексное число нулевое
        return atan2(im, re)
    }

    operator fun plus(other: ComplexNum) : ComplexNum = ComplexNum(re + other.re, im + other.im)
    operator fun plusAssign(other: ComplexNum): Unit{
        re += other.re
        im += other.im
    }

    operator fun minus(other: ComplexNum) = ComplexNum(re - other.re, im - other.im)
    operator fun minusAssign(other: ComplexNum): Unit{
        re -= other.re
        im -= other.im
    }

    operator fun times(other: ComplexNum) = ComplexNum(re * other.re - im * other.im, re * other.im + im * other.re)
    operator fun times(other: Double) = ComplexNum( re * other, im * other)
    operator fun div(divisor: Double): ComplexNum {
        return ComplexNum(re / divisor, im / divisor)
    }
    operator fun timesAssign(other: ComplexNum){
        val r = re * other.re - im * other.im
        im = re * other.im + im * other.re
        re = r
    }

    operator fun unaryMinus() = ComplexNum(-re, -im)

    operator fun div(other: ComplexNum) = ComplexNum((re * other.re + im * other.im) / other.abs2(), (im * other.re - re * other.im) / other.abs2())
    operator fun divAssign(other: ComplexNum){
        val r = (re * other.re + im * other.im) / other.abs2()
        im = (im * other.re - re * other.im) / other.abs2()
        re = r
    }

    override fun toString() = buildString {
        append("(")
        if ((re != 0.0) || (im == 0.0)) append("%.4f".format(re))
        if(im != 0.0) {
            append(if(im < 0.0) "-" else if(re != 0.0) "+" else "")
            val formattedIm = if(im.absoluteValue != 1.0) "%.4f".format(im.absoluteValue) else ""
            append(formattedIm)
            append("i")
        }
        append(")")
    }
    private fun conj() = ComplexNum(re, -im)
    fun sin(): ComplexNum {
        return ComplexNum(sin(re) * cosh(im), cos(re) * sinh(im))
    }

    fun cos(): ComplexNum {
        return ComplexNum(cos(re) * cosh(im), -sin(re) * sinh(im))
    }

    operator fun not() = conj()

    fun abs() = sqrt(re * re + im * im)
    fun abs2() = re * re + im * im
    fun sign(): ComplexNum = this/this.abs()

    fun sqrt() = ComplexNum(this.abs() * cos(this.arg()) )
    fun toDouble(): Double? = if(im eq 0.0 ) re else null

    infix fun eq(other: ComplexNum) =
        abs(re - other.re) < max(re.ulp, other.re.ulp) * 10.0 &&
        abs(im - other.im) < max(im.ulp, other.im.ulp) * 10.0
    infix fun neq(other: ComplexNum) = !this.eq(other)

    val Double.i: ComplexNum
        get() = ComplexNum(0.0, this)

}