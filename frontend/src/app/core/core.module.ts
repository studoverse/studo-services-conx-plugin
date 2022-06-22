import {NgModule} from '@angular/core';
import {TranslateModule} from '@ngx-translate/core';
import {HttpClientModule} from '@angular/common/http';
import {AppStateService} from './state/app-state.service';
import {
  DESKTOP_API_ENVIRONMENT,
  DesktopMockUriService,
  DesktopModule,
  DesktopServiceModule,
  DesktopUriService
} from '@campusonline/desktop';
import {AppUriService} from '@shared/uri/app-uri.service';
import {environment} from '@env/environment';
import {AppMockUriService} from '@shared/uri/mock/app-mock-uri.service';
import {AppEnvironment} from '@env/app-enivronment';

/**
 * The core module is only imported by the app component.
 * It is responsible for setting up app wide providers
 * (like auth and desktop services)
 * and app wide logic (like state).
 */
@NgModule({
  imports: [
    HttpClientModule,
    TranslateModule.forRoot(),
    DesktopServiceModule
  ],
  declarations: [
  ],
  providers: [

    // environment
    { provide: AppEnvironment, useValue: environment },

    // global app services
    {
      provide: AppUriService,
      useClass: environment?.useMocks ? AppMockUriService : AppUriService
    },

    // desktop api
    { provide: DESKTOP_API_ENVIRONMENT, useValue: environment },

    {
      provide: DesktopUriService,
      useClass: environment?.useMocks ? DesktopMockUriService : DesktopUriService
    },

    AppStateService
  ],
  exports: [
    TranslateModule,
    DesktopModule,
    DesktopServiceModule
  ]
})
export class CoreModule {

}
