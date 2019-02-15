# LongScreenshot
This tool makes a number of screenshots, scrolling screen content automatically between each shot

![Alt text](https://raw.githubusercontent.com/PGSSoft/scrollscreenshot/master/illustration.png "Optional Title")

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


##Step 3: 

Stop Screenshot process 

```
longScreenshot.stopScreenshot();
```

##Step 4: 

If all goes fine bitmap will be recieved  

``` 
@Override 
public void getScreenshot(Bitmap bitmap) {} 
```
  
