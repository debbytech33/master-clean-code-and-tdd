package com.b.simple.design.business.text;

public class TextHelper {

	public String swapLastTwoCharacters(String str) {

		int length = str.length();

		if (length < 2) return str;

		char lastChar = str.charAt(length -1);

		char secondLastChar = str.charAt(length -2);

		String mainPartOfString = str.substring(0, str.length()-2);

		return  mainPartOfString + lastChar + secondLastChar;
	}

	public String truncateAInFirst2Positions(String str) {
		if (str.length() < 2)
			return str.replaceAll("A", "");

		String first2Characters = str.substring(0, 2);
		String first2CharactersUpdated = first2Characters.replaceAll("A", "");
		String restOfTheString = str.substring(2);

		return first2CharactersUpdated + restOfTheString;
	}
}
