import { Injectable } from '@angular/core';
import { combineLatest, Observable } from 'rxjs';
import { AppState } from './app-state';
import {map, switchMap, tap} from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { INIT_DESKTOP_STATE } from './init/init-desktop-state';
import { INIT_LANGUAGE } from './init/init-language';
import {
  DesktopModeService,
  DesktopNavigationService,
  DesktopPageResource,
  DesktopPageService,
  DesktopResource,
  DesktopResourceService,
  DesktopSessionResource,
  DesktopSessionService,
  DesktopStateService,
  DesktopUriService
} from '@campusonline/desktop';
import { I18nService } from './i18n.service';
import { INIT_TEXT_BUNDLES } from './init/init-text-bundles';

/**
 * This service manages the global app state
 * and offers state manipulation utilities to our components.
 */
@Injectable()
export class AppStateService {

  constructor(
    private sessionService: DesktopSessionService,
    private desktopStateService: DesktopStateService,
    private desktopService: DesktopResourceService,
    private pageService: DesktopPageService,
    private modeService: DesktopModeService,
    private navigationService: DesktopNavigationService,
    private i18nService: I18nService,
    private desktopUriService: DesktopUriService,
    private authUriService: DesktopUriService,
    private http: HttpClient) {
  }

  public loadDesktop(): Observable<DesktopResource> {
    return this.http.get<DesktopResource>(this.desktopUriService.getDesktopUri());
  }

  private loadDesktopAndTranslations(session: DesktopSessionResource):
    Observable<[DesktopSessionResource, DesktopResource]> {
    return combineLatest([
      this.loadDesktop(),
      this.i18nService.updateTranslations(INIT_LANGUAGE)
    ]).pipe(
      map(([desktop]) => {
        return [session, desktop];
      }));
  }

  getInitialState$(): Observable<AppState> {
    this.i18nService.addTextBundles(INIT_TEXT_BUNDLES);

    return this.sessionService.loadSession().pipe(
      switchMap((session) => this.loadDesktopAndTranslations(session))
    ).pipe(
      map(([session, desktop]) => {
        return {
          desktopState: {...INIT_DESKTOP_STATE, desktop: desktop},
          session: session,
          textBundles: INIT_TEXT_BUNDLES
        };
      }));
  }

  nextDesktopState(desktop: DesktopResource): void {
    this.desktopService.nextDesktop(desktop);
  }

  nextState(state: AppState): void {
    this.sessionService.nextSession(state.session);
    this.desktopService.nextDesktop(state.desktopState.desktop);
    this.pageService.nextPage(state.desktopState.page);
    this.navigationService.nextMenu(state.desktopState.menu);
    this.modeService.nextMode(state.desktopState.mode);
  }

  /**
   * A little app specific helper.
   * User of this api always can pass the whole page information as DesktopPageResource.
   * For a better developer experience, we allow to pass only the page key,
   * and automatically fill in default values.
   * You can make this logic much more complicated, if you want.
   *
   * @param page the page object or only the page key
   */
  nextPage(page: DesktopPageResource | string): void {
    if (typeof page === 'string') {
      this.pageService.nextPage({
        key: page, title: { key: page }, selectedMenuEntries: page
      });
    } else {
      this.pageService.nextPage(page);
    }
  }

  getState$(): Observable<AppState> {
    return combineLatest([
      this.sessionService.getSession$(),
      this.desktopStateService.getDesktopState$()
    ]).pipe(
      map(([session, desktop]) => {
        return { session: session, desktopState: desktop };
      })
    );
  }

  updateTranslations(language: string): Observable<boolean> {
    return this.i18nService.updateTranslations(language);
  }

  getLanguage$(): Observable<string> {
    return this.sessionService.getLanguage$();
  }

  setLanguage$(language: string): Observable<boolean> {
    return this.sessionService.setLanguage$(language)
      .pipe(
        map(language => this.sessionService.getSessionValue()),
        switchMap(session => {
          return this.http.put(this.authUriService.getSessionUri(), session);
        }),
        map(() => true)
      );
  }

}
