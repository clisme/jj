package 大佬二进行2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

/**
 * 播放音乐的线程
 * 
 * @author 伍峰
 * 
 */
public class Music extends Thread {
	boolean flag;
	Mp3 mp3;
	String filename;

	public Music(String filename, boolean flag) {
		this.filename = filename;
		this.flag = flag;
		mp3 = new Mp3(filename);
	}

	@Override
	public void run() {
		do {
			mp3.play();
		} while (flag);
	}
	
	public static void main(String[] args) {
		Music music = new Music("sounds/wuniu.mp3",true);
		music.start();
	}
}
class Mp3 {
	String filename;
	Player player;

	public Mp3(String filename) {
		this.filename = filename;

	}

	public void play() {
		try {
			BufferedInputStream buffer = new BufferedInputStream(
					new FileInputStream(filename));
			player = new Player(buffer);
			player.play();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}

