# MyTracks
*A respawn of Google MyTracks*

**This fork has been updated to compile in 
Android Studio 3.1.2, Build Tools version 27.**

**MyTracks** is a GPS tracking application, that was [open-sourced by Google](http://google-latlong.blogspot.fr/2010/05/code-for-my-tracks-is-now-yours.html) on May 28, 2010. It is licensed under the terms of [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0). 

On 2014, Google [announced](https://code.google.com/archive/p/mytracks/) updates will no longer be published as open-source, and source will be removed from Google Code on January 01, 2015.

On January 29, 2016, Google [announced](https://support.google.com/maps/answer/6333516) **MyTracks** will no longer be available after April 30, 2016. The application has been removed from Google Play.

The last open source version available is **2.0.6**, released in February 2014. This fork is based on this version.

-------

Releases since **2.0.5** :

| Version | Release date      | Min SDK | Changelog                                                         |
|---------|-------------------|---------|-------------------------------------------------------------------|
| 2.0.11  | January 28, 2016  |      14 |                                                                   |
| 2.0.10  | April 29, 2015    |      14 | A new map layer, Earth, for viewing tracks in 3D                  |
| 2.0.9   | December 11, 2014 |       9 | Sync to Google Fit                                                |
| 2.0.8   | October 16, 2014  |       9 | Support Android Wear. Remove sharing with other apps on the phone |
| 2.0.7   | June 18, 2014     |       9 | Export to Google Maps Engine. Remove export to Google Fusion Tables and Google Maps  |
| 2.0.6   | Jan 30, 2014      |       9 | Insert photo markers. Calculate calories burned Play multiple tracks in Google Earth |

----

More information about **Google MyTracks**:
- [Wikipedia page](https://en.wikipedia.org/wiki/MyTracks)
- [Google code archive](https://code.google.com/archive/p/mytracks/)
- [Release history on AppBrain](http://www.appbrain.com/app/my-tracks/com.google.android.maps.mytracks)

#How to build
Two Google API keys are needed to build this project:
- Google Map API key
- Google Backup API key

Those keys can be obtained using the [Google API console](https://console.developers.google.com/apis/).

As keys are not meant to be shared, they are located in their own value file: `/myTracks/src/main/res/values/apikeys.xml`, which is *git-ignored*. This means you have to **create it in your local project** to be able to build. Here is how it should look:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
  <string name="backup_api_key">xxxxxx</string>
  <string name="maps_api_key">yyyyyy</string>
</resources>
```

Once you have added this file, the project should be able to find and use your keys.

Note: you can use any other file if you prefer than `apikeys.xml`, but in that case be careful to not commit it.
