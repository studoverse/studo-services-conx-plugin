import { ChangeDetectorRef, Component } from '@angular/core';
import { take } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { AppStateService } from '../../../core/state/app-state.service';
import { RolesOfIdentitySetResource } from '@shared/model/model';
import { AppUriService } from '@shared/uri/app-uri.service';

@Component({
  templateUrl: 'whoami-page.component.html'
})
export class WhoamiPageComponent {

  whoami?: RolesOfIdentitySetResource;

  constructor(
    private stateService: AppStateService,
    private uri: AppUriService,
    private http: HttpClient,
    private cd: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.stateService.nextPage('$supersonic.whoami.title');

    this.http.get<RolesOfIdentitySetResource>(this.uri.getAuthDemoSubjectUri())
      .pipe(take(1)).subscribe(whoami => {
      this.whoami = whoami;
      this.cd.markForCheck();
    });
  }

}
