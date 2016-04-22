package animecoper.provider;

import java.io.File;

/**
 * 指定されたディレクトリ内のmdeiaファイルを扱うクラス
 * @author van
 *
 */
public class Media {
	
	public final MediaType type;	
	public final String title;
	public final File file;
	
	public Media(MediaType type, String title, File file) {
		super();
		this.type = type;
		this.title = title;
		this.file = file;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

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
