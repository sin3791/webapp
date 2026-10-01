package com.sisait.webapp.domain;


import lombok.ToString;

@ToString
public class PagingVO {
    private int nowPage = 1;
    //현재 페이지의 표시할 레코드 수
    private int onePageRecord = 5;
    private int totalRecord;
    private int totalPage;

    private int offsetPoint;

    private int onePageNumCount = 5;
    private int startPageNum=1;

    private String searchKey;
    private String searchWord;

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;

        startPageNum = (nowPage-1)/onePageNumCount*onePageNumCount+1;
    }

    public int getOnePageRecord() {

        return onePageRecord;
    }

    public void setOnePageRecord(int onePageRecord) {
        this.onePageRecord = onePageRecord;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;

        if(totalRecord % onePageRecord == 0){
            totalPage = totalRecord / onePageRecord;
        }else{
            totalPage = totalRecord / onePageRecord + 1;

        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getOffsetPoint() {
        return offsetPoint;
    }

    public void setOffsetPoint(int offsetPoint) {
        this.offsetPoint = offsetPoint;
    }

    public int getOnePageNumCount() {
        return onePageNumCount;
    }

    public void setOnePageNumCount(int onePageNumCount) {
        this.onePageNumCount = onePageNumCount;
    }

    public int getStartPageNum() {
        return startPageNum;
    }

    public void setStartPageNum(int startPageNum) {
        this.startPageNum = startPageNum;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}
