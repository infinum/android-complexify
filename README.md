![Maven Central](https://img.shields.io/maven-central/v/com.infinum.complexify/complexify-core) ![Maven Central](https://img.shields.io/maven-central/v/com.infinum.complexify/complexify-android)

# Complexify

A library which makes checking the quality of user's password a breeze.

Port of Dan Palmers's [jquery.complexify.js](https://github.com/danpalmer/jquery.complexify.js/).

Complexify's default settings will enforce a minimum level of complexity that would mean brute-forcing should take ~600 years on a commodity desktop machine. The 'perfect' password used to scale the complexity percentage would take 3x10^33 years. These are equivalent to a 12 character password with uppercase, lowercase and numbers included, and a 25 character password with uppercase, lowercase, numbers and a wide range of punctuation.

## Getting started

To include _Complexify_ in your project, you have to add buildscript dependencies in your project level `build.gradle` or `build.gradle.kts`:

**Groovy**
```groovy
buildscript {
    repositories {
        mavenCentral()
    }
}
```
**KotlinDSL**
```kotlin
buildscript {
    repositories {
        mavenCentral()
    }
}
```

Then add one of the following dependencies in your app `build.gradle` or `build.gradle.kts` :

**Groovy**
```groovy
implementation "com.infinum:complexify-core:3.0.0"
implementation "com.infinum:complexify-android:3.0.0" // can be used as a standalone dependency
```
**KotlinDSL**
```kotlin
implementation("com.infinum:complexify-core:3.0.0")
implementation("com.infinum:complexify-android:3.0.0") // can be used as a standalone dependency
```

Note that the Android module is optional; it contains helper functions for easier usage in Android projects. If you add it as the dependency,
complexify-core is _not_ required.

## Requirements

The library is written entirely in Kotlin. 
The core library module (complexify-core) doesn't have any requirements. 
Minimum API for the complexify-android module is 21.

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

