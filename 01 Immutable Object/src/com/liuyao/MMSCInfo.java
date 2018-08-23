package com.liuyao;

/**
 * 信息中心
 * @author liuyao
 * @date 2018/08/23
 */
public class MMSCInfo {
    private final String deviceID;

    private final String url;

    private final int maxAttachmentSizeInBytes;

    public MMSCInfo(String deviceID, String url, int maxAttachmentSizeInBytes) {
        this.deviceID = deviceID;
        this.url = url;
        this.maxAttachmentSizeInBytes = maxAttachmentSizeInBytes;
    }

    public MMSCInfo(MMSCInfo prototype){
        this.deviceID=prototype.deviceID;
        this.url=prototype.url;
        this.maxAttachmentSizeInBytes=prototype.maxAttachmentSizeInBytes;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getUrl() {
        return url;
    }

    public int getMaxAttachmentSizeInBytes() {
        return maxAttachmentSizeInBytes;
    }
}
