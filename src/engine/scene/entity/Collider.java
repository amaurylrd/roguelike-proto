package engine.scene.entity;

//collidable object
//rigib body
public abstract class Collider extends Component {
    public double restitution; //TODO: protected
    //bounciness coefficient of restitution elasticity
	public double friction; //TODO: protected
	protected boolean solid = true;
	
	public Collider(double x, double y, double width, double height, int layer) {
		super(x, y, width, height, layer);
	}

	/**
     * Specifies whether this {@code Entity} is solid or not.
     * 
     * @param isSolid <i>true</i> if this {@code Entity} should be solid
     * @see isSolid()
     */
    public void setSolid(boolean isSolid) {
        solid = isSolid;
    }

    /**
     * Checks whether or not this {@code Entity} is solid.
     * 
     * @return <i>true</i> if this {@code Entity} is solid, <i>false</i> otherwise
     * @see setSolid(boolean isSolid)
     */
    public boolean isSolid() {
        return solid;
    }
}
