package engine.physics2d;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import engine.scene.entity.Entity;
import engine.scene.entity.Entity.Manifold;
import engine.scene.entity.Collider;

public class Collisions {
	private static Map<Entity, Collection<Manifold>> contacts = new java.util.HashMap<Entity, Collection<Manifold>>();

	public static void detection(List<Entity> entities, Collection<Collider> tiles) {
		for (int i = 0; i < entities.size(); ++i) {
			Collection<Manifold> collisions = new ArrayList<>();
			Entity entity = entities.get(i);
			for (int j = i + 1; j < entities.size(); ++j) {
				Manifold collision = entity.collides(entities.get(j));
				if (collision.collides)
					collisions.add(collision);
			}

			for (Collider tile : tiles) {
				Manifold collision = entity.collides(tile);
				if (collision.collides)
					collisions.add(collision);
			}

			if (!collisions.isEmpty())
				contacts.put(entity, collisions);
		}
	}

	public static void resolve() {
		for (Map.Entry<Entity, Collection<Manifold>> entry : contacts.entrySet()) {
			Entity entity = entry.getKey();
			for (Manifold contact : entry.getValue()) {
				if (contact.collider instanceof Entity) {
					Entity colldier = (Entity) contact.collider;
					final Vector normal = contact.normal;

					double v1 = entity.velocity.dot(normal), v2 = colldier.velocity.dot(normal);
					double m1 = entity.mass, m2 = colldier.mass;
					double vf = (entity.restitution + colldier.restitution) * (2 * m2 * v2 +  (m1 - m2) * v1) / (m1 + m2);
					double vf2 = (entity.restitution + colldier.restitution) * (2 * m1 * v1 +  (m2 - m1) * v2) / (m1 + m2);

					entity.updateImpulse(Vector.scale(normal, vf - v1));
					colldier.updateImpulse(Vector.scale(normal, vf2 - v2));
				} else {
					final Vector normal = contact.normal;
					final Vector tangent = new Vector(-normal.getY(), normal.getX());

					double restitution = (1 + 0*Math.max(entity.restitution, contact.collider.restitution)) * entity.velocity.dot(normal); //TODO
					double friction = 50 / entity.mass * Math.min(entity.friction, contact.collider.friction) * entity.velocity.dot(tangent);

					Vector forces = Vector.scale(normal, -restitution); 
					forces.translate(Vector.scale(tangent, -friction)); //TODO
					entity.updateImpulse(forces);
				}
			}
		}
	}

	public static void correction() {
		for (Map.Entry<Entity, Collection<Manifold>> entry : contacts.entrySet()) {
			for (Manifold contact : entry.getValue()) {
				entry.getKey().getBounds().translate(Vector.scale(contact.normal, 0.1 * contact.penetration));
				if (contact.collider instanceof Entity)
					((Entity) contact.collider).getBounds().translate(Vector.scale(contact.normal, -0.1 * contact.penetration));
			}
		}
	}

	public static void clear() {
		contacts.clear();
	}
}
