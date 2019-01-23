package com.kai.game.util;


import com.kai.game.hud.SelectionScreen;
import com.kai.game.core.Screen;
import com.kai.game.items.Item;
import com.kai.game.items.LootInstance;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Input {

    private static int currentMouseX, currentMouseY;

    private static List<Integer> keyPresses = new ArrayList<>();
    private static List<Integer> keyReleases = new ArrayList<>();
    private static List<int[]> mouseClicks = new ArrayList<>();

    public static boolean mouse_pressed = false;

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
                    //E
                    if (keycode == 69) {
                        Screen.getPlayer().useStarterSkill(currentMouseX, currentMouseY);
                    }
                    //F
                    if (keycode == 70) {
                        if (Screen.getPlayer().getSkills().size() >= 4) {
                            Screen.getPlayer().getSkills().get(3).use(currentMouseX, currentMouseY);
                        }
                    }
                    //R
                    if (keycode == 82) {
                        if (Screen.getPlayer().getSkills().size() >= 2) {
                            Screen.getPlayer().getSkills().get(1).use(currentMouseX, currentMouseY);
                        }
                    }
                    //T
                    if (keycode == 84) {
                        if (Screen.getPlayer().getSkills().size() >= 3) {
                            Screen.getPlayer().getSkills().get(2).use(currentMouseX, currentMouseY);
                        }
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
                    Screen.getSelectionScreen().abilitySelectionAttempt(mouseX, mouseY);
                    if (Screen.getSelectionScreen().checkHover(mouseX, mouseY)) {
                        Screen.transitionToScene(GameState.RUNNING);
                    }
                    break;
                case "Running":
                    if (!Screen.getPlayer().checkRingSwap(mouseX, mouseY)) {
                        Screen.getPlayer().createProjectile(mouseX, mouseY);
                        for (LootInstance l : Screen.getRoomHandler().getRoomLoot()) {
                            l.testClicked(Screen.getPlayer(), mouseX, mouseY);
                        }
                    }
                    break;
                case "Death Screen":
                    if (Screen.getDeathScreen().testHover(mouseX, mouseY)) {
                        Screen.transitionToScene(GameState.SELECT);
                        Screen.setConnection(new ClientConnection());

                    }
                    break;
            }
        }
        if (Screen.state == GameState.RUNNING) {
            if (mouse_pressed && Screen.getPlayer().SHOOT) {
                Screen.getPlayer().createProjectile(currentMouseX, currentMouseY);
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

    public static void mouseExists(int mouseX, int mouseY) {
        switch (Screen.state.getName()) {
            case "Menu":
                Screen.getMainMenu().checkHover((int) (MouseInfo.getPointerInfo().getLocation().getX() - mouseX), (int) (MouseInfo.getPointerInfo().getLocation().getY() - mouseY));
                break;
            case "Selection Screen":
                Screen.getSelectionScreen().checkHover((int) (MouseInfo.getPointerInfo().getLocation().getX() - mouseX), (int) (MouseInfo.getPointerInfo().getLocation().getY() - mouseY));
                break;
            case "Running":
                currentMouseX = (int) (MouseInfo.getPointerInfo().getLocation().getX() - mouseX);
                currentMouseY = (int) (MouseInfo.getPointerInfo().getLocation().getY() - mouseY);

                for (Item i: Screen.getPlayer().getRings()) {
                    if (i != null) {
                        i.checkHover(currentMouseX, currentMouseY);
                    }
                }

                for (LootInstance l: Screen.getRoomHandler().getRoomLoot()) {
                    if (l.isDisplayContents()) {
                        for (Item item : l.getContainedItems()) {
                            item.checkHover(currentMouseX, currentMouseY);
                        }
                    }
                }
                break;
            case "Death Screen":
                Screen.getDeathScreen().testHover((int) (MouseInfo.getPointerInfo().getLocation().getX() - mouseX), (int) (MouseInfo.getPointerInfo().getLocation().getY() - mouseY));
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