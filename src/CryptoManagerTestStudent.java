package Assignment3;

import junit.framework.TestCase;
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

public class CryptoManagerTestStudent extends TestCase
{
	private static final String ERROR = "The selected string is not in bounds, Try again.";
	
	public void testIsStringInBounds()
	{
		assertTrue(CryptoManager.isStringInBounds("SEVERN 2026!"));
		assertTrue(CryptoManager.isStringInBounds(""));
		assertFalse(CryptoManager.isStringInBounds("Hello"));
		assertFalse(CryptoManager.isStringInBounds("TEST~"));
		assertFalse(CryptoManager.isStringInBounds(null));
	}
	
	public void testVigenereEncryption()
	{
		String plain = "JAVA PROGRAM 2026!";
		String key = "CODE123";
		
		String encrypted = CryptoManager.vigenereEncryption(plain, key);
		
		assertFalse(encrypted.equals(plain));
		assertEquals(plain, CryptoManager.vigenereDecryption(encrypted, key));
		assertEquals(ERROR, CryptoManager.vigenereEncryption("Invalid~", key));
	}
	
	public void testVigenereDecryption()
	{
		String plain = "JAVA PROGRAM 2026!";
		String key = "CODE123";
		
		String encrypted = CryptoManager.vigenereEncryption(plain, key);
		
		assertEquals(plain, CryptoManager.vigenereDecryption(encrypted, key));
		assertEquals(ERROR, CryptoManager.vigenereDecryption("Invalid~", key));
	}
	
	public void testPlayfairEncryption()
	{
		String plain = "COMPUTER 2026!";
		String key = "SECURITY";
		
		String encrypted = CryptoManager.playfairEncryption(plain, key);
		
		assertFalse(encrypted.equals(plain));
		assertEquals(plain, CryptoManager.playfairDecryption(encrypted, key));
		assertEquals(ERROR, CryptoManager.playfairEncryption("Invalid~", key));
	}
	
	public void testPlayfairDecryption()
	{
		String plain = "COMPUTER 2026!";
		String key = "SECURITY";
		
		String encrypted = CryptoManager.playfairEncryption(plain, key);
		
		assertEquals(plain, CryptoManager.playfairDecryption(encrypted, key));
		assertEquals(ERROR, CryptoManager.playfairDecryption("Invalid~", key));
	}
	
	public void testCaesarEncryption()
	{
		assertEquals("DEF", CryptoManager.caesarEncryption("ABC", 3));
		
		assertEquals("ABC", CryptoManager.caesarEncryption("ABC", 64));
		
		assertEquals(ERROR, CryptoManager.caesarEncryption("Invalid~", 3));
	}
	
	public void testCaesarDecryption()
	{
		assertEquals("ABC", CryptoManager.caesarDecryption("DEF", 3));
		assertEquals("ABC", CryptoManager.caesarDecryption("ABC", 64));
		assertEquals(ERROR, CryptoManager.caesarDecryption("Invalid~", 3));
	}

}
