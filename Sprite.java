public class Sprite
/* OVERVIEW: Class that contains the sprite graphics for each sprite
Along with functions to manipulate sprite properties
Each class instance contains one ship instance.
*/
{

	private final String SHIP = "/\\";
	private final String MINE = "+";
	private final String GATE = "><";
	private final String PORT = "0";
	private final long MAX;
	private final long DMG;
	private final double DMGMOD = 0.3;
	private final double HEALTHMOD = 0.1;
	private final long MIN_ENERGY = 20;
	private final double FUEL_COST = 100;

	private long energy;

	Sprite(long e, long m, int d)
	{
		energy = e;
		MAX = m;
		DMG = d;
	}

	public String getShip(){
		return this.SHIP;
	};

	public String getMine(){
		return this.MINE;
	};

	public String getGate()
	{
		return this.GATE;
	};

	public String getPort(){
		return this.PORT;
	};

	public long getEnergy(){
		return this.energy;
	}

	public boolean enoughFuel(double dist) {
	//REQUIRES: int dist, indicating distance between travel co-ordinates
	//EFFECTS: returns true/false if the energy is greater than the fuel cost (distance * 100)

		return(energy > dist*FUEL_COST);
	}

	public void useFuel(double dist) {
	//REQUIRES: int dist, indicating distance between travel co-ordinates
	//MODIFIES: decrements the fuel cost from the ship's energy level

		energy -= dist*FUEL_COST;
	}


	public boolean shipDead() {
	//EFFECTS: returns true or false if the ship has died (energy drops below 20 units).
	
		return(energy < MIN_ENERGY);
	}

	public boolean shipDead(int hits) {	 		
	//REQUIRES: int hits, indicating how many mine hits the ship has taken
	//MODIFIES: decrements the damage taken from the ship's energy
	//EFFECTS: returns true or false if the ship has died (energy drops below 20 units).

		double damage;
		for (int i = 0; i< hits; i++) {
			damage = energy * DMGMOD;
			energy -= damage;
		}
		return(shipDead());
	}

	public void shipHeal() {
	//MODIFIES: increases the ship's energy by the HEALTHMOD percentage.

		energy += energy * HEALTHMOD;
	}

}