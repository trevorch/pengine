package org.bizboost.pengine.deepclone;

import lombok.Data;

import java.io.*;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class Email implements Serializable {
    private Attachment attachment;
    private String name;
    private String date;

    public Email deepClone() throws IOException,ClassNotFoundException{
        //将对象写入流中
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(this);
        //从流中取出
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (Email)objectInputStream.readObject();

    }

}