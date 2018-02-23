
public class PlayerEx extends Player {

	public PlayerEx(int chips) {
		super("Example", chips); 
		// TODO Auto-generated constructor stub
	}


	@Override
	public int make_bet() {
		
		if(getBet()==20)
			setBet(40);
		else
			setBet(20);
		if (getBet() <= get_current_chips()) {
			return getBet();
		} else {
			setBet(0);
			return getBet();
		}
	}

	@Override
	public boolean hit_me(Table tbl) {
		if(getTotalValue()>=17)
			return false;
		else
			return true;
	}

	private boolean isSoft() {
		int tmp = 0;
		if (hasAce()) {
			for (Card c : getOneRoundCard()) {
				boolean firstAce = true;
				if (c.getRank() == 1 && firstAce) {
					tmp += 11;
					firstAce = false;
				} else if (c.getRank() >= 10)
					tmp += 10;
				else
					tmp += c.getRank();
			}
			if (tmp > 21)
				return false;
			else
				return true;
		} else
			return false;
	}

	

}
