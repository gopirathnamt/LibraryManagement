import { Routes } from '@angular/router';
import { LoginComponent } from './component/login-component/login-component';
import { HomeComponent } from './component/home-component/home-component';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home',
  },
  {
    path: 'home',
    component: HomeComponent,
    // canActivate: [authGuard]
  },
];
