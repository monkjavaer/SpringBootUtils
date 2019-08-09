package com.car.orbit.orbitservice.bo;

import java.util.List;

/**
 * @author monkjavaer
 * @date 2018/03/23.
 */
public class HistoryVideoBO {
    /**
     * rtsp串集合
     */
    private List<String> rtsps;
    /**
     * 视频服务器
     */
    private String videoServerIP;
    private String relayIP;
    private String credential;
    private String relayUsername;
    private String relayPassword;


    public List<String> getRtsps() {
        return rtsps;
    }

    public void setRtsps(List<String> rtsps) {
        this.rtsps = rtsps;
    }

    public String getVideoServerIP() {
        return videoServerIP;
    }

    public void setVideoServerIP(String videoServerIP) {
        this.videoServerIP = videoServerIP;
    }

    public String getRelayIP() {
        return relayIP;
    }

    public void setRelayIP(String relayIP) {
        this.relayIP = relayIP;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getRelayUsername() {
        return relayUsername;
    }

    public void setRelayUsername(String relayUsername) {
        this.relayUsername = relayUsername;
    }

    public String getRelayPassword() {
        return relayPassword;
    }

    public void setRelayPassword(String relayPassword) {
        this.relayPassword = relayPassword;
    }
}
