package com.petnote.api.common.upload;

public enum UploadType {

    COMMUNITY("upload/community"),
    MYPAGE("upload/mypage"),
    REMIND("upload/remind"),
    MAIN("upload/main"),
    ANIMAL("upload/animal"),
    GOAL("upload/goal");

    private final String path;

    UploadType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

