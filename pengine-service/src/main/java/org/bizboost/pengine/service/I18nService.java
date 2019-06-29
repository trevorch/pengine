package org.bizboost.pengine.service;

import java.util.Locale;

public interface I18nService {
    String get(String key);
    String get(String key, Locale locale);
    String get(String key, Object[] params, Locale locale);
}
