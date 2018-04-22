# **FocusWork**

![](https://d2mxuefqeaa7sj.cloudfront.net/s_D166E634E7B7A9E609B9426AC107EE32E4BB78FEBA1CBBB666B0041B0EAB0451_1524411209335_bullseye+2.png)  It is a simple Library which will help to create a simple focus work among EditText.



## Features:
- Can create a Otp view
- Can create a Pin Entry view
- You can manage your own design and inputType

### Installation:

Add it in your root build.gradle at the end of repositories:

```javascript
            allprojects {
                    repositories {
                            ...
                            maven { url 'https://jitpack.io' }
                    }
            }
```
 Add the dependency
```javascript
             dependencies {
                    compile 'com.github.Collabalist:FocusWork_:0.1'
            }
```

### Usage:
```java
    FocusWork.with(MainActivity.this)
            .createOTPfocusFor(otp1,
                   otp2,
                   otp3,
                   otp4,
                   otp5);
```

### To-Do:
- [ ] Make focus for other kind of EditText
- [ ] Make focus work with other kind of views also
