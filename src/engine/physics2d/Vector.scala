package engine.physics2d

import scala.math.sqrt

class Vector(var x: Float, var y: Float) {
    def getX() : Float = x
    
    def getY() : Float = y
    
    def setX(x: Float) : Unit = this.x = x

    def setY(y: Float) : Unit = this.y = y
    
    def set(x: Float, y: Float) : Unit = {
        this.x = x
        this.y = y
    }

    def set(vector: Vector) : Unit = set(vector.x, vector.y)

    def translateX(x: Float) : Unit = this.x += x

     def translateY(y: Float) : Unit = this.y += y

    def getMagnitude() : Float = sqrt(x*x + y*y).asInstanceOf[Float]

    def normalize() : Unit = {
        val magnitude : Float = getMagnitude()
        if (magnitude > 0) {
            x /= magnitude
            y /= magnitude
        }
    }

    def negate() : Unit = {
        x = -x
        y = -y
    }

    def scale(factor: Float) : Vector = {
        new Vector(x * factor, y * factor)
    }

    def dot(vector: Vector) : Float = {
		x*vector.x + y*vector.y
    }

    def sub(vector: Vector) : Vector = {
        new Vector(x - vector.x, y - vector.y) 
    }

    override def toString: String = s"($x, $y)"
}
