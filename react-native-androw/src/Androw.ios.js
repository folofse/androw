// @flow
import * as React from 'react';
import { View } from 'react-native';

class ShadowView extends React.PureComponent {
	render() {
		return <View {...this.props} />;
	}
}

export default ShadowView;
