package songtradr;

import com.songtradr.automate.SongtradrAutomate;

public class main {
	public static void main(String[] args) throws Exception {
		SongtradrAutomate songTradrAutomate = new SongtradrAutomate();
		songTradrAutomate.loginInSongTradr();
		songTradrAutomate.uploadAllAlbums();

	}
}
