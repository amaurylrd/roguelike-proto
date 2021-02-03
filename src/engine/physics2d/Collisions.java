package engine.physics2d;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import engine.scene.entity.Entity;
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
					if (!list.get(i).isStatic() || !list.get(j).isStatic() && list.get(j).isSolid()) {
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

			double va = A.velocity.dot(normal), vb = B.velocity.dot(normal);
			double bouciness = Math.max(A.restitution, B.restitution);
			if (A.isDynamic()) {
				double vf = bouciness * (2 * A.im * vb + (B.im - A.im) * va) / (A.im + B.im);
				A.updateImpulse(Vector.scale(normal, (Math.abs(vf) < 0.01 ? 0 : vf) - va));
			}
			if (B.isDynamic()) {
				double vf = bouciness * (2 * B.im * va + (A.im - B.im) * vb) / (A.im + B.im);
				B.updateImpulse(Vector.scale(normal, (Math.abs(vf) < 0.01 ? 0 : vf) - vb));
			}
		}
	}

	/**
	 * The penetration allowance.
	 */
	private static final double PENETRATION_ALLOWANCE = 0.05;

	/**
	 * The penetration percentage to correct
	 */
	private static final double PENETRATION_CORRETION = 0.9; 

	/**
	 * This applies a positional correction for each contact.
	 */
	public static void correction() {
		for (Manifold contact : contacts) {
			Collider A = contact.colliderA;
			Collider B = contact.colliderB;

			double correction = A.im + B.im > 0.0 ? Math.max(contact.penetration - PENETRATION_ALLOWANCE, 0.0) / (A.im + B.im) * PENETRATION_CORRETION : 0.0;
			if (A.isDynamic())
				A.getBounds().translate(Vector.scale(contact.normal, A.im * correction));
			if (B.isDynamic())
				B.getBounds().translate(Vector.scale(contact.normal, -B.im * correction));
		}
	}

	/**
	 * This method removes all elements from the list of collisions.
	 */
	public static void clear() {
		contacts.clear();
	}
}
