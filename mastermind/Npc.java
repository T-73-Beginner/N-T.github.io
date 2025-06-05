package mastermind;

import java.util.LinkedHashSet;
import java.util.Random;

public class Npc implements Player, GameRule{

	// 値を決定する
	@Override
	public LinkedHashSet<Integer> decideValue() {
		// 入力値格納用
		LinkedHashSet<Integer> num = new LinkedHashSet<>();
		Random r = new Random();
		
		// ランダムな値の格納
		// Setなので値が重複した場合はスルーされる
		while(num.size() != DIGIT) {
			num.add(r.nextInt(10));
		}
		
		return num;
	}
}
