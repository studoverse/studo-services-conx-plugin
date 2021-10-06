import { NgModule } from '@angular/core';
import { HomePageComponent } from './home-page/home-page.component';
import { HomeRoutingModule } from './home-routing.module';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    HomePageComponent
  ],
  exports: [
    HomeRoutingModule
  ]
})
export class HomeModule {

}
