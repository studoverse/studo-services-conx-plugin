import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {AppStateService} from './core/state/app-state.service';
import {NavigationMenuResource} from '@campusonline/model';
import {Router} from '@angular/router';
import {
  DesktopDebugService,
  DesktopLoginService,
  DesktopMode,
  DesktopPageResource,
  DesktopResource,
  DesktopSessionResource
} from '@campusonline/desktop';
import {switchMap, take} from 'rxjs/operators';
import {apiUri} from "@shared/uri/api-uri";

/**
 * This is our supreme component in our app.
 * It subscribes the app state and propagate
 * the state to the child components.
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  menu?: NavigationMenuResource;
  desktop?: DesktopResource;
  session?: DesktopSessionResource;
  page?: DesktopPageResource;
  mode?: DesktopMode;
  loadSidebar?: boolean = false; // lazy load sidebar improves performance

  subs: Subscription[] = [];

  constructor(
    private stateService: AppStateService,
    private router: Router,
    public debugService: DesktopDebugService,
    private loginService: DesktopLoginService,
    private cd: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.subscribeInitialState();
    this.subscribeLanguage();
  }

  /**
   * Initialize the state and watch than subscribe to the state.
   */
  private subscribeInitialState(): void {
    this.subs.push(
      this.stateService.getInitialState$().pipe(take(1)).subscribe(state => {
        this.subscribeState();
        this.stateService.nextState(state);
      }));
  }

  /**
   * Subscribe to language changes and load translations if necessary.
   */
  private subscribeLanguage(): void {
    this.subs.push(
      this.stateService.getLanguage$()
        .pipe(switchMap(lang => this.stateService.updateTranslations(lang)))
        .subscribe(() => {
          this.cd.markForCheck();
        }));
  }

  /**
   * Watch state changes and propagate information to the template.
   */
  private subscribeState(): void {
    this.subs.push(
      this.stateService.getState$().subscribe(state => {
        this.desktop = state.desktopState.desktop;
        this.page = state.desktopState.page;
        this.menu = state.desktopState.menu;
        this.mode = state.desktopState.mode;
        this.session = state.session;

        // we log every state change
        console.info('app-state', state);

        // state change is triggered from router outlet
        // by pushing a new state on the subject
        // so cd has do be done manually
        this.cd.markForCheck();
        this.cd.detectChanges();
      }));
  }

  onSidenavOpen(): void {
    this.loadSidebar = true;
  }

  onLanguageSelect(language: string): void {
    this.subs.push(this.stateService.setLanguage$(language).pipe(
      switchMap(() => this.stateService.loadDesktop())
    ).subscribe((desktop) => {
      this.stateService.nextDesktopState(desktop);
      this.cd.markForCheck();
    }));
  }

  onLogin(): void {
    this.stateService.loadDesktop().subscribe(desktop => {
      if (this.loginService.handleLogin({
        desktop: desktop,
        defaultLoginUri: apiUri('/auth/authn/login') + '?post_login_route={post_login_route}',
        postLoginRoute: '/'
      })) {
        console.info('login handler handled login');
      } else {
        console.info('login handler does not handle login');
        this.stateService.nextDesktopState(desktop);
        this.cd.markForCheck();
      }
    });
  }

  onLogout(): void {
    location.href = apiUri('/auth/authn/logout');
  }

}
