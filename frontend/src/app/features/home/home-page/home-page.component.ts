import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {AppStateService} from '../../../core/state/app-state.service';
import {DesktopSessionResource, DesktopSessionService} from "@campusonline/desktop";

@Component({
  templateUrl: 'home-page.component.html'
})
export class HomePageComponent implements OnInit, OnDestroy {

  private subs: Subscription[] = [];

  session?: DesktopSessionResource;

  constructor(
    private stateService: AppStateService,
    private sessionService: DesktopSessionService) {
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
