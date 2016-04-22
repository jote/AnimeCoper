/**
 * 
 */
package animecoper.provider;

import java.util.Enumeration;

/**
 * @author van
 *
 * mediaの情報を扱うインターフェース
 */
public interface MediaSetProvider {
	/**
	 * mediaSet のリストをEnumerationを使って取得できる
	 * @return String
	 */
	public Enumeration<String> mediaSetNames();
	/**
	 * mediaSetを取得する
	 * @param mediaSetName
	 * @return MediaSet
	 */
	public MediaSet getMediaSet(String mediaSetName);
}
