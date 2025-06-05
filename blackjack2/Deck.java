package blackjack2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Deck {
	// 手札情報を格納
	private char[] cards;
	private int[] nums;
	private int[] points;
	// 手札の総数、総得点
	private int sheet;
	private int sum;
	// 11点扱いのAの要素番号を格納
	private List<Integer> ace;

	// カード追加し、手札のカード数+1
	public void setCards(char swit, int num, int points) {
		int n = sheet;
		cards[n] = swit;
		nums[n] = num;
		this.points[n] = points;

		addSumPoint();
		sheet++;
	}
	
	public char getCardsSwit(int i) {
		return cards[i];
	}
	
	public int getNums(int i) {
		return nums[i];
	}
	
	public int getPoint(int i) {
		return points[i];
	}
	
	public int getSum() {
		return sum;
	}
	
	public int getSheet() {
		return sheet;
	}
	
	// Npcクラスで使用
	public int getAceSize() {
		return ace.size();
	}
	
	// 手札の合計点数を加算するメソッド
	private void addSumPoint() {
		int i = sheet;
		if (points[i] != 11) {
			sum += points[i];
		} else {
			// Aの場合の点数処理
			// sum + 11が21以内なら11点扱い
			// オーバーする場合は1点扱い
			if ((sum + 11) <= 21) {
				sum += 11;
				// 11点扱いの場合、要素数を保存
				ace.add(i);
			} else {
				sum += 1;
				points[i] = 1;
			}
		}
	}

	// バーストしていないか判定するメソッド
	// セーフ = true、アウト = false
	// iteratorを使う形に修正
	public boolean judge() {
		boolean flg = false;
		var itr = ace.iterator();		
		if(sum > 21) {
			while(itr.hasNext()) {
				// 現在11点扱いのAを1点扱いにする
				sum -= 10;
				points[itr.next()] = 1;
				// 1点扱いにした場合、リストから削除
				itr.remove();
				// 1点扱いにして21点以内になればflgを立てる
				if(sum <= 21) {
					flg = true;
					break;
				}
			}
		} else {
			// 21点以内なのでflgを立てる
			flg = true;
		}

		return flg;
	}
	
	// 初期化
	void clear() {
		Arrays.fill(cards, '\0');
		Arrays.fill(nums, '\0');
		Arrays.fill(points, '\0');
		ace.clear();
		sum = sheet = 0;
	}

	// コンストラクタ
	public Deck() {
		cards = new char[11];
		nums = new int[11];
		points = new int[11];
		ace = new ArrayList<>();
	}

}
