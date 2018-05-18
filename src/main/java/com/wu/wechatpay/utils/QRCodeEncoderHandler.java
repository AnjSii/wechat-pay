package com.wu.wechatpay.utils;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.swetake.util.Qrcode;

import sun.misc.BASE64Encoder;


public class QRCodeEncoderHandler {

	private static final String CHARSET = "utf-8";

	public static final String FORMAT_NAME = "JPG";

	// 二维码尺寸
	private static final int QRCODE_SIZE = 185;

	// LOGO宽度
	private static final int WIDTH = 60;

	// LOGO高度
	private static final int HEIGHT = 60;

	public void encoderQRCode(String content, OutputStream output, char errorCorrect) throws IOException {
		byte[] contentBytes;
		try {
			contentBytes = content.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}

		if ((contentBytes.length == 0) || (contentBytes.length >= 120))
			throw new IllegalArgumentException("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");

		Qrcode qrcodeHandler = new Qrcode();
		qrcodeHandler.setQrcodeErrorCorrect(errorCorrect);
		qrcodeHandler.setQrcodeEncodeMode('B');
		qrcodeHandler.setQrcodeVersion(7);

		BufferedImage bufImg = new BufferedImage(140, 140, 1);

		Graphics2D gs = bufImg.createGraphics();

		gs.setBackground(Color.WHITE);
		gs.clearRect(0, 0, 140, 140);

		gs.setColor(Color.BLACK);

		int pixoff = 2;

		boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);

		for (int i = 0; i < codeOut.length; i++) {
			for (int j = 0; j < codeOut.length; ++j) {
				if (codeOut[j][i])
					gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
			}
		}

		gs.dispose();
		bufImg.flush();

		ImageIO.write(bufImg, "png", output);
	}

	/**
	 * 生成二维码的方法
	 * @param content 目标URL
	 * @param imgPath LOGO图片地址
	 * @param needCompress 是否压缩LOGO
	 * @return 二维码图片
	 * @throws Exception 写入二维码是出现的错误
	 */
	public BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
						: 0xFFFFFFFF);
			}
		}
		if (imgPath == null || "".equals(imgPath)) {
			return image;
		}
		// 插入图片
		insertImage(image, imgPath, needCompress);
		return image;
	}

	/**
	 * 插入LOGO
	 * @param source 二维码图片
	 * @param imgPath LOGO图片地址
	 * @param needCompress 是否压缩
	 * @throws Exception IO错误
	 */
	private void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
		File file = new File(imgPath);
		if (!file.exists()) {
			System.err.println("" + imgPath + "   该文件不存在！");
			return;
		}
		Image src = ImageIO.read(new File(imgPath));
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > WIDTH) {
				width = WIDTH;
			}
			if (height > HEIGHT) {
				height = HEIGHT;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_SIZE - width) / 2;
		int y = (QRCODE_SIZE - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	/**
	 * 将图片转换成base64格式进行存储
	 * @param bufferedImage bufferedImage图片
	 * @param type type后缀
	 * @return base64
	 * @throws IOException IO错误
	 */
	public String encodeToBase64(BufferedImage bufferedImage, String type) throws IOException {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, type, bos);
			byte[] imageBytes = bos.toByteArray();
			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}

	public void encoderQRCode(String content, OutputStream output) throws IOException {
		encoderQRCode(content, output, 'M');
	}

	public static void main(String[] args) throws IOException {
		String imgPath = "/tmp/code.png";

		String content = "http://www.hicailiao.com/store_1.htm";
		//String content = "http://www.hicailiao.com/store_1.htmadfadfasdfasdfwerowueioruoaiuofjalsdjflajsdflajlsdfjwioeruowieuroiwueroiuaodfsjlasdf";

		QRCodeEncoderHandler handler = new QRCodeEncoderHandler();
		try (OutputStream os = new FileOutputStream(new File(imgPath))) {
			handler.encoderQRCode(content, os);
		}

		System.out.println("encoder QRcode success");
	}
}
