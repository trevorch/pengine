package org.bizboost.pengine.deepclone;

import java.io.IOException;
import java.util.Date;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
public class TestClone {
    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {

        Attachment attachment2 = new Attachment();
        attachment2.setName("attachment origin");
        Email email2 = new Email();
        email2.setAttachment(attachment2);
        email2.setDate(new Date().toString());
        email2.setName("email origin");
        // 深度克隆
        Email deepClone = email2.deepClone();
        deepClone.getAttachment().setName("clone");
        System.out.println("email:name="+(email2.getAttachment().getName()));
    }
}
