package engine.physics2d;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import engine.scene.entity.Entity;
import engine.scene.entity.Collider.Manifold;
import engine.scene.entity.Collider;

public class Collisions {
	private static Map<Entity, Collection<Manifold>> contacts = new java.util.Hashtable<Entity, Collection<Manifold>>();

	/**
	 * This method manages collision detection. It stores each collision and resolves collision response.
	 * 
	 * @param dynamicObjects
	 * @param kinematicObjects
	 * @param staticObjects
	 */
	public static void detection(List<Entity> dynamicObjects, List<Collider> kinematicObjects, Collection<Collider> staticObjects) {
		for (int i = 0; i < dynamicObjects.size(); ++i) {
			Entity entity = dynamicObjects.get(i);
			Collection<Manifold> collisions = new ArrayList<>();
			for (int j = i + 1; j < dynamicObjects.size(); ++j) {
				Manifold collision = entity.collides(dynamicObjects.get(j));
				if (collision.collides) {
					collisions.add(collision);
					entity.applyCollision(collision);
					
					//handleCollision()
				}
			}
			for (Collider kinematicObject : kinematicObjects) {
				Manifold collision = entity.collides(kinematicObject);
				if (collision.collides) {
					collisions.add(collision);
					//kinematicObjects.get(j).handleCollision(entity);
				}
			}
			for (Collider staticObject : staticObjects) {
				Manifold collision = entity.collides(staticObject);
				if (collision.collides) {
					collisions.add(collision);
					final Vector normal = collision.normal;
					final Vector tangent = new Vector(-normal.getY(), normal.getX());
					double restitution = (1 + 0*Math.max(entity.restitution, collision.collider.restitution)) * entity.velocity.dot(normal); //TODO
					double friction = 50 / entity.mass * Math.min(entity.friction, collision.collider.friction) * entity.velocity.dot(tangent);
					Vector forces = Vector.scale(normal, -restitution); 
					forces.translate(Vector.scale(tangent, -friction)); //TODO
					entity.updateImpulse(forces);
				}
			}
			if (!collisions.isEmpty())
				contacts.put(entity, collisions);
		}

		for (int i = 0; i < kinematicObjects.size(); ++i) {
			Collection<Manifold> collisions = new ArrayList<>();
			for (int j = i + 1; j < kinematicObjects.size(); ++j) {
				Manifold collision = kinematicObjects.get(i).collides(kinematicObjects.get(j));
				if (collision.collides) {
					//kinematicObjects.get(j).handleCollision(entity);
					//kinematicObjects.get(i).handleCollision(entity);
				}
			}

			for (Collider staticObject : staticObjects) {
				Manifold collision = kinematicObjects.get(i).collides(staticObject);
				if (collision.collides) {
					//kinematicObjects.get(i).handleCollision(entity);
				}
			}
		}
	}

	public static void correction() {
		for (Map.Entry<Entity, Collection<Manifold>> entry : contacts.entrySet()) {
			for (Manifold contact : entry.getValue()) {
				// double f = penetration / (m1 + m2)
				// m1 = normal.scale(f * 0.8 * m2)
				// m2 = normal.scale(f * 0.8 * m1)

				entry.getKey().getBounds().translate(Vector.scale(contact.normal, 0.1 * contact.penetration));
				if (!contact.collider.isKinematic())
					contact.collider.getBounds().translate(Vector.scale(contact.normal, -0.1 * contact.penetration));
			}
		}
	}

	public static void clear() {
		contacts.clear();
	}
}
