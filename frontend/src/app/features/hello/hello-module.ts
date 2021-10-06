import { NgModule } from '@angular/core';
import { HelloPageComponent } from './hello-page/hello-page.component';
import { HelloRoutingModule } from './hello-routing.module';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    HelloPageComponent
  ],
  exports: [
    HelloRoutingModule
  ]
})
export class HelloModule {

}
