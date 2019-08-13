# react-native-androw

## NOTE

This version is still under development and in beta.


## Motivation

The problem is that a shadows does not work with React Native in Android. This view takes its children's, 
creates a bitmap representation, blur it and color it to styles shadow values like in iOS

## Whats new
Verison 0.0.33
* Fixed pixel density on Android devices to better match iOS devices shadows.

Verison 0.0.32
* Fixed crash bug with multiple androw views, increased the performace when fading in multiple images at the same time.

Verison 0.0.31
* Added support for React Native 0.60.x and Android X

Verison 0.0.30
* Fixed shadow render problem with multiple Androw views and multiple image elements.

## Limitations

* Android has a bitmap limitation of 2048x2048, but this might depend on API version.
* This plugin uses Software layer and might experience performance issues.


## Getting started

`$ npm install react-native-androw --save`

### Mostly automatic installation

`$ react-native link react-native-androw`

## Usage
```javascript
import Androw from 'react-native-androw';

<Androw style={styles.shadow}>
  {//Act as a regular view but with shadow support in Android}
</Androw>

const styles = Stylesheet.create({
  shadow:{
      shadowColor: '#000000',
      shadowOffset:{
		width: 5, 
        height: 5,
      },
      shadowOpacity:.5,
      shadowRadius: .3
    }
});
```

## Usage with Flatlist
To make this work with FlatList and related components you need to replace `CellRendererComponent` with `Androw`, for example:

```jsx
<FlatList
  data={this.state.data}
  keyExtractor={item => item.id}
  CellRendererComponent={Androw}
  renderItem={({ item, index }) => (
      <Androw style={styles.item}>
      // item....
      </Androw>
   )}
/>
```

## Usage with Animated Views
To make this work in place of an `Animated.View`, you need to use `Animated.createAnimatedComponent` to create an animatable version of `Androw`. For example:

```
import Androw from 'react-native-androw';
const AnimatedAndrow = Animated.createAnimatedComponent(Androw);
```

You can then use `AnimatedAndrow` in place of `Animated.View`.

### Manual installation

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import se.folof.RNAndrowPackage;` to the imports at the top of the file
  - Add `new RNAndrowPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-androw'
  	project(':react-native-androw').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-androw/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      implementation project(':react-native-androw')
  	```
