package com.petnote.api.community.dto;

import com.petnote.api.common.upload.UploadDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Log4j2
public class CommunityDTO extends UploadDTO {

    private String postNo;          //글 번호
    private String title;           //제목
    private String contents;        //내용
    private String postType;        //카테고리 (피드/칼럼/리뷰)
    private String reviewType;      //리뷰카테고리 (병원/상품 등)
    private int likeCount;          //좋아요 수
    private String placeNo;         //장소 일련번호
    private String productNo;       //상품 일련번호
    private String insertUser;      //작성자 ID
    private String updateUser;      //수정자 ID
    private String insertDate;      //입력 일시
    private String updateDate;      //수정 일시


}
