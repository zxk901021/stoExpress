package com.zhy_9.stoexpress.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import android.annotation.SuppressLint;
import android.util.Base64;

@SuppressLint("NewApi")
public class GZipUtil {

	/**
	 * GZIP—πÀı
	 * 
	 * @param sourceString
	 * @return
	 */
	public static String gZipString(String sourceString) {
		String destString = "";
		byte[] b = null;
		byte[] data = sourceString.getBytes(Charset.forName("UTF-16LE"));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			
			gzip.write(data);
			gzip.finish();
			gzip.close();
			b = bos.toByteArray();
			bos.close();
			destString = Base64.encodeToString(b, Base64.NO_WRAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destString;
	}

	/**
	 * GZIPΩ‚—π
	 * 
	 * @param sourceString
	 * @return
	 */
	public static String gZipUnString(String sourceString) {
		String destString = "";
		byte[] data = Base64.decode(sourceString, Base64.NO_WRAP);
		byte[] b = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		try {
			GZIPInputStream gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
			baos.close();
			gzip.close();
			bis.close();
			destString = new String(b,Charset.forName("UTF-16LE"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destString;
	}
}
