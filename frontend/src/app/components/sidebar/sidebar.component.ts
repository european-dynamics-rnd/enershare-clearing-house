import { Component, OnInit } from '@angular/core';
import {
    faBoxArchive, faCartShopping,
    faChartBar, faCoins,
    faFileArrowDown,
    faFileArrowUp, faFolderOpen, faGavel,
    faUsers,
    IconDefinition
} from '@fortawesome/free-solid-svg-icons';
import {AuthenticationService} from "../../services/system/auth/auth.service";
import {Router} from "@angular/router";
import {faTrophy} from "@fortawesome/free-solid-svg-icons/faTrophy";
import {faReceipt} from "@fortawesome/free-solid-svg-icons/faReceipt";


declare interface RouteInfo {
    path: string;
    title: string;
    icon: IconDefinition;
    class: string;
}
export const ROUTES: RouteInfo[] = [
    { path: '/dashboard', title: 'Dashboard',  icon: faChartBar, class: '' },
    { path: '/ingress-logs', title: 'Ingress Logs',  icon: faFileArrowDown, class: '' },
    { path: '/egress-logs', title: 'Egress Logs',  icon: faFileArrowUp, class: '' },
    { path: '/user-list', title: 'Users',  icon: faUsers, class: '' },
    { path: '/resources', title: 'Resources',  icon: faFolderOpen, class: '' },
    { path: '/purchase-resources', title: 'Purchase Resources',  icon: faReceipt, class: '' },
    { path: '/proposed-auctions', title: 'Proposed Auctions',  icon: faGavel, class: '' },
    { path: '/won-auctions', title: 'Won Auctions',  icon: faTrophy, class: '' },
    { path: '/bids-placed', title: 'Bids Placed',  icon: faCoins, class: '' },
    { path: '/bids-accepted', title: 'Bids Accepted',  icon: faCartShopping, class: '' },
    // { path: '/icons', title: 'Icons',  icon:'education_atom', class: '' },
    // { path: '/notifications', title: 'Notifications',  icon:'ui-1_bell-53', class: '' },
    // { path: '/user-profile', title: 'User Profile',  icon:'users_single-02', class: '' },
    //{ path: '/table-list', title: 'Table List',  icon:'design_bullet-list-67', class: '' },
    // { path: '/typography', title: 'Typography',  icon:'text_caps-small', class: '' },

];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  constructor(private authService: AuthenticationService, private router: Router) { }

  ngOnInit() {
    this.menuItems = this.getFilteredRoutes();
  }

  getFilteredRoutes(): RouteInfo[] {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser && currentUser.role === 'ADMIN') {
      return ROUTES.filter(route => route.title === 'Users');
    } else {
      return ROUTES.filter(route => route.title !== 'Users');
    }
  }

  isMobileMenu() {
      if ( window.innerWidth > 991) {
          return false;
      }
      return true;
  };
}
