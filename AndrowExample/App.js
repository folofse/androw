/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import { Button, Platform, StyleSheet, Text, View } from 'react-native';

import Androw from 'react-native-androw';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu (Cmd/Ctrl+M) button for dev menu',
});

export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = { toggled: false };
  }
  onPress() {
    console.log('onPress');
    this.setState({ toggled: !this.state.toggled });
  }
  render() {
    const shadowStyle = this.state.toggled ? styles.shadow : styles.noShadow;
    console.log(shadowStyle);
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Welcome to React Native!</Text>
        <Text style={styles.instructions}>
          To get started, edit App.js 1234
        </Text>
        <Text style={styles.instructions}>{instructions}</Text>

        <Androw style={shadowStyle}>
          <View
            style={{
              width: 50,
              height: 50,
              borderRadius: 999,
              marginTop: 10,
              backgroundColor: '#00ff00',
            }}
          />
          <View
            style={{
              width: 200,
              height: 50,
              borderRadius: 999,
              marginTop: 10,
              backgroundColor: '#00ff00',
            }}
          />
          <Text style={{ color: 'black', fontSize: 18, marginTop: 10 }}>
            A text view with shadow
          </Text>
        </Androw>

        <View style={{ paddingTop: 50 }}>
          <Button onPress={this.onPress.bind(this)} title="Toggle shadow" />
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  noShadow: {
    justifyContent: 'center',
    alignItems: 'flex-start',
  },
  shadow: {
    justifyContent: 'center',
    alignItems: 'flex-start',
    shadowColor: '#000',
    shadowOpacity: 0.5,
    shadowRadius: 10,
    shadowOffset: {
      width: 0,
      height: 0,
    },
  },
});
