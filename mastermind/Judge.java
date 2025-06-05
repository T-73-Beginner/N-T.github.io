package mastermind;

import java.util.LinkedHashSet;

// 判定を行うクラス
public class Judge {
	// 内部で決定した値を格納
	private final LinkedHashSet<Integer> right;
	// eatとbiteを格納する配列
	private int[] result = new int[2];
	
	// 正解を返却する
	public LinkedHashSet<Integer> getRight() {
		return right;
	}
	
	// 判定値を出力する
	public void showResult(final LinkedHashSet<Integer> num) {
		judgement(num);
		System.out.println(result[0] + "eat " + result[1] + "bite です。");
	}
	
	public int getEat() {
		return result[0];
	}
	
	// 判定を行い、resultを更新
	public void judgement(final LinkedHashSet<Integer> num) {
		int eat, bite, i, j;
		eat = bite = i = j = 0;
		
		// eatとbiteの走査
		for(int number : num) {
			for(int answer : right) {
				if(number == answer) {
					if(i == j) {
						eat++;
					} else {
						bite++;
					}
				}
				j++;
			}
			i++;
			j = 0;
		}
		result[0] = eat;
		result[1] = bite;
	}

	// コンストラクタ
	public Judge(LinkedHashSet<Integer> ans) {
		this.right = ans;
	}
}
