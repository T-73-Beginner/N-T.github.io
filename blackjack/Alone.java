package blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Alone {
	protected Card card = new Card();
	protected Deck deck = new Deck();
	
	// フラグ(Multipleでも流用する)
	protected boolean dec;
	protected BufferedReader br;

	public void one() {
		// 山札作成
		card.reset();
		// 2枚セット
		sets();
		// 手札調整
		choice();
		// 結果を表示
		result();
	}

	// 手札に2枚追加する
	protected void sets() {
		for (int i = 0; i < 2; i++) {
			addCard();
		}
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

	// カード追加→決定の一連の流れ
	protected void choice() {
		// ドローフラグ
		boolean redraw = false;
		// 入力エラー検出用フラグ
		boolean error = true;
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
			// 手札の内訳表示
			showDeck();
			System.out.println("得点 : " + deck.getSum());

			// ドローの選択
			do {
				// カードを引くか確認
				try {
					System.out.print("\n一枚ドローしますか？");
					System.out.print("< (0)…No / (1)…Yes > : ");
					if ((Integer.parseInt(br.readLine())) == 1) {
						redraw = true;
						error = true;
					} else {
						redraw = false;
						error = true;
						dec = true;
					}
				} catch (NumberFormatException e) {
					System.out.println("0か1を入力してください。");
					error = false;
				} catch (IOException e) {
					// このルートの処理は後々実装する
					System.out.println("入力ストリームエラー");
					error = false;
					break;
				}
			} while (!error);

		} while (redraw);
	}
	
	protected void result() {
		showDeck();
		System.out.println("得点 : " + deck.getSum() + "\n");
	}

	// コンストラクタ
	public Alone() {
		card = new Card();
		deck = new Deck();
		
		// 入力ストリームセット
		this.br = new BufferedReader(new InputStreamReader(System.in));
	}
}
