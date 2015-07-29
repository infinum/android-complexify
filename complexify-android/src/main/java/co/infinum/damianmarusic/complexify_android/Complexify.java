package co.infinum.damianmarusic.complexify_android;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Class for calculation of password complexity.
 *
 * Created by Damian Marusic on 29.07.15..
 */
public class Complexify {

	private static final int[][] CHARSETS_ARRAY = {
			// Commonly Used
			////////////////////
			{0x0030, 0x0039}, // Nubrs
			{0x0041, 0x005A}, // Uppercase
			{0x0061, 0x007A}, // Lowercase
			{0x0021, 0x002F}, // Punctuation
			{0x003A, 0x0040}, // Punctuation
			{0x005B, 0x0060}, // Punctuation
			{0x007B, 0x007E}, // Punctuation
			// Everythig Else
			////////////////////
			{0x0080, 0x00FF}, // Latin-1 Supplement
			{0x0100, 0x017F}, // Latin Extended-A
			{0x0180, 0x024F}, // Latin Extended-B
			{0x0250, 0x02AF}, // IPA Extensions
			{0x02B0, 0x02FF}, // Spacing Modifier Letters
			{0x0300, 0x036F}, // Combining Diacritical Marks
			{0x0370, 0x03FF}, // Greek
			{0x0400, 0x04FF}, // Cyrillic
			{0x0530, 0x058F}, // Armenian
			{0x0590, 0x05FF}, // Hebrew
			{0x0600, 0x06FF}, // Arabic
			{0x0700, 0x074F}, // Syriac
			{0x0780, 0x07BF}, // Thaana
			{0x0900, 0x097F}, // Devanagari
			{0x0980, 0x09FF}, // Bengali
			{0x0A00, 0x0A7F}, // Gurmukhi
			{0x0A80, 0x0AFF}, // Gujarati
			{0x0B00, 0x0B7F}, // Oriya
			{0x0B80, 0x0BFF}, // Tamil
			{0x0C00, 0x0C7F}, // Telugu
			{0x0C80, 0x0CFF}, // Kannada
			{0x0D00, 0x0D7F}, // Malayalam
			{0x0D80, 0x0DFF}, // Sinhala
			{0x0E00, 0x0E7F}, // Thai
			{0x0E80, 0x0EFF}, // Lao
			{0x0F00, 0x0FFF}, // Tibetan
			{0x1000, 0x109F}, // Myanmar
			{0x10A0, 0x10FF}, // Georgian
			{0x1100, 0x11FF}, // Hangul Jamo
			{0x1200, 0x137F}, // Ethiopic
			{0x13A0, 0x13FF}, // Cherokee
			{0x1400, 0x167F}, // Unified Canadian Aboriginal Syllabics
			{0x1680, 0x169F}, // Ogham
			{0x16A0, 0x16FF}, // Runic
			{0x1780, 0x17FF}, // Khmer
			{0x1800, 0x18AF}, // Mongolian
			{0x1E00, 0x1EFF}, // Latin Extended Additional
			{0x1F00, 0x1FFF}, // Greek Extended
			{0x2000, 0x206F}, // General Punctuation
			{0x2070, 0x209F}, // Superscripts and Subscripts
			{0x20A0, 0x20CF}, // Currency Symbols
			{0x20D0, 0x20FF}, // Combining Marks for Symbols
			{0x2100, 0x214F}, // Letterlike Symbols
			{0x2150, 0x218F}, // Number Forms
			{0x2190, 0x21FF}, // Arrows
			{0x2200, 0x22FF}, // Mathematical Operators
			{0x2300, 0x23FF}, // Miscellaneous Technical
			{0x2400, 0x243F}, // Control Pictures
			{0x2440, 0x245F}, // Optical Character Recognition
			{0x2460, 0x24FF}, // Enclosed Alphanumerics
			{0x2500, 0x257F}, // Box Drawing
			{0x2580, 0x259F}, // Block Elements
			{0x25A0, 0x25FF}, // Geometric Shapes
			{0x2600, 0x26FF}, // Miscellaneous Symbols
			{0x2700, 0x27BF}, // Dingbats
			{0x2800, 0x28FF}, // Braille Patterns
			{0x2E80, 0x2EFF}, // CJK Radicals Supplement
			{0x2F00, 0x2FDF}, // Kangxi Radicals
			{0x2FF0, 0x2FFF}, // Ideographic Description Characters
			{0x3000, 0x303F}, // CJK Symbols and Punctuation
			{0x3040, 0x309F}, // Hiragana
			{0x30A0, 0x30FF}, // Katakana
			{0x3100, 0x312F}, // Bopomofo
			{0x3130, 0x318F}, // Hangul Compatibility Jamo
			{0x3190, 0x319F}, // Kanbun
			{0x31A0, 0x31BF}, // Bopomofo Extended
			{0x3200, 0x32FF}, // Enclosed CJK Letters and Months
			{0x3300, 0x33FF}, // CJK Compatibility
			{0x3400, 0x4DB5}, // CJK Unified Ideographs Extension A
			{0x4E00, 0x9FFF}, // CJK Unified Ideographs
			{0xA000, 0xA48F}, // Yi Syllables
			{0xA490, 0xA4CF}, // Yi Radicals
			{0xAC00, 0xD7A3}, // Hangul Syllables
			{0xD800, 0xDB7F}, // High Surrogates
			{0xDB80, 0xDBFF}, // High Private Use Surrogates
			{0xDC00, 0xDFFF}, // Low Surrogates
			{0xE000, 0xF8FF}, // Private Use
			{0xF900, 0xFAFF}, // CJK Compatibility Ideographs
			{0xFB00, 0xFB4F}, // Alphabetic Presentation Forms
			{0xFB50, 0xFDFF}, // Arabic Presentation Forms-A
			{0xFE20, 0xFE2F}, // Combining Half Marks
			{0xFE30, 0xFE4F}, // CJK Compatibility Forms
			{0xFE50, 0xFE6F}, // Small Form Variants
			{0xFE70, 0xFEFE}, // Arabic Presentation Forms-B
			{0xFEFF, 0xFEFF}, // Specials
			{0xFF00, 0xFFEF}, // Halfwidth and Fullwidth Forms
			{0xFFF0, 0xFFFD}  // Specials
	};

	// Generated from 500 worst passwords and 370 Banned Twitter lists found <a href="http://www.skullsecurity
	// .org/wiki/index.php/Passwords">here</a>
	private static final String[] BAN_LIST = {"0", "1111", "1212", "1234", "1313", "2000", "2112", "2222", "3333",
			"4128", "4321", "4444", "5150", "5555", "6666", "6969", "7777", "aaaa", "alex", "asdf", "baby", "bear",
			"beer", "bill", "blue", "cock", "cool", "cunt", "dave", "dick", "eric", "fire", "fish", "ford", "fred",
			"fuck", "girl", "golf", "jack", "jake", "john", "king", "love", "mark", "matt", "mike", "mine", "pass",
			"paul", "porn", "rock", "sexy", "shit", "slut", "star", "test", "time", "tits", "wolf", "xxxx", "11111",
			"12345", "angel", "apple", "beach", "billy", "bitch", "black", "boobs", "booty", "brian", "bubba",
			"buddy", "chevy", "chris", "cream", "david", "dirty", "eagle", "enjoy", "enter", "frank", "girls",
			"great", "green", "happy", "hello", "horny", "house", "james", "japan", "jason", "juice", "kelly",
			"kevin", "kitty", "lover", "lucky", "magic", "money", "movie", "music", "naked", "ou812", "paris",
			"penis", "peter", "porno", "power", "pussy", "qwert", "sammy", "scott", "smith", "stars", "steve",
			"super", "teens", "tiger", "video", "viper", "white", "women", "xxxxx", "young", "111111", "112233",
			"121212", "123123", "123456", "131313", "232323", "654321", "666666", "696969", "777777", "987654",
			"aaaaaa", "abc123", "abcdef", "access", "action", "albert", "alexis", "amanda", "andrea", "andrew",
			"angela", "angels", "animal", "apollo", "apples", "arthur", "asdfgh", "ashley", "august", "austin",
			"badboy", "bailey", "banana", "barney", "batman", "beaver", "beavis", "bigdog", "birdie", "biteme",
			"blazer", "blonde", "blowme", "bonnie", "booboo", "booger", "boomer", "boston", "brandy", "braves",
			"brazil", "bronco", "buster", "butter", "calvin", "camaro", "canada", "carlos", "carter", "casper",
			"cheese", "coffee", "compaq", "cookie", "cooper", "cowboy", "dakota", "dallas", "daniel", "debbie",
			"dennis", "diablo", "doctor", "doggie", "donald", "dragon", "dreams", "driver", "eagle1", "eagles",
			"edward", "erotic", "falcon", "fender", "flower", "flyers", "freddy", "fucked", "fucker", "fuckme",
			"gators", "gemini", "george", "giants", "ginger", "golden", "golfer", "gordon", "guitar", "gunner",
			"hammer", "hannah", "harley", "helpme", "hentai", "hockey", "horney", "hotdog", "hunter", "iceman",
			"iwantu", "jackie", "jaguar", "jasper", "jeremy", "johnny", "jordan", "joseph", "joshua", "junior",
			"justin", "killer", "knight", "ladies", "lakers", "lauren", "legend", "little", "london", "lovers",
			"maddog", "maggie", "magnum", "marine", "martin", "marvin", "master", "matrix", "member", "merlin",
			"mickey", "miller", "monica", "monkey", "morgan", "mother", "muffin", "murphy", "nascar", "nathan",
			"nicole", "nipple", "oliver", "orange", "parker", "peanut", "pepper", "player", "please", "pookie",
			"prince", "purple", "qazwsx", "qwerty", "rabbit", "rachel", "racing", "ranger", "redsox", "robert",
			"rocket", "runner", "russia", "samson", "sandra", "saturn", "scooby", "secret", "sexsex", "shadow",
			"shaved", "sierra", "silver", "skippy", "slayer", "smokey", "snoopy", "soccer", "sophie", "spanky",
			"sparky", "spider", "squirt", "steven", "sticky", "stupid", "suckit", "summer", "surfer", "sydney",
			"taylor", "tennis", "teresa", "tester", "theman", "thomas", "tigers", "tigger", "tomcat", "topgun",
			"toyota", "travis", "tucker", "turtle", "united", "vagina", "victor", "viking", "voodoo", "walter",
			"willie", "wilson", "winner", "winter", "wizard", "xavier", "xxxxxx", "yamaha", "yankee", "yellow",
			"zxcvbn", "zzzzzz", "1234567", "7777777", "8675309", "abgrtyu", "amateur", "anthony", "arsenal",
			"asshole", "bigcock", "bigdick", "bigtits", "bitches", "blondes", "blowjob", "bond007", "brandon",
			"broncos", "bulldog", "cameron", "captain", "charles", "charlie", "chelsea", "chester", "chicago",
			"chicken", "college", "cowboys", "crystal", "cumming", "cumshot", "diamond", "dolphin", "extreme",
			"ferrari", "fishing", "florida", "forever", "freedom", "fucking", "fuckyou", "gandalf", "gateway",
			"gregory", "heather", "hooters", "hunting", "jackson", "jasmine", "jessica", "johnson", "leather",
			"letmein", "madison", "matthew", "maxwell", "melissa", "michael", "monster", "mustang", "naughty",
			"ncc1701", "newyork", "nipples", "packers", "panther", "panties", "patrick", "peaches", "phantom",
			"phoenix", "porsche", "private", "pussies", "raiders", "rainbow", "rangers", "rebecca", "richard",
			"rosebud", "scooter", "scorpio", "shannon", "success", "testing", "thunder", "thx1138", "tiffany",
			"trouble", "twitter", "voyager", "warrior", "welcome", "william", "winston", "yankees", "zxcvbnm",
			"11111111", "12345678", "access14", "baseball", "bigdaddy", "butthead", "cocacola", "computer",
			"corvette", "danielle", "dolphins", "einstein", "firebird", "football", "hardcore", "iloveyou",
			"internet", "jennifer", "marlboro", "maverick", "mercedes", "michelle", "midnight", "mistress",
			"mountain", "nicholas", "password", "princess", "qwertyui", "redskins", "redwings", "rush2112",
			"samantha", "scorpion", "srinivas", "startrek", "starwars", "steelers", "sunshine", "superman",
			"swimming", "trustno1", "victoria", "whatever", "xxxxxxxx", "password1", "password12", "password123"};

	private static final int MIN_COMPLEXITY = 49; // 12 chars with Upper, Lower and Number
	private static final int MAX_COMPLEXITY = 120; // 25 chars, all charsets



	private ComplexifyBanMode banMode;
	private ComplexifyExecuteMode executeMode;
	private int strengthScaleFactor;
	private int minimumChars;

	/**
	 * Default constructor.
	 *
	 * Default params are:
	 * banMode - ComplexifyBanMode.STRICT
	 * executeMode - ComplexifyExecuteMode.SYNC
	 * strengthScaleFactor - 1
	 * minimumChars - 8
	 *
	 */
	public Complexify(EditText editText, ComplexifyListener listener) {
		this(editText, listener, ComplexifyBanMode.STRICT, ComplexifyExecuteMode.SYNC, 1, 8);
	}

	/**
	 * Constructor.
	 *
	 *
	 * @param editText EditText for which text complexity will be calculated
	 * @param listener ComplexifyListener which is triggered on every text change, after complexity is calculated
	 * @param banMode Use strict or loose comparisons for banned passwords. (default: ComplexifyBanMode.STRICT)
	 * @param executeMode Defines whether execution is synchronous or asynchronous. (default: ComplexifyExecuteMode.SYNC)
	 * @param strengthScaleFactor Required password strength multiplier (default: 1)
	 * @param minimumChars Minimum password length (default: 8)
	 */
	public Complexify(EditText editText, final ComplexifyListener listener, ComplexifyBanMode banMode, final ComplexifyExecuteMode executeMode, int strengthScaleFactor, int minimumChars) {
		this.banMode = banMode;
		this.executeMode = executeMode;
		this.strengthScaleFactor = strengthScaleFactor;
		this.minimumChars = minimumChars;

		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(executeMode == ComplexifyExecuteMode.SYNC) {
					checkComplexityOfPassword(s.toString(), listener);
				} else {
					checkComplexityOfPasswordAsync(s.toString(), listener);
				}
			}
		});
	}

	/**
	 * Check the complexity of a password.
     *
     * Works synchronously.
	 *
	 * @param password The password to check
	 * @param listener Callback
	 */
	private void checkComplexityOfPassword(String password, ComplexifyListener listener) {
		double complexity = 0;

		if (!isInBanList(password)) {

			// Add character complexity
			for (int i = CHARSETS_ARRAY.length - 1; i >= 0; i--) {
				complexity += additionalComplexityForString(password, CHARSETS_ARRAY[i]);
			}
		} else {
			complexity = 1;
		}

		// Use natural log to produce linear scale
		complexity = Math.log(Math.pow(complexity, password.length())) * (1.0 / strengthScaleFactor);

		boolean valid = (complexity > MIN_COMPLEXITY && password.length() >= minimumChars);

		// Scale to percentage, so it can be used for a progress bar
		complexity = (complexity / MAX_COMPLEXITY) * 100;
		complexity = (complexity > 100) ? 100 : complexity;


		listener.onSuccess(valid, complexity);
	}

    /**
     * Check the complexity of a password.
     *
     * Works asynchronously.
     *
     * @param password The password to check
     * @param listener Callback
     */
    private void checkComplexityOfPasswordAsync(final String password, final ComplexifyListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                checkComplexityOfPassword(password, listener);
            }
        }).start();

    }

	private int additionalComplexityForString(String string, int[] charset) {
		for (int i = string.length() - 1; i >= 0; i--) {
			if (charset[0] <= string.charAt(i) && string.charAt(i) <= charset[1]) {
				return charset[1] - charset[0] + 1;
			}
		}
		return 0;
	}

	private boolean isInBanList(String password) {
		if (banMode == ComplexifyBanMode.STRICT) {
			for (int i = 1; i <= password.length(); i++) {
				String subString = password.substring(0, i);
				if (banListContains(subString)) {
					return true;
				}
			}
			return false;
		} else {
			return banListContains(password);
		}
	}

	private boolean banListContains(String string) {
		for (String s : BAN_LIST) {
			if (s.equals(string)) {
				return true;
			}
		}
		return false;
	}

	public ComplexifyBanMode getBanMode() {
		return banMode;
	}

	public void setBanMode(ComplexifyBanMode banMode) {
		this.banMode = banMode;
	}

	public ComplexifyExecuteMode getExecuteMode() {
		return executeMode;
	}

	public void setExecuteMode(ComplexifyExecuteMode executeMode) {
		this.executeMode = executeMode;
	}

	public int getStrengthScaleFactor() {
		return strengthScaleFactor;
	}

	public void setStrengthScaleFactor(int strengthScaleFactor) {
		this.strengthScaleFactor = strengthScaleFactor;
	}

	public int getMinimumChars() {
		return minimumChars;
	}

	public void setMinimumChars(int minimumChars) {
		this.minimumChars = minimumChars;
	}
}
