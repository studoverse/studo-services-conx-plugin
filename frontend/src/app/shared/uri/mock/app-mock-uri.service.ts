import { AppUriService } from '../app-uri.service';
import { appMockUri } from './app-mock-uri';
import { Injectable } from '@angular/core';
import { AppEnvironment } from '@env/app-enivronment';

@Injectable()
export class AppMockUriService extends AppUriService {

  constructor(public environment: AppEnvironment) {
    super(environment);
  }

  getGreetingsUri(): string {
    return appMockUri('/app/greetings-resource.json');
  }

  getAuthDemoSubjectUri(): string {
    return appMockUri('/app/subject-resource.json');
  }
}
