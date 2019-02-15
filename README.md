# LongScreenshot
This tool makes a number of screenshots, scrolling screen content automatically between each shot

![Alt text](https://raw.githubusercontent.com/PGSSoft/scrollscreenshot/master/illustration.png "Optional Title")


### Prerequisites

Add this in your root build.gradle file (not your module build.gradle file):

```
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

### Dependency

Add this to your module's build.gradle file (make sure the version matches the JitPack badge above):

```
dependencies {
	...
    implementation 'com.github.peter1492:LongScreenshot:1.0'
}
```

### How to use
## Step 1: 

#### Implement Inteface 
```
BigScreenshot.ProcessScreenshot
```

## Step 2: 
Create Class object

*x will be (Recycleview/ NestedScrollview / Scrollview ..)*

*y will be (parent ViewGroup Container (LinearLayout/FrameLayout etc..))*

```
BigScreenshot longScreenshot = new BigScreenshot(this, x, y);
longScreenshot.startScreenshot();
```


## Step 3: 

Stop Screenshot process 

```
longScreenshot.stopScreenshot();
```

## Step 4: 

If all goes fine bitmap will be recieved  

``` 
@Override 
public void getScreenshot(Bitmap bitmap) {} 
```
  
