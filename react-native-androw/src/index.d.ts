declare module 'react-native-androw' {
  import {FC} from 'react';
  import {ViewProps} from 'react-native';

  export interface AndrowProps extends ViewProps {}
  const Androw: FC<AndrowProps>;
  export default Androw;
}
