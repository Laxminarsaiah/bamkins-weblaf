package com.lnragi.tools.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.managers.language.data.TooltipWay;

public class HomeWebFrame extends WebFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7114042622021457073L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static WebPanel mainPanel, northPanel, southPanel, dialogueOuterPanel, dialogueTopPanel, dialogueFieldsPanel,
			dialogueBottomPanel, messageOuterPanel, messageCenterPanel, messageTopPanel, messageBottomPanel;
	public static WebLabel northPanelLabel, southPanelLabel, dialogueTopLabel, dialogueFieldsLabel, dialogueBottomLabel,
			messageBottomLabel, messageTopLabel;
	public static WebButton genMvn, buildMvn, buildAnt, putty, mvnGenSubmit, mvnBuildSubmit, antBuildSubmit, cancel,
			browse, deploy;
	String dstDir = "";

	public HomeWebFrame() {
		initialize();
		initializeHomeWebFrame();
		dstDir = System.getenv("DST_DIR");
		WebTabbedPane tabbedPane = new WebTabbedPane();
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setBounds(10, 110, 60, 60);
		tabbedPane.setTabbedPaneStyle(TabbedPaneStyle.attached);
		Font myFont1 = new Font("Calibri", Font.BOLD, 15);
		tabbedPane.setFont(myFont1);
		tabbedPane.addTab("  Home  ", ImageFactory.loadImage(ImageFactory.GEN_MVN), new HomePanel());
		tabbedPane.addTab("  Generate Maven Project  ", ImageFactory.loadImage(ImageFactory.GEN_MVN),
				new GenerateMavenProjectPanel());
		tabbedPane.addTab("  Build Exists Maven Project  ", ImageFactory.loadImage(ImageFactory.BUILD_MVN),
				new BuildMavenProjectPanel());
		tabbedPane.addTab("  Build Ant Project  ", ImageFactory.loadImage(ImageFactory.BUILD_ANT),
				new BuildAntProjectPanel());
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		putty = new WebButton("Open Putty");
		putty.setRolloverDecoratedOnly(true);
		putty.setIcon(ImageFactory.loadImage(ImageFactory.PUTTY_ICON));
		putty.setToolTip("Open Putty Session", TooltipWay.down, 0);
		putty.setActionCommand("opnpty");
		putty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PuttyWindow();
			}
		});

		northPanel = new WebPanel(true);
		// northPanel.setUndecorated(false);
		northPanel.setBackground(Color.WHITE);
		northPanel.setMargin(new Insets(3, 3, 3, 3));
		northPanel.setRound(StyleConstants.largeRound);
		northPanel.setPaintSides(false, false, true, false);
		northPanelLabel = new WebLabel("Dummy Software", WebLabel.LEADING).setBoldFont();
		//northPanelLabel.setIcon(ImageFactory.loadImage(ImageFactory.LOGO_ICON));
		northPanel.add(northPanelLabel, BorderLayout.CENTER);

		GroupPanel grpPanel = new GroupPanel(GroupingType.fillFirst, 5);
		if (!dstDir.equals("")) {
			grpPanel.add(putty);
		}
		northPanel.add(grpPanel, BorderLayout.AFTER_LINE_ENDS);
		getContentPane().add(northPanel, BorderLayout.NORTH);

	}

	private void initializeHomeWebFrame() {
		Image icon = Toolkit.getDefaultToolkit().getImage(HomeWebFrame.class.getResource("/images/icon.png"));
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName());
			WebLookAndFeel.initializeManagers();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		setPreferredSize(screenSize);
		setIconImage(icon);
		setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE);
		setTitle("BAMBKINS");
		setVisible(true);
		pack();
		setExtendedState(getExtendedState() | WebFrame.MAXIMIZED_BOTH);
		getContentPane().setBackground(new Color(95, 158, 160));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new HomeWebFrame();
			}
		});
	}

}
