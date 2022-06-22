import {DesktopSessionResource, DesktopState} from '@campusonline/desktop';

export interface AppState {

  desktopState: DesktopState;
  session: DesktopSessionResource;
  textBundles?: string[];

}
