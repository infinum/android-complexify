# android-complexify

Android port of Dan Palmers's [jquery.complexify.js](https://github.com/danpalmer/jquery.complexify.js/).

## Download

Use Gradle:

```gradle
repositories {
  jcenter()
}

dependencies {
  compile 'co.infinum:complexify-android:2.0.1'
}
```

## Usage

``` java

EditText editText = (EditText) findViewById(R.id.editText); // your editText where you want to check password complexity as user writes

Complexify complexify = new Complexify(editText, new ComplexifyListener() {
    @Override
    public void onSuccess(boolean isValid, double complexity) {
        // isValid is true if password is valid, false otherwise
        // complexity is number form range [0.0, 100.0] where greater number represents greater complexity
    }
});

```

## Configuration

You can override the default configuration by using setters:

``` java

setBanMode(ComplexifyBanMode banMode) // use strict (don't allow substrings of banned passwords) or loose (only ban exact matches) comparisons for banned passwords. (default: ComplexifyBanMode.STRICT)

setExecuteMode(ComplexifyExecuteMode executeMode) // defines whether execution is synchronous or asynchronous. (default: ComplexifyExecuteMode.SYNC)

setStrengthScaleFactor(int strengthScaleFactor) // scale the required password strength (higher numbers require a more complex password) (default: 1)

setMinimumChars(int minimumChars) // the minimum acceptable password length (default: 8)

setBanList(String[] banList) // array of banned passwords (default: Generated from 500 worst passwords and 370 Banned Twitter lists found <a href="http://www.skullsecurity.org/wiki/index.php/Passwords">here</a>)

```

## License

This code is distributed under the WTFPL v2 licence.
