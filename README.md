# android-complexify

Android port of Dan Palmers's [jquery.complexify.js](https://github.com/danpalmer/jquery.complexify.js/).

## Download

Use Gradle:

```gradle
repositories {
    mavenCentral()
}

dependencies {
  implementation 'com.infinum:complexify:3.0.0'
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

## Contributing

Feedback and code contributions are very much welcome. Just make a pull request with a short description of your changes. By making contributions to this project you give permission for your code to be used under the same [license](LICENSE).  
For easier developing a `sample` application with proper implementations is provided.

## License

```
Copyright 2021 Infinum

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Credits

Maintained and sponsored by [Infinum](http://www.infinum.com).

<a href='https://infinum.co'>
  <img src='https://infinum.co/infinum.png' href='https://infinum.com' width='264'>
</a>

