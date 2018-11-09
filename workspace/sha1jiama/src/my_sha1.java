import java.security.MessageDigest;

public class my_sha1 {
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
	public static String encode(String str2,String pwd) {
		if (str2 == null) {
			return null;
		}
		String str="9823\"rhdAGF3\'4T>%$@gadgu8@324"+pwd+"ASosrg89025r\'ng^&@12-\"~!$^%sbvuofsd"+str2;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void main(String[] args) {
		String s=my_sha1.encode("13076220975",",bhs@mangohm");
		System.out.println(s);
	}
}
