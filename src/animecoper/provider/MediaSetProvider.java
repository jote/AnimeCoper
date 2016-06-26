package animecoper.provider;

import java.util.Enumeration;

/**
 * すべてのメディアセット名、及び {@link MediaSet} を提供するインターフェイス 
 *
 * @author van
 */
public interface MediaSetProvider {
	/**
	 * メディアセット名を列挙します
	 * 
	 * @return メディアセット名を列挙子
	 */
	public Enumeration<String> mediaSetNames();
	
	/**
	 * 指定されたメディアセット名から {@link MediaSet} を取得します
	 * 
	 * @param mediaSetName メディアセット名
	 * @return MediaSet 指定されたメディアセットの {@link MediaSet}
	 */
	public MediaSet getMediaSet(String mediaSetName);
}
