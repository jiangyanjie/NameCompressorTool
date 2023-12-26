package shortenBasedCtx;

import java.util.ArrayList;

public class Heu {
	public static String H1(String abbr, ArrayList<String> terms) {
		if (abbr.length() == 1) {
			return abbr +"@@"+null;
		}
		if (abbr.length() > terms.size()) {
			return abbr +"@@"+null;
		}

		for (int i = 0; i <= terms.size() - abbr.length(); i++) {
			String ics = "";

			for (int j = i; j < abbr.length() + i; j++) {
				String term = terms.get(j);
				ics = ics + term.toLowerCase().charAt(0);
			}
			if (abbr.equals(ics)) {
				String temp = "";

				for (int j = i; j < abbr.length() + i; j++) {
					String term = terms.get(j);
					temp = temp + " " + term;
				}

				String expansion = temp;
				return abbr +"@@"+expansion.trim();
			}
		}

		return abbr +"@@"+null;
	}

	public static String H2(String abbr, ArrayList<String> terms) {
		if (!H1(abbr, terms).equals(abbr+"@@"+null)) {

			return abbr +"@@"+null;
		}
		ArrayList<String> possibleExpansions = new ArrayList<String>();
		for (String term : terms) {
			term = term.toLowerCase();
//			System.out.print(term+"\t");
//			System.out.println(Dic.isInDict(term, false));
			if (term.startsWith(abbr)) {
				possibleExpansions.add(term);
			}
		}
		if (possibleExpansions.size() == 0) {
			// expand fail
			return abbr +"@@"+null;
		}

		String expansion = "";
		for (String possibleExpansion : possibleExpansions) {
			expansion += possibleExpansion + "#";
		}

		return abbr +"@@"+ expansion.substring(0, expansion.length() - 1);
	}

	public static int UpperLetterNum(String s) {
		char[] cs = s.toCharArray();
		int counter = 0;
		for (char c : cs) {
			if (c <= 'Z' && c >= 'A') {
				counter++;
			}
		}
		return counter;
	}

	public static String H3(String abbr, ArrayList<String> terms) {
		String oriAbbr = abbr;
		abbr = abbr.toLowerCase();

		if ((!H1(abbr, terms).equals(abbr+"@@"+null))  || (!H2(abbr, terms).equals(abbr+"@@"+null))) {
			return abbr+"@@"+ null;
		}
		if (abbr.length() <= 1) {
			return abbr+"@@"+null;
		}

		if (UpperLetterNum(oriAbbr) >= 2) {
//			System.out.println(oriAbbr + "大写字母超过2个，不能用H3");
			return abbr+"@@"+null;// 大写字母超过2个，不能用H3
		}

		ArrayList<String> possibleExpansions = new ArrayList<String>();
		for (String term : terms) {
			term = term.toLowerCase();
			if (!(term.charAt(0) + "").toLowerCase().equals((abbr.charAt(0) + "").toLowerCase()))// 缩写词第一个字母必须和扩展相同
				continue;

			if (Util.isSequence(term.toLowerCase(), abbr)) {
				possibleExpansions.add(term);
			}
		}
		if (possibleExpansions.size() == 0) {
			return abbr+"@@"+null;
		}

		String expansion = "";
		for (String possibleExpansion : possibleExpansions) {
			expansion += possibleExpansion + "#";
		}
		return abbr+"@@"+expansion.substring(0, expansion.length() - 1);
	}
}
