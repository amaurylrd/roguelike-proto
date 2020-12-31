package engine.scene.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.application.Ressources;

public abstract class Tile extends Component implements Collidable {
	private BufferedImage texture;
	protected boolean solid = false;

	public Tile(String ressourceName, double x, double y, double width, double height, int layer) {
		super(x, y, width, height, layer);
		texture = Ressources.ressource(ressourceName);
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(texture, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(), null);
		super.render(graphics);
	}

	@Override
	public boolean isRemovable() {
		return false;
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
		return isSolid() && component.isSolid() && bounds.intersects(((Component) component).bounds);
	}
}
