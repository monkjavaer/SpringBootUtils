/**
 * Copyright(C) 2017 Car All rights reserved.
 * Original Author: monkjavaer, 2017/11/7
 */
package com.car.base.webservice;

import java.io.*;

/**
 *
 */
public class UserLoginMsg implements Serializable {

    private String  userName;

    private boolean logout = true;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public UserLoginMsg byteArrayToMessage(byte[] arrMessage){
        UserLoginMsg message = null;

        ByteArrayInputStream byteArray = new ByteArrayInputStream(arrMessage);
        try {
            ObjectInputStream in = new ObjectInputStream(byteArray);
            message = (UserLoginMsg)in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return message;
    }

    public byte[] messageToByteArray() {
        byte[] data = null;

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteArray);
            out.writeObject(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        data = byteArray.toByteArray();
        return data;
    }
}
