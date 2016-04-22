package animecoper.provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MediaSet {
	private Set<Media> medias;
	private String name;
	
	public MediaSet(String name, Set<Media> medias) {
		super();
		this.medias = medias;
	}

	public Set<Media> getMedias(MediaType type) {
		Set<Media> result = new HashSet<>();
		for (Media media: medias) {
			if(media.type == type) {
				result.add(media);
			}
		}
		return result;
	}
	
	public Collection<Media> getAllMedias() {
		return new HashSet<>(medias);
	}
	
	public String getName() {
		return this.name.toString();
	}

}
