package org.bizboost.pengine.util;

public class TraceTool {
    public final static int getLineNumber(){
        return Thread.currentThread().getStackTrace()[1].getLineNumber();
    }

    public final static String getMethodName(){
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }

    public final static String getMethodNameAndLineNumber(String splitter){
        return getMethodName()+splitter+getLineNumber();
    }
}
