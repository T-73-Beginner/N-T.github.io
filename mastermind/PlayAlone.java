package mastermind;

import java.util.LinkedHashSet;

public class PlayAlone implements GameSystem {
	// 人間(1人)
	Human h;
	// NPC(値決定係)
	Npc n;
	// 判定用クラス
	Judge j;
	
	// 再挑戦フラグ
	boolean retry = true;
	
	@Override
	public void play() {
		// ルール説明
		if(h.answer("ルール説明は必要ですか？")) {
			System.out.println("このゲームはCPUが生成した" + DIGIT + "桁の値を当てるゲームです。\n");
			System.out.println("数字はランダムに生成され、全桁異なる数字となるように設定されています。");
			System.out.println("入力値が生成値と一致しない場合はヒントが与えられます。");
			System.out.println("EAT(イート)		: 入力値が桁・数値共に一致している");
			System.out.println("BITE(バイト)	: 入力値が数値のみ一致している");
			System.out.println("ヒントをもとに値を特定しましょう！\n\n");
		}
		
		do {
			reset();
			do {
				// 回答フェーズ
				LinkedHashSet<Integer> val = h.decideValue();
				if(!h.getRetire()) {
					j.showResult(val);
					if(j.getEat() == DIGIT) {
						System.out.println("お見事！正解です！");
					}
				} else {
					// 正解の表示
					System.out.print("正解は");
					j.getRight().forEach(System.out::print);
					System.out.println("でした。");
					break;
				}
			} while(j.getEat() != DIGIT);

			// 再挑戦するか確認
			retry = h.answer("\n再挑戦しますか？");
					
		} while(retry);
	}
	
	// リセット
	public void reset() {
		// 値の決定
		j = new Judge(n.decideValue());
	}
	
	// コンストラクタ
	public PlayAlone() {
		h = new Human();
		n = new Npc();
	}
}
