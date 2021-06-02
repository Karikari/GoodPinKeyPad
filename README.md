[![](https://jitpack.io/v/Karikari/GoodPinKeyPad.svg)](https://jitpack.io/#Karikari/GoodPinKeyPad)

### Description
#### GoodPinKeyPad is an android Custume View design to provide a User Interface for users to enter their PIN.

| White Style     | Dark Sytle   
| ------------- |:-------------:|
| <img src="https://user-images.githubusercontent.com/6484414/60676516-9a126a00-9e6e-11e9-92cb-f62dc66c04ad.gif" width="400" height="750" />     | <img src="https://user-images.githubusercontent.com/6484414/60676515-9a126a00-9e6e-11e9-9f4c-520a21631a81.gif" width="400" height="750" /> |

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
```xml
   <com.karikari.goodpinkeypad.GoodPinKeyPad
        android:id="@+id/key"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="100dp"
        app:keyPadStyle="dark_border" // style availble are "dark, white, white_border and dark_border"
        app:backgroundColor="@color/white"
        app:textColor="@color/eeirie_black"
        app:marginTop="20dp"
        app:pinEntry="four"/> // You can set it to five and six default is four
```
4. Add the Code
```java
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
