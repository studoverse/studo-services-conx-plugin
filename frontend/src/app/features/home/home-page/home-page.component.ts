import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthSessionResource, AuthSessionService } from '@campusonline/auth';
import { Subscription } from 'rxjs';
import { AppStateService } from '../../../core/state/app-state.service';

@Component({
  templateUrl: 'home-page.component.html'
})
export class HomePageComponent implements OnInit, OnDestroy {

  private subs: Subscription[] = [];

  session?: AuthSessionResource;

  constructor(
    private stateService: AppStateService,
    private sessionService: AuthSessionService) {
  }

  ngOnInit(): void {
    this.stateService.nextPage({
      key: '$supersonic.home.title',
      selectedMenuEntries: '$supersonic.home.menu-entry'
    });

    this.subs.push(
      this.sessionService.getSession$()
        .subscribe(session => this.session = session));
  }

  ngOnDestroy(): void {
    this.subs.forEach(sub => sub.unsubscribe());
  }

}
