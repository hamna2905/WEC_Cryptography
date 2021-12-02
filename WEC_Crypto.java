
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class WEC_Crypto {


	//Conversion of base64 to plaintext
	public static String base64decode(String text) {
		String plainText = "";
		String temp1 = "";
		
		//Creating a hashmap to store the base64 characters and their values.
		Map<Character,Integer> base64Chart = new HashMap<Character,Integer>();
		int k = 0;
		char c = 'A';
		for(k=0,c='A';k<26;k++,c++) {
			base64Chart.put(c, k);
		}
		for(k=26,c='a';c<='z';k++,c++) {
			base64Chart.put(c, k);
		}
		for(k=52,c='0';c<='9';k++,c++) {
			base64Chart.put(c, k);
		}
		
		//Converting each character to to its 6-bit binary form using the values from hashmap
		for(int i=0; i<text.length()-2; i++) {
			int n = base64Chart.get(text.charAt(i));
			String temp = "";
			for(int j=0; j<6; j++) {
				temp += n%2;
				n /= 2;
			}
			for(int j=0; j<6; j++) {
				temp1 += temp.charAt(5-j);
			}
		}
		
		//Converting each group of 8 bits to its corresponding ascii character to get the decoded string
		for(int i=0; i<temp1.length()-8; i+=8) {
			int n = Integer.parseInt(temp1.substring(i,i+8));
			int m = 0;
			int x = 1;
			while(n!=0) {
				m += (n%10)*x;
				n /= 10;
				x *= 2;
			}
			plainText += (char)m;
		}

		return plainText;
	}
	
	//Value of n is negative for shift to the left and positive for shift to the right
	public static String caesarShift(String text, int n) {
		char[] string = new char[text.length()];
		
		//Shifting each letter by a value of n
		for(int i=0; i<text.length(); i++) {
			if(text.charAt(i)>90 || text.charAt(i)<65) {                 //Checking if character is a letter
				string[i] = text.charAt(i);
				continue;
			}
			if(text.charAt(i)+n>90)
				string[i] = (char) (text.charAt(i)-(26-n));
			else if(text.charAt(i)+n<65)
				string[i] = (char) (text.charAt(i)+(26+n));
			else
				string[i] = (char) (text.charAt(i)+n);
		}
		return String.copyValueOf(string);
	}
	
	
	//Decoding of playfair cipher
	public static String playFair(String text) {
		String plainText = "";
		
		//Creating the keysquare which is a 5X5 matrix with alphabets from A-Z except J.
		char[][] keySquare = new char[5][5];
		char x = 'A';
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				if(x == 'J')
					x++;
				keySquare[i][j] = x;
				x++;
			}
		}
		
		//Finding the corresponding plaintext letters for each digraph(a pair of two letters)
		for(int i=0; i<text.length()-1; i+=2) {
			
			int[] pos1 = getPosition(text.charAt(i),keySquare);
			int[] pos2 = getPosition(text.charAt(i+1),keySquare);
			
			if(pos1[0] == pos2[0]) {            //If both letters are in the same row of the keysquare, the plaintext letters are the ones to their immediate left.
				if(pos1[1] == 0)
					pos1[1] = 4;
				else
					pos1[1]--;
				if(pos2[1] == 0)
					pos2[1] = 4;
				else
					pos2[1]--;
			}
			
			else if(pos1[1] == pos2[1]) {       //If both letters are in the same column of the keysquare, the plaintext letters are the letters one place above them.
				if(pos1[0] == 0)
					pos1[0] = 4;
				else
					pos1[0]--;
				if(pos2[0] == 0)
					pos2[0] = 4;
				else
					pos2[0]--;
			}
			
			else {                             //If both letters are not in the same row or column and form a rectangle, the plaintext letters are the other two corners of the rectangle.
				int temp = pos1[1];
				pos1[1] = pos2[1];
				pos2[1] = temp;
			}
			plainText = plainText + keySquare[pos1[0]][pos1[1]] + keySquare[pos2[0]][pos2[1]];

		}

		return plainText;
	}
	
	//Method to get the position of a letter in the keysquare
	public static int[] getPosition(char c,char[][] matrix) {
		int[] pos = new int[2];
		for(int i=0; i<5; i++) {
			for(int j=0; j<5; j++) {
				if(matrix[i][j] == c) {
					pos[0] = i;
					pos[1] = j;
					return pos;
				}
			}
		}
		return null;
	}
	
	
	//RSA encryption of a given number with the public keys n and e
	public static int RSA(int n, int e, int x) {
		BigInteger N = BigInteger.valueOf(n);
		BigInteger X = BigInteger.valueOf(x);
		
		
		BigInteger RSA = (X.pow(e)).mod(N);
		return RSA.intValue();
	}
	
	
	public static void main(String[] args) {
		
		//QR code in the jpg file is scanned to get the below string which is then base64 decoded.
		String encryptedText = "R3JlYXQgam9iLiBKdWxpdXMgQ2Flc2VyIHdhcyBib3JuIGluIHRoZSAxMDAgQkM6ClBEQSBKQVRQIFlFTERBTiBHQVVPTVFXTkEgRU8gUERBIFdITERXWEFQTyBTRVBES1FQIEYKT1BYV09EUFNLUUxPTkNYUU5VSkVPTFhQV0FFSE1PVVpPRVFYWFZLVUpPV0JMTVdYUFFVSU9FTFBNWUtZRUhNT0dPS1lRWEFYS1lLRExZUVpZTFlIQVdXQkxNV1hRWUxXVldPWQ==";
		String decryptedText = base64decode(encryptedText);
		System.out.println("Step 1:\n" + decryptedText + "\n");
		
		//The decoded text then undergoes a caesar shift of 4 to get next step
		String str = caesarShift(decryptedText.split("\n")[1],4) + "\n" + caesarShift(decryptedText.split("\n")[2],4);
		System.out.println("Step 2:\n" + str + "\n");
		
		//The second line after caesar shift is decrypted using playfair cipher with key square as all alphabets without J.
		str = playFair(str.split("\n")[1]);
		System.out.println("Step 3:\n" + str + "\n");
		
		//The number 243 is RSA encrypted with n value as 2419 and e value as 11 to get the password to access zip file.
		int password = RSA(2419,11,243);
		System.out.println("Step 4(RSA Encryption):\nPassword = " + password + "\n");
		
		//The string in text file is decoded using caesar shift of 5 to the left
		String text = "TM, DTZ KTZSI RJ. HTSLWFYX. YMNX NX YMJ JSILTFQ. TW NX NY?";
		System.out.println("Step 5:\n" + caesarShift(text,-5));
		
		
	}

}

