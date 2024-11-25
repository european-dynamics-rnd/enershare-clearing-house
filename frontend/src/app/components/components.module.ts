import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { UserFormComponent } from './users/user-form/user-form.component';
import { UserListComponent } from './users/user-list/user-list.component';
import {ConfirmationDialogComponent} from "./confirmation-dialog/confirmation-dialog.component";
import { RegisterUserComponent } from './register-user/register-user.component';
import { PurchaseResourcesComponent } from './purchase-resources/purchase-resources.component';
import { ResourcesComponent } from './resources/resources.component';
import { ProposedAuctionsComponent} from "./proposed-auctions/proposed-auctions.component";
import { WonAuctionsComponent} from "./won-auctions/won-auctions.component";
import { BidsPlacedComponent } from './bids-placed/bids-placed.component';
import { BidsAcceptedComponent} from "./bids-accepted/bids-accepted.component";


@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        NgbModule,
        FontAwesomeModule,
        FormsModule,
        ReactiveFormsModule,

    ],
  declarations: [
    FooterComponent,
    NavbarComponent,
    SidebarComponent,
    UserFormComponent,
    UserListComponent,
    ConfirmationDialogComponent,
    RegisterUserComponent,
    PurchaseResourcesComponent,
    ResourcesComponent,
    ProposedAuctionsComponent,
    WonAuctionsComponent,
    BidsPlacedComponent,
    BidsAcceptedComponent,
  ],
  exports: [
    FooterComponent,
    NavbarComponent,
    SidebarComponent
  ]
})
export class ComponentsModule { }
