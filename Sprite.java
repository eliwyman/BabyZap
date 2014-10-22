public class Sprite
{

	private final String SHIP = "/\\";
	private final String MINE = "+";
	private final String GATE = "><";
	private final String PORT = "0";
	private final long MAX;
	private final long DMG;
	private final float DMGMOD;

	private long energy;

	Sprite(long e, long m, int d)
	{
		energy = e;
		MAX = m;
		DMG = d;
		DMGMOD = (float)(DMG/100f);
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

	public boolean enoughFuel(int dist) {
		return(energy > dist*100);
	}

	public void useFuel(int dist) {
		energy -= dist*100;
	}

	public boolean shipDead(int hits) {	 
		
		float damage;
		for (int i = 0; i< hits; i++) {
			damage = energy * DMGMOD;
			energy -= damage;
		}
		return(energy < 1);
	}

	public void shipHeal() {
		energy += 10;
	}

}