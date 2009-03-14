package engine;

public final class BoidState extends ConstBoidState {
	
	static void setList(BoidList newList) {
		list = newList;
	}
	
	BoidState (Boid boid, PhysState state) {
		
	}

	void commitNextMove() {
		PhysState tmp = state;
		state = nextState;
		nextState = tmp;
	}
	
}
