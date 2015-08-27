package com.dualfual.remote.coned;

import java.util.Base64;

public class ConEdStatus {

    private String status;
    private String imgUrl;
    private byte[] image;
    private String imageBase64;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
        setImageBase64(image);
    }

    public void setImageBase64(byte[] image) {
        if (image != null && image.length != 0)
            this.imageBase64 = Base64.getEncoder().encodeToString(image);
    }

    public String getImageBase64() {
        return imageBase64;
    }
}
