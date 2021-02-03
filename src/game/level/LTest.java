package game.level;

import java.util.List;
import java.util.ArrayList;

import engine.scene.Scene;
import engine.scene.entity.Player;
import engine.util.Random;
import sandbox.LateralMovingPlateform;
import sandbox.VerticalMovingPlateform;
import sandbox.collission.BoxTest;
import sandbox.collission.PhyEntity;

public class LTest extends Level {
	private final static int EMPTY = 0000;

	public LTest() {
		int startX = 0, startY = 0;
		int[][] grid = generate(initialize(10, 10), startX, startY, 10, .2f);
		int startValue = grid[startX][startY];
		
		//System.exit(0);
		
		double blockSize = 400;
		int width = grid.length, height = grid[0].length;
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; j++) {
				if (grid[i][j] != EMPTY) {
					String str = grid[i][j] + "";
					str = "0".repeat(4 - str.length()) + grid[i][j];
					//add(new TileTest(str, (double) j*blockSize, (double) i*blockSize, blockSize, blockSize, -5));
				}
			}
		}
		


		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 4; j++) {
				add(new BoxTest(i*100, 250, 100, 10, 0, true)); //plafond banane
				add(new BoxTest(i*100, j*100 + 500, 100, 100, -3)); //background -3
				add(new BoxTest(i*100+100, j*100 + 500, 100, 100, 0)); //sol banane test
			}
		}
		add(new VerticalMovingPlateform(-200, 500, 150, 70, 0));
		add(new LateralMovingPlateform(50, 100, 150, 70, 0));

		PhyEntity e = new PhyEntity(200, 0, 100, 100, 0);
		e.restitution = 0.8;
		e.friction = 1;
		add(e);

		PhyEntity d = new PhyEntity(350, 100, 100, 100, 0, true);
		d.restitution = 0.8;
		d.friction = 1;
		add(d);

		Player p = new Player(150, 300, 100, 100, 0);
		add(p);

		
		//add(new MovingPlateform(100, 300, 300, 50, SOLIDS_LAYER));
	}

	public int[][] initialize(int width, int height) {
		int[][] array = new int[width][height];
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; j++) {
				array[i][j] = EMPTY;
			}
		}

		return array;
	}

	public class Truc {
		public int x, y, v, ov;

		public Truc(int x, int y, int v, int ov) {
			this.x = x;
			this.y = y;
			this.v = v;
			this.ov = ov;
		}
	}

	public int[][] generate(int[][] array, int startX, int startY, int rooms, float split) {
		int steps = rooms;
		int x = startX, y = startY;
		int width = array.length, height = array[0].length;
		
		while (steps > 0) {
			List<Truc> neighbors = new ArrayList<Truc>();
			if (y + 1 < height && array[x][y + 1] == EMPTY) //droit
				neighbors.add(new Truc(x, y + 1, 100, 1000));

			if (x - 1 > -1 && array[x - 1][y] == EMPTY) //haut
				neighbors.add(new Truc(x - 1, y, 1, 10));

			if (y - 1 > -1  && array[x][y - 1] == EMPTY) //gauche
				neighbors.add(new Truc(x, y - 1, 1000, 100));

			if (x + 1 < width && array[x + 1][y] == EMPTY) //bas
				neighbors.add(new Truc(x + 1, y, 10, 1));
			
			if (neighbors.size() == 0)
				return array;
			
			Truc neighbor = neighbors.get(Random.nextInt(neighbors.size()));
			
			array[x][y] += neighbor.v;
			x = neighbor.x;
			y = neighbor.y;
			array[x][y] = neighbor.ov;
			

			// for (int ioff = -1 ; ioff <= 1 ; ++ioff) {
			// 	for (int joff = -1 ; joff <= 1 ; ++joff) {
			// 		int xoff = x + ioff;
			// 		int yoff = y + joff;
			// 		if (xoff > -1 && xoff < width && yoff > -1 && yoff < height && (xoff == x ^ yoff == y)
			// 			&& array[xoff][yoff] == EMPTY) {
			// 			neighbors.add(new Point(xoff, yoff));
			// 		}
			// 	}
			// }
			
			
			/*
			Point neighbor = neighbors.get(Random.nextInt(neighbors.size()));
			x = neighbor.x;
			y = neighbor.y;

			int b = (y + 1 < height && array[x][y + 1] != EMPTY) ? 1 : 0;
			int g = (x - 1 > -1 && array[x - 1][y] != EMPTY) ? 1 : 0;
			int h = (y - 1 > -1  && x < height && array[x][y - 1] != EMPTY) ? 1 : 0;
			int d = (x + 1 < width && array[x + 1][y] != EMPTY) ? 1 : 0;
			array[x][y] = b << 3 | h << 2 | d << 1 | g;

			neighbors.remove(neighbor);
			if (neighbors.size() != 0 && Random.nextDouble() >= split) {
				neighbor = neighbors.get(Random.nextInt(neighbors.size()));
				array = generate(array, neighbor.x, neighbor.y, steps - 1, split/4); //split
			}
			*/
			steps--;
			//split += .05f;
		}

		return array;
	}

	public void print(int[][] array) {
		for (int i = 0; i < array.length; ++i) {
			for (int j = 0; j < array[i].length; j++) {
				String str = " " + array[i][j];
				
				System.out.print("0".repeat(5 - str.length()) + array[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
