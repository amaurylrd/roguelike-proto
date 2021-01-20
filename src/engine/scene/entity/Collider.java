package engine.scene.entity;

import engine.physics2d.Vector;

/**
 * The class {@code Collider} a collidable body which are placed under the law of the physics engine.
 */
public abstract class Collider extends Component {
    /**
     * The attribut {@code velocity} specified the linear velocity of this body per update.
     */
    public Vector velocity = new Vector(0, 0); //TODO: protected
    
    /**
     * This coefficient sets the elasticity of this body. The value should be in between 0 and 1.
     * The higher is {@code restitution}, the more this {@code Collider} is bouncy.
     */
    public double restitution; //TODO: protected

    /**
     * This coefficient sets the resistance to movement that occurs when colliding this surface.
     * The higher is {@code friction}, the greater will be the deceleration.
     */
    public double friction; //TODO: protected

    /**
     * This flag tells if this body is solid or not.
     * If set to <i>true</i> objetcs will go through this {@collider Collider}.
     */
    protected boolean solid = true;

    /**
     * Tells if this plateform is passable from below.
     */
    protected boolean traversable = false;

	public Collider(double x, double y, double width, double height, int zindex) {
        super(x, y, width, height, zindex);
	}

	/**
     * Specifies whether this {@code Collider} is solid or not.
     * 
     * @param isSolid <i>true</i> if this {@code Collider} should be solid
     * @see isSolid()
     */
    public void setSolid(boolean isSolid) {
        solid = isSolid;
    }

    /**
     * Checks whether or not this {@code Collider} is solid.
     * 
     * @return <i>true</i> if this {@code Collider} is solid, <i>false</i> otherwise
     * @see setSolid(boolean isSolid)
     */
    public boolean isSolid() {
        return solid;
    }

    // public void updateVelocity(Vector vector) {
    //     velocity.set(vector);
    // }

    // public void applyForce(Vector vector) {
    //     velocity.translate(vector);
    // }
}
