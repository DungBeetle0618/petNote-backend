package com.petnote.api.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Log4j2
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp insertDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp updateDate;

    /* 페이징 */
    private Integer page;
    private Integer rows;
    private Integer records;


    public void setRecords( Integer records) {
        this.records = records;
        // 만약 page가 전체 페이지 보다 크면 마직막페이지로 설정한다.
        if( records!=null && rows != null && page != null) {
            int total = (records - 1 ) / rows + 1;
            if( page > total ) {
                page = total;
            }
        }
    }

    public Integer getTotal() {
        return  records!=null && rows != null ?  (records - 1 ) / rows + 1 : null;

    }
}
