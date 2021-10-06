import { ChangeDetectorRef, Component } from '@angular/core';
import { take } from 'rxjs/operators';
import { ExampleService } from './example-service/example.service';
import { AppStateService } from '../../../core/state/app-state.service';
import { GreetingListResource, GreetingResource } from "@shared/model/model";

@Component({
  templateUrl: 'hello-page.component.html',
  styleUrls: ['hello-page.component.scss']
})
export class HelloPageComponent {

  greetings?: GreetingResource[];

  constructor(
    private stateService: AppStateService,
    private exampleService: ExampleService,
    private cd: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.stateService.nextPage('$supersonic.hello.title');

    this.exampleService.greetings().pipe(take(1)).subscribe(list => {
      this.greetings = list.items;
      this.cd.markForCheck();
    });
  }

}
