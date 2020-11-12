package sandbox;

import engine.scene.entity.particle.Particle;

public class TestParticle extends Particle {
    public TestParticle() {
        super(200, 200, 20, 2);
        setColor(155, 200, 100);
    }
}
