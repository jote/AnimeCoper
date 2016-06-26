package animecoper.provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * メディアの集合を表現します
 * 
 * @author van
 */
public class MediaSet {
	/** すべての {@link Media} の集合 */
	private Set<Media> medias;
	
	/** メディアセット名 */
	private String name;
	
	/**
	 * コンストラクタ
	 * 
	 * @param name メディアセット名
	 * @param medias メディアセット名に関連する {@link Media} の集合
	 */
	public MediaSet(String name, Set<Media> medias) {
		super();
		this.medias = medias;
	}

	/**
	 * 指定されたメディアタイプに対応する {@link Media} の集合を取得します
	 * 
	 * @param type メディアタイプ
	 * @return 指定されたメディアタイプに対応する {@link Media} の集合
	 */
	public Set<Media> getMedias(MediaType type) {
		Set<Media> result = new HashSet<>();
		for (Media media: medias) {
			if(media.type == type) {
				result.add(media);
			}
		}
		return result;
	}
	
	/**
	 * 全ての {@link Media} を取得します
	 * 
	 * @return 全ての {@link Media}
	 */
	public Collection<Media> getAllMedias() {
		return new HashSet<>(medias);
	}
	
	/**
	 * メディアセット名を取得します
	 * 
	 * @return メディアセット名
	 */
	public String getName() {
		return name;
	}

}
