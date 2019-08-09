package com.lnragi.tools.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.SwingConstants;

import com.alee.extended.label.WebVerticalLabel;
import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.thoughtworks.xstream.io.binary.Token.MapIdToValue;

public class HomePanel extends WebPanel implements SwingConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -445195431776912650L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Font font = new Font("Consolas", Font.BOLD, 24);

	WebLabel javaStatus, antStatus, mavenStatus;
	WebPanel javaStatusPanel, antStatusPanel, mavenStatusPanel;

	public HomePanel() {
		add(createFirstPanel());
		new PrerequisitesThread().start();
	}

	private WebPanel createFirstPanel() {
		final WebPanel panel = new WebPanel();
		panel.setUndecorated(false);
		panel.setLayout(new BorderLayout());
		panel.setWebColoredBackground(false);

		final WebPanel northPanel = new WebPanel();
		northPanel.setPaintSides(false, false, true, false);
		setupPanel(northPanel, NORTH);
		panel.add(northPanel, BorderLayout.NORTH);

		final WebPanel southPanel = new WebPanel();
		southPanel.setPaintSides(true, false, false, false);
		setupPanel(southPanel, SOUTH);
		panel.add(southPanel, BorderLayout.SOUTH);

		final WebPanel leadingPanel = new WebPanel();
		leadingPanel.setPaintLeft(false);
		setupPanel(leadingPanel, WEST);
		panel.add(leadingPanel, BorderLayout.LINE_START);

		final WebPanel trailingPanel = new WebPanel();
		trailingPanel.setPaintRight(false);
		setupPanel(trailingPanel, EAST);
		panel.add(trailingPanel, BorderLayout.LINE_END);

		final WebPanel centerPanel = new WebPanel();
		javaStatus = new WebLabel().setBoldFont();
		antStatus = new WebLabel().setBoldFont();
		mavenStatus = new WebLabel().setBoldFont();
		setupPanel(centerPanel, CENTER);
		panel.add(centerPanel, BorderLayout.CENTER);
		return panel;
	}

	private void setupPanel(final WebPanel panel, final int location) {
		// Decoration settings
		panel.setBackground(Color.WHITE);
		panel.setMargin(new Insets(3, 3, 3, 3));
		panel.setRound(StyleConstants.largeRound);

		// Custom content
		switch (location) {
		case NORTH: {
			panel.add(new WebLabel("", WebLabel.CENTER));
			break;
		}
		case SOUTH: {
			panel.add(new WebLabel(" ", WebLabel.CENTER));
			break;
		}
		case WEST: {
			WebVerticalLabel lbl = new WebVerticalLabel(false);
			lbl.setIcon(ImageFactory.loadImage(ImageFactory.BGIMG_ICON));
			panel.add(lbl, WebLabel.CENTER);
			// panel.add(new WebVerticalLabel("WEst", WebLabel.CENTER, false));
			break;
		}
		case EAST: {
			panel.add(new WebVerticalLabel("", WebLabel.CENTER, true));
			break;
		}
		case CENTER: {
			javaStatusPanel = new WebPanel();
			javaStatusPanel.setBackground(Color.WHITE);
			javaStatusPanel.add(javaStatus);
			mavenStatusPanel = new WebPanel();
			mavenStatusPanel.setBackground(Color.WHITE);
			mavenStatusPanel.add(mavenStatus);
			antStatusPanel = new WebPanel();
			antStatusPanel.setBackground(Color.WHITE);
			antStatusPanel.add(antStatus);
			panel.setLayout(new GridLayout(16, 1));
			// panel.add(javaStatusPanel, BorderLayout.CENTER);
			panel.add(mavenStatusPanel, BorderLayout.CENTER);
			panel.add(antStatusPanel, BorderLayout.CENTER);
			break;
		}
		}
	}

	class PrerequisitesThread extends Thread {

		public PrerequisitesThread() {
			super();
		}

		public void run() {
			try {
				Thread.sleep(2000);
				// javaStatus.setIcon(ImageFactory.loadImage(ImageFactory.PROBAR_ICON));
				// javaStatus.setText("Checking for java home...");
				// Thread.sleep(3000);
				// String javaHome = System.getenv("JAVA_HOME");
				// if (javaHome != null) {
				// javaStatus.setIcon(ImageFactory.loadImage(ImageFactory.SUCCESS_ICON));
				// javaStatus.setText("<html><font color=green>Found JAVA_HOME in your
				// machine.</font></html>");
				// } else {
				// javaStatus.setIcon(ImageFactory.loadImage(ImageFactory.FAILED_ICON));
				// javaStatus.setText("<html><font color=red>JAVA_HOME not found!!! in your
				// machine.</font></html>");
				// }
				mavenStatus.setIcon(ImageFactory.loadImage(ImageFactory.PROBAR_ICON));
				mavenStatus.setText("Checking for maven home...");
				Thread.sleep(1000);
				String mvnHome = System.getenv("MAVEN_HOME");
				if (mvnHome != null) {
					mavenStatus.setIcon(ImageFactory.loadImage(ImageFactory.SUCCESS_ICON));
					mavenStatus.setText("<html><font color=green>Found MAVEN_HOME in your machine.</font></html>");
				} else {
					mavenStatus.setIcon(ImageFactory.loadImage(ImageFactory.FAILED_ICON));
					mavenStatus
							.setText("<html><font color=red>MAVEN_HOME not found!!! in your machine. </font></html> ");
				}
				antStatus.setIcon(ImageFactory.loadImage(ImageFactory.PROBAR_ICON));
				antStatus.setText("Checking for ant home...");
				Thread.sleep(1000);
				String antHome = System.getenv("ANT_HOME");
				if (antHome != null) {
					antStatus.setIcon(ImageFactory.loadImage(ImageFactory.SUCCESS_ICON));
					antStatus.setText("<html><font color=green>Found ANT_HOME in your machine.</font></html> ");
				} else {
					antStatus.setIcon(ImageFactory.loadImage(ImageFactory.FAILED_ICON));
					antStatus.setText("<html><font color=red>ANT_HOME not found!!! in your machine.</font></html> ");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String checkForProcess(String[] commandName) {
		ProcessBuilder probuilder = new ProcessBuilder(commandName);
		Process process;
		try {
			process = probuilder.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			StringBuffer finalstr = new StringBuffer();
			while ((line = br.readLine()) != null) {
				mavenStatus.setText(String.valueOf((line)));
			}
			return finalstr.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
