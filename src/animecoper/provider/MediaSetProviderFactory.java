package animecoper.provider;

import java.io.IOException;

public class MediaSetProviderFactory {

	public static MediaSetProvider getProvider(MediaSetProviderType type) throws IOException {
		MediaSetProvider provider;
		switch (type) {
		case anime:
			provider = new AnimeMediaSetProvider();
			break;
		case keyword:
			provider = new KeywordMediaSetProvider();
			break;
		case epg:
			provider = new EpgMediaSetProvider();
			break;

		default:
			throw new IllegalArgumentException();
		}
		return provider;
	}
}
