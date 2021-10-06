import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './features/home/home-page/home-page.component';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: () => import('./features/home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'hello',
    loadChildren: () => import('./features/hello/hello-module').then(m => m.HelloModule)
  },
  {
    path: 'whoami',
    loadChildren: () => import('./features/whoami/whoami.module').then(m => m.WhoamiModule)
  },
  {
    path: 'coa',
    children: [
      { path: '', loadChildren: () => import('@campusonline/desktop').then(m => m.DesktopDebugRoutingModule) },
      { path: '', loadChildren: () => import('@campusonline/auth').then(m => m.AuthRoutingModule) }
    ]
  },
  {
    path: '**',
    component: HomePageComponent
  }
];

/**
 * This is the supreme routing module of our app.
 * Every other routing definition is loaded lazy.
 */
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
