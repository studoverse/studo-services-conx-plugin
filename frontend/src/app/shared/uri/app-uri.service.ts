import { apiUri } from './api-uri';
import { Injectable } from '@angular/core';
import { AppEnvironment } from '@env/app-enivronment';

@Injectable()
export class AppUriService {

  constructor(public environment: AppEnvironment) {
  }

  getGreetingsUri(): string {
    return apiUri('/greetings');
  }

  getAuthDemoSubjectUri(): string {
    return apiUri('/auth-demo/subject');
  }

}
