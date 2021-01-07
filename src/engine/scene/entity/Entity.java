package engine.scene.entity;

import engine.physics2d.Vector;

import java.awt.geom.Point2D;
import engine.geom.shape.Rectangle;

public abstract class Entity extends Collider {
    public Vector velocity = new Vector(0, 0); //TODO: protected
    //private boolean grounded = false;
    
    
    public double mass; //TODO: protected density
    public Rectangle FUTUR; //TODO: protected

    //private Map<String, Sprite> sprites;
    //private String currentSprite;

    public Entity(double x, double y, double width, double height, int layer) {
        super(x, y, width, height, layer);
    }

    public Entity(double x, double y, double width, double height) {
        super(x, y, width, height, 0);
    }

    @Override
	public void update(double dt) {
		bounds.translate(velocity.scale(dt));
    }

    public void pre(double dt) {
        FUTUR = bounds.clone();
        FUTUR.translate(velocity.scale(dt));
    }

    //sprites = new HashMap<String, Sprite>();
    //currentSprite = null;

    // /**
    //  * Specifies whether this component is grounded or not.
    //  * 
    //  * @param isGrounded <i>true</i> if this component should be grounded
    //  * @see isGrounded()
    //  */
    // public void setGrounded(boolean isGrounded) {
    //     grounded = isGrounded;
    // }

    // /**
    //  * Checks whether or not this component is grounded.
    //  * 
    //  * @return <i>true</i> if this component is grounded, <i>false</i> otherwise
    //  * @see setGrounded(boolean isGrounded)
    //  */
    // public boolean isGrounded() {
    //     return grounded;
    // }
    
    
    public boolean collides(Rectangle hitbox) {
        return FUTUR.intersects(hitbox);
    }
    
    public Vector getNormal(Entity component) {
        Point2D.Double center = bounds.center();
        Point2D.Double center2 = component.bounds.center();

        double x = (bounds.getWidth() + component.bounds.getWidth()) / 2;
        double y = (bounds.getHeight() + component.bounds.getHeight()) / 2;

        if (Math.abs(center.x - center2.x) < x)
            return new Vector(0, 1);
        if (Math.abs(center.y - center2.y) < y)
            return new Vector(1, 0);

        Vector centVector = new Vector(center.x, center.y);
        Vector cent2Vector = new Vector(center2.x, center2.y);
        double d = Math.sqrt(x * x  + y * y);
        double e = 0.1;
        if (centVector.sub(cent2Vector).magnitude() > d - e)
           return new Vector(0, 0);

        center.setLocation(center.x + (center.x < center2.x ? 1 : -1) * component.bounds.getWidth() / 2,
                center.y + (center.y < center2.y ? 1 : -1) * component.bounds.getHeight() / 2);

        if (Math.abs(center.x - center2.x) * bounds.getHeight() - Math.abs(center.y - center2.y) * bounds.getWidth() < 0)
            return new Vector(0, 1);
        return new Vector(1, 0);
    }

    public Vector getNormal(Tile component) {
        Point2D.Double center = bounds.center();
        Point2D.Double center2 = component.bounds.center();

        double x = (bounds.getWidth() + component.bounds.getWidth()) / 2;
        double y = (bounds.getHeight() + component.bounds.getHeight()) / 2;

        if (component.traversable)
            return center2.y - center.y > y ? new Vector(0, 1) : new Vector(0, 0);

        if (Math.abs(center.x - center2.x) < x)
            return new Vector(0, 1);
        if (Math.abs(center.y - center2.y) < y)
            return new Vector(1, 0);

        Vector centVector = new Vector(center.x, center.y);
        Vector cent2Vector = new Vector(center2.x, center2.y);
        double d = Math.sqrt(x * x  + y * y);
        double e = 0.1;
        if (centVector.sub(cent2Vector).magnitude() > d - e)
           return new Vector(0, 0);

        center.setLocation(center.x + (center.x < center2.x ? 1 : -1) * component.bounds.getWidth() / 2,
                center.y + (center.y < center2.y ? 1 : -1) * component.bounds.getHeight() / 2);

        if (Math.abs(center.x - center2.x) * bounds.getHeight() - Math.abs(center.y - center2.y) * bounds.getWidth() < 0)
            return new Vector(0, 1);
        return new Vector(1, 0);
    }

    //si collides true dans scene, player.apply(comp) comp.apply(player)
}