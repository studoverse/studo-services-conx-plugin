import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {EMPTY, Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {Location} from '@angular/common';
import {apiUri} from "@shared/uri/api-uri";

@Injectable()
export class AppAuthHttpInterceptor implements HttpInterceptor {

  constructor(private location: Location) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request)
      .pipe(
        catchError(error => {
          // we make us independent of the concrete http status, and interpret the json in the body
          if (error instanceof HttpErrorResponse && (error.status === 401) && error?.error?.auth) {
            location.href = apiUri('/auth/authn/login') + '?post_login_route=' + this.location.path()
            return EMPTY;
          }
          return next.handle(request);
        })
      );
  }

}
