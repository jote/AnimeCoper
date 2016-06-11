package animecoper.provider;

import java.io.IOException;

/**
 * 指定された {@link MediaSetProviderType} の {@link MediaSetProvider} を提供するファクトリ
 * 
 * @author van
 */
public class MediaSetProviderFactory {

	/**
	 * 指定された {@link MediaSetProviderType} の {@link MediaSetProvider} を提供します
	 * 
	 * @param type 取得したい {@link MediaSetProvider} の {@link MediaSetProviderType}
	 * @return 指定された {@link MediaSetProviderType} の {@link MediaSetProvider}
	 * @throws IOException 入出力例外が発生した場合
	 */
	public static MediaSetProvider getProvider(MediaSetProviderType type) throws IOException {
		switch (type) {
		case anime:
			return new AnimeMediaSetProvider();
		case keyword:
			return new KeywordMediaSetProvider();
		case epg:
			return new EpgMediaSetProvider();
		default:
			throw new IllegalArgumentException();
		}
	}
}
