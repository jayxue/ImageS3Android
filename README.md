# ImageS3Android - Android SDK for ImageS3 service integration

ImageS3Android is an Android SDK that helps integrate ImageS3, an open source service for image resizing and hosting.

![Demo Screenshot 1](https://github.com/jayxue/ImageS3Android/blob/master/ImageS3Android/res/raw/screenshot_1_github.png)
![Demo Screenshot 2](https://github.com/jayxue/ImageS3Android/blob/master/ImageS3Android/res/raw/screenshot_2_github.png)
![Demo Screenshot 3](https://github.com/jayxue/ImageS3Android/blob/master/ImageS3Android/res/raw/screenshot_3_github.png)
![Demo Screenshot 4](https://github.com/jayxue/ImageS3Android/blob/master/ImageS3Android/res/raw/screenshot_4_github.png)
![Demo Screenshot 5](https://github.com/jayxue/ImageS3Android/blob/master/ImageS3Android/res/raw/screenshot_5_github.png)

Check ImageS3Android Demo application on GooglePlay:<br />
<a target="_blank" href="https://play.google.com/store/apps/details?id=com.wms.opensource.images3android.demo">
  <img alt="Get it on Google Play" src="https://github.com/jayxue/ImageS3Android/blob/master/ImageS3Android/res/raw/google_play.png" />
</a>

Details
-------
ImageS3 is an outstanding image resizing and hosting service. Based on Amazon S3 for maximal reliability, it works as a private cloud service specialized in resizing and storing images for software applications. Developers can easily set up ImageS3 service and let it connect to Amazon S3, then enjoy unlimited image uploading, hosting, resizing and retrieving. More details about ImageS3 can be found from http://images3.com/ and https://github.com/images3/images3-play.

ImageS3Android is a SDK for developing Android applications that consume ImageS3 service. It wraps service calls and data structures and makes integration dramatically simple.

The SDK works as an application SDK, meaning that you can include it as a library, change some configurations, and get a fully working Android application without writing any code. Of course, you can integrate components it provides into your application and build richer functionality for end users.

This SDK provides the following features:
* Take pictures and upload to ImageS3 service.
* Pick images from gallery and upload to ImageS3 service.
* Browse images on multiple pages.
* Browse images of different sizes.
* Save images into gallery.
* Set images as wallpaper.
* Delete images.
* Automatically detect image changes in image plant.

All these features are built around the concepts of ImagePlant and Template. Refer to ImageS3's documentation for understanding these concepts.

Administration of image plants and templates can be included in the SDK but it is not necessary to do this on mobile devices. Usually a developer can create image plants and templates from Web admin dashboard on a computer. On mobile device the major work is creating and uploading images and browsing images.

Usage
-----

In order to utilize this library, you just need to do some configurations without writing any code.
* Import all the three projects into workspace: android-support-v7-support, AndroidViewPageIndicator and ImageS3AndroidSDK. You can also get android-support-v7-support from Android SDK. The other one, ImageS3AndroidDemo, is an app showing how to use this library.
* In your application, include ```ImageS3AndroidSDK``` as library.
* In your application's ```AndroidManifest.xml```, make sure that you have the following permissions:
  * ```android.permission.INTERNET```
  * ```android.permission.ACCESS_NETWORK_STATE```
  * ```android.permission.ACCESS_WIFI_STATE```
  * ```android.permission.WRITE_EXTERNAL_STORAGE```
  * ```android.permission.CAMERA```
  * ```android.permission.SET_WALLPAPER```
  * ```com.android.launcher.permission.INSTALL_SHORTCUT```
* In your application's ```AndroidManifest.xml```, include two activities:
  * ```com.wms.opensource.images3android.activity.UploadImageActivity```
  * ```com.wms.opensource.images3android.activity.ImageListFragmentActivity```
* In your applications' ```res/values/strings.xml```,
  * Set ```ImageS3ServiceURL``` which is the URL of your ImageS3 service. Your application won't work without this being properly set.
  * Set ```imagePlantId``` which is the id of the image plant used for your application. Your application won't work without this being properly set.

Acknowledgement
---------------

This library utilizes the following service/libraries/contributions:
* Image S3 developed by stevesun21: https://github.com/images3/images3-play
* Android ViewPageIndicator developed by Patrik Åkerfeldt/Jake Wharton: https://github.com/JakeWharton/ViewPagerIndicator
* Android Query: https://code.google.com/p/android-query/
* Jackson project: https://github.com/FasterXML/jackson
* Many Google libraries

Developer
---------
* Jay Xue <yxue24@gmail.com>, Waterloo Mobile Studio

License
-------

    Copyright 2015 Waterloo Mobile Studio

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
