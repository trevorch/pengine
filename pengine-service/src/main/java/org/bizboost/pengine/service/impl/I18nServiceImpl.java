package org.bizboost.pengine.service.impl;

import org.bizboost.pengine.service.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Service
public class I18nServiceImpl implements I18nService {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String get(String key) {
        return get(key,null, LocaleContextHolder.getLocale());
    }

    @Override
    public String get(String key, Locale locale) {
        return get(key, null, locale);
    }

    @Override
    public String get(String key, Object[] params, Locale locale) {
        try {
            return messageSource.getMessage(key, params, locale);
        } catch (Exception e) {
            return key;
        }
    }
}

