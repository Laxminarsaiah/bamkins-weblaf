package com.lnragi.tools.build;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.time.WebClock;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;

public class NotificationFactory {

	public static void showNotification(String message, String type) {
		final WebNotification notificationPopup = new WebNotification();
		notificationPopup.setIcon(getNotificationType(type));
		notificationPopup.setDisplayTime(3000);
		final WebClock clock = new WebClock();
		clock.setTimeLeft(3000);
		clock.setTimePattern("'" + message + "'");
		notificationPopup.setContent(new GroupPanel(clock));
		NotificationManager.showNotification(notificationPopup);
		clock.start();
	}

	public static NotificationIcon getNotificationType(String type) {
		switch (type) {
		case "INFO":
			return NotificationIcon.information;
		case "ERROR":
			return NotificationIcon.error;
		case "MAIL":
			return NotificationIcon.mail;
		case "WARN":
			return NotificationIcon.warning;
		default:
			break;
		}
		return null;
	}

}
