import java.util.HashMap;

public class Player_B0544222 extends Player {

	public Player_B0544222(int chips) {
		super("ChengYiLi (B0544222)", chips);
	}
	private int bet = 10;
	private int[] lastbet = { 10, 10 };
	@Override
	public int make_bet() {
		if (lastbet[0] == getBet()) {
			if (lastbet[1] >= 50)
				bet = lastbet[1] - 10;
			else
				bet = lastbet[1] * 3;
		} else {
			if (lastbet[0] >= 50)
				bet = lastbet[0] - 10;
			else
				bet = lastbet[0] * 3;
		}
		setBet(bet);
	
		if (getBet() > get_current_chips()) {
			return get_current_chips();
		} else {
			return getBet();
		}
	}

	@Override
	public boolean hit_me(Table tbl) {
		lastbet = tbl.get_palyers_bet();//取得玩家的賭注

		int dealer = tbl.get_face_up_card_of_dealer().getRank();
		boolean hit = false;

		if (getTotalValue() <= 11)
			hit = true;
		 else {
			if (getTotalValue() <= 16 && (dealer >= 6 || dealer == 1))
				hit = true;
		}
		return hit;
	}

}