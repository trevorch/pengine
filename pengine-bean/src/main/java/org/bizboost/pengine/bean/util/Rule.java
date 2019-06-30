package org.bizboost.pengine.bean.util;

import lombok.Data;
import org.bizboost.pengine.bean.Clone;

import java.io.Serializable;

/**
 * @author ：cdm
 * @date ：Created in 2019/7/1 0:00
 * @description：规则解析器
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class Rule extends Clone<Rule> implements Serializable {
    private boolean exactMatch;
    private String expression;
}
