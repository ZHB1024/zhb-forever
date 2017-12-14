package com.forever.zhb.vo;

import java.awt.Font;

public class WatermarkVO {
    
    public Font font;
    public int centerX;
    public int centerY;

    public WatermarkVO(Font font, int centerX, int centerY) {
        this.font = font;
        this.centerX = centerX;
        this.centerY = centerY;
    }

}
