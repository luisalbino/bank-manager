package com.bankmanager.application.helpers;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationHelper {

    public static void success(String message) {
        show(message, NotificationVariant.LUMO_SUCCESS);
    }

    public static void show(String message, NotificationVariant type) {
        Notification notification = Notification.show(message);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.setDuration(2000);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
