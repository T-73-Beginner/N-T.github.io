package blackjack2;

import java.util.ArrayList;
import java.util.List;

public class Duel{
	int member;
	Player[] players;
	// 順位を格納するリスト
	private List<int[]> rank;
	
	// 山札の作成
	Card card = new Card();

	// ゲーム本体
	public void battle() {
		// 山札作成
		card.reset();

		// カード選定
		for (int i = 0; i < this.member; i++) {
			// 2枚セット
			players[i].sets();
			System.out.println("プレイヤー" + (i + 1) + "の番です。");
			// 手札調整
			// Pcクラスのインスタンスの場合、手札を表示する
			players[i].choice(players[i] instanceof Pc);

			System.out.println();
		}

		System.out.println();

		// 手札出力
		for (int i = 0; i < this.member; i++) {
			System.out.println("プレイヤー" + (i + 1) + "の手札");
			players[i].result();
		}

		this.rankingList();

		this.rankingShow();

		}

	// 勝敗を判定するメソッド
	// 引き分けの処理
	private void rankingList(){
		int i = 0, j = 0;
		// 得点と要素数を代入して、リストに追加
		int[][] array = new int[this.member][3];
		array[i][0] = players[i].deck.getSum();
		array[i][1] = j;
		rank.add(0, array[i]);
		// 各プレイヤー(1～総数まで)を走査
		for(i = 1; i < this.member; i++) {

			// 比較用変数にdecks[i]の得点を代入
			int point = players[i].deck.getSum();

			if(point <= 21) {
				System.out.println();
				// リストを先頭から走査
				// 要素を挿入すべきjの値を見つける
				for(j = 0; j < i; j++) {
					int nextpoint = rank.get(j)[0];
					// 次要素バーストor次要素より得点が高い
					if((nextpoint > 21) || (point > nextpoint)) {
						break;
					} 
				}
				// 得点と要素数を代入して、リストに追加
				array[i][0] = point;
				array[i][1] = i;
				rank.add(j, array[i]);
			} else {
				// バースト時は末尾に追加
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
	
	// 順位を表示するプログラム
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
	public Duel() {
		super();
		// プレイヤー関係
		this.member = 2;
		this.players = new Player[this.member];
		// とりあえずNPCと1vs1を想定
		this.players[0] = new Pc();
		this.players[1] = new Npc();

		// ランキング関係
		this.rank = new ArrayList<>();

	}
}

