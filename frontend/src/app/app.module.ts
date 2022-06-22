import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { MatSidenavModule } from '@angular/material/sidenav';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {AppAuthHttpInterceptor} from "./app-auth-http-interceptor";

/**
 * This is the supreme module of our app.
 */
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,

    // core and shared
    CoreModule,
    SharedModule,

    // app main page specific
    BrowserAnimationsModule,
    MatSidenavModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AppAuthHttpInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
