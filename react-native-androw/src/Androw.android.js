// @flow
import * as React from 'react';

import AndrowView from './Androw-native';

class ShadowView extends React.PureComponent {
	render() {
		return <AndrowView {...this.props} />;
	}
}
export default ShadowView;