package sandbox.collission;

import engine.scene.entity.Tile;

public class RigidBody extends Tile {
	public RigidBody(String ressourceName, double x, double y, double width, double height, int layer) {
		super(ressourceName, x, y, width, height, layer);
		setSolid(true);
	}	
}
