package com.lnragi.tools.build;

import javax.swing.ImageIcon;

public class ImageFactory {
	public static final ImageIcon logoIcon = new ImageIcon(ImageFactory.class.getResource("/images/gradle.png"));
	public static final ImageIcon genmvn = new ImageIcon(ImageFactory.class.getResource("/images/newprj_wiz.png"));
	public static final ImageIcon buildmvn = new ImageIcon(ImageFactory.class.getResource("/images/mvn.gif"));
	public static final ImageIcon buildant = new ImageIcon(ImageFactory.class.getResource("/images/ant_buildfile.png"));
	public static final ImageIcon monit = new ImageIcon(ImageFactory.class.getResource("/images/console_view.png"));
	public static final ImageIcon puttyIcon = new ImageIcon(ImageFactory.class.getResource("/images/putty.png"));

	public static final ImageIcon genMvnIcon = new ImageIcon(ImageFactory.class.getResource("/images/newjprj_wiz.png"));
	public static final ImageIcon momlogIcon = new ImageIcon(ImageFactory.class.getResource("/images/copy_edit.png"));

	public static final ImageIcon emptyIcon = new ImageIcon(ImageFactory.class.getResource("/images/empty.png"));

	public static final ImageIcon successIcon = new ImageIcon(ImageFactory.class.getResource("/images/success.png"));
	public static final ImageIcon successTiny = new ImageIcon(
			ImageFactory.class.getResource("/images/success_tiny.png"));
	public static final ImageIcon successSmall = new ImageIcon(
			ImageFactory.class.getResource("/images/success_small.png"));

	public static final ImageIcon failedIcon = new ImageIcon(ImageFactory.class.getResource("/images/wrong.png"));
	public static final ImageIcon failedIconSmall = new ImageIcon(
			ImageFactory.class.getResource("/images/wrong_small.png"));
	public static final ImageIcon failedTinyIcon = new ImageIcon(
			ImageFactory.class.getResource("/images/wrong_tiny.png"));

	public static final ImageIcon errorIcon = new ImageIcon(
			ImageFactory.class.getResource("/images/message_error.png"));
	public static final ImageIcon probar = new ImageIcon(ImageFactory.class.getResource("/images/probar.gif"));
	public static final ImageIcon probarsmall = new ImageIcon(
			ImageFactory.class.getResource("/images/probar_small.gif"));

	public static final ImageIcon refreshIcon = new ImageIcon(ImageFactory.class.getResource("/images/refresh.png"));
	public static final ImageIcon startIcon = new ImageIcon(ImageFactory.class.getResource("/images/run_exc.png"));
	public static final ImageIcon stopIcon = new ImageIcon(ImageFactory.class.getResource("/images/stop.png"));
	public static final ImageIcon connectIcon = new ImageIcon(ImageFactory.class.getResource("/images/conn.png"));
	public static final ImageIcon disconnectIcon = new ImageIcon(ImageFactory.class.getResource("/images/disconn.png"));
	public static final ImageIcon restartIcon = new ImageIcon(
			ImageFactory.class.getResource("/images/external_tools.png"));

	public static final ImageIcon loginIcon = new ImageIcon(ImageFactory.class.getResource("/images/login.png"));
	public static final ImageIcon deployIcon = new ImageIcon(ImageFactory.class.getResource("/images/deploy.png"));
	public static final ImageIcon cancelIcon = new ImageIcon(
			ImageFactory.class.getResource("/images/message_error.png"));
	public static final ImageIcon backGrndImg = new ImageIcon(ImageFactory.class.getResource("/images/gradient.jpg"));
	public static final String LOGO_ICON = "logoIcon";
	public static final String GEN_MVN = "genmvn";
	public static final String BUILD_MVN = "buildmvn";
	public static final String BUILD_ANT = "buildant";
	public static final String PUTTY_ICON = "puttyIcon";
	public static final String MONIT = "monit";
	public static final String LOAD_ICON = "loadIcon";
	public static final String BUILD_ICON = "buildIcon";
	public static final String EMPTY_ICON = "emptyIcon";
	public static final String SUCCESS_ICON = "successIcon";
	public static final String SUCCESS_SML_ICON = "successSmall";
	public static final String SUCCESS_TINY_ICON = "successTiny";
	public static final String FAILED_ICON = "failedIcon";
	public static final String FAILED_SML_ICON = "failedIconSmall";
	public static final String FAILED_TINY_ICON = "failedTinyIcon";

	public static final String PROBAR_ICON = "probar";
	public static final String PROBAR_SML_ICON = "probarsmall";
	public static final String ERROR_ICON = "errorIcon";
	public static final String REFRESH_ICON = "refresh";

	public static final String START_ICON = "start";
	public static final String STOP_ICON = "stop";
	public static final String RESTART_ICON = "restart";
	public static final String CONN_ICON = "connect";
	public static final String DISCONN_ICON = "disconnect";
	public static final String LOGIN_ICON = "login";
	public static final String MOM_ICON = "momlog";
	public static final String DEPLOY_ICON = "deploy";
	public static final String CANCEL_ICON = "cancel";
	public static final String BGIMG_ICON = "bgimg";

	public static ImageIcon loadImage(String imageName) {
		switch (imageName) {
		case "logoIcon":
			return logoIcon;
		case "genmvn":
			return genmvn;
		case "buildmvn":
			return buildmvn;
		case "buildant":
			return buildant;
		case "puttyIcon":
			return puttyIcon;
		case "monit":
			return monit;
		case "genMvnIcon":
			return genMvnIcon;
		case "emptyIcon":
			return emptyIcon;
		case "successIcon":
			return successIcon;
		case "successSmall":
			return successSmall;
		case "successTiny":
			return successTiny;
		case "failedIcon":
			return failedIcon;
		case "failedIconSmall":
			return failedIconSmall;
		case "failedTinyIcon":
			return failedTinyIcon;
		case "probar":
			return probar;
		case "probarsmall":
			return probarsmall;
		case "errorIcon":
			return errorIcon;
		case "refresh":
			return refreshIcon;
		case "start":
			return startIcon;
		case "stop":
			return stopIcon;
		case "restart":
			return restartIcon;
		case "connect":
			return connectIcon;
		case "disconnect":
			return disconnectIcon;
		case "login":
			return loginIcon;
		case "momlog":
			return momlogIcon;
		case "deploy":
			return deployIcon;
		case "cancel":
			return cancelIcon;
		case "bgimg":
			return backGrndImg;
		default:
			break;
		}
		return null;

	}

}
