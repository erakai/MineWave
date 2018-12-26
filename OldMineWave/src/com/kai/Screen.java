package com.kai;


import com.kai.*;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class Screen extends JPanel implements KeyListener, MouseListener{
	Player mc;
	Menu pM;
	PlayerWand wand;
	LevelHandler lvlH;
	PlayerAbility pa;
	DeathScreen ds;
	boolean pL, pR, pU, pD, special;
	static boolean start, end;
	
	
	public Screen() {
		mc = new Player(600, 300);
		pM = new Menu();
		wand = new PlayerWand();
		lvlH = new LevelHandler();
		pa = new PlayerAbility();
		ds = new DeathScreen();
		start = true;
		end = false;
		
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
	}
	
	
	public Dimension getPreferredSize() {
		return new Dimension(1200,600);
	}	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (start) {
			pa.drawMe(g);
		} else if (end) {
			ds.drawMe(g);
		} else {	
			if (pL) {
				mc.moveLeft();
			}		
			if (pR) {
				mc.moveRight();
			}	
			if (pU) {
				mc.moveForward();
			}	
			if (pD) {
				mc.moveDown();
			}
			lvlH.drawBackground(g);
			pa.updateCooldown();
			pa.drawMe(g);
			lvlH.checkEnemyCollisions();
			lvlH.drawMe(g);
			mc.drawMe(g);
			wand.drawMines(g);
			lvlH.callEnemyChase();
			pM.updateMenu(g, Player.health);
			lvlH.drawEnemies(g);
		}
	}

	
	public void animate() {
		while (true) {
			try {
				Thread.sleep(10); 
			} catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
			
			if (!start) {
				lvlH.createNewLevel();
			}
		
			repaint();
		
		}	
	}	

	public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 10 && start) {
				boolean startOver = pa.setAbility();
				start = startOver;
			}	
			if (e.getKeyCode() == 65) {
				pL = true;
			}
			if (e.getKeyCode() == 68) {
				pR = true;
			}	
			if (e.getKeyCode() == 87) {
				pU = true;
			}	
			if (e.getKeyCode() == 83) {
				pD = true;
			}	
			if (e.getKeyCode() == 32) {
				special = true;
			}	
		
			if (end) {
				if (e.getKeyCode() == 8) {
					ds.makeName("dlt");
				}	
				if (e.getKeyCode() == 65) {
					ds.makeName("A");
				}	
				if (e.getKeyCode() == 66) {
					ds.makeName("B");
				}	
				if (e.getKeyCode() == 67) {
					ds.makeName("C");
				}	
				if (e.getKeyCode() == 68) {
					ds.makeName("D");
				}	
				if (e.getKeyCode() == 69) {
					ds.makeName("E");
				}	
				if (e.getKeyCode() == 70) {
					ds.makeName("F");
				}	
				if (e.getKeyCode() == 71) {
					ds.makeName("G");
				}	
				if (e.getKeyCode() == 72) {
					ds.makeName("H");
				}	
				if (e.getKeyCode() == 73) {
					ds.makeName("I");
				}	
				if (e.getKeyCode() == 74) {
					ds.makeName("J");
				}	
				if (e.getKeyCode() == 75) {
					ds.makeName("K");
				}	
				if (e.getKeyCode() == 76) {
					ds.makeName("L");
				}	
				if (e.getKeyCode() == 77) {
					ds.makeName("M");
				}	
				if (e.getKeyCode() == 78) {
					ds.makeName("N");
				}	
				if (e.getKeyCode() == 79) {
					ds.makeName("O");
				}	
				if (e.getKeyCode() == 80) {
					ds.makeName("P");
				}	
				if (e.getKeyCode() == 81) {
					ds.makeName("Q");
				}	
				if (e.getKeyCode() == 82) {
					ds.makeName("R");
				}	
				if (e.getKeyCode() == 83) {
					ds.makeName("S");
				}	
				if (e.getKeyCode() == 84) {
					ds.makeName("T");
				}	
				if (e.getKeyCode() == 85) {
					ds.makeName("U");
				}	
				if (e.getKeyCode() == 86) {
					ds.makeName("V");
				}	
				if (e.getKeyCode() == 87) {
					ds.makeName("W");
				}	
				if (e.getKeyCode() == 88) {
					ds.makeName("X");
				}	
				if (e.getKeyCode() == 89) {
					ds.makeName("Y");
				}	
				if (e.getKeyCode() == 90) {
					ds.makeName("Z");
				}	
				if (e.getKeyCode() == 10) {
					DeathScreen.confirmed = true;
				}
			}
	}
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == 65) {
			pL = false;
		}	
		if (e.getKeyCode() == 68) {
			pR = false;
		}	
		if (e.getKeyCode() == 87) {
			pU = false;
		}	
		if (e.getKeyCode() == 83) {
			pD = false;
		}	
		if (e.getKeyCode() == 32) {
			special = false;
		}	
		
	}

    public void keyTyped(KeyEvent e) {}
	public void mousePressed(MouseEvent e) {
		if (start) {
			pa.infoText(e.getX(), e.getY());
		} else if (end) {
			if (e.getX() > 20 && e.getX() < 330 && e.getY() > 415 && e.getY() < 455) {
				DeathScreen.confirmed = true;
			}	
		} else {		
			if (special && PlayerAbility.cooldown == 0) {
				pa.useAbility(e.getX(), e.getY());
				lvlH.checkEnemyCollisions();
			} else if (Player.health > 0 && PlayerWand.listOfMines.size() < Player.maxMines) {
					PlayerWand.newMine(e.getX(), e.getY());
				}	
			}
		
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e){}




}

















