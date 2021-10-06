import { NgModule } from '@angular/core';
import { WhoamiPageComponent } from './whoami-page/whoami-page.component';
import { WhoamiRoutingModule } from './whoami-routing.module';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    WhoamiPageComponent
  ],
  exports: [
    WhoamiRoutingModule
  ]
})
export class WhoamiModule {

}
