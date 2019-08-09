package com.car.netty.utils;

import com.alibaba.druid.util.DruidPasswordCallback;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SuppressWarnings("serial")
public class DecyptPgPasswd extends DruidPasswordCallback
{
    private int mICurrPos = 0;
    private List<Integer> mPool = new ArrayList<Integer>();

    /**
     * PG数据库密码解密函数
     * 
     * @param value
     * @return
     */
    public String decrypt(String value)
    {
        if (value.isEmpty())
        {
            return "";
        }
        mICurrPos = 0;
        String reStr = stringToString(value);

        List<Integer> randList = new ArrayList<Integer>();
        char[] charArr = reStr.toCharArray();
        for (int i = 0; i < charArr.length; i++)
        {
            int val = getNextInt();
            randList.add(val);
        }

        String result = "";
        for (int i = 0; i < charArr.length; i++)
        {
            char tmp = charArr[i] + randList.get(i) > 255 ? (char) (charArr[i] + randList.get(i) - 256)
                    : (char) (charArr[i] + randList.get(i));
            result = result + tmp;
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }

    /**
     * @param str
     * @return
     */
    private String stringToString(String str)
    {
        String strResult = "";
        char cVal;

        String[] strArr = str.split("-");
        for (int i = 0; i < strArr.length; i++)
        {
            String value = strArr[i];
            if (!("".equals(value) || null == value))
            {
                int num = 0;
                num = Integer.parseInt(value);
                cVal = (char) num;
                strResult += cVal;
            }
        }
        return strResult;
    }

    private int getNextInt()
    {
        if (mICurrPos == 0)
        {
            for (int i = 0; i < 256; i++)
            {
                mPool.add(i);
            }
        }
        int lNRet = (int) mPool.get(mICurrPos);
        mICurrPos = ((mICurrPos + 16) > 255) ? 0 : mICurrPos + 16;
        return lNRet;
    }

    @Override
    public void setProperties(Properties properties)
    {
        super.setProperties(properties);
        String strPwd = properties.getProperty("password");
        if (StringUtils.isNotBlank(strPwd))
        {
            try
            {
                // 这里的password是将jdbc.properties配置得到的密码进行解密之后的值
                // 所以这里的代码是将密码进行解密
                // 将pwd进行解密;
                String password = decrypt(strPwd);
                setPassword(password.toCharArray());
            }
            catch (Exception e)
            {
                setPassword(strPwd.toCharArray());
            }
        }
    }

    /**
     * 测试主函数
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        DecyptPgPasswd oDecyptPgPasswd = new DecyptPgPasswd();
//        String strTemp = "-65-84-77-57-46-225-210-195-180-165-150-206-190";
        String strTemp = "-112-81-83-67-55-20-30-14";
        String strTEMP = oDecyptPgPasswd.decrypt(strTemp);

        System.out.println("解密前的：" + strTemp);
        System.out.println("解密后的：" + strTEMP);
          
     }
}
