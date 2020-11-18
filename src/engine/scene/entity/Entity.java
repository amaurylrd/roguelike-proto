package engine.scene.entity;

import engine.physics2d.Vector;

public abstract class Entity extends Component implements Collidable {
    protected Vector veclocity;
    private boolean grounded = false;
    private boolean solid = false;

    public Entity(double x, double y, double width, double height, int layer) {
        super(x, y, width, height, layer);
        veclocity = new Vector(0, 0);
    }

    /**
     * Specifies whether this component is grounded or not.
     * 
     * @param isGrounded <i>true</i> if this component should be grounded
     * @see isGrounded()
     */
    public void setGrounded(boolean isGrounded) {
    	grounded = isGrounded;
    }

    /**
     * Checks whether or not this component is grounded.
     * 
     * @return <i>true</i> if this component is grounded, <i>false</i> otherwise
     * @see setGrounded(boolean isGrounded)
     */
    public boolean isGrounded() {
    	return grounded;
    }

    /**
     * Specifies whether this component is solid or not.
     * 
     * @param isSolid <i>true</i> if this component should be solid
     * @see isSolid()
     */
    public void setSolid(boolean isSolid) {
    	solid = isSolid;
    }

    /**
     * Checks whether or not this component is solid.
     * 
     * @return <i>true</i> if this component is solid, <i>false</i> otherwise
     * @see setSolid(boolean isSolid)
     */
    public boolean isSolid() {
    	return solid;
    }
}