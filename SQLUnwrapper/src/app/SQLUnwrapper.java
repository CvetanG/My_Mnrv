package app;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.InflaterInputStream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class SQLUnwrapper {
	
	public static String Inflate(String src)  {
		

		try {
			Path path = Paths.get("SQLtoUnwrapped.txt");
			byte[] data = Files.readAllBytes(path);
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			InflaterInputStream iis = new InflaterInputStream(bis);
			StringBuffer sb = new StringBuffer();
			for (int c = iis.read(); c != -1; c = iis.read()) {
				sb.append((char) c);
			}
			return sb.toString();
		} catch (Exception e) {
		}
		return null;
	}

	public static byte[] Deflate(String src, int quality) {
		try {
			byte[] tmp = new byte[src.length() + 100];
			Deflater defl = new Deflater(quality);
			defl.setInput(src.getBytes("UTF-8"));
			defl.finish();
			int cnt = defl.deflate(tmp);
			byte[] res = new byte[cnt];
			for (int i = 0; i < cnt; i++)
				res[i] = tmp[i];
			return res;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static void main(String[] args) {
		String file = "SQLtoUnwrapped.txt";
		String output = Inflate(file);
		System.out.println(output);
	}
}
