/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Button,
  Platform,
  StyleSheet,
  Text,
  View,
  Animated,
  Easing,
} from 'react-native';

import Androw from 'react-native-androw';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu (Cmd/Ctrl+M) button for dev menu',
});

const AnimatedAndrow = Animated.createAnimatedComponent(Androw);

export default class App extends Component {
  state = { toggled: false, anim: new Animated.Value(0) };

  startAnimation() {
    Animated.loop(
      Animated.timing(this.state.anim, {
        easing: Easing.linear,
        duration: 3000,
        toValue: 1,
      }),
    ).start();
  }

  componentDidMount() {
    this.startAnimation();
  }

  onPress() {
    this.setState({ toggled: !this.state.toggled });
  }

  radius = 5;
  offsetX = 3;
  offsetY = 3;
  samples = 30;
  phase = Math.PI / 4;
  input = Array.from({ length: this.samples }).map(
    (_, i) => i / (this.samples - 1),
  );
  x = this.input.map(
    x => Math.cos(x * 2 * Math.PI + this.phase) * this.radius + this.offsetX,
  );
  y = this.input.map(
    x => Math.sin(x * 2 * Math.PI + this.phase) * this.radius + this.offsetY,
  );

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Welcome to React Native!</Text>
        <Text style={styles.instructions}>
          To get started, edit App.js 1234
        </Text>
        <Text style={styles.instructions}>{instructions}</Text>

        <AnimatedAndrow
          style={
            this.state.toggled
              ? styles.androw
              : [
                  styles.androw,
                  {
                    shadowColor: this.state.anim.interpolate({
                      inputRange: Array.from({ length: 7 }).map(
                        (_, i) => i / 6,
                      ),
                      outputRange: [
                        '#f00',
                        '#ff0',
                        '#0f0',
                        '#0ff',
                        '#00f',
                        '#f0f',
                        '#f00',
                      ],
                    }),
                    shadowOpacity: this.state.anim.interpolate({
                      inputRange: [0, 0.5, 1],
                      outputRange: [0.5, 1, 0.5],
                    }),
                    shadowRadius: 10,
                    shadowOffset: {
                      width: this.state.anim.interpolate({
                        inputRange: this.input,
                        outputRange: this.x,
                      }),
                      height: this.state.anim.interpolate({
                        inputRange: this.input,
                        outputRange: this.y,
                      }),
                    },
                  },
                ]
          }
        >
          <View
            style={{
              width: 50,
              height: 50,
              borderRadius: 999,
              marginTop: 10,
              backgroundColor: this.state.toggled ? '#00ff00':'#0000ff',
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
            A text view with shadow {this.state.toggled ? '#00ff00':'#0000ff'}
          </Text>
        </AnimatedAndrow>

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
  androw: {
    justifyContent: 'center',
    alignItems: 'flex-start',
  },
});
