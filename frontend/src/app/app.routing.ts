import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule  } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import {LoginComponent} from "./components/login/login.component";
import {AuthGuard} from "./guards/auth.guard";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {RegisterComponent} from "./components/register/register.component";
import { RegisterUserComponent } from './components/register-user/register-user.component';

const routes: Routes =[
  {path:'',
    redirectTo:'login',
    pathMatch:'full',
    
  },

  {
    path:'login',
    component:LoginComponent
  },

  {
    path: 'register',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },

  {
    path: 'register-user',
    component: RegisterUserComponent
  },

  {
    path: '',
    component: AdminLayoutComponent,
    canActivate:[AuthGuard],
    children: [
        {
      path: '',
      loadChildren: () => import('./layouts/admin-layout/admin-layout.module').then(x=>x.AdminLayoutModule)
  }]},
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
  ],
})
export class AppRoutingModule { }
