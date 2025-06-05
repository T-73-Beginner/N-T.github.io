package blackjack2;

import java.util.Random;

// NPCの制御プログラム
public class Npc extends Player {
	
	// String型の引数を受け取るが使用しない
	public boolean redrawDecide(String str) {
		// 現在の得点と11点扱いのAの枚数を受け取る。
		int pt = deck.getSum();
		int ace = deck.getAceSize();
		
		Random random = new Random();
		// 引く確率の変数
		int voltage;
		// Aを1点扱いにした得点を格納
		int prept = pt - (ace * 10);
		boolean draw;

		if(prept <= 12) {
			draw = true;
		} else if(prept >= 19) {
			draw = false;
		} else {
			// 点数によって確率変更
			// 84%(13点)～4%(18点)の間で遷移
			voltage = 100 - ((prept - 12) * 16);
			if(random.nextInt(100) < voltage) {
				draw = true;
			} else {
				draw = false;
			}
		}
		return draw;
	}
}
