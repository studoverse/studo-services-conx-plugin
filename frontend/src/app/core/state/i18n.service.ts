import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DesktopUriService } from '@campusonline/desktop';
import { TranslateService } from '@ngx-translate/core';
import { combineLatest, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class I18nService {

  private bundles: string[] = [];

  constructor(
    private desktopUriService: DesktopUriService,
    private translateService: TranslateService,
    private http: HttpClient
  ) {
  }

  private createLoaders(bundles: string[], language: string): Observable<any>[] {
    return bundles.map(bundle => {
      return this.http.get<any>(this.desktopUriService.getTranslationUri(bundle, language));
    });
  }

  addTextBundles(bundles: string[]): void {
    this.bundles.push(...bundles);
  }

  updateTranslations(language: string): Observable<boolean> {
    return combineLatest(this.createLoaders(this.bundles, language)).pipe(
      tap((jsons) => {
        jsons.forEach(json => {
          this.translateService.setTranslation(language, json, true);
        });
      }),
      tap(() => this.translateService.use(language)),
      map(() => true)
    );
  }

}
