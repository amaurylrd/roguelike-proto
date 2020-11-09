package engine.scene.entity;

import engine.geom.Veclocity;

public abstract class Entity extends Component implements Collidable {
    protected Veclocity veclocity;
    private boolean grounded = false;
    private boolean solid = false;

    public Entity(double x, double y, double width, double height, int layer) {
        super(x, y, width, height, layer);
        veclocity = new Veclocity(0, 0);
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

//     protected Rectangle offbox;
//     /**
//      * velocity x
//      */
//     private double xspeed;
//     private double yspeed;
//     private int layer;
    
//     private boolean grounded = false;
//     private boolean solid = false;
    
//     //add animation, hitbox

//     public Entity(int x, int y, double width, double height, int zindex) {
//         init(x, y, width, height, zindex);
//     }

//     public Entity(int x, int y, double width, double height) {
//         init(x, y, width, height, 0);
//     }

//     public void init(int x, int y, double width, double height, int zindex) {
//         offbox = new Rectangle(x, y, width, height);
//         setLayer(zindex);
//     }
// }
