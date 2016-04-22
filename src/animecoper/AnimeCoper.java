package animecoper;

import java.util.Enumeration;

import animecoper.provider.MediaSet;
import animecoper.provider.MediaSetCopier;
import animecoper.provider.MediaSetProvider;
import animecoper.provider.MediaSetProviderFactory;
import animecoper.provider.MediaSetProviderType;

public class AnimeCoper {
	private final MediaSetProvider provider;
	/**
	 * AnimeRocker で録画した動画を録画の種類別にバックアップをとります。
	 * @param args 録画の種類
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		try {
			new AnimeCoper(MediaSetProviderType.valueOf(args[0]));			
		} catch (IllegalArgumentException e) {
			String s = "引数は下記のどれかを指定してください。\n" + MediaSetProviderType.values();
			throw new IllegalArgumentException(s);
		}
	}

	/**
	 * 指定された録画の種類から provider を作成しコピーします。
	 * @param type 録画の種類
	 * @throws Exception
	 */
	public AnimeCoper(MediaSetProviderType type) throws Exception {
		super();
		provider = MediaSetProviderFactory.getProvider(type);

		// mediaSet ごとにコピー
		for (Enumeration<String> e = provider.mediaSetNames(); e.hasMoreElements();) {
			String mediaSetName = e.nextElement();
			
			MediaSet mediaSet = provider.getMediaSet(mediaSetName);
			MediaSetCopier copier = new MediaSetCopier(mediaSet);
			copier.copy();
		}
	}
	
	
}
