package engine.physics2d;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import engine.scene.entity.Entity;
import engine.scene.entity.Entity.Collision;
import engine.scene.entity.Tile;

public class Collisions {
	private static Map<Entity, Collection<Collision>> contacts;

	public static void detection(List<Entity> entities, Collection<Tile> tiles) {
		contacts = new java.util.HashMap<Entity, Collection<Collision>>();
		for (int i = 0; i < entities.size(); ++i) {
			Collection<Collision> collisions = new ArrayList<>();
			Entity entity = entities.get(i);
			for (int j = i + 1; j < entities.size(); ++j) {
				Collision collision = entity.collides(entities.get(j));
				if (collision.collides)
					collisions.add(collision);
			}

			for (Tile tile : tiles) {
				Collision collision = entity.collides(tile);
				if (collision.collides)
					collisions.add(collision);
			}

			if (!collisions.isEmpty())
				contacts.put(entity, collisions);
		}
	}

	public static void resolve() {
		for (Map.Entry<Entity, Collection<Collision>> entry : contacts.entrySet()) {
			Entity entity = entry.getKey();
			for (Collision contact : entry.getValue()) {
				if (contact.collider instanceof Entity) {
					Entity colldier = (Entity) contact.collider;
					final Vector normal = contact.normal;

					double v1 = entity.velocity.dot(normal);
					double v2 = colldier.velocity.dot(normal);
					double m1 = entity.mass;
					double m2 = colldier.mass;
					double vf = (entity.restitution + colldier.restitution) * (2 * m2 * v2 +  (m1 - m2) * v1) / (m1 + m2);
					double vf2 = (entity.restitution + colldier.restitution) * (2 * m1 * v1 +  (m2 - m1) * v2) / (m1 + m2);
					
					Vector forces = Vector.scale(normal, vf - v1);
					if (Math.abs(entity.impulse.getX()) < Math.abs(forces.getX()))
						entity.impulse.setX(forces.getX());
					
					if (Math.abs(entity.impulse.getY()) < Math.abs(forces.getY()))
						entity.impulse.setY(forces.getY());

					forces = Vector.scale(normal, vf2 - v2);
					if (Math.abs(colldier.impulse.getX()) < Math.abs(forces.getX()))
						colldier.impulse.setX(forces.getX());
					
					if (Math.abs(colldier.impulse.getY()) < Math.abs(forces.getY()))
						colldier.impulse.setY(forces.getY());

				} else if (contact.collider instanceof Tile) {
					Tile colldier = (Tile) contact.collider;
					
					final Vector normal = contact.normal;
					final Vector tangeante = new Vector(-normal.getY(), normal.getX());
						
					double bounce = (1 + 0*Math.max(entity.restitution, colldier.restitution)) * entity.velocity.dot(normal);
					double frict = 50/entity.mass * Math.min(entity.friction, colldier.friction) * entity.velocity.dot(tangeante);

					Vector f = Vector.scale(normal, -bounce);
					f.translate(Vector.scale(tangeante, -frict));
					if (Math.abs(entity.impulse.getX()) < Math.abs(f.getX()))
						entity.impulse.setX(f.getX());
					
					if (Math.abs(entity.impulse.getY()) < Math.abs(f.getY()))
						entity.impulse.setY(f.getY());
				}
			}
		}
	}

	public static void correction() {
		for (Map.Entry<Entity, Collection<Collision>> entry : contacts.entrySet()) {
			Entity entity = entry.getKey();
			for (Collision contact : entry.getValue()) {
				entity.getBounds().translate(Vector.scale(contact.normal, 0.1 * contact.depth));
				if (contact.collider instanceof Entity)
					((Entity) contact.collider).getBounds().translate(Vector.scale(contact.normal, -0.1 * contact.depth));
			
			}
		}
	}
}
