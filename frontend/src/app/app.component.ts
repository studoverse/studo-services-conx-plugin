import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AuthActionResource, AuthActionService, AuthSessionResource } from '@campusonline/auth';
import { Subscription } from 'rxjs';
import { AppStateService } from './core/state/app-state.service';
import { NavigationMenuResource } from '@campusonline/model';
import { Router } from '@angular/router';
import {
  DesktopDebugService,
  DesktopMode,
  DesktopPageResource,
  DesktopResource
} from '@campusonline/desktop';
import { switchMap, take } from 'rxjs/operators';

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
  session?: AuthSessionResource;
  page?: DesktopPageResource;
  mode?: DesktopMode;
  loadSidebar?: boolean = false; // lazy load sidebar improves performance
  authActionResource?: AuthActionResource;

  subs: Subscription[] = [];

  constructor(
    private stateService: AppStateService,
    private router: Router,
    public debugService: DesktopDebugService,
    private authActionService: AuthActionService,
    private cd: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.subscribeInitialState();
    this.subscribeAuthActions();
    this.subscribeLanguage();
  }

  /**
   * Initialize the state and watch than subscribe to the state.
   */
  private subscribeInitialState(): void {
    this.subs.push(
      this.stateService.getInitialState$().pipe(take(1)).subscribe(state => {
        this.stateService.nextState(state);
        this.subscribeState();
      }));
  }

  /**
   * Subscribe to auth actions (401, 403 from backend) and handle auth actions.
   * You can use the auth debug container, to manually trigger redirects.
   */
  private subscribeAuthActions(): void {
    this.subs.push(this.authActionService.getAction$().subscribe(action => {

      if (this.debugService.isContainerEnabled('auth')) {
        this.authActionResource = action;
      } else {
        this.authActionService.handleAction(action);
      }

      this.cd.markForCheck();
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
    this.subs.push(this.stateService.setLanguage$(language).subscribe());
  }

  onLogin(): void {
    this.router.navigate(['/coa/auth/login']);
  }

  onLogout(): void {
    this.router.navigate(['/coa/auth/logout']);
  }

}
