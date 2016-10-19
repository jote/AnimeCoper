package animecoper.provider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MediaSetCopier {
	private final MediaSet mediaSet;
	private final Path copyDir;
	private final Path destParentDir = new File("/hoge/shared/videos").toPath();

	/**
	 * copyDir の場所に mediaSet 内の media ファイル全てをコピーします
	 * 
	 * @throws IOException コピーに失敗
	 */
	public void copy() throws IOException {
		for (Media media : this.mediaSet.getAllMedias()) {
			File copyFile = new File(this.copyDir.toString()+ "/" + media.title + "." + media.type);
			if (copyFile.exists()) {
				copyFile.delete();
			}
			Files.copy(media.file.toPath(), copyFile.toPath());
		}
	}

	/**
	 * mdeiaSet を使ってコピー先のディレクトリを準備をします
	 * 
	 * @param コピーしたい mediaSet を指定します
	 * @throws IOException コピー用のディレクトリが無い
	 */
	public MediaSetCopier(MediaSet mediaSet) throws IOException  {
		super();
		this.mediaSet = mediaSet;
		if(!Files.isDirectory(this.destParentDir)) {
			throw new IOException("コピー先のディレクトリが存在しません: " + this.destParentDir.toString());
		}
		this.copyDir = new File(this.destParentDir.toString() + "/" + this.mediaSet.getName()).toPath();
		if( !Files.isDirectory(this.copyDir) ) {
			Files.createDirectory(this.copyDir);
		}
	}
	

}
