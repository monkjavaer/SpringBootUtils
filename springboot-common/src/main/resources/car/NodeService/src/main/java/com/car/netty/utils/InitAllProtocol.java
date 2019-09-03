package com.base.springboot.car.NodeService.src.main.java.com.car.netty.utils;

import com.base.springboot.car.NodeService.src.main.java.com.car.netty.struct.base.InterProtocolFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * 
 * @Description: 根据路径读取xml文件， 并将读取的xml节点内容保存包ArrayList中.
 * @return
 */
public class InitAllProtocol
{

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(InitAllProtocol.class);
    /**
     * 保存读取xml节点内容的ArrayList
     */
    public ArrayList<InterProtocolFrame> list = new ArrayList<InterProtocolFrame>();

    /**
     * @param fileName
     */
    public InitAllProtocol(String fileName)
    {
        try
        {
            if (null == fileName || "".endsWith(fileName))
            {
                LOGGER.error("Trans Path is null.");
                return;
            }

            File file = new File(fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList nlist = doc.getElementsByTagName("protocol");
            int ilen = nlist.getLength();
            for (int i = 0; i < ilen; i++)
            {
                InterProtocolFrame oInterProtocolFrame = new InterProtocolFrame();

                Node oDllLib = doc.getElementsByTagName("DllLib").item(i).getFirstChild();
                Node oEnableStatus = doc.getElementsByTagName("EnableStatus").item(i).getFirstChild();
                Node oConfPath = doc.getElementsByTagName("ConfPath").item(i).getFirstChild();
                Node oTransFlag = doc.getElementsByTagName("TransFlag").item(i).getFirstChild();

                if (oDllLib != null)
                {
                    oInterProtocolFrame.setstrDllLib(oDllLib.getNodeValue());
                }

                if (oEnableStatus != null)
                {
                    oInterProtocolFrame.setstrEnableStatus(oEnableStatus.getNodeValue());
                }

                if (oConfPath != null)
                {
                    oInterProtocolFrame.setStrConfPath(oConfPath.getNodeValue());
                }

                if (oTransFlag != null)
                {
                    oInterProtocolFrame.setStrTransFlag(oTransFlag.getNodeValue());
                }

                list.add(oInterProtocolFrame);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("path=" + fileName + "。 interprotocolframe.xml failed", e);
        }
    }
}
