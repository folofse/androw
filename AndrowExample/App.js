/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component, Fragment } from 'react';
import {
	Animated,
	Easing,
	SafeAreaView,
	StyleSheet,
	ScrollView,
	View,
	Text,
	StatusBar,
} from 'react-native';

import {
	Header,
	LearnMoreLinks,
	Colors,
	DebugInstructions,
	ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import Androw from 'react-native-androw';

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
			<View>
				<StatusBar barStyle="dark-content" />
				<SafeAreaView>
					
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
								backgroundColor: this.state.toggled ? '#00ff00' : '#0000ff',
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
							A text view with shadow {this.state.toggled ? '#00ff00' : '#0000ff'}
						</Text>
					</AnimatedAndrow>
				</SafeAreaView>
			</View>
		);
	};
}

const styles = StyleSheet.create({
	scrollView: {
		backgroundColor: Colors.lighter,
	},
	engine: {
		position: 'absolute',
		right: 0,
	},
	body: {
		backgroundColor: Colors.white,
	},
	sectionContainer: {
		marginTop: 32,
		paddingHorizontal: 24,
	},
	sectionTitle: {
		fontSize: 24,
		fontWeight: '600',
		color: Colors.black,
	},
	sectionDescription: {
		marginTop: 8,
		fontSize: 18,
		fontWeight: '400',
		color: Colors.dark,
	},
	highlight: {
		fontWeight: '700',
	},
	footer: {
		color: Colors.dark,
		fontSize: 12,
		fontWeight: '600',
		padding: 4,
		paddingRight: 12,
		textAlign: 'right',
	},
	androw: {
		width:'100%',
		height: '100%',
		justifyContent: 'center',
		alignItems: 'center',
	},
});