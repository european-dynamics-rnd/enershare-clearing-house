import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { RegisterComponent } from './register/register.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { UserFormComponent } from './users/user-form/user-form.component';
import { UserListComponent } from './users/user-list/user-list.component';
import {ConfirmationDialogComponent} from "./confirmation-dialog/confirmation-dialog.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        NgbModule,
        FontAwesomeModule,
        FormsModule,
        ReactiveFormsModule
    ],
  declarations: [
    FooterComponent,
    NavbarComponent,
    SidebarComponent,
    RegisterComponent,
    UserFormComponent,
    UserListComponent,
    ConfirmationDialogComponent
  ],
  exports: [
    FooterComponent,
    NavbarComponent,
    SidebarComponent
  ]
})
export class ComponentsModule { }
