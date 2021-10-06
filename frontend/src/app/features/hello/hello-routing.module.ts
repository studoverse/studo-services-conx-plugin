import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HelloPageComponent } from './hello-page/hello-page.component';

const ROUTES: Routes = [
  {
    path: '',
    component: HelloPageComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(ROUTES)
  ]
})
export class  HelloRoutingModule {

}
