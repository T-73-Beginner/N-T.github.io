package blackjack;

import java.util.Random;

// NPCの制御プログラム
public class Npc {
	// Npcの手札追加判定
	// 現在の得点と11点扱いのAの枚数を受け取る
	protected static boolean npcDraw(int pt, int ace) {
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
