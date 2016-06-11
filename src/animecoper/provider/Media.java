package animecoper.provider;

import java.io.File;

/**
 * タイトルと種類毎にメディアを表現するクラス
 * 
 * @author van
 */
public class Media {
	/** メディア種別 */
	public final MediaType type;
	
	/** メディアのタイトル */
	public final String title;
	
	/** メディアのファイル */
	public final File file;
	
	/**
	 * コンストラクタ
	 * 
	 * @param type メディア種別
	 * @param title メディアのタイトル
	 * @param file メディアのファイル
	 */
	public Media(MediaType type, String title, File file) {
		super();
		this.type = type;
		this.title = title;
		this.file = file;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Media other = (Media) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
}
