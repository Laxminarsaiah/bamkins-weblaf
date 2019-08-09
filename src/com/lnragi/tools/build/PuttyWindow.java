package com.lnragi.tools.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.color.CMMException;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.TreeSelectionModel;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.tree.WebFileTree;
import com.alee.global.GlobalConstants;
import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.alee.laf.tree.TreeSelectionStyle;
import com.alee.managers.language.data.TooltipWay;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class PuttyWindow extends WebFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static WebPanel mainPanel, northPanel, southPanel, dialogueOuterPanel, dialogueTopPanel, dialogueFieldsPanel,
			dialogueListPanel, dialogueBottomPanel, monitOuterPanel, monitTopPanel, monitTextAreaPanel,
			deployCenterPanel, monitBottomPanel, serviceInputPanel, deployOuterPanel, deployTopPanel, deployBottomPanel,
			treeMainPanel, localMachine, leftTitleBar, leftMainPanel, nonlocalMachine, rightTitleBar, rightMainPanel;
	public static WebLabel northPanelLabel, southPanelLabel, dialogueTopLabel, dialogueFieldsLabel, dialogueBottomLabel,
			monitTopLabel, monitBottomLabel, serviceLabel, deployBottomLabel, deployTopLabel, deployCenterLabel,
			deployeStatusLabel, requiredMessageLabel, leftTitle, rightTitle;
	public static WebTextArea textArea, monitTextArea, momlogTextArea;
	public static WebButton puttyLogin, monitSummary, startService, stopService, restartService, momLogs, refresh,
			cancel, submit, load, deploy, browse, lcopy, lrename, ldelete, rcopy, rrename, rdelete, root, rroot;
	public static WebLabel connectingStatus;
	public static JList<?> list;
	public static WebTextField ip, username, port, serviceName, deployTarget;
	public static WebPasswordField password;
	public static Session session;
	public List<File> lisFiles;
	public WebScrollPane rareaScroll;
	public String line, fileName;
	public WebFileTree remoteFileTree;
	public RemoteDirectoryThread rdpThread;

	public PuttyWindow() {
		initialize();
		initializesPuttyWebFrame();
		requiredMessageLabel = new WebLabel();
		requiredMessageLabel.setBackground(Color.WHITE);
		mainPanel = new WebPanel();
		mainPanel.setUndecorated(false);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setRound(StyleConstants.borderWidth);

		northPanel = new WebPanel();
		// northPanel.setUndecorated(false);
		northPanel.setBackground(Color.WHITE);
		northPanel.setMargin(new Insets(3, 3, 3, 3));
		northPanel.setRound(StyleConstants.largeRound);
		northPanel.setPaintSides(false, false, true, false);
		northPanelLabel = new WebLabel(" ");
		northPanel.add(northPanelLabel);

		southPanel = new WebPanel();
		southPanel.setBackground(Color.WHITE);
		southPanel.setMargin(new Insets(3, 3, 3, 3));
		southPanel.setRound(StyleConstants.largeRound);
		southPanel.setPaintSides(false, false, true, false);
		southPanelLabel = new WebLabel("Dummy Software", WebLabel.LEADING).setBoldFont();
		southPanel.add(southPanelLabel);

		treeMainPanel = new WebPanel();
		treeMainPanel.setOpaque(true);
		treeMainPanel.setBackground(Color.white);
		treeMainPanel.setLayout(new GridLayout(1, 2));

		localMachine = new WebPanel(true);
		localMachine.setBackground(Color.white);
		leftTitleBar = new WebPanel(true);
		leftTitle = new WebLabel("Local Machine").setBoldFont();
		leftMainPanel = new WebPanel();
		leftMainPanel.setBackground(Color.white);
		leftTitleBar.add(leftTitle);
		localMachine.add(leftTitleBar, BorderLayout.NORTH);
		localMachine.add(leftMainPanel, BorderLayout.SOUTH);

		lcopy = new WebButton("Copy");
		lcopy.setRolloverDecoratedOnly(true);
		lcopy.setIcon(ImageFactory.loadImage(ImageFactory.MOM_ICON));
		lcopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NotificationFactory.showNotification("Work under construction", "ERROR");
				return;
			}
		});
		lrename = new WebButton("Rename");
		lrename.setRolloverDecoratedOnly(true);
		lrename.setIcon(ImageFactory.loadImage(ImageFactory.MOM_ICON));
		lrename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NotificationFactory.showNotification("Work under construction", "ERROR");
				return;
			}
		});
		ldelete = new WebButton("Delete");
		ldelete.setRolloverDecoratedOnly(true);
		ldelete.setIcon(ImageFactory.loadImage(ImageFactory.CANCEL_ICON));
		ldelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NotificationFactory.showNotification("Work under construction", "ERROR");
				return;
			}
		});

		GroupPanel grpPanel1 = new GroupPanel(GroupingType.fillFirst, 3, lcopy, lrename, ldelete);
		leftTitleBar.add(grpPanel1, BorderLayout.AFTER_LINE_ENDS);

		JFileChooser jfc = new JFileChooser();
		jfc.setDragEnabled(true);
		localMachine.add(jfc);

		nonlocalMachine = new WebPanel(true);
		nonlocalMachine.setBackground(Color.white);
		rightTitleBar = new WebPanel(true);
		rightTitle = new WebLabel("Non Local Machine").setBoldFont();
		rightMainPanel = new WebPanel();
		rightMainPanel.setBackground(Color.white);
		rightTitleBar.add(rightTitle);
		nonlocalMachine.add(rightTitleBar, BorderLayout.NORTH);
		nonlocalMachine.add(rightMainPanel, BorderLayout.SOUTH);
		root = new WebButton("/");
		root.setRolloverDecoratedOnly(true);
		root.setIcon(ImageFactory.loadImage(ImageFactory.MOM_ICON));
		root.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NotificationFactory.showNotification("Work under construction", "ERROR");
				return;
			}
		});
		rroot = new WebButton("root");
		rroot.setRolloverDecoratedOnly(true);
		rroot.setIcon(ImageFactory.loadImage(ImageFactory.MOM_ICON));
		rroot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NotificationFactory.showNotification("Work under construction", "ERROR");
				return;
			}
		});

		rcopy = new WebButton("Copy");
		rcopy.setRolloverDecoratedOnly(true);
		rcopy.setIcon(ImageFactory.loadImage(ImageFactory.MOM_ICON));
		rcopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NotificationFactory.showNotification("Work under construction", "ERROR");
				return;
			}
		});
		rrename = new WebButton("Rename");
		rrename.setRolloverDecoratedOnly(true);
		rrename.setIcon(ImageFactory.loadImage(ImageFactory.MOM_ICON));
		rrename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NotificationFactory.showNotification("Work under construction", "ERROR");
				return;
			}
		});
		rdelete = new WebButton("Delete");
		rdelete.setRolloverDecoratedOnly(true);
		rdelete.setIcon(ImageFactory.loadImage(ImageFactory.CANCEL_ICON));
		rdelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NotificationFactory.showNotification("Work under construction", "ERROR");
				return;
			}
		});

		GroupPanel grpPanel = new GroupPanel(GroupingType.fillFirst, 3, root, rroot, rcopy, rrename, rdelete);
		rightTitleBar.add(grpPanel, BorderLayout.AFTER_LINE_ENDS);
		treeMainPanel.add(localMachine, BorderLayout.WEST);
		treeMainPanel.add(nonlocalMachine, BorderLayout.EAST);

		puttyLogin = new WebButton();
		puttyLogin.setRolloverDecoratedOnly(true);
		puttyLogin.setIcon(ImageFactory.loadImage(ImageFactory.LOGIN_ICON));
		puttyLogin.setToolTip("Login to putty", TooltipWay.down, 0);
		puttyLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enableDialogueDecoration();
			}
		});

		monitSummary = new WebButton();
		monitSummary.setRolloverDecoratedOnly(true);
		monitSummary.setIcon(ImageFactory.loadImage(ImageFactory.MONIT));
		monitSummary.setToolTip("Monit Summary", TooltipWay.down, 0);
		monitSummary.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = "monit summary";
				if (session == null) {
					NotificationFactory.showNotification("No session is avaiable please login into putty", "ERROR");
					return;
				}
				new MonitSummaryFrame();
				new MonitSummaryThread(command).start();
			}

		});

		startService = new WebButton();
		startService.setRolloverDecoratedOnly(true);
		startService.setIcon(ImageFactory.loadImage(ImageFactory.START_ICON));
		startService.setToolTip("Start a service", TooltipWay.down, 0);
		startService.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (session == null) {
					NotificationFactory.showNotification("No session is avaiable please login into putty", "ERROR");
					return;
				}
				serviceInputPanel = new WebPanel();
				serviceLabel = new WebLabel("Specify service name to start:");
				serviceName = new WebTextField(20);
				serviceInputPanel.setLayout(new GridLayout(2, 1));
				serviceInputPanel.add(serviceLabel, BorderLayout.PAGE_START);
				serviceInputPanel.add(serviceName, BorderLayout.PAGE_END);
				int input = WebOptionPane.showConfirmDialog(null, serviceInputPanel, "Service",
						WebOptionPane.OK_CANCEL_OPTION);
				if (input == 0) {
					String sname = serviceName.getText();
					String command = "monit start " + sname;
					new ServiceThread(command).start();
				}
			}
		});

		stopService = new WebButton();
		stopService.setRolloverDecoratedOnly(true);
		stopService.setIcon(ImageFactory.loadImage(ImageFactory.STOP_ICON));
		stopService.setToolTip("Stop a service", TooltipWay.down, 0);
		stopService.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (session == null) {
					NotificationFactory.showNotification("No session is avaiable please login into putty", "ERROR");
					return;
				}
				serviceInputPanel = new WebPanel();
				serviceLabel = new WebLabel("Specify service name to start:");
				serviceName = new WebTextField(20);
				serviceInputPanel.setLayout(new GridLayout(2, 1));
				serviceInputPanel.add(serviceLabel, BorderLayout.PAGE_START);
				serviceInputPanel.add(serviceName, BorderLayout.PAGE_END);
				int input = WebOptionPane.showConfirmDialog(null, serviceInputPanel, "Service",
						WebOptionPane.OK_CANCEL_OPTION);
				if (input == 0) {
					String sname = serviceName.getText();
					String command = "monit stop " + sname;
					new ServiceThread(command).start();
				}
			}
		});

		restartService = new WebButton();
		restartService.setRolloverDecoratedOnly(true);
		restartService.setIcon(ImageFactory.loadImage(ImageFactory.REFRESH_ICON));
		restartService.setToolTip("Restart a service", TooltipWay.down, 0);
		restartService.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (session == null) {
					NotificationFactory.showNotification("No session is avaiable please login into putty", "ERROR");
					return;
				}
				serviceInputPanel = new WebPanel();
				serviceLabel = new WebLabel("Specify service name to start:");
				serviceName = new WebTextField(20);
				serviceInputPanel.setLayout(new GridLayout(2, 1));
				serviceInputPanel.add(serviceLabel, BorderLayout.PAGE_START);
				serviceInputPanel.add(serviceName, BorderLayout.PAGE_END);
				int input = WebOptionPane.showConfirmDialog(null, serviceInputPanel, "Service",
						WebOptionPane.OK_CANCEL_OPTION);
				if (input == 0) {
					String sname = serviceName.getText();
					String command = "monit restart " + sname;
					new ServiceThread(command).start();
				}
			}
		});

		momLogs = new WebButton();
		momLogs.setRolloverDecoratedOnly(true);
		momLogs.setIcon(ImageFactory.loadImage(ImageFactory.MOM_ICON));
		momLogs.setToolTip("Mom Logs", TooltipWay.down, 0);
		momLogs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (session == null) {
					NotificationFactory.showNotification("No session is avaiable please login into putty", "ERROR");
					return;
				}
				String command = "tail -600f /dumps/mom.log.0";
				new MomLogTextAreaThread(command).start();
			}
		});
		deploy = new WebButton();
		deploy.setEnabled(false);
		deploy.setRolloverDecoratedOnly(true);
		deploy.setIcon(ImageFactory.loadImage(ImageFactory.DEPLOY_ICON));
		deploy.setToolTip("Deploy", TooltipWay.down, 0);
		deploy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (session == null) {
					NotificationFactory.showNotification("No session is avaiable please login into putty", "ERROR");
					return;
				}
				new DeployeFrame();
			}
		});

		connectingStatus = new WebLabel();
		northPanel.add(connectingStatus);

		GroupPanel grpPanel3 = new GroupPanel(GroupingType.fillFirst, 5, puttyLogin, monitSummary, startService,
				stopService, restartService, deploy);
		northPanel.add(grpPanel3, BorderLayout.AFTER_LINE_ENDS);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(treeMainPanel);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		add(mainPanel);
	}

	private void hideErrorMessage(WebLabel label, String message) {
		label.setBorder(null);
		label.setIcon(null);
		label.setText(message);
	}

	private void showErrorMessage(WebLabel label, String message) {
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
		label.setIcon(ImageFactory.loadImage(ImageFactory.FAILED_SML_ICON));
		label.setBorder(border);
		label.setText("<html><font color=red>" + message + "</font></html>");
		label.setBackground(new Color(255, 218, 185));
	}

	public String[] loadSavedSessions() {
		try {
			String[] sessions = new FileCreater().readSessions();
			return sessions;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new PuttyWindow();
			}
		});
	}

	public void enableDialogueDecoration() {
		boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
		WebLookAndFeel.setDecorateDialogs(true);
		OpenPuttyLoginDailogueBox dialogue = new OpenPuttyLoginDailogueBox();
		dialogue.pack();
		dialogue.setLocationRelativeTo(null);
		dialogue.setVisible(true);
		WebLookAndFeel.setDecorateDialogs(decorateFrames);
	}

	private void initializesPuttyWebFrame() {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName());
			WebLookAndFeel.initializeManagers();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		Image icon = Toolkit.getDefaultToolkit().getImage(PuttyWindow.class.getResource("/images/icon.png"));
		setPreferredSize(screenSize);
		setIconImage(icon);
		setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);
		setTitle("BAMBKINS");
		setVisible(true);
		pack();
		setExtendedState(getExtendedState() | WebFrame.MAXIMIZED_BOTH);
	}

	public class MonitSummaryFrame extends WebFrame {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MonitSummaryFrame() {
			initialize();
			initializeHomeWindow();
			monitOuterPanel = new WebPanel(true);
			monitOuterPanel.setUndecorated(false);
			monitOuterPanel.setLayout(new BorderLayout());

			monitTopPanel = new WebPanel();
			monitTopPanel.setUndecorated(false);
			monitTopPanel.setMargin(new Insets(3, 3, 3, 3));
			monitTopPanel.setRound(StyleConstants.largeRound);
			monitTopPanel.setPaintSides(false, false, true, false);
			monitTopLabel = new WebLabel(" ", WebLabel.LEADING);
			monitTopPanel.add(monitTopLabel);

			monitTextAreaPanel = new WebPanel();
			Font font = new Font("Consolas", Font.PLAIN, 14);
			monitTextArea = new WebTextArea(25, 80);
			DefaultCaret caret = (DefaultCaret) monitTextArea.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			monitTextArea.setFont(font);
			monitTextArea.setBorder(null);
			monitTextArea.setToolTipText("You can see console output here...");
			monitTextArea.setEditable(false);
			monitTextArea.setForeground(new Color(0, 0, 0));
			monitTextArea.setBackground(Color.WHITE);
			monitTextArea.setLineWrap(true);
			monitTextArea.setWrapStyleWord(true);
			monitTextArea.setAutoscrolls(true);
			monitTextAreaPanel.setAutoscrolls(true);
			monitTextAreaPanel.add(new WebScrollPane(monitTextArea));

			monitBottomPanel = new WebPanel();
			monitBottomPanel.setMargin(new Insets(3, 3, 3, 3));
			monitBottomPanel.setRound(StyleConstants.largeRound);
			monitBottomPanel.setPaintSides(false, false, true, false);
			monitBottomLabel = new WebLabel(" ", WebLabel.LEADING);
			monitBottomPanel.add(monitBottomLabel);

			refresh = new WebButton("Refresh");
			refresh.setIcon(ImageFactory.loadImage(ImageFactory.REFRESH_ICON));
			refresh.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String command = "monit summary";
					new MonitSummaryThread(command).start();
				}
			});

			cancel = new WebButton("Cancel");
			cancel.setIcon(ImageFactory.loadImage(ImageFactory.CANCEL_ICON));
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			GroupPanel grpPanel = new GroupPanel(GroupingType.fillFirst, 5, cancel, refresh);
			monitBottomPanel.add(grpPanel, BorderLayout.AFTER_LINE_ENDS);
			monitOuterPanel.add(monitTextAreaPanel, BorderLayout.CENTER);
			monitOuterPanel.add(monitBottomPanel, BorderLayout.SOUTH);
			add(monitOuterPanel);
		}

		public void initializeHomeWindow() {
			Image icon = Toolkit.getDefaultToolkit().getImage(MonitSummaryFrame.class.getResource("/images/icon.png"));
			setBackground(Color.WHITE);
			setBounds(250, 100, 200, 200);
			setPreferredSize(new Dimension(800, 500));
			setIconImage(icon);
			setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
			setAlwaysOnTop(true);
			setTitle("Monit Summary:");
			pack();
			try {
				UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName());
				WebLookAndFeel.initializeManagers();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

		}

	}

	public class DeployeFrame extends WebFrame {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DeployeFrame() {
			initialize();
			initializeDeployeFrame();
			deployOuterPanel = new WebPanel(true);
			deployOuterPanel.setUndecorated(false);
			deployOuterPanel.setLayout(new BorderLayout());

			deployTopPanel = new WebPanel();
			deployTopPanel.setUndecorated(false);
			deployTopPanel.setMargin(new Insets(3, 3, 3, 3));
			deployTopPanel.setRound(StyleConstants.largeRound);
			deployTopPanel.setPaintSides(false, false, true, false);
			deployTopLabel = new WebLabel(" ", WebLabel.LEADING);
			deployTopPanel.add(deployTopLabel);

			deployCenterPanel = new WebPanel();
			deployCenterPanel.setBackground(Color.WHITE);
			deployCenterPanel.setMargin(new Insets(3, 3, 3, 3));
			deployCenterPanel.setRound(StyleConstants.largeRound);
			deployCenterPanel.setPaintSides(false, false, true, false);
			deployCenterLabel = new WebLabel(" ", WebLabel.LEADING);
			deployCenterPanel.add(deployCenterLabel);

			deployBottomPanel = new WebPanel();
			// deployBottomPanel.setUndecorated(false);
			deployBottomPanel.setMargin(new Insets(3, 3, 3, 3));
			deployBottomPanel.setRound(StyleConstants.largeRound);
			deployBottomPanel.setPaintSides(false, false, true, false);
			deployBottomLabel = new WebLabel(" ", WebLabel.LEADING);
			deployBottomPanel.add(deployBottomLabel);

			deploy = new WebButton("Deploy");
			deploy.setIcon(ImageFactory.loadImage(ImageFactory.DEPLOY_ICON));
			deploy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String s = "pscp -pw " + password.getText() + " -P " + port.getText() + " source "
							+ deployTarget.getText() + " " + username.getText() + "@" + ip.getText() + ":/home/";
					String[] command = { "CMD", "/C", s };
					new DeployThread(command).start();
				}
			});

			cancel = new WebButton("Cancel");
			cancel.setIcon(ImageFactory.loadImage(ImageFactory.CANCEL_ICON));
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			deployeStatusLabel = new WebLabel();
			deployBottomPanel.add(deployeStatusLabel, BorderLayout.WEST);
			GroupPanel grpPanel = new GroupPanel(GroupingType.fillFirst, 5, cancel, deploy);
			deployBottomPanel.add(grpPanel, BorderLayout.AFTER_LINE_ENDS);
			deployCenterPanel.add(showDeployFileFieldGrid(), BorderLayout.CENTER);
			deployOuterPanel.add(deployTopPanel, BorderLayout.NORTH);
			deployOuterPanel.add(deployCenterPanel, BorderLayout.CENTER);
			deployOuterPanel.add(deployBottomPanel, BorderLayout.SOUTH);

			add(deployOuterPanel);
		}

		public void initializeDeployeFrame() {
			Image icon = Toolkit.getDefaultToolkit().getImage(MonitSummaryFrame.class.getResource("/images/icon.png"));
			setBackground(Color.WHITE);
			setBounds(280, 100, 200, 200);
			setPreferredSize(new Dimension(500, 200));
			setIconImage(icon);
			setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
			setAlwaysOnTop(true);
			setTitle("Deploy:");
			pack();
			try {
				UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName());
				WebLookAndFeel.initializeManagers();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

		}

		public Component showDeployFileFieldGrid() {
			WebPanel targetPanel = new WebPanel();
			targetPanel.setLayout(new GridLayout());
			targetPanel.setBackground(Color.WHITE);
			deployTarget = new WebTextField(35);
			WebLabel targetLabel = new WebLabel("  Location");
			targetLabel.setLabelFor(deployTarget);
			browse = new WebButton("Browse");
			browse.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					WebFileChooser chooser = new WebFileChooser();
					chooser.setMultiSelectionEnabled(false);
					int option = chooser.showOpenDialog(DeployeFrame.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						File sf = chooser.getSelectedFile();
						String filelist = sf.getAbsolutePath();
						deployTarget.setText(filelist);
					}
				}
			});

			targetPanel.add(targetLabel);
			return new GroupPanel(false, new GroupPanel(targetPanel, deployTarget, browse));
		}
	}

	class OpenPuttyLoginDailogueBox extends WebDialog {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public OpenPuttyLoginDailogueBox() {
			initialize();
			initializePuttyLoginDialog();
			dialogueOuterPanel = new WebPanel(true);
			dialogueOuterPanel.setUndecorated(false);
			dialogueOuterPanel.setLayout(new BorderLayout());

			dialogueTopPanel = new WebPanel();
			dialogueTopPanel.setUndecorated(false);
			dialogueTopPanel.setMargin(new Insets(3, 3, 3, 3));
			dialogueTopPanel.setRound(StyleConstants.largeRound);
			dialogueTopPanel.setPaintSides(false, false, true, false);
			dialogueTopLabel = new WebLabel(" ", WebLabel.LEADING);
			dialogueTopPanel.add(dialogueTopLabel);

			dialogueFieldsPanel = new WebPanel();
			dialogueFieldsPanel.setLayout(new GridLayout());

			dialogueBottomPanel = new WebPanel();
			dialogueBottomPanel.setBackground(Color.white);
			// dialogueBottomPanel.setUndecorated(false);
			dialogueBottomPanel.setMargin(new Insets(3, 3, 3, 3));
			dialogueBottomPanel.setRound(StyleConstants.largeRound);
			dialogueBottomPanel.setPaintSides(false, false, true, false);
			dialogueBottomLabel = new WebLabel(" ", WebLabel.LEADING);
			dialogueBottomPanel.add(dialogueBottomLabel);
			submit = new WebButton("Login");
			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						login();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});

			cancel = new WebButton("Cancel");
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			load = new WebButton("Load");
			load.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String slctdItem = (String) list.getSelectedValue();
					String sltcdSession;
					try {
						if (slctdItem == null) {
							showErrorMessage(requiredMessageLabel, "Select a saved session and click on load again");
							return;
						}
						sltcdSession = new FileCreater().readSession(slctdItem);
						String[] splits = sltcdSession.split(",");
						ip.setText(splits[0]);
						username.setText(splits[1]);
						password.setText(splits[2]);
						port.setText(splits[3]);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			});

			dialogueFieldsPanel.add(showPuttyLoginFieldGrid());
			dialogueFieldsPanel.setBackground(Color.white);
			GroupPanel grpPanel = new GroupPanel(GroupingType.fillFirst, 5, cancel, load, submit);
			grpPanel.setBackground(Color.white);
			dialogueBottomPanel.add(grpPanel, BorderLayout.AFTER_LINE_ENDS);

			dialogueOuterPanel.add(dialogueTopPanel, BorderLayout.NORTH);
			dialogueOuterPanel.add(dialogueFieldsPanel, BorderLayout.CENTER);
			dialogueOuterPanel.add(dialogueBottomPanel, BorderLayout.SOUTH);

			add(dialogueOuterPanel);

		}

		public void login() throws IOException {
			if (ip.getText().equals("") || username.getText().equals("") || password.getText().equals("")
					|| port.getText().equals("")) {
				showErrorMessage(requiredMessageLabel, "All fields are mandatory!!");
				return;
			} else {
				hideErrorMessage(requiredMessageLabel, "");
			}
			new FileCreater().saveSession(ip.getText(), username.getText(), password.getText(), port.getText());
			new PuttyLoginThread(ip.getText(), username.getText(), password.getText(), Integer.parseInt(port.getText()))
					.start();
			dispose();
		}

		public Component showPuttyLoginFieldGrid() {
			WebPanel hostnamePanel = new WebPanel();
			hostnamePanel.setBackground(Color.white);
			hostnamePanel.setLayout(new GridLayout());
			WebLabel hostnameLabel = new WebLabel("Enter Hostname ");
			ip = new WebTextField(30);
			hostnameLabel.setLabelFor(ip);
			hostnamePanel.add(hostnameLabel);
			hostnamePanel.add(ip);

			WebPanel usernamePanel = new WebPanel();
			usernamePanel.setBackground(Color.white);
			usernamePanel.setLayout(new GridLayout());
			WebLabel usernameLabel = new WebLabel("Enter Username ");
			username = new WebTextField(30);
			usernameLabel.setLabelFor(username);
			usernamePanel.add(usernameLabel);
			usernamePanel.add(username);

			WebPanel passwordPanel = new WebPanel();
			passwordPanel.setBackground(Color.white);
			passwordPanel.setLayout(new GridLayout());
			password = new WebPasswordField(35);
			WebLabel passwordLabel = new WebLabel("Enter Password ");
			passwordLabel.setLabelFor(password);
			passwordPanel.add(passwordLabel);
			passwordPanel.add(password);

			WebPanel portPanel = new WebPanel();
			portPanel.setBackground(Color.white);
			portPanel.setLayout(new GridLayout());
			port = new WebTextField(35);
			WebLabel portLabel = new WebLabel("Enter port ");
			portLabel.setLabelFor(port);
			portPanel.add(portLabel);
			portPanel.add(port);

			WebPanel listPanel = new WebPanel();
			listPanel.setBackground(Color.white);
			listPanel.setLayout(new GridLayout());
			String[] ss = loadSavedSessions();
			list = new JList<String>(ss);
			WebScrollPane sp = new WebScrollPane(list);
			list.setPreferredSize(new Dimension(100, 100));
			WebLabel listLabel = new WebLabel("Select Session ");
			listLabel.setLabelFor(list);
			listPanel.add(listLabel);
			if (ss.length != 0) {
				listPanel.add(list);
			}
			GroupPanel gp = new GroupPanel(false, hostnamePanel, usernamePanel, passwordPanel, portPanel, listPanel,
					requiredMessageLabel);
			gp.setBackground(Color.white);
			return gp;
		}

		public void initializePuttyLoginDialog() {

			try {
				UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName());
				WebLookAndFeel.initializeManagers();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
			setPreferredSize(new Dimension(400, 400));
			setIconImages(WebLookAndFeel.getImages());
			setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
			setResizable(false);
			setModal(true);
			setTitle("Login:");
			pack();

		}
	}

	public class PuttyLoginThread extends Thread {
		private String ip;
		private String username;
		private String password;
		private int port;

		public PuttyLoginThread(String ip, String username, String password, int port) {
			super();
			this.ip = ip;
			this.username = username;
			this.password = password;
			this.port = port;
		}

		public void run() {
			try {
				connectingStatus.setIcon(ImageFactory.loadImage(ImageFactory.PROBAR_SML_ICON));
				connectingStatus.setText("Connecting to " + ip + " user with " + username);
				Thread.sleep(2000);
				Properties props = new Properties();
				props.put("StrictHostKeyChecking", "no");
				JSch jsch = new JSch();
				connectingStatus.setText("Authenticating....");
				Thread.sleep(3000);
				connectingStatus.setText("Authenticated");
				Thread.sleep(1000);
				connectingStatus.setText("Starting Session...");
				session = jsch.getSession(username, ip, port);
				Thread.sleep(1000);
				session.setTimeout(60000);
				session.setDaemonThread(true);
				session.setPassword(password);
				session.setConfig(props);
				session.connect();
				Thread.sleep(1000);
				connectingStatus.setText("Connected!!!");
				setTitle(username + "@" + ip);
				connectingStatus.setIcon(ImageFactory.loadImage(ImageFactory.CONN_ICON));
				// new ServiceThread("cd /").start();
				String str = "/*";
				rdpThread = new RemoteDirectoryThread("ls -d " + str);
				rdpThread.setName("laxmi");
				rdpThread.start();
			} catch (InterruptedException | JSchException e) {
				connectingStatus.setIcon(ImageFactory.loadImage(ImageFactory.DISCONN_ICON));
				connectingStatus.setText(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public class MonitSummaryThread extends Thread {
		private String command;

		public MonitSummaryThread(String command) {
			super();
			this.command = command;
		}

		public void run() {
			monitTextArea.setText("");
			try {
				if (session == null) {
					monitTextArea.append("Please login to putty first.");
				}
				Channel channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command);
				InputStream in = channel.getInputStream();
				((ChannelExec) channel).setErrStream(System.err);
				channel.connect();
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
				while ((line = br.readLine()) != null) {
					monitTextArea.append(String.valueOf((line)));
					monitTextArea.append("\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class MomLogTextAreaThread extends Thread {
		private String command;

		public MomLogTextAreaThread(String command) {
			super();
			this.command = command;
		}

		public void run() {
			textArea.setText("");
			try {
				if (session == null) {
					NotificationFactory.showNotification("No session is avaiable please login into putty", "ERROR");
					return;
				}
				Channel channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command);
				InputStream in = channel.getInputStream();
				((ChannelExec) channel).setErrStream(System.err);
				channel.connect();
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
				while ((line = br.readLine()) != null) {
					textArea.append(String.valueOf((line)));
					textArea.append("\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	class RemoteDirectoryThread extends Thread {
		private String command;
		WebScrollPane rareaScroll;

		public RemoteDirectoryThread(String command) {
			super();
			this.command = command;
		}

		public void run() {
			System.out.println(command);
			try {
				if (session == null) {
					NotificationFactory.showNotification("No session is avaiable please login into putty", "ERROR");
					return;
				}
				Channel channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command);
				InputStream in = channel.getInputStream();
				((ChannelExec) channel).setErrStream(System.err);
				channel.connect();
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
				lisFiles = new ArrayList<>();
				while ((line = br.readLine()) != null) {
					lisFiles.add(new File(line));
				}

				File[] files = lisFiles.stream().toArray(File[]::new);
				remoteFileTree = new WebFileTree(files);
				remoteFileTree.setSelectionStyle(TreeSelectionStyle.single);
				remoteFileTree.setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
				remoteFileTree.setFileFilter(GlobalConstants.ALL_FILES_FILTER);

				rareaScroll = new WebScrollPane(remoteFileTree);
				rareaScroll.setPreferredSize(new Dimension(250, 250));
				rareaScroll.setPreferredSize(new Dimension(250, 250));
				remoteFileTree.setDragEnabled(true);
				remoteFileTree.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
								String file = remoteFileTree.getSelectedFile().getName().replace("\\", "");
								System.out.println(file);
								new RemoteDirectoryThread("ls /" + file).start();
						}
					}
				});
				nonlocalMachine.add(rareaScroll);
				nonlocalMachine.revalidate();
				nonlocalMachine.repaint();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	class ChangeDirectoryThread extends Thread {
		private String command;

		public ChangeDirectoryThread(String command) {
			super();
			this.command = command;
		}

		public ChangeDirectoryThread() {
		}

		public void run() {
			System.out.println(command);
			lisFiles.clear();
			if (session == null) {
				NotificationFactory.showNotification("No session is avaiable please login into putty", "ERROR");
				return;
			}
			Channel channel;
			try {
				channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command);
				InputStream in = channel.getInputStream();
				((ChannelExec) channel).setErrStream(System.err);
				channel.connect();
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
				while ((line = br.readLine()) != null) {
					lisFiles.add(new File(line));
					System.out.println(line);
				}
				File[] files = lisFiles.stream().toArray(File[]::new);
				remoteFileTree = new WebFileTree(files);
				rareaScroll = new WebScrollPane(remoteFileTree);
				rareaScroll.setPreferredSize(new Dimension(250, 250));
				remoteFileTree.setDragEnabled(true);
				nonlocalMachine.add(rareaScroll);
				nonlocalMachine.revalidate();
				nonlocalMachine.repaint();
			} catch (JSchException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	public class ServiceThread extends Thread {
		private String command;

		public ServiceThread(String command) {
			super();
			this.command = command;
		}

		public void run() {
			try {
				if (session == null) {
					NotificationFactory.showNotification("No session is avaiable please login into putty", "ERROR");
					return;
				}
				Channel channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command);
				channel.getInputStream();
				((ChannelExec) channel).setErrStream(System.err);
				channel.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class DeployThread extends Thread {
		private String[] command;

		public DeployThread(String[] command) {
			super();
			this.command = command;
		}

		public void run() {
			deployeStatusLabel.setIcon(ImageFactory.loadImage(ImageFactory.PROBAR_SML_ICON));
			deployeStatusLabel.setText("Deploying...");
			ProcessBuilder probuilder = new ProcessBuilder(command);
			Process process;
			try {
				process = probuilder.start();
				InputStream is = process.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				boolean finishFlag = false;
				while ((line = br.readLine()) != null) {
					finishFlag = false;
					deployeStatusLabel.setText(line);
					finishFlag = true;
				}
				if (finishFlag == true) {
					deployeStatusLabel.setIcon(ImageFactory.loadImage(ImageFactory.SUCCESS_ICON));
					deployeStatusLabel.setText("Finished");
				}
			} catch (IOException e) {
				e.printStackTrace();
				try {
					deployeStatusLabel.setText("");
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();
			}
		}
	}
}
