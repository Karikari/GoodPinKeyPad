[![](https://jitpack.io/v/Karikari/GoodPinKeyPad.svg)](https://jitpack.io/#Karikari/GoodPinKeyPad)

### Description
#### GoodPinKeyPad is an android Custume View design to provide a User Interface for users to enter their PIN.

| White Style     | Dark Sytle   
| ------------- |:-------------:|
| col 3 is      | right-aligned |
| col 2 is      | centered      |

<img src="https://user-images.githubusercontent.com/6484414/57070437-edc5d300-6cc6-11e9-960f-f1a9efc7e92d.gif" width="400" height="750" />

<img src="https://user-images.githubusercontent.com/6484414/57071996-ad1c8880-6ccb-11e9-92b0-56214022e5a4.gif" width="400" height="750" />

### How
1.  Add it in your root build.gradle at the end of repositories:
```gradle
  allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the Dependency [![](https://jitpack.io/v/Karikari/GoodPinKeyPad.svg)](https://jitpack.io/#Karikari/GoodPinKeyPad)
```gradle
  dependencies {
	   implementation 'com.github.Karikari:GoodPinKeyPad:latest-version'
	}
```
3. Add the View to your layout
```
   <com.karikari.goodpinkeypad.GoodPinKeyPad
        android:id="@+id/key"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="100dp"
        app:keyPadStyle="dark_border" // style availble are "dark, white, white border"
        app:keyPadBackgroundColor="@color/white"
        app:keyPadTextColor="@color/eeirie_black"
        app:keyPadMarginTop="20dp"
        app:pinEntry="four"/> // You can set it to five and six default is four
```
4. Add the Code
```
    GoodPinKeyPad keyPad = findViewById(R.id.key);

    keyPad.setKeyPadListener(new KeyPadListerner() {
            @Override
            public void onKeyPadPressed(String value) {
                Log.d(TAG, "Pin : "+ value);
            }

            @Override
            public void onKeyBackPressed() {
	       //implement your code
            }

            @Override
            public void onClear() {

            }
     });
```
