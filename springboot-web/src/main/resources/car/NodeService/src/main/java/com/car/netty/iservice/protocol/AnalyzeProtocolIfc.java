package com.car.netty.iservice.protocol;

import com.car.netty.struct.unv.bean.UnvProtocol;


public interface AnalyzeProtocolIfc {

    public UnvProtocol analyze(byte[] bInBytes);
}
