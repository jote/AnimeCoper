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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * キーワード録画のための {@link MediaSetProvider} 実装
 * 
 * @author van
 */
public class KeywordMediaSetProvider implements MediaSetProvider {
	/** 拡張子を取り出す正規表現 */
	private static final Pattern PATTERN_EXTENSION = Pattern.compile("\\.(.*?)$");
	
	/** キーワード録画された media が置かれるディレクトリ */
	private static final File KEYWORD_DIR = new File("/home/foltia/php/DLNAroot/04-キーワード録画/");
	
	/** キーワード録画された mediaSet の名前一覧 */
	private final List<String> mediaSetNameList;
	
	/** キーワード録画された mediaSet 名と録画ファイルの Map */
	private final Map<String, MediaSet> mediaSetMap;

	/**
	 * コンストラクタ
	 * 
	 * @throws IOException 入出力例外が発生した場合 
	 */
	public KeywordMediaSetProvider() throws IOException {
		super();
		this.mediaSetNameList = new ArrayList<>();
		this.mediaSetMap = new HashMap<>();
		
		// mediaSetNameListを用意する
		Files.walkFileTree(KEYWORD_DIR.toPath(), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dirPath, BasicFileAttributes attrs) throws IOException {
				if(dirPath.getParent().endsWith(KEYWORD_DIR.toPath())) {
					KeywordMediaSetProvider.this.mediaSetNameList.add(dirPath.getFileName().toString());
				}
				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	/**
	 * @see animecoper.provider.MediaSetProvider#mediaSetNames()
	 */
	@Override
	public Enumeration<String> mediaSetNames() {
		return new Enumeration<String>() {
			private Iterator<String> i = mediaSetNameList.iterator();
			public boolean hasMoreElements() {
				return i.hasNext();
			}
			public String nextElement() {
				return i.next();
			} 
		};
	}

	/**
	 * 指定された mediaSetName と一致する mediaSet を取得する
	 * 
	 * @param mediaSet の名前
	 * @return 指定した mediaSet
	 * @throws IOException
	 */
	@Override
	public MediaSet getMediaSet(String mediaSetName) {
		Set<Media> medias = new HashSet<Media>();
		
		if(this.mediaSetMap.containsKey(mediaSetName)) {
			return this.mediaSetMap.get(mediaSetName);
		}
		Path targetDir = new File(KEYWORD_DIR.toString() + "/" + mediaSetName).toPath();
		if (!targetDir.toFile().isDirectory()) {
			throw new IllegalArgumentException("指定したmediaSetは存在しません: "+ mediaSetName);
		}
		
		try {
			Files.walkFileTree(targetDir, new SimpleFileVisitor<Path>(){
				public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
					Matcher m = PATTERN_EXTENSION.matcher(filePath.toFile().getName());
					if(!m.find()) {
						return FileVisitResult.CONTINUE;
					}
					
					MediaType type;
					try {
						type = MediaType.valueOf(m.group(1));
					} catch (IllegalArgumentException e) {
						// MediaTypeでサポートしていない拡張子は medias には入れない
						return FileVisitResult.CONTINUE;
					}
					
					// ファイル名は 数字-数字_タイトル_動画種類_-数字 となっている
					String[] splitFineName = filePath.getFileName().toString().split("_", 3);
					String title = splitFineName[1];
					Media media = new Media(type, title, filePath.toFile());
					medias.add(media);
					
					return FileVisitResult.CONTINUE;					
				}
			});
			// mediaSetName は 番号-キーワード となっているのでキーワードを取り出す
			String[] splitMediaSetName = mediaSetName.split("-", 2);
			MediaSet mediaSet = new MediaSet(splitMediaSetName[1], medias);
			this.mediaSetMap.put(mediaSetName, mediaSet);
			return mediaSet;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
