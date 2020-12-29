package engine.scene.entity;

public interface Collidable {
	public abstract boolean collides(Collidable component);

	public abstract boolean isSolid();

	public abstract void setSolid(boolean isSolid);
}