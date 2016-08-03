package co.infinum.complexify_android;

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
    private String[] banList = {"000000", "111111", "11111111", "112233", "121212", "123123", "123456", "1234567", "12345678", "123456789", "131313", "232323", "654321", "666666", "696969", "777777", "7777777", "8675309", "987654", "nnnnnn", "nop123", "nopqrs", "noteglh", "npprff", "npprff14", "npgvba", "nyoreg", "nyoregb", "nyrkvf", "nyrwnaqen", "nyrwnaqeb", "nznaqn", "nzngrhe", "nzrevpn", "naqern", "naqerj", "natryn", "natryf", "navzny", "nagubal", "ncbyyb", "nccyrf", "nefrany", "neguhe", "nfqstu", "nfuyrl", "nffubyr", "nhthfg", "nhfgva", "onqobl", "onvyrl", "onanan", "onearl", "onfronyy", "ongzna", "orngevm", "ornire", "ornivf", "ovtpbpx", "ovtqnqql", "ovtqvpx", "ovtqbt", "ovtgvgf", "oveqvr", "ovgpurf", "ovgrzr", "oynmre", "oybaqr", "oybaqrf", "oybjwbo", "oybjzr", "obaq007", "obavgn", "obaavr", "obbobb", "obbtre", "obbzre", "obfgba", "oenaqba", "oenaql", "oenirf", "oenmvy", "oebapb", "oebapbf", "ohyyqbt", "ohfgre", "ohggre", "ohggurnq", "pnyiva", "pnzneb", "pnzreba", "pnanqn", "pncgnva", "pneybf", "pnegre", "pnfcre", "puneyrf", "puneyvr", "purrfr", "puryfrn", "purfgre", "puvpntb", "puvpxra", "pbpnpbyn", "pbssrr", "pbyyrtr", "pbzcnd", "pbzchgre", "pbafhzre", "pbbxvr", "pbbcre", "pbeirggr", "pbjobl", "pbjoblf", "pelfgny", "phzzvat", "phzfubg", "qnxbgn", "qnyynf", "qnavry", "qnavryyr", "qroovr", "qraavf", "qvnoyb", "qvnzbaq", "qbpgbe", "qbttvr", "qbycuva", "qbycuvaf", "qbanyq", "qentba", "qernzf", "qevire", "rntyr1", "rntyrf", "rqjneq", "rvafgrva", "rebgvp", "rfgeryyn", "rkgerzr", "snypba", "sraqre", "sreenev", "sveroveq", "svfuvat", "sybevqn", "sybjre", "sylref", "sbbgonyy", "sberire", "serqql", "serrqbz", "shpxrq", "shpxre", "shpxvat", "shpxzr", "shpxlbh", "tnaqnys", "tngrjnl", "tngbef", "trzvav", "trbetr", "tvnagf", "tvatre", "tvmzbqb", "tbyqra", "tbysre", "tbeqba", "tertbel", "thvgne", "thaare", "unzzre", "unaanu", "uneqpber", "uneyrl", "urngure", "uryczr", "uragnv", "ubpxrl", "ubbgref", "ubearl", "ubgqbt", "uhagre", "uhagvat", "vprzna", "vybirlbh", "vagrearg", "vjnagh", "wnpxvr", "wnpxfba", "wnthne", "wnfzvar", "wnfcre", "wraavsre", "wrerzl", "wrffvpn", "wbuaal", "wbuafba", "wbeqna", "wbfrcu", "wbfuhn", "whavbe", "whfgva", "xvyyre", "xavtug", "ynqvrf", "ynxref", "ynhera", "yrngure", "yrtraq", "yrgzrva", "yvggyr", "ybaqba", "ybiref", "znqqbt", "znqvfba", "znttvr", "zntahz", "znevar", "znevcbfn", "zneyobeb", "znegva", "zneiva", "znfgre", "zngevk", "znggurj", "znirevpx", "znkjryy", "zryvffn", "zrzore", "zreprqrf", "zreyva", "zvpunry", "zvpuryyr", "zvpxrl", "zvqavtug", "zvyyre", "zvfgerff", "zbavpn", "zbaxrl", "zbafgre", "zbetna", "zbgure", "zbhagnva", "zhssva", "zhecul", "zhfgnat", "anxrq", "anfpne", "anguna", "anhtugl", "app1701", "arjlbex", "avpubynf", "avpbyr", "avccyr", "avccyrf", "byvire", "benatr", "cnpxref", "cnagure", "cnagvrf", "cnexre", "cnffjbeq", "cnffjbeq1", "cnffjbeq12", "cnffjbeq123", "cngevpx", "crnpurf", "crnahg", "crccre", "cunagbz", "cubravk", "cynlre", "cyrnfr", "cbbxvr", "cbefpur", "cevapr", "cevaprff", "cevingr", "checyr", "chffvrf", "dnmjfk", "djregl", "djreglhv", "enoovg", "enpury", "enpvat", "envqref", "envaobj", "enatre", "enatref", "erorppn", "erqfxvaf", "erqfbk", "erqjvatf", "evpuneq", "eboreg", "eboregb", "ebpxrg", "ebfrohq", "ehaare", "ehfu2112", "ehffvn", "fnznagun", "fnzzl", "fnzfba", "fnaqen", "fnghea", "fpbbol", "fpbbgre", "fpbecvb", "fpbecvba", "fronfgvna", "frperg", "frkfrk", "funqbj", "funaaba", "funirq", "fvreen", "fvyire", "fxvccl", "fynlre", "fzbxrl", "fabbcl", "fbppre", "fbcuvr", "fcnaxl", "fcnexl", "fcvqre", "fdhveg", "fevavinf", "fgnegerx", "fgnejnef", "fgrryref", "fgrira", "fgvpxl", "fghcvq", "fhpprff", "fhpxvg", "fhzzre", "fhafuvar", "fhcrezna", "fhesre", "fjvzzvat", "flqarl", "grdhvreb", "gnlybe", "graavf", "grerfn", "grfgre", "grfgvat", "gurzna", "gubznf", "guhaqre", "guk1138", "gvssnal", "gvtref", "gvttre", "gbzpng", "gbctha", "gblbgn", "genivf", "gebhoyr", "gehfgab1", "ghpxre", "ghegyr", "gjvggre", "havgrq", "intvan", "ivpgbe", "ivpgbevn", "ivxvat", "ibbqbb", "iblntre", "jnygre", "jneevbe", "jrypbzr", "jungrire", "jvyyvnz", "jvyyvr", "jvyfba", "jvaare", "jvafgba", "jvagre", "jvmneq", "knivre", "kkkkkk", "kkkkkkkk", "lnznun", "lnaxrr", "lnaxrrf", "lryybj", "mkpioa", "mkpioaz", "mmmmmm", "password", "1234", "pussy", "12345", "dragon", "qwerty", "mustang", "letmein", "baseball", "master", "michael", "football", "shadow", "monkey", "abc123", "pass", "fuckme", "6969", "jordan", "harley", "ranger", "iwantu", "jennifer", "hunter", "fuck", "2000", "test", "batman", "trustno1", "thomas", "tigger", "robert", "access", "love", "buster", "soccer", "hockey", "killer", "george", "sexy", "andrew", "charlie", "superman", "asshole", "fuckyou", "dallas", "jessica", "panties", "pepper", "1111", "austin", "william", "daniel", "golfer", "summer", "heather", "hammer", "yankees", "joshua", "maggie", "biteme", "enter", "ashley", "thunder", "cowboy", "silver", "richard", "fucker", "orange", "merlin", "michelle", "corvette", "bigdog", "cheese", "matthew", "patrick", "martin", "freedom", "ginger", "blowjob", "nicole", "sparky", "yellow", "camaro", "secret", "dick", "falcon", "taylor", "bitch", "hello", "scooter", "please", "porsche", "guitar", "chelsea", "black", "diamond", "nascar", "jackson", "cameron", "computer", "amanda", "wizard", "xxxxxxxx", "money", "phoenix", "mickey", "bailey", "knight", "iceman", "tigers", "purple", "andrea", "horny", "dakota", "aaaaaa", "player", "sunshine", "morgan", "starwars", "boomer", "cowboys", "edward", "charles", "girls", "booboo", "coffee", "xxxxxx", "bulldog", "ncc1701", "rabbit", "peanut", "john", "johnny", "gandalf", "spanky", "winter", "brandy", "compaq", "carlos", "tennis", "james", "mike", "brandon", "fender", "anthony", "blowme", "ferrari", "cookie", "chicken", "maverick", "chicago", "joseph", "diablo", "sexsex", "hardcore", "willie", "welcome", "chris", "panther", "yamaha", "justin", "banana", "driver", "marine", "angels", "fishing", "david", "maddog", "hooters", "wilson", "butthead", "dennis", "fucking", "captain", "bigdick", "chester", "smokey", "xavier", "steven", "viking", "snoopy", "blue", "eagles", "winner", "samantha", "house", "miller", "flower", "jack", "firebird", "butter", "united", "turtle", "steelers", "tiffany", "zxcvbn", "tomcat", "golf", "bond007", "bear", "tiger", "doctor", "gateway", "gators", "angel", "junior", "thx1138", "porno", "badboy", "debbie", "spider", "melissa", "booger", "1212", "flyers", "fish", "porn", "matrix", "teens", "scooby", "jason", "walter", "cumshot", "boston", "braves", "yankee", "lover", "barney", "victor", "tucker", "princess", "mercedes", "5150", "doggie", "zzzzzz", "gunner", "horney", "bubba", "2112", "fred", "johnson", "xxxxx", "tits", "member", "boobs", "donald", "bigdaddy", "bronco", "penis", "voyager", "rangers", "birdie", "trouble", "white", "topgun", "bigtits", "bitches", "green", "super", "qazwsx", "magic", "lakers", "rachel", "slayer", "scott", "2222", "asdf", "video", "london", "7777", "marlboro", "srinivas", "internet", "action", "carter", "jasper", "monster", "teresa", "jeremy", "bill", "crystal", "peter", "pussies", "cock", "beer", "rocket", "theman", "oliver", "prince", "beach", "amateur", "muffin", "redsox", "star", "testing", "shannon", "murphy", "frank", "hannah", "dave", "eagle1", "11111", "mother", "nathan", "raiders", "steve", "forever", "angela", "viper", "ou812", "jake", "lovers", "suckit", "gregory", "buddy", "whatever", "young", "nicholas", "lucky", "helpme", "jackie", "monica", "midnight", "college", "baby", "cunt", "brian", "mark", "startrek", "sierra", "leather", "4444", "beavis", "bigcock", "happy", "sophie", "ladies", "naughty", "giants", "booty", "blonde", "fucked", "golden", "fire", "sandra", "pookie", "packers", "einstein", "dolphins", "chevy", "winston", "warrior", "sammy", "slut", "zxcvbnm", "nipples", "power", "victoria", "asdfgh", "vagina", "toyota", "travis", "hotdog", "paris", "rock", "xxxx", "extreme", "redskins", "erotic", "dirty", "ford", "freddy", "arsenal", "access14", "wolf", "nipple", "iloveyou", "alex", "florida", "eric", "legend", "movie", "success", "rosebud", "jaguar", "great", "cool", "cooper", "1313", "scorpio", "mountain", "madison", "brazil", "lauren", "japan", "naked", "squirt", "stars", "apple", "alexis", "aaaa", "bonnie", "peaches", "jasmine", "kevin", "matt", "qwertyui", "danielle", "beaver", "4321", "4128", "runner", "swimming", "dolphin", "gordon", "casper", "stupid", "shit", "saturn", "gemini", "apples", "august", "3333", "canada", "blazer", "cumming", "hunting", "kitty", "rainbow", "arthur", "cream", "calvin", "shaved", "surfer", "samson", "kelly", "paul", "mine", "king", "racing", "5555", "eagle", "hentai", "newyork", "little", "redwings", "smith", "sticky", "cocacola", "animal", "broncos", "private", "skippy", "marvin", "blondes", "enjoy", "girl", "apollo", "parker", "qwert", "time", "sydney", "women", "voodoo", "magnum", "juice", "abgrtyu", "dreams", "maxwell", "music", "rush2112", "russia", "scorpion", "rebecca", "tester", "mistress", "phantom", "billy", "6666", "albert", "abcdef", "password1", "password12", "password123", "twitter"};

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
     */
    public Complexify(EditText editText, ComplexifyListener listener) {
        this(editText, listener, ComplexifyBanMode.STRICT, ComplexifyExecuteMode.SYNC, 1, 8);
    }

    /**
     * Constructor.
     *
     * @param editText            EditText for which text complexity will be calculated
     * @param listener            ComplexifyListener which is triggered on every text change, after complexity is calculated
     * @param banMode             Use strict or loose comparisons for banned passwords. (default: ComplexifyBanMode.STRICT)
     * @param executeMode         Defines whether execution is synchronous or asynchronous. (default: ComplexifyExecuteMode.SYNC)
     * @param strengthScaleFactor Required password strength multiplier (default: 1)
     * @param minimumChars        Minimum password length (default: 8)
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
                if (executeMode == ComplexifyExecuteMode.SYNC) {
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
        String passwordLowerCase = password.toLowerCase();
        if (banMode == ComplexifyBanMode.STRICT) {
            for (String s : banList) {
                if (passwordLowerCase.contains(s)) { //banList elements already in lower case
                    return true;
                }
            }
            return false;
        } else {
            for (String s : banList) {
                if (s.equals(passwordLowerCase)) { //banList elements already in lower case
                    return true;
                }
            }
            return false;
        }
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

    public String[] getBanList() {
        return banList;
    }

    public void setBanList(String[] banList) {
        this.banList = banList;
    }
}
