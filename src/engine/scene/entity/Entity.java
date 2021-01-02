package engine.scene.entity;

import java.util.Map;
import java.awt.Graphics2D;
import java.util.HashMap;

import engine.physics2d.Vector;
import engine.scene.image.Sprite;
import java.awt.geom.Point2D;

public abstract class Entity extends Component implements Collidable {
    public Vector velocity;
    protected boolean grounded = false;
    protected boolean solid = false;

    private Map<String, Sprite> sprites;
    private String currentSprite;

    public Entity(double x, double y, double width, double height, int layer) {
        super(x, y, width, height, layer);
        init();
    }

    public Entity(double x, double y, double width, double height) {
        super(x, y, width, height, 0);
        init();
    }

    private void init() {
        velocity = new Vector(0, 0);
        sprites = new HashMap<String, Sprite>();
        currentSprite = null;
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
    @Override
    public void setSolid(boolean isSolid) {
        solid = isSolid;
    }

    /**
     * Checks whether or not this component is solid.
     * 
     * @return <i>true</i> if this component is solid, <i>false</i> otherwise
     * @see setSolid(boolean isSolid)
     */
    @Override
    public boolean isSolid() {
        return solid;
    }

    @Override
    public boolean collides(Collidable component) {
        // TODO Auto-generated method stub

        //mes hitbox touchent sa hurtbox
        //ses hitbox touchent ma hurtbox
        return isSolid() && component.isSolid() && bounds.intersects(((Component) component).bounds);
    }

    public Vector getNormal(Component component) {
        Point2D.Double center = bounds.center();
        Point2D.Double center2 = component.bounds.center();

        if (Math.abs(center.x - center2.x) < component.bounds.getWidth()/2)
            return new Vector(0, 1);
        if (Math.abs(center.y - center2.y) < component.bounds.getHeight()/2)
            return new Vector(1, 0);

        center.setLocation(center.x + (center.x < center2.x ? 1 : -1)*component.bounds.getWidth()/2,
            center.y + (center.y < center2.y ? 1 : -1)*component.bounds.getHeight()/2);
        if (Math.abs(center.x - center2.x)*bounds.getHeight() < Math.abs(center.y - center2.y)*bounds.getWidth())
            return new Vector(0, 1);
        return new Vector(1, 0);
    }

    //si collides true dans scene, player.apply(comp) comp.apply(player)
}