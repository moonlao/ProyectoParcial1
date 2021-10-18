
public class Nino {

	private int x,y,score;
	private boolean onHands;
	private boolean leftMovement = false;
	private boolean rightMovement = false;
	private boolean dropStrawberry = false;
	private boolean takeStrawberry = false;
	
	
	public Nino(int x, int y, int score, boolean onHands) {
		super();
		this.x = x;
		this.y = y;
		this.score = score;
		this.onHands = onHands;
	}
	
	public void acciones() {
		if (takeStrawberry && !onHands) {
			onHands=true;
		}
		
		if (dropStrawberry) {
			if(x>=900 && x<=1150 && onHands) {
				onHands=false;
				score+=1;
			}
		}
		
		if (leftMovement) {
			this.x-=6;
			
			if (x<= 25) {
				
				x += 6;
			}
		}
		
		if (rightMovement) {
			this.x+=6;
			if (x>= 1170) {
				
				x -= 6;
			}
		}
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean getOnHands() {
		return onHands;
	}

	public void setOnHands(boolean onHands) {
		this.onHands = onHands;
	}

	public void activateTakeStrawberry() {
		takeStrawberry=true;
	}
	
	public void desactivateTakeStrawberry() {
		takeStrawberry=false;
	}

	public void activateDropStrawberry() {
		dropStrawberry=true;
	}

	public void desactivateDropStrawberry() {
		dropStrawberry=false;
	}

	public void activateRightMovement() {
		rightMovement=true;
	}

	public void desactivateRightMovement() {
		rightMovement=false;
	}

	public void activateLeftMovement() {
		leftMovement=true;
	}

	public void desactivateLeftMovement() {
		leftMovement=false;
	}
	

}
