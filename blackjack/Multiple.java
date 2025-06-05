package blackjack;

import java.util.ArrayList;
import java.util.List;

// 複数人プレイ用クラス
public class Multiple extends Alone {
	int member;
	Deck[] decks;
	// 現在の手番を表す変数
	private int turn;
	// 順位を格納するリスト
	private List<int[]> rank;
	// NPCフラグ
	private boolean[] npcflg;

	// ゲーム本体
	public void multi() {
		// 山札作成
		card.reset();

		for (int i = 0; i < this.member; i++) {
			// 2枚セット
			super.sets();
			System.out.println("プレイヤー" + (i + 1) + "の番です。");
			// プレイヤーに応じた手札調整
			if(npcflg[i]){
				npcChoice();
			} else {
				super.choice();
			}
			// 次ターンに回す
			turnManager();

			System.out.println();
		}

		System.out.println();

		// 手札出力
		for (int i = 0; i < member; i++) {
			System.out.println("プレイヤー" + (i + 1) + "の手札");
			super.result();
			turnManager();
		}

		// 順位リスト作成と表示
		rankingList();
		rankingShow();

	}

	// ターンを次に回す
	private void turnManager() {
		turn++;
		if (turn >= member) {
			turn = 0;
		}
		// deckに手番プレイヤーのインスタンスを代入
		super.deck = decks[turn];
	}
	
	// カード追加→決定の一連の流れ(Npc版)
	protected void npcChoice() {
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
					redraw = false;
					dec = true;
					break;
				}
			}

			if (Npc.npcDraw(deck.getSum(), deck.getAceSize())) {
				redraw = true;
			} else {
				redraw = false;
				dec = true;
			}

		} while (redraw);
	}

	// 順位リスト作成
	private void rankingList(){
		int i = 0, j = 0;
		// 得点と要素数を代入して、リストに追加
		int[][] array = new int[member][3];
		array[i][0] = decks[i].getSum();
		array[i][1] = j;
		rank.add(0, array[i]);
		// 各プレイヤー(1～総数まで)を走査
		for(i = 1; i < member; i++) {
			// 比較用変数
			int point = decks[i].getSum();

			if(point <= 21) {
				System.out.println();
				// 要素を挿入すべきjの値を見つける
				for(j = 0; j < i; j++) {
					int nextpoint = rank.get(j)[0];
					// 次要素バーストor次要素より得点が高い
					if((nextpoint > 21) || (point > nextpoint)) {
						break;
					} 
				}
				// プレイヤー情報を挿入
				array[i][0] = point;
				array[i][1] = i;
				rank.add(j, array[i]);
			} else {
				// バースト時は末尾に挿入
				array[i][0] = point;
				array[i][1] = i;
				rank.add(array[i]);
			}
		}
		
		// [i][2]に順位を代入する
		// バーストしている場合は-1を代入
		rank.get(0)[2] = 1;
		for(i = 0; i < rank.size() - 1; i++) {
			int c_point = rank.get(i)[0];
			int n_point = rank.get(i + 1)[0];
			if(c_point == n_point) {
				rank.get(i + 1)[2] = i + 1;
			} else if(n_point > 21) {
				rank.get(i + 1)[2] = -1;
			} else {
				rank.get(i + 1)[2] = i + 2;
			}
		}
	}
	
	// 順位を表示
	public void rankingShow() {
		System.out.println("勝者はプレイヤー" + (rank.get(0)[1] + 1) + "です。");
		// 各プレイヤーの順位と点数を表示
		for(int i = 0; i < rank.size(); i++) {
			if(rank.get(i)[2] != -1) {
				System.out.println(rank.get(i)[2] + "位 … プレイヤー" + (rank.get(i)[1] + 1)
						+ "\t得点 : " + (rank.get(i)[0]) + "点");
			} else {
				System.out.println("-位 … プレイヤー" + (rank.get(i)[1] + 1)
						+ "\t得点 : " + (rank.get(i)[0]) + "点");
			}
		}
	}
	
	// デバッグ用リスト表示メソッド
	public void debug() {
		int a0, a1;
		for (int i = 0; i < rank.size(); i++) {
			a0 = rank.get(i)[0];
			a1 = rank.get(i)[1];
		    System.out.println("(順位 , 要素数)"+ " : " + a0 + " , " + a1);
		}
	}

	// コンストラクタ
	public Multiple() {
		super();
		member = 2;
		decks = new Deck[member];
		for (int i = 0; i < member; i++) {
			decks[i] = new Deck();
		}
		turn = 0;
		deck = decks[turn];

		rank = new ArrayList<>();
		npcflg = new boolean[member];
		npcflg[1] = true;
	}
}
