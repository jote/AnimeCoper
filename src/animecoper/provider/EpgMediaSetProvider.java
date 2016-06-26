package animecoper.provider;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EPG録画の為の {@link MediaSetProvider} 実装
 * 
 * @author van
 */
public class EpgMediaSetProvider implements MediaSetProvider {
	/** 拡張子を取り出す正規表現 */
	public static final Pattern PATTERN_EXTENSION = Pattern.compile("\\.(.*?)$");
	
	/** providerが扱う録画種類 */
	public static final String PROVIDER_NAME = "epg";
	
	/** EPG録画された mediaSet の名前一覧 */
	private final List<String> mediaSetNameList;
	
	/** EPG録画された mediaSet 名と録画ファイルの Map */
	private final Map<String, MediaSet> mediaSetMap;
	
	/** EPG録画のファイルがあるディレクトリ */
	private final File epgDir;

	/**
	 * mediaSetMap を初期化します。
	 * EPG録画したファイルを保存しているディレクトリを設定します。
	 * EPG録画した番組ディレクトリ名は epg のみで、 mediaSetNameList に格納します。
	 */
	public EpgMediaSetProvider() {
		super();
		this.mediaSetNameList = new ArrayList<>();
		this.mediaSetMap = new HashMap<>();
		this.epgDir = new File("/home/foltia/php/DLNAroot/03-EPG録画/");
		
		// mediaSetName はEPG録画の場合 epg 一つだけ
		this.mediaSetNameList.add(PROVIDER_NAME);
	}

	/**
	 * @see animecoper.provider.MediaSetProvider#mediaSetNames()
	 */
	@Override
	public Enumeration<String> mediaSetNames() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see animecoper.provider.MediaSetProvider#getMediaSet(java.lang.String)
	 */
	@Override
	public MediaSet getMediaSet(String mediaSetName) {
		Set<Media> medias = new HashSet<Media>();

		if (this.mediaSetMap.containsKey(mediaSetName)) {
			return this.mediaSetMap.get(mediaSetName);
		}
		
		// EPG録画ディレクトリ以下の録画ファイルから Media を作り medias に格納する
		MediaSet mediaSet;
		try {
			Files.walkFileTree(this.epgDir.toPath(), new SimpleFileVisitor<Path> (){
				public FileVisitResult visitFile(Path targetFilePath, BasicFileAttributes attrs) {
					
					Matcher m = PATTERN_EXTENSION.matcher(targetFilePath.getFileName().toString());
					// 拡張子がなければそのファイルは無視する
					if (!m.find()) {
						return FileVisitResult.CONTINUE;
					}
					
					MediaType type;
					try {
						type = MediaType.valueOf(m.group(1));						
					} catch (IllegalArgumentException e2) {
						// 対応する拡張子でなければ無視する
						return FileVisitResult.CONTINUE;
					}
					String title[] = targetFilePath.getFileName().toString().split("_", 3);
					Media media = new Media(type, title[1], targetFilePath.toFile());
					medias.add(media);
					return FileVisitResult.CONTINUE;
				}
			});
			mediaSet = new MediaSet(PROVIDER_NAME, medias);
			this.mediaSetMap.put(PROVIDER_NAME, mediaSet);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

}
