package Assignment3;

import java.util.ArrayList;

/**
 * Utility class for encrypting and decrypting messages using
 * Vigenere, Playfair, and Caesar ciphers.
 * 
 * Class: CMSC203-22537
 * Instructor: Ahmed Tarek
 * Description: (Cryptography, decoding using three methods; Vigenere, Playfair, and Caesar)
 * Due: 10/15/2026
 * Platform/compiler: Eclipse / Java
 * I pledge that I have completed the programming  assignment independently. 
*  I have not copied the code from a student or any source. 
*  I have not given my code to any student.
*  Print your Name here: ____Luis Aguero______
*/

public class CryptoManager {
	
	private static final char LOWER_RANGE = ' ';
	private static final char UPPER_RANGE = '_';
	private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
	
	//All 64 permitted ASCII characters, in a consistent cipher order.
	private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !\"#$%&'()*+,-./:;<=>?@[\\]^_";
	
	private static final String ERROR_MESSAGE = "The selected string is not in bounds, Try again.";
	
	/**
	 * Checks whether every character is in the permitted ASCII range.
	 * 
	 * @param plainText text to check
	 * @return true when all characters are in range
	 */
	public static boolean isStringInBounds(String plainText)
	{
		if (plainText == null)
		{
			return false;
		}
		
		for (int i = 0; i < plainText.length(); i++)
		{
			char character = plainText.charAt(i);
			
			if (character < LOWER_RANGE || character > UPPER_RANGE)
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Encrypts a message using the Vigenere cipher.
	 * 
	 * @param plainText original message
	 * @param key repeating keyword
	 * @return encrypted message or an error message
	 */
	public static String vigenereEncryption(String plainText, String key)
	{
		if (!isStringInBounds(plainText) || !isValidKey(key))
		{
			return ERROR_MESSAGE;
		}
		
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < plainText.length(); i++)
		{
			int plainPosition = ALPHABET64.indexOf(plainText.charAt(i));
			int keyPosition = ALPHABET64.indexOf(key.charAt(i % key.length()));
			
			int encryptedPosition = (plainPosition + keyPosition) % RANGE;
			
			result.append(ALPHABET64.charAt(encryptedPosition));
		}
		
		return result.toString();
	}
	
	/**
	 * Decrypts a Vigenere ciphertext.
	 * 
	 * @param encryptedText encrypted message
	 * @param key original keyword
	 * @return decrypted message or an error message
	 */
	 public static String vigenereDecryption(String encryptedText, String key)
	 {
		 if (!isStringInBounds(encryptedText) || !isValidKey(key))
		 {
			 return ERROR_MESSAGE;
		 }
		 
		 StringBuilder result = new StringBuilder();
		 
		 for (int i = 0; i < encryptedText.length(); i++)
		 {
			 int encryptedPosition = ALPHABET64.indexOf(encryptedText.charAt(i));
			 int keyPosition = ALPHABET64.indexOf(key.charAt(i % key.length()));
			 int decryptedPosition = Math.floorMod(encryptedPosition - keyPosition, RANGE);
			 
			 result.append(ALPHABET64.charAt(decryptedPosition));
		 }
		
		 return result.toString();
		 
	 }
	 
	 /**
	  * Encrypts pairs of characters using an 8 x 8 Playfair matrix.
	  * 
	  * @param plainText original message
	  * @param key keyword used to build the matrix
	  * @return encrypted message or an error message
	  */
	 public static String playfairEncryption(String plainText, String key)
	 {
		 if (!isStringInBounds(plainText) || !isValidKey(key))
		 {
			 return ERROR_MESSAGE;
		 }
		 
		 char[][] matrix = buildMatrix(key);
		 StringBuilder result = new StringBuilder();
		 
		 //Process complete pairs. An odd final character is preserved.
		 int i = 0;
		 while (i + 1 < plainText.length())
		 {
			 char first = plainText.charAt(i);
			 char second = plainText.charAt(i + 1);
			 
			 int[] p1 = findPosition(matrix, first);
			 int[] p2 = findPosition(matrix, second);
			
			if (p1[0] == p2[0])
			{
				result.append(matrix[p1[0]][(p1[1] + 1) % 8]);
				result.append(matrix[p2[0]][(p2[1] + 1) % 8]);
			}
			 
			else if(p1[1] == p2[1])
			{
				result.append(matrix[(p1[0] + 1) % 8][p1[1]]);
				result.append(matrix[(p2[0] + 1) % 8][p2[1]]);
			}
			 
			else
			{
				result.append(matrix[p1[0]][p2[1]]);
				result.append(matrix[p2[0]][p1[1]]);
			}
			 
			 i += 2;
		 }
		 
		 if (i < plainText.length())
		 {
			 result.append(plainText.charAt(i));
		 }
		 
		 return result.toString();
	 }
	 
	 /**
	  * Decrypts a Playfair ciphertext.
	  * 
	  * @param encryptedText encrypted message
	  * @param key original keyword
	  * @return decrypted message or an error message
	  */
	 public static String playfairDecryption(String encryptedText, String key)
	 {
		 if (!isStringInBounds(encryptedText) || !isValidKey(key))
		 {
			 return ERROR_MESSAGE;
		 }
		 
		 char [][] matrix = buildMatrix(key);
		 StringBuilder result = new StringBuilder();
		 
		 int i = 0;
		 while (i + 1 < encryptedText.length())
		 {
			 char first = encryptedText.charAt(i);
			 char second = encryptedText.charAt(i + 1);
			 
			 int[] p1 = findPosition(matrix, first);
			 int[] p2 = findPosition(matrix, second);
			 
			 if (p1[0] == p2[0])
			 {
				 result.append(matrix[p1[0]][Math.floorMod(p1[1] - 1, 8)]);
				 result.append(matrix[p2[0]][Math.floorMod(p2[1] - 1, 8)]);
			 }
			 
			 else if(p1[1] == p2[1])
			 {
				 result.append(matrix[Math.floorMod(p1[0] - 1, 8)][p1[1]]);
				 result.append(matrix[Math.floorMod(p2[0] - 1, 8)][p2[1]]);
			 }
			 else
			 {
				 result.append(matrix[p1[0]][p2[1]]);
				 result.append(matrix[p2[0]][p1[1]]);
			 }
			 
			 i += 2;
		 }
		 
		 if (i < encryptedText.length())
		 {
			 result.append(encryptedText.charAt(i));
		 }
		 
		 return result.toString();
	 }
	 
	 /**
	  * Encrypts a messaage using the Caesar cipher.
	  * 
	  * @param plainText original message
	  * @param key shift amount
	  * @return encrypted message or an error message
	  */
	 public static String caesarEncryption(String plainText, int key)
	 {
		 if(!isStringInBounds(plainText))
		 {
			 return ERROR_MESSAGE;
		 }
		 
		 StringBuilder encryptedText = new StringBuilder();
		 int shift = Math.floorMod(key, RANGE);
		 
		 for (int i = 0; i < plainText.length(); i++)
		 {
			 int position = ALPHABET64.indexOf(plainText.charAt(i));
			 int encryptedPosition = (position + shift) % RANGE;
			 
			 encryptedText.append(ALPHABET64.charAt(encryptedPosition));
		 }
		 
		 return encryptedText.toString();
		 
	 }

	 /**
	  * Decrypts a Caesar ciphertext.
	  * 
	  * @param encryptedText encrypted message
	  * @param key original shift amount
	  * @return decrypted message or an error message
	  */
	 public static String caesarDecryption(String encryptedText, int key)
	 {
		 if (!isStringInBounds(encryptedText))
		 {
			 return ERROR_MESSAGE;
		 }
		 
		 StringBuilder plainText = new StringBuilder();
		 int shift = Math.floorMod(key, RANGE);
		 
		 for (int i = 0; i < encryptedText.length(); i++)
		 {
			 int position = ALPHABET64.indexOf(encryptedText.charAt(i));
			 int decryptedPosition = Math.floorMod(position - shift, RANGE);
			 
			 plainText.append(ALPHABET64.charAt(decryptedPosition));
		 }
		 
		 return plainText.toString();
	 }
	 
	 /**
	  * Validates a cipher keyword.
	  * 
	  * @param key keyword to check
	  * @return true if the keyword is non-null, nonempty, and in range
	  */
	 private static boolean isValidKey(String key)
	 {
		 return key != null && !key.isEmpty() && isStringInBounds(key);
	 }
	 
	 /**
	  * Builds an 8 x 8 matrix using unique keyword characters first,
	  * followed by the remaining alphabet characters.
	  * 
	  * @param key keyword
	  * @return completed matrix
	  */
	 
	 private static char[][] buildMatrix(String key)
	 {
		 ArrayList<Character> characters = new ArrayList<>();
		 
		 for (int i = 0; i < key.length(); i++)
		 {
			 char ch = key.charAt(i);
			 
			 if (!characters.contains(ch))
			 {
				 characters.add(ch);
			 }
		 }
		 
		 for (int i = 0; i < ALPHABET64.length(); i ++)
		 {
			 char ch = ALPHABET64.charAt(i);
			 
			 if (!characters.contains(ch))
			 {
				 characters.add(ch);
			 }
		 }
		 
		 char[][] matrix = new char [8][8];
		 int index = 0;
		 
		 for (int row = 0; row < 8; row++)
		 {
			 for(int col = 0; col < 8; col++) 
		{
				 
			 matrix[row][col] = characters.get(index++);
		 }
	 }
	 
	 return matrix;
}

	 /**
	  * Locates a character in the matrix.
	  * 
	  * @param matrix Playfair matrix
	  * @param target character to locate
	  * @return array containing its row and column
	  */
	 private static int[] findPosition(char[][] matrix, char target)
	 {
		 for (int row = 0; row < 8; row++)
		 {
			 for(int col = 0; col < 8; col++)
			 {
				 if (matrix[row][col] == target)
				 {
					 return new int[] { row, col };
				 }
			 }
		 }
		 
		 return new int[] {-1, -1};
	 }
	 
}
