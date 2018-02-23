/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class Table {
/*     */   public static final int MAXPLAYER = 4;
/*     */   private Deck deck;
/*     */   private Player[] players;
/*     */   private Dealer dealer;
/*     */   private int[] pos_betArray;
/*     */   private int nDecks;
/*     */   
/*     */   public Table(int nDecks) {
/*  12 */     this.nDecks = nDecks;
/*  13 */     deck = new Deck(nDecks);
/*  14 */     players = new Player[4];
/*     */   }
/*     */   
/*     */   public void play() {
/*  18 */     ask_each_player_about_bets();
/*  19 */     distribute_cards_to_dealer_and_players();
/*  20 */     ask_each_player_about_hits();
/*  21 */     ask_dealer_about_hits();
/*  22 */     calculate_chips();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void set_player(int pos, Player p)
/*     */   {
/*  29 */     if ((pos >= 0) && (pos <= players.length))
/*  30 */       players[pos] = p;
/*     */   }
/*     */   
/*     */   public Player[] get_player() {
/*  34 */     return players;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void set_dealer(Dealer d)
/*     */   {
/*  41 */     dealer = d;
/*     */   }
/*     */   
/*     */ 
/*     */   public Card get_face_up_card_of_dealer()
/*     */   {
/*  47 */     if (dealer != null) {
/*  48 */       Card dealersFaceUpCard = (Card)dealer.getOneRoundCard().get(0);
/*  49 */       return dealersFaceUpCard;
/*     */     }
/*     */     
/*  52 */     System.out.println("Sorry!! There is no Dealer!");
/*  53 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public java.util.ArrayList<Card> getOpenedCards()
/*     */   {
/*  59 */     return deck.getOpenedCard();
/*     */   }
/*     */   
/*  62 */   public int getNumberOfDeck() { return nDecks; }
/*     */   
/*     */   private void ask_each_player_about_bets() {
/*  65 */     pos_betArray = new int[players.length];
/*  66 */     for (int i = 0; i < players.length; i++) {
/*  67 */       if (players[i] != null) {
/*  68 */         players[i].say_hello();
/*  69 */         int bet = players[i].make_bet();
/*  70 */         if (bet > players[i].get_current_chips()) {
/*  71 */           if (players[i].get_current_chips() == 0) {
/*  72 */             players[i].setBet(0);
/*  73 */             pos_betArray[i] = 0;
/*     */           } else {
/*  75 */             players[i].setBet(players[i].get_current_chips());
/*  76 */             pos_betArray[i] = players[i].get_current_chips();
/*     */           }
/*     */         } else {
/*  79 */           pos_betArray[i] = players[i].make_bet();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void distribute_cards_to_dealer_and_players() {
/*  86 */     for (int i = 0; i < players.length; i++) {
/*  87 */       if ((players[i] != null) && (pos_betArray[i] != 0)) {
/*  88 */         java.util.ArrayList<Card> playersCard = new java.util.ArrayList();
/*  89 */         playersCard.add(deck.getOneCard(true));
/*  90 */         playersCard.add(deck.getOneCard(true));
/*  91 */         players[i].setOneRoundCard(playersCard);
/*     */       }
/*     */     }
/*     */     
/*  95 */     if (dealer != null) {
/*  96 */       java.util.ArrayList<Card> dealersCard = new java.util.ArrayList();
/*  97 */       dealersCard.add(deck.getOneCard(true));
/*  98 */       dealersCard.add(deck.getOneCard(false));
/*  99 */       dealer.setOneRoundCard(dealersCard);
/* 100 */       System.out.print("Dealer's face up card is ");
/* 101 */       Card dealers_face_up_card = get_face_up_card_of_dealer();
/* 102 */       dealers_face_up_card.printCard();
/*     */     }
/*     */   }
/*     */   
/*     */   private void ask_each_player_about_hits()
/*     */   {
/* 108 */     for (int i = 0; i < players.length; i++)
/* 109 */       if ((players[i] != null) && (pos_betArray[i] != 0)) {
/* 110 */         System.out.print(players[i].get_name() + "'s Cards now:");
/* 111 */         players[i].printAllCard();
/* 112 */         hit_process(i, players[i].getOneRoundCard());
/* 113 */         System.out.println(players[i].get_name() + "'s hit is over!");
/*     */       }
/*     */   }
/*     */   
/*     */   private void hit_process(int pos, java.util.ArrayList<Card> cards) {
/*     */     boolean hit;
/*     */     do {
/* 120 */       hit = players[pos].hit_me(this);
/* 121 */       if (hit) {
/* 122 */         cards.add(deck.getOneCard(true));
/* 123 */         players[pos].setOneRoundCard(cards);
/* 124 */         System.out.print("Hit! ");
/* 125 */         System.out.print(players[pos].get_name() + "'s Cards now:");
/* 126 */         players[pos].printAllCard();
/* 127 */         if (players[pos].getTotalValue() > 21) {
/* 128 */           hit = false;
/*     */         }
/*     */       }
/*     */       else {
/* 132 */         System.out.println("Pass hit!");
/*     */       }
/*     */       
/* 135 */     } while (hit);
/*     */   }
/*     */   
/* 138 */   private void ask_dealer_about_hits() { java.util.ArrayList<Card> cards = dealer.getOneRoundCard();
/*     */     boolean hit;
/*     */     do {
/* 141 */       hit = dealer.hit_me(this);
/* 142 */       if (hit) {
/* 143 */         cards.add(deck.getOneCard(true));
/* 144 */         dealer.setOneRoundCard(cards);
/*     */       }
/* 146 */       if (dealer.getTotalValue() > 21) {
/* 147 */         hit = false;
/*     */       }
/*     */       
/* 150 */     } while (hit);
/* 151 */     System.out.println("Dealer's hit is over!");
/*     */   }
/*     */   
/*     */   private void calculate_chips() {
/* 155 */     int dealersCradValue = dealer.getTotalValue();
/* 156 */     System.out.print("Dealer's card value is " + dealersCradValue + " ,Cards:");
/* 157 */     dealer.printAllCard();
/* 158 */     for (int i = 0; i < players.length; i++) {
/* 159 */       if ((players[i] != null) && (pos_betArray[i] != 0))
/*     */       {
/*     */ 
/* 162 */         System.out.print(players[i].get_name() + "'s Cards: ");
/* 163 */         players[i].printAllCard();
/* 164 */         caculate_process(i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void caculate_process(int pos)
/*     */   {
/* 172 */     System.out.print(players[pos].get_name() + " card value is " + players[pos].getTotalValue());
/* 173 */     if (players[pos].getTotalValue() > 21) {
/* 174 */       if (dealer.getTotalValue() > 21) {
/* 175 */         System.out.println(", chips have no change!, the Chips now is: " + players[pos].get_current_chips());
/*     */       }
/*     */       else {
/* 178 */         players[pos].increase_chips(-pos_betArray[pos]);
/* 179 */         System.out.println(", Loss " + pos_betArray[pos] + " Chips, the Chips now is: " + players[pos].get_current_chips());
/*     */       }
/*     */     }
/* 182 */     else if (players[pos].getTotalValue() == 21) {
/* 183 */       if ((players[pos].getOneRoundCard().size() == 2) && (players[pos].hasAce())) {
/* 184 */         if (dealer.getTotalValue() != 21) {
/* 185 */           players[pos].increase_chips(pos_betArray[pos] * 2);
/* 186 */           System.out.println(",Black jack!!! Get " + pos_betArray[pos] + " Chips, the Chips now is: " + players[pos].get_current_chips());
/*     */ 
/*     */         }
/* 189 */         else if ((dealer.getOneRoundCard().size() == 2) && (dealer.hasAce())) {
/* 190 */           System.out.println(",Black Jack!!!! But chips have no change!, the Chips now is: " + players[pos].get_current_chips());
/*     */         }
/*     */         else {
/* 193 */           players[pos].increase_chips(pos_betArray[pos] * 2);
/* 194 */           System.out.println(",Black jack!!! Get " + pos_betArray[pos] + " Chips, the Chips now is: " + players[pos].get_current_chips());
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 199 */       else if (dealer.getTotalValue() != 21) {
/* 200 */         players[pos].increase_chips(pos_betArray[pos] * 2);
/* 201 */         System.out.println(",Get " + pos_betArray[pos] + " Chips, the Chips now is: " + players[pos].get_current_chips());
/*     */       }
/*     */       else {
/* 204 */         System.out.println(",chips have no change!The Chips now is: " + players[pos].get_current_chips());
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 209 */     else if (dealer.getTotalValue() > 21) {
/* 210 */       players[pos].increase_chips(pos_betArray[pos]);
/* 211 */       System.out.println(", Get " + pos_betArray[pos] + " Chips, the Chips now is: " + players[pos].get_current_chips());
/*     */     }
/* 213 */     else if (dealer.getTotalValue() < players[pos].getTotalValue()) {
/* 214 */       players[pos].increase_chips(pos_betArray[pos]);
/* 215 */       System.out.println(", Get " + pos_betArray[pos] + " Chips, the Chips now is: " + players[pos].get_current_chips());
/*     */     }
/* 217 */     else if (dealer.getTotalValue() > players[pos].getTotalValue()) {
/* 218 */       players[pos].increase_chips(-pos_betArray[pos]);
/* 219 */       System.out.println(", Loss " + pos_betArray[pos] + " Chips, the Chips now is: " + players[pos].get_current_chips());
/*     */     }
/*     */     else {
/* 222 */       System.out.println(", chips have no change! The Chips now is: " + players[pos].get_current_chips());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public int[] get_palyers_bet()
/*     */   {
/* 229 */     return pos_betArray;
/*     */   }
/*     */   
/* 232 */   public double getPercentofUsedCard() { return deck.nUsed / deck.getAllCards().size(); }
/*     */ }

/* Location:           D:\Java Workspace\Final-master\Class
 * Qualified Name:     Table
 * Java Class Version: 8 (52.0)
 * JD-Core Version:    0.7.1
 */