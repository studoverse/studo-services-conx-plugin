import { AuthSessionResource } from '@campusonline/auth';
import { DesktopState } from '@campusonline/desktop';

export interface AppState {

  desktopState: DesktopState;
  session: AuthSessionResource;
  textBundles?: string[];

}
