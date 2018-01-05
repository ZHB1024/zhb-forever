package com.forever.zhb.utils.image;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Arrays;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.sanselan.Sanselan;

public class Identify {
	
	protected static final Logger logger = Logger.getLogger(Identify.class);
	private static final byte[] jpgHead = new byte[] { -1, -40 };
	private static final byte[] jpgTail = new byte[] { -1, -39 };

	public static ImageVO checkImg(byte[] photo) {
		ImageVO vo = new ImageVO();
		if (photo == null) {
			vo.setOk(false);
			vo.setMsg("照片大小应介于1KB至100KB");
			return vo;
		} else {
			int head = photo.length;
			if (head >= 1024 && head < 102400) {
				byte[] head1 = new byte[2];
				System.arraycopy(photo, 0, head1, 0, 2);
				if (!Arrays.equals(head1, jpgHead)) {
					vo.setOk(false);
					vo.setMsg("照片文件不规范01");
					return vo;
				} else {
					int width;
					int height;
					try {
						ByteArrayInputStream ex = new ByteArrayInputStream(photo);
						BufferedImage sourceImg = ImageIO.read(ex);
						width = sourceImg.getWidth();
						height = sourceImg.getHeight();
					} catch (IIOException arg8) {
						try {
							Dimension ex1 = Sanselan.getImageSize(photo);
							width = (int) ex1.getWidth();
							height = (int) ex1.getHeight();
						} catch (Exception arg7) {
							logger.error(arg7.getMessage());
							vo.setOk(false);
							vo.setMsg("照片文件不规范03");
							return vo;
						}
					} catch (Exception arg9) {
						logger.error(arg9.getMessage());
						vo.setOk(false);
						vo.setMsg("服务端异常04");
						return vo;
					}

					if (width <= 480 && width >= 90 && height >= width) {
						if (height <= 640 && height >= 100) {
							vo.setOk(true);
							vo.setWidth(width);
							vo.setHeight(height);
							return vo;
						} else {
							vo.setOk(false);
							vo.setMsg("照片高度应在100至640之间");
							return vo;
						}
					} else {
						vo.setOk(false);
						vo.setMsg("照片宽度应在90至480之间且小于高度");
						return vo;
					}
				}
			} else {
				vo.setOk(false);
				vo.setMsg("照片大小应介于1KB至100KB");
				return vo;
			}
		}
	}

	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; ++n) {
			stmp = Integer.toHexString(b[n] & 255);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}

		return hs.toUpperCase();
	}

	public static int searchTailFromStart(byte[] bytes) {
		for (int i = 0; i < bytes.length - 1; ++i) {
			if (bytes[i] == jpgTail[0] && bytes[i + 1] == jpgTail[1]) {
				return i + 1;
			}
		}

		return -1;
	}

	public static int searchTailFromEnd(byte[] bytes) {
		for (int i = bytes.length - 1; i > 0; --i) {
			if (bytes[i] == jpgTail[1] && bytes[i - 1] == jpgTail[0]) {
				return i;
			}
		}

		return -1;
	}

}
