package com.lnragi.tools.build;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.rootpane.WebWindow;

public class LoadingFrame extends WebFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final ImageIcon probar = new ImageIcon(ImageFactory.class.getResource("/images/probar_home.gif"));
	
	public LoadingFrame() {
		initialize();
		WebLookAndFeel.install();
	}
	public static void main(String[] args) {
		anotherStyle();

	}

	private static void anotherStyle() {

		try {
			WebWindow window = new WebWindow();
			WebPanel panel = new WebPanel();
			WebLabel label = new WebLabel();
			panel.setBackground(Color.WHITE);
			panel.setOpaque(true);
			label.setIcon(probar);
			panel.add(label);
			window.getContentPane().add(panel, BorderLayout.CENTER);
			window.setBounds(550, 300, 190, 40);
			window.setVisible(true);
			String[] task = { "Please wait...", "Loading application....", "Decorating...", "Finishing..." };
			for (int i = 0; i < task.length; i++) {
				label.setText(task[i]);
				Thread.sleep(1000);
			}
			window.dispose();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new HomeWebFrame();
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}