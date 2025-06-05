package blackjack2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// 一人のプレイヤーが持っている機能を纏める
public abstract class Player implements RedrawSystem {
	
	// 手札クラス
	protected Deck deck;
	// 山札クラス
	protected Card card;
	
	protected boolean dec;
	protected BufferedReader br;
	
	// 開始前準備
	public void one() {
		// 山札作成
		card.reset();
		// 2枚セット
		sets();
		// 手札調整(準備段階の為、手札は表示しない)
		choice(false);
		// 手札出力
		result();
	}

	// 手札に2枚追加する
	protected void sets() {
		for (int i = 0; i < 2; i++) {
			addCard();
		}
	}

	// カード追加→決定の一連の流れ
	public void choice(boolean p) {
		// ドローフラグ
		boolean redraw = false;
		// 決定フラグを折っておく
		dec = false;

		// カードを引くか確認
		// 引く場合はaddCard呼出→繰返し
		// 引かない場合はループ終了
		do {
			// 1回目ループ時はfalseなのでスルーされる
			if (redraw) {
				addCard();
				redraw = false;
			}

			if (!dec) {
				// バーストした場合
				if (!deck.judge()) {
					System.out.println("バーストしました。\n");
					redraw = false;
					dec = true;
					break;
				}
			}
			
			// 人間の場合は手札の内訳を表示
			if(p) {
				showDeck();
				System.out.println("得点 : " + deck.getSum() + "\n");
			}
			
			// redrawDecideはサブクラスでオーバーライド
			redraw = redrawDecide("\n一枚ドローしますか？");
			
			if(!redraw) {
				dec = true;
			}

		} while (!dec);
	}
	
	// カードを1枚引く
	protected void addCard() {
		// リストから抽選→スート、ナンバー、得点を格納
		card.check(card.draw());
		// 格納されたカード情報を手札に格納
		deck.setCards
		(card.getC_suit(), card.getC_num(), card.getC_point());
	}

	// 手札の詳細と得点を全表示
	protected void showDeck() {
		// 現在の枚数を取得
		int n = deck.getSheet();

		for (int i = 0; i < n; i++) {
			System.out.print((i + 1) + " : " + deck.getCardsSwit(i));
			System.out.print(" " + deck.getNums(i));
			System.out.println(" \t/ " + deck.getPoint(i) + "pt");
		}
	}
	
	// 結果を表示するメソッド
	protected void result() {
		showDeck();
		System.out.println("得点 : " + deck.getSum() + "\n");
	}
	
	// コンストラクタ
	public Player() {
		card = new Card();
		deck = new Deck();

		this.br = new BufferedReader(new InputStreamReader(System.in));
	}
}
