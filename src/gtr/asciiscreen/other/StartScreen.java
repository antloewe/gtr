package gtr.asciiscreen.other;

import gtr.asciiscreen.ScreenType;
import gtr.util.ReadFile;
import gtr.util.datatype.Location;
import jade.core.Messenger;
import jade.ui.Terminal;

import java.util.ArrayList;

import rogue.creature.Player;

public class StartScreen extends OtherScreen {

	private static ArrayList<String> leveldesign;

	public StartScreen(Player player, String screen) {
		super(gtr.asciiscreen.AsciiScreen.getWidth(ReadFile
				.readScreenFile("/res/screens/"+screen+".txt")),
				gtr.asciiscreen.AsciiScreen.getHeight(ReadFile
						.readScreenFile("/res/screens/"+screen+".txt")));
		
		leveldesign = ReadFile.readScreenFile("/res/screens/"+screen+".txt");

		screenType = ScreenType.StartScreen;

		currentLevel = new Location(LevelEnum.StartScreen);
		// steht nur da, da momentan StartScreen nicht nur am Anfang, sondern
		// nochmals erreichbar ist
		/** !TODO hier */
		if (lastLevel != null && nextLevel != null
				&& lastLevel.getLevelEnum().equals(nextLevel.getLevelEnum()))
			updateLevelVariables();

		Messenger.player = player;
		player.getTerm().clearBuffer();
		createAsciiScreen(leveldesign, this, player.getTerm());
		
		setPlayerOnScreen(leveldesign);
	}

	public Location inLevel() {
		Terminal term = player.getTerm();

		while (!player.expired()) {

			if (nextLevel != null) {
				this.removeActor(player);
				player.setFace(player.getStandardFace());
				break; // Verlassen der while-Schleife
			}

			changeAndRefreshScreenAndTick(term);
		}
		return nextLevel;
	}
}
