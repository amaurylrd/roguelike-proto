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
					//collision.collider.applyCollision(collision);
				}
			}
			for (Collider kinematicObject : kinematicObjects) {
				Manifold collision = entity.collides(kinematicObject);
				if (collision.collides) {
					collisions.add(collision);
					final Vector normal = collision.normal;
					final Vector tangent = new Vector(-normal.getY(), normal.getX());
					double restitution = Math.max(entity.restitution, collision.collider.restitution) * entity.velocity.dot(normal);
					if (Math.abs(restitution) < 0.01)
						restitution = 0;
					double friction = 50 / entity.mass * Math.min(entity.friction, collision.collider.friction) * entity.velocity.dot(tangent);
					entity.updateImpulse(Vector.sub(Vector.scale(normal, -entity.velocity.dot(normal) - restitution), Vector.scale(tangent, friction)));
					
					//vole le momentum de la plateform

					//kinematicObjects.get(j).handleCollision(entity);
				}
			}
			for (Collider staticObject : staticObjects) {
				Manifold collision = entity.collides(staticObject);
				if (collision.collides) {
					collisions.add(collision);
					final Vector normal = collision.normal;
					final Vector tangent = new Vector(-normal.getY(), normal.getX());
					double restitution = Math.max(entity.restitution, collision.collider.restitution) * entity.velocity.dot(normal);
					if (Math.abs(restitution) < 0.01)
						restitution = 0;
					double friction = 50 / entity.mass * Math.min(entity.friction, collision.collider.friction) * entity.velocity.dot(tangent);
					entity.updateImpulse(Vector.sub(Vector.scale(normal, -entity.velocity.dot(normal) - restitution), Vector.scale(tangent, friction)));
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
		java.util.Set<Entity> set = new java.util.HashSet<>();

		for (Map.Entry<Entity, Collection<Manifold>> entry : contacts.entrySet()) {
			for (Manifold contact : entry.getValue()) {
				set.add(entry.getKey());
				entry.getKey().getBounds().translate(Vector.scale(contact.normal, 0.8 * contact.penetration));
				if (contact.collider.isDynamic()) {
					contact.collider.getBounds().translate(Vector.scale(contact.normal, -0.8 * contact.penetration));
					//set.add(contact.collider);
				}
			}
		}
	}

	public static void clear() {
		contacts.clear();
	}
}
