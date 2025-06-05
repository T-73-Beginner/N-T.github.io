package blackjack2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//カード関係の処理用クラス
public class Card {
	// カードの出現可否を管理するリスト
	private static List<Integer> tramp;
	// カード総数
	private static int total;
	// 直前に引いたカードの情報
	private char c_suit;
	private int c_num;
	private int c_point;

	private Random random;

	public char getC_suit() {
		return c_suit;
	}

	public int getC_num() {
		return c_num;
	}

	public int getC_point() {
		return c_point;
	}

	// 乱数でカードを選定する
	// 選定したカードをint型の値(0～51)で返却
	public int draw() {
		// 乱数値と、そのインデックスの値を格納
		int num = random.nextInt(Card.total);
		int pick = tramp.get(num);

		// 乱数で引き当てたインデックスを削除
		// リストは自動で詰めてくれるので、
		// boolean型配列と比べて、再抽選する手間を省ける
		tramp.remove(num);
		Card.total--;

		return pick;
	}

	// 引いた値をカード情報に変換
	public void check(int number) {
		// スートの格納
		c_suit = switch(number / 13) {
		case 0 -> '♠';
		case 1 -> '♥';
		case 2 -> '♣';
		case 3 -> '♦';
		default -> '?';
		};
		
		// ナンバーの格納
		// 1～13の値に変換して格納する
		c_num = ((number % 13) + 1);
		// 得点の計算と格納
		c_point = point(c_num);
		
	}
	
	// カードの数値(1～13)を受け取り、得点を返却
	private int point(int num) {
		// 2～10…表記通りの点数
		// J,Q,K…10点
		// A…11点扱いとする
		if (2 <= num && num <= 10) {
			return num;
		} else if (11 <= num && num <= 13) {
			return 10;
		} else {
			// 最初は11点とし、Deck.judgeで必要に応じて1点にする
			return 11;
		}
	}

	// 山札をリセットする
	public void reset() {
		total = 52;
		// リストの各要素に要素番号(インデックス)を代入
		// 格納した要素番号がそのままカードの出現判定に。
		// (リスト内に残っている = 未出現)
		for (int i = 0; i < total; i++) {
			tramp.add(i);
		}
	}

	// 初期化子
	static {
		Card.total = 52;
		tramp = new ArrayList<>(total);
	}
	
	// コンストラクタ
	public Card() {
		random = new Random();
	}
}
