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

	private static Map<Collider, Collection<Manifold>> contacts2 = new java.util.Hashtable<Collider, Collection<Manifold>>();
	//TODO: map to list et manifold A
	
	//collision quand pas isRemovable()
	public static void test(List<Collider> list) {
		for (int i = 0; i < list.size(); ++i) {
			Collider a = list.get(i);
			Collection<Manifold> collisions = new ArrayList<>();
			for (int j = i + 1; j < list.size(); ++j) {
				if (!a.isStatic() || !list.get(j).isStatic()) {
					Manifold collision = a.collides(list.get(j));
					if (collision.collides) {
						collisions.add(collision);
					}
				}
			}
			if (!collisions.isEmpty())
				contacts2.put(a, collisions);
		}
	}

	public static void test2() {
		for (Map.Entry<Collider, Collection<Manifold>> entry : contacts2.entrySet()) {
			Collider a = entry.getKey();
			for (Manifold contact : entry.getValue()) {
				final Vector normal = contact.normal;
				final Vector tangent = new Vector(-normal.getY(), normal.getX());
				
				Collider b = contact.collider;

				double va = a.velocity.dot(normal), vb = b.velocity.dot(normal);
				double maxRestitution = Math.max(a.restitution, b.restitution);
				if (a.isDynamic()) {
					//double vf = maxRestitution * (2 * b.mass * vb + (a.mass - b.mass) * va) / (a.mass + b.mass);
					double vf = maxRestitution * (2 * a.im * vb + (b.im - a.im) * va) / (a.im + b.im);
					a.updateImpulse(Vector.scale(normal, (Math.abs(vf) < 0.01 ? 0 : vf) - va));
				}
				if (b.isDynamic()) {
					//double vf = maxRestitution * (2 * a.mass * va + (b.mass - a.mass) * vb) / (a.mass + b.mass);
					double vf = maxRestitution * (2 * b.im * va + (a.im - b.im) * vb) / (a.im + b.im);
					b.updateImpulse(Vector.scale(normal, (Math.abs(vf) < 0.01 ? 0 : vf) - vb));
				}
			}
		}
	}

	public static void corr2() {
		final double PENETRATION_ALLOWANCE = 0.05; //en variable static Penetration allowance
		final double PENETRATION_CORRETION = 0.4; //en variable static Penetration percentage to correct
		for (Map.Entry<Collider, Collection<Manifold>> entry : contacts2.entrySet()) {
			Collider a = entry.getKey();
			for (Manifold contact : entry.getValue()) {
				Collider b = contact.collider;
				if (a.im + b.im != 0) {
					double correction = Math.max(contact.penetration - PENETRATION_ALLOWANCE, 0.0) / (a.im + b.im) * PENETRATION_CORRETION;
					if (a.isDynamic())
						a.getBounds().translate(Vector.scale(contact.normal, a.im * correction));
					if (b.isDynamic())
						b.getBounds().translate(Vector.scale(contact.normal, -b.im * correction));
				}
			}
		}
	}

	public static void cle2() {
		contacts2.clear();
	}

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
