package com.momforoneday.momforoneday.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielguimo on 14/03/17.
 */

public class NotificationsService {

    private static List<String> _notificationsPrefference = new ArrayList<>();

    public static void addNotificationPrefference(String notificationName){
        _notificationsPrefference.add(notificationName);
    }

    public static void removeNotificationPrefference(String notificationName) {
        _notificationsPrefference.remove(notificationName);
    }

    public static List<String> getNotificationsPrefference() {
        return _notificationsPrefference;
    }

    public static void clearNotificationPrefference(){
        _notificationsPrefference = new ArrayList<>();
    }

}
