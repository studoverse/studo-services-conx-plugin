import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WhoamiPageComponent } from './whoami-page/whoami-page.component';

const ROUTES: Routes = [
  {
    path: '',
    component: WhoamiPageComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(ROUTES)
  ]
})
export class WhoamiRoutingModule {

}
