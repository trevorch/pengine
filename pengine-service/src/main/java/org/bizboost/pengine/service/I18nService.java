package org.bizboost.pengine.service;

import java.util.Locale;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
public interface I18nService {
    String get(String key);
    String get(String key, Locale locale);
    String get(String key, Object[] params, Locale locale);
}
