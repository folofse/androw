// @flow
import { requireNativeComponent, View } from 'react-native';

const componentInterface = {
	name: 'RNAndrow',
	propTypes: {
		...View.propTypes, // include the default view properties
	},
};

export default requireNativeComponent('RNAndrow', componentInterface);