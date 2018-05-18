package com.forever.zhb.vo;

import java.util.List;

public class StatisticsResultVO {
    
    private String optionId;
    private String title;
    private String count;
    private String percent;
    private List<StatisticsResultVO> titleVos;
    private List<StatisticsResultVO> resultVos;
    
    public String getOptionId() {
        return optionId;
    }
    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public String getPercent() {
        return percent;
    }
    public void setPercent(String percent) {
        this.percent = percent;
    }
    public List<StatisticsResultVO> getResultVos() {
        return resultVos;
    }
    public void setResultVos(List<StatisticsResultVO> resultVos) {
        this.resultVos = resultVos;
    }
    public List<StatisticsResultVO> getTitleVos() {
        return titleVos;
    }
    public void setTitleVos(List<StatisticsResultVO> titleVos) {
        this.titleVos = titleVos;
    }

}
