package com.kai.game.util;


import com.kai.game.hud.SelectionScreen;
import com.kai.game.core.Screen;

import java.util.ArrayList;
import java.util.List;

public class Input {

    private static int currentMouseX, currentMouseY;

    private static List<Integer> keyPresses = new ArrayList<>();
    private static List<Integer> keyReleases = new ArrayList<>();
    private static List<int[]> mouseClicks = new ArrayList<>();

    public static void updateChanges() {
        keyPressChanges();
        keyReleaseChanges();
        mouseClickChanges();
    }

    /*
    I'm using a queue for any input rather then doing it directly in the menu, as this prevents ( i think )
    concurrent modification. I believe java creates a new thread for listeners, but I'm not sure.
     */

    private static void keyPressChanges() {
        for (int i = 0; i < keyPresses.size(); i++ ) {
            int keycode = keyPresses.get(i);
            switch (Screen.state.getName()) {
                case "Menu":

                    break;
                case "Selection Screen":

                    break;
                case "Running":
                    if (keycode == 65 || keycode == 87 || keycode == 68 || keycode == 83) {
                        Screen.getPlayer().movementKeyPressed(keycode);
                    }
                    break;
                case "Death Screen":

                    break;
            }
        }
        keyPresses.clear();
    }

    private static void keyReleaseChanges() {
        for (int i = 0; i < keyReleases.size(); i++) {
            int keycode = keyReleases.get(i);
            switch (Screen.state.getName()) {
                case "Menu":

                    break;
                case "Selection Screen":
                    if (keycode == 10) { //enter
                        if (SelectionScreen.currentlySelected != null) {
                            Screen.transitionToScene(GameState.RUNNING);
                        }
                    }
                    break;
                case "Running":
                    if (keycode == 65 || keycode == 87 || keycode == 68 || keycode == 83) {
                        Screen.getPlayer().movementKeyReleased(keycode);
                    }
                    if (keycode == 69) {
                        Screen.getPlayer().useStarterSkill(currentMouseX, currentMouseY);
                    }
                    break;
                case "Death Screen":

                    break;
            }
        }
        keyReleases.clear();
    }

    private static void mouseClickChanges() {
        for (int i = 0; i < mouseClicks.size(); i++) {
            int[] mousePos = mouseClicks.get(i);
            int mouseX = mousePos[0];
            int mouseY = mousePos[1];
            switch (Screen.state.getName()) {
                case "Menu":
                    //"click to play" pressed
                    if (mouseX > ((double)408/1224 * Screen.WINDOW_WIDTH) && mouseX < ((double)816/1224 * Screen.WINDOW_WIDTH)
                            && (mouseY > ((double)512/768 * Screen.WINDOW_HEIGHT) && mouseY < ((double)640*760 * Screen.WINDOW_HEIGHT))) {
                        Screen.transitionToScene(GameState.SELECT);
                    }
                    break;
                case "Selection Screen":
                    SelectionScreen.abilitySelectionAttempt(mouseX, mouseY);
                    break;
                case "Running":
                    Screen.getPlayer().createProjectile(mouseX, mouseY);
                    break;
                case "Death Screen":

                    break;
            }
        }
        mouseClicks.clear();
    }

    public static void keyPressed(int keycode) {
        keyPresses.add(keycode);
    }

    public static void keyReleased(int keycode) {
        keyReleases.add(keycode);
    }

    public static void mouseClicked(int mouseX, int mouseY) {
        mouseClicks.add(new int[]{mouseX, mouseY});
    }

    public static void mouseMoved(int mouseX, int mouseY) {
        switch (Screen.state.getName()) {
            case "Menu":
                break;
            case "Selection Screen":

                break;
            case "Running":
                currentMouseX = mouseX;
                currentMouseY = mouseY;
                break;
            case "Death Screen":

                break;
        }
    }

}



/*        switch (Screen.state.getName()) {
            case "Menu":

                break;
            case "Selection Screen":

                break;
            case "Running":

                break;
            case "Death Screen":

                break;
        }
*/