import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EMPTY, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { GreetingListResource } from '@shared/model/model';
import { AppUriService } from '@shared/uri/app-uri.service';

@Injectable({providedIn: 'root'})
export class ExampleService {

  constructor(
    private uri: AppUriService,
    private http: HttpClient) {
  }

  greetings(): Observable<GreetingListResource> {
    return this.http.get<GreetingListResource>(this.uri.getGreetingsUri())
      .pipe(
        catchError(error => {
          console.error('something bad happened', error);
          return EMPTY;
        })
      );
  }
}
