package org.bizboost.pengine.bean;

import java.io.*;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
public abstract class Clone<T> {
    public T deepClone() throws IOException,ClassNotFoundException{
        //将对象写入流中
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(this);
        //从流中取出
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (T)objectInputStream.readObject();

    }
}
