import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';

/**
 * The shared module provides common modules and shared components
 * between the different features of the app.
 */
@NgModule({
  exports: [
    CommonModule,
    TranslateModule
  ]
})
export class SharedModule {

}
