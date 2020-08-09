/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utils {

    public static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
