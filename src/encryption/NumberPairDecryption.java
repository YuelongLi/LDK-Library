package encryption;

public class NumberPairDecryption {

	public static void main(String[] args) {
		System.out.println(encrypt("lol?"));
	}
	
	public static String decrypt(String n) {
		final int fi = (int)'A';
		StringBuilder build = new StringBuilder();
		int i = 0;
		while(i<n.length()-1) {
			build.append((char)(fi+Integer.valueOf(n.substring(i,i+2))));
			i+=2;
		}
		return build.toString();
	}
	
	public static String encrypt(String n) {
		final int fi = (int)'A';
		StringBuilder build = new StringBuilder();
		int i = 0;
		while(i<n.length()) {
			build.append(toPair((int)n.charAt(i)-fi));
			i++;
		}
		return build.toString();
	}
	
	private static String toPair(int ch) {
		return String.valueOf(ch/10)+String.valueOf(ch%10);
	}
}
