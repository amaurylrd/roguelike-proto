package engine.physics2d

import scala.math.sqrt

class Vector(var x: Double, var y: Double) {
    def getX() : Double = x
    
    def getY() : Double = y
    
    def setX(x: Double) : Unit = this.x = x

    def setY(y: Double) : Unit = this.y = y
    
    def set(x: Double, y: Double) : Unit = {
        this.x = x
        this.y = y
    }

    def set(vector: Vector) : Unit = set(vector.x, vector.y)

    def translateX(x: Double) : Unit = this.x += x

     def translateY(y: Double) : Unit = this.y += y

    def getMagnitude() : Double = sqrt(x*x + y*y)

    def normalize() : Unit = {
        val magnitude : Double = getMagnitude()
        if (magnitude > 0) {
            x /= magnitude
            y /= magnitude
        }
    }

    def negate() : Unit = {
        x = -x
        y = -y
    }

    def scale(factor: Double) : Vector = {
        new Vector(x * factor, y * factor)
    }

    def dot(vector: Vector) : Double = {
		x*vector.x + y*vector.y
    }

    def sub(vector: Vector) : Vector = {
        new Vector(x - vector.x, y - vector.y) 
    }

    override def toString: String = s"($x, $y)"
}
