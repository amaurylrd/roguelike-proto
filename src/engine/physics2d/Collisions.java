package engine.physics2d;

import java.util.List;
import java.util.ArrayList;

import engine.scene.entity.Collider.Manifold;
import engine.scene.entity.Collider;

public class Collisions {
	private static List<Manifold> contacts = new ArrayList<Manifold>();

	/**
	 * This method stores each collision {@code Manifold}.
	 * 
	 * @param list the list of objects
	 */
	public static void detection(List<Collider> list) {
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i).isSolid()) {
				for (int j = i + 1; j < list.size(); ++j) {
					if (!(list.get(i).isStatic() && list.get(j).isStatic()) && list.get(j).isSolid()) {
						Manifold collision = list.get(i).collides(list.get(j));
						if (collision.collides)
							contacts.add(collision);
					}
				}
			}
		}
	}

	/**
	 * This method handles the collision's response and resolves each contact.
	 */
	public static void resolution() {
		for (Manifold contact : contacts) {
			final Vector normal = contact.normal;
			final Vector tangent = new Vector(-normal.getY(), normal.getX());

			Collider A = contact.colliderA;
			Collider B = contact.colliderB;

			float va = A.velocity.dot(normal), vb = B.velocity.dot(normal);
			float bouciness = Math.max(A.restitution, B.restitution);
			float friction = Math.min(A.friction, B.friction) * Vector.sub(A.velocity, B.velocity).dot(tangent);

			if (A.isDynamic()) {
				float vf = bouciness * (2 * A.im * vb + (B.im - A.im) * va) / (A.im + B.im);
				A.updateImpulse(Vector.scale(normal, (Math.abs(vf) < 0.01f ? 0 : vf) - va));
				A.updateImpulse(Vector.scale(tangent, friction * -A.im));
			}
			if (B.isDynamic()) {
				float vf = bouciness * (2 * B.im * va + (A.im - B.im) * vb) / (A.im + B.im);
				B.updateImpulse(Vector.scale(normal, (Math.abs(vf) < 0.01f ? 0 : vf) - vb));
				B.updateImpulse(Vector.scale(tangent, friction * B.im));
			}
		}
	}

	/**
	 * The penetration allowance.
	 */
	private static final float PENETRATION_ALLOWANCE = 0.05f;

	/**
	 * The penetration percentage to correct
	 */
	private static final float PENETRATION_CORRETION = 0.9f;

	/**
	 * This applies a positional correction for each contact.
	 */
	public static void correction() {
		for (Manifold contact : contacts) {
			if (contact.colliderA.im + contact.colliderB.im > 0) {
				float correction =  Math.max(contact.penetration - PENETRATION_ALLOWANCE, 0) / (contact.colliderA.im + contact.colliderB.im) * PENETRATION_CORRETION;
				if (contact.colliderA.isDynamic())
					contact.colliderA.getBounds().translate(Vector.scale(contact.normal, contact.colliderA.im * correction));
				if (contact.colliderB.isDynamic())
					contact.colliderB.getBounds().translate(Vector.scale(contact.normal, -contact.colliderB.im * correction));
			}
		}
	}

	/**
	 * This method removes all elements from the list of collisions.
	 */
	public static void clear() {
		contacts.clear();
	}
}
