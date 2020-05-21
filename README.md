# react-native-ibb-mobile-services

## Getting started

`$ npm install react-native-ibb-mobile-services --save`

### Mostly automatic installation

`$ react-native link react-native-ibb-mobile-services`

## Usage
```javascript
import IbbMobileServices from 'react-native-ibb-mobile-services';

// TODO: What to do with the module?
IbbMobileServices;
```
function Component için
Class Component için ise componentDidMount() içinde çağrılması gerekmektedir.
## Configure
```
    useEffect(() => {

        const package_name = 'com.grnt.ibbstoredemo';
        IbbMobileServices.configure(package_name);

  }, []);
```
Başlangıç için App.js veya ilk çağrılan dosyanın içinde ilk eylem olarak IbbMobileServices.configure() methodunu çağırarak başlayın. !!!Önemli @package_name@ değeri değişecektir.

Android içinde AndroidManifest.xml içinde gerekli içinler için
## Eklemeler
```
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
```
eklemeleri yapmayı unutmayın!!!
