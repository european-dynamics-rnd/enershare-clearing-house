import { Routes } from '@angular/router';

import { DashboardComponent } from '../../components/dashboard/dashboard.component';
import { UserProfileComponent } from '../../components/user-profile/user-profile.component';
import { TableListComponent } from '../../components/table-list/table-list.component';
import { TypographyComponent } from '../../components/typography/typography.component';
import { IconsComponent } from '../../components/icons/icons.component';
import { NotificationsComponent } from '../../components/notifications/notifications.component';
import {IngressLogsComponent} from "../../components/ingress-logs/ingress-logs.component";
import {EgressLogsComponent} from "../../components/egress-logs/egress-logs.component";
import {UserListComponent} from "../../components/users/user-list/user-list.component";
import {UserFormComponent} from "../../components/users/user-form/user-form.component";
import {AuthGuard} from "../../guards/auth.guard";


export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard',      component: DashboardComponent },
    { path: 'user-profile',   component: UserProfileComponent },
    { path: 'table-list',     component: TableListComponent },
    { path: 'typography',     component: TypographyComponent },
    { path: 'icons',          component: IconsComponent },
    { path: 'notifications',  component: NotificationsComponent },
    { path: 'ingress-logs',   component: IngressLogsComponent },
    { path: 'egress-logs',   component: EgressLogsComponent },
    {
    path: 'user-list',
    component: UserListComponent,
    canActivate: [AuthGuard], // Implement AuthGuard to check user role
    data: { roles: ['admin'] } // Specify the roles allowed to access this route
    },
    { path: 'user-form',   component: UserFormComponent },
    { path: 'user-form/:id', component: UserFormComponent },

];

