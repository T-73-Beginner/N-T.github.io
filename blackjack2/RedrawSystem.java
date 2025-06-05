package blackjack2;
@FunctionalInterface
public interface RedrawSystem {
	// PcクラスとNpcクラスに実装させる。
	// 再ドローするかを決定する。
	boolean redrawDecide(String str);
}
