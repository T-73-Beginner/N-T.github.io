package mastermind;

import java.util.LinkedHashSet;

public interface Player {
	// 値を決定する
	LinkedHashSet<Integer> decideValue();
}
