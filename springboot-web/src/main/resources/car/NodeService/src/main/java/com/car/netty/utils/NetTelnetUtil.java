package com.car.netty.utils;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.net.SocketException;


public class NetTelnetUtil {

    /**
     * telnet检测远端端口是否可用
     *
     * @param serverIp
     * @param port
     * @param timeout
     * @return
     */
    public static boolean checkServerAvaliable(String serverIp, String port, int timeout)
    {
        boolean isConnect = false;
        TelnetClient telnet = new TelnetClient();
        try
        {
            telnet.setConnectTimeout(timeout);
            telnet.connect(serverIp, Integer.valueOf(port));
            if (telnet != null)
            {
                isConnect = telnet.isConnected();
            }
        }
        catch (SocketException ioe)
        {
            System.out.println(ioe);
        }
        catch (IOException se)
        {
            System.out.println(se);
        }
        finally
        {
            disconnect(telnet);
        }
        if (isConnect)
        {
            System.out.println("服务器:" + serverIp + "端口: " + port + " 是可用的，通过telnet。.");
        }
        else
        {
            System.out.println("服务器:" + serverIp + "端口: " + port + " 是不可用的，通过telnet。.");
        }
        return isConnect;
    }

    public static void disconnect(TelnetClient telnet)
    {
        try
        {
            telnet.disconnect();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
}
