// Program: BabyZap
// Author: Eli Wyman
// Date: Oct 2014
//
// Purpose: This class handles all Sprite properties in the game, and their instance variables.
//
// Notes: N/A

public class Sprite
/* OVERVIEW: Class that contains the sprite graphics for each sprite
Along with functions to manipulate sprite properties
Each class instance contains one ship instance.
*/
{

	//Sprite graphics
	private final String SHIP = "/\\";
	private final String K_MINE = "+";
	private final String L_MINE = "#";
	private final String GATE = "><";
	private final String PORT = "0";

	//Mine variables
	private final double KDMGMOD = 0.35;
	private final double LDMGMOD = 0.2;

	//Ship variables
	private final double HEALTHMOD = 0.09;
	private final long MIN_ENERGY = 20;
	private final double FUEL_COST = 105;

	private long energy = 100;

	Sprite()
	{
	}

	public String getShip(){
		return this.SHIP;
	};

	public String getKMine(){
		return this.K_MINE;
	};

	public String getLMine(){
		return this.L_MINE;
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
	//REQUIRES: double dist, indicating distance between travel co-ordinates
	//EFFECTS: returns true/false if the energy is greater than the fuel cost (distance * FUEL_COST)

		return(energy > dist*FUEL_COST);
	}

	public boolean shipDead() {
	//EFFECTS: returns true or false if the ship has died (energy drops below MIN_ENERGY units).
	
		return(energy < MIN_ENERGY);
	}

	public boolean shipKMineHit() {	 		
	//MODIFIES: decrements the damage taken from the ship's energy
	//EFFECTS: returns true or false if the ship has died , via shipDead().
		energy -= energy * KDMGMOD;
		return(shipDead());
	}


	public boolean shipLMineHit(int sev) {	 		
	//MODIFIES: decrements the damage taken from the ship's energy
	//EFFECTS: returns true or false if the ship has died via shipDead().
		energy -= energy * (LDMGMOD / sev);
		return(shipDead());
	}

	public void shipHeal() {
	//MODIFIES: increases the ship's energy by the HEALTHMOD percentage.

		energy += energy * HEALTHMOD;
	}

}