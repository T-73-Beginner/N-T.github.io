package mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;

// 桁数が正しくない場合にスローする
class DigitException extends Exception{}

public class Human implements Player, GameRule {
	
	protected BufferedReader br;
	// リタイアフラグ
	boolean retire = false;

	// 直近の入力値
	public boolean getRetire() {
		return retire;
	}
	
	// 値を決定する
	public LinkedHashSet<Integer> decideValue() {
		// 入力値(文字列)を格納
		String input;
		// 入力値(数値)格納用
		LinkedHashSet<Integer> num = new LinkedHashSet<>();
		// 継続フラグ
		boolean end = false;
		// 初期化
		input = null;
		num.clear();
		retire = false;
		
		do {
			// 文字列データ入力
			try {
				System.out.println("\n" + DIGIT + "桁の重複が無い値を入力してください。");
				System.out.println("リタイアコードは「*」です。");
				System.out.print("値を入力 : ");
				input = br.readLine();
			} catch(IOException e) {
				// 暫定の処理
				input = null;
			}
				
			// 文字列型→整数型
			try {
				// String型の値をint型に変換し、setに格納
				for(int i = 0; i < input.length(); i++) {
					num.add(Integer.parseInt(input.substring(i, i + 1)));
				}
				
				// 桁数チェック
				if(num.size() != DIGIT) {
					throw new DigitException();
				}
				
				end = true;
				
			} catch(NumberFormatException e) {
				if(input.charAt(0) == '*') {
					// リタイアされた場合
					System.out.println("リタイアコードが入力されました。");
					end = true;
					retire = true;
				} else {
					System.out.println("値が不正です。");
					num.clear();
					end = false;
				}
			} catch(StringIndexOutOfBoundsException | DigitException e) {
				System.out.println("桁数が不正です。(もしくは値が重複しています。)");
				num.clear();
				end = false;
			}
		} while(!end);
		
		return num;
	}

	// プレイヤーに問いかける処理
	// 問いかける文字列を引数に受け取る
	public boolean answer(String str) {
		boolean hear = false;
		// 文字列データ入力
		try {
			System.out.print(str);
			System.out.print(" < 1 … Yes / 0 … No > : ");
			int ans = Integer.parseInt(br.readLine());
			if(ans == 1) {
				hear = true;
			}
		} catch(IOException e) {
			// 暫定の処理
			System.out.println("入力ストリームエラー");
		}
		return hear;
	}
	
	// コンストラクタ
	public Human() {
		br = new BufferedReader(new InputStreamReader(System.in));
	}
}
