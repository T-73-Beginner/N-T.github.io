package blackjack2;

import java.io.IOException;

public class Pc extends Player {

	@Override
	public final boolean redrawDecide(String str){
		boolean b = false;
		boolean error = false;

		do {
			try {
				System.out.print(str);
				System.out.print("< (0)…No / (1)…Yes > : ");
				int ans = Integer.parseInt(br.readLine());
				if(ans == 1) {
					b = true;
					break;
				} else if(ans == 0) {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("0か1を入力してください。");
			} catch(IOException e) {
				error = true;
			}
		} while(!error);
		return b;
	}
}
