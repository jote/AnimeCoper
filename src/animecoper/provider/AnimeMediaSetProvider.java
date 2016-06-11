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
 * アニメ録画のための {@link MediaSetProvider} 実装
 * 
 * @author van
 */
public class AnimeMediaSetProvider implements MediaSetProvider {
	/** 拡張子を取り出す正規表現 */
	private static final Pattern PATTERN_EXTENSION = Pattern.compile("\\.(.*?)$");
	
	/** アニメ録画のファイルがあるディレクトリ */
	private static final File ANIME_DIR =  new File("/home/foltia/php/DLNAroot/02-アニメ自動録画/");

	/** アニメ録画された全てのメディアセット名 */
	private final List<String> mediaSetNameList;
	
	/** アニメ録画されたメディアセット名と {@link MediaSet} のマップ */
	private final Map<String, MediaSet> mediaSetMap;

	/**
	 * コンストラクタ
	 * 
	 * @throws IOException 入出力例外が発生した場合
	 */
	public AnimeMediaSetProvider() throws IOException {
		super();
		
		this.mediaSetNameList = new ArrayList<>();
		this.mediaSetMap = new HashMap<>();
		
		// ディレクトリを巡回してmediaSetNameを集める
		Files.walkFileTree(ANIME_DIR.toPath(), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dirPath, BasicFileAttributes attrs) throws IOException {
				if(dirPath.getParent().getParent().endsWith(ANIME_DIR.toPath())) {
					AnimeMediaSetProvider.this.mediaSetNameList.add(dirPath.getFileName().toString());
				}
				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	/**
	 * @see animecoper.provider.MediaSetProvider#getMediaSet(java.lang.String)
	 */
	@Override
	public MediaSet getMediaSet(String mediaSetName) {
		Set<Media> medias = new HashSet<Media>();

		// すでに mediaSetMap に存在した時
		if(this.mediaSetMap.containsKey(mediaSetName)) {
			return this.mediaSetMap.get(mediaSetName);
		}
		
		// 指定されたディレクトリが存在するかチェック
		File targetDir = new File(ANIME_DIR.toString() + "/" + mediaSetName);
		if (!targetDir.isDirectory()) {
			throw new IllegalArgumentException("指定したmediaSetは存在しません: "+ mediaSetName);
		}

		// ディレクトリ内にあるファイルから Media を作成し mediaSet に入れる
		for (File mediaFile : targetDir.listFiles()) {
			// 拡張子を取得
			Matcher m = PATTERN_EXTENSION.matcher(mediaFile.getName());
			if (!m.find()) {
				continue;
			}
			
			MediaType type;
			try {
				type = MediaType.valueOf(m.group(1));
			} catch (IllegalArgumentException e) {
				continue; // サポートしていない拡張子は meidas に入れない
			}
			
			// anime ファイルは 話数_サブタイトル_type_id の形式になっているので分割
			String[] splitTitle = mediaFile.getName().split("_", 3);
			String storyNumber = splitTitle[0];
			String subTitle = splitTitle[1];
			String title = type + "#" + storyNumber + "_" + subTitle;
			
			medias.add(new Media(type, title, mediaFile));
		}
		
		// mediaSetName は 番組ID-番組名 となっているので 番組名だけをMediaSetに設定する
		String[] splitName = mediaSetName.split("-", 2);
		MediaSet mediaSet = new MediaSet(splitName[1], medias);
		this.mediaSetMap.put(mediaSetName, mediaSet);
		return mediaSet;
	}

	/**
	 * @see animecoper.provider.MediaSetProvider#mediaSetNames()
	 */
	@Override
	public Enumeration<String> mediaSetNames() {
		// TODO Auto-generated method stub
		return null;
	}
}
