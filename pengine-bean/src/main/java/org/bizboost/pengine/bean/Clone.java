package org.bizboost.pengine.bean;

import java.io.*;

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
