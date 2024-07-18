import { Component, OnInit } from '@angular/core';
import { UsersService } from "../../../services/users/users.service";
import { Router } from "@angular/router";
import {User} from "../../../dtos/user";
import {PageRequest} from "../../../dtos/pageRequest";
import {ConfirmationDialogComponent} from "../../confirmation-dialog/confirmation-dialog.component";
import {NgbModal, NgbModalConfig, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

    pageRequest = new PageRequest(0,5);
    users: User[];
    totalUsers: number;
    formTitle: string;
    buttonText: string;

  constructor(private usersService: UsersService, private router: Router,
              private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.fetchTotalUsers();
    this.refreshUsers();
  }

  fetchTotalUsers() {
    this.usersService.getTotalUsers().subscribe(total => {
      this.totalUsers = total;
      this.pageRequest.collectionSize = total;
    });
  }

  refreshUsers() {
    const pageNumber = Math.max(this.pageRequest.page - 1, 0);
    this.usersService.getUsers(pageNumber, this.pageRequest.pageSize).subscribe(users => {
      this.users = users.content;
    });
  }


    setPageSize(size: number) {
        this.pageRequest.pageSize = size;
        this.refreshUsers();
    }

  edit(id: string) {
    const user = this.usersService.getUserById(id);
    if (user) {
      this.router.navigate(['/user-form', id], { queryParams: { action: 'edit', formTitle: 'Edit User', buttonText: 'Update' } });
    }
  }

  createNew(): void {
    this.router.navigate(['/user-form'], { queryParams: { action: 'create', formTitle: 'Create New User', buttonText: 'Create' } });
    this.refreshUsers();
  }

  // delete(id: string) {
  //   this.usersService.delete(id).subscribe(data => {
  //     this.refreshUsers();
  //   });
  // }

  delete(id: string) {

    const modalOptions: NgbModalOptions = {
      backdrop: false,
    };

    const modalRef = this.modalService.open(ConfirmationDialogComponent,modalOptions);
    modalRef.componentInstance.title = 'Confirm Delete';
    modalRef.componentInstance.message = `Are you sure you want to delete this user ?`;

    modalRef.result.then((result) => {
      if (result === 'Yes click') {
        this.usersService.delete(id).subscribe(
          () => {
            this.refreshUsers();
          },
          (error) => {
            console.error('Error deleting user:', error);
          }
        );
      }
    }).catch((reason) => {
      console.log(`Dismissed: ${reason}`);
    });
  }
}
