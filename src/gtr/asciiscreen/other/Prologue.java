package gtr.asciiscreen.other;

import jade.core.Messenger;
import jade.ui.TermPanel;
import jade.ui.Terminal;
import jade.util.datatype.ColoredChar;

import java.util.ArrayList;

import rogue.creature.Player;

import gtr.asciiscreen.ScreenType;
import gtr.util.ReadFile;
import gtr.util.datatype.Location;

public class Prologue extends OtherScreen {

	private static ArrayList<String> leveldesign = ReadFile.readScreenFile("res/screens/prologue.txt");;

	public Prologue(Player player) {
		super(gtr.asciiscreen.AsciiScreen.getWidth(leveldesign),
				gtr.asciiscreen.AsciiScreen.getHeight(leveldesign));
		
		screenType = ScreenType.Prologue;
		
		updateLevelVariables();
		
		Messenger.player = player;
		createAsciiScreen(leveldesign, this, player.getTerm());
		
		int x = TermPanel.DEFAULT_COLS / 2;
		int y = TermPanel.DEFAULT_ROWS / 2;
		player.setFace(ColoredChar.create(leveldesign.get(y).charAt(x)));
		addActor(player, x, y);
	}

	@Override
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
