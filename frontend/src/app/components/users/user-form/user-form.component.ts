import { Component, OnInit } from '@angular/core';
import {UsersService} from "../../../services/users/users.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NotificationService} from "../../../services/notification/notification.service";
import {User} from "../../../dtos/user";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {

    user = new User();
    showPassword: boolean = false;
    id = this.user.id;
    buttonText: string;
    title: string;
    showRole: boolean = true;
    fromNavbar: boolean = false;
    readonlyConnectorUrl:boolean = false;
    readonlyParticipantId:boolean = false;


  constructor(private usersService :UsersService,
              private router: Router,
              private notificationService: NotificationService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.title = params['formTitle'] || 'User Form';
      this.buttonText = params['buttonText'] || 'Submit';
      this.showRole = params['showRole'] !== 'false';
      this.fromNavbar = params['fromNavbar'] === 'true';
      this.readonlyConnectorUrl = params['readonlyConnectorUrl'] === 'true';
      this.readonlyParticipantId = params['readonlyParticipantId'] === 'true';
    });

    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.fetchUser();
      }
    });
  }

    fetchUser() {
        this.usersService.getUserById(this.id).subscribe(
            (user: any) => {
                this.user = { ...user };
            },
            (error) => {
                console.error('Error occurred while fetching user:', error);
                this.notificationService.error('Failed to fetch user details.');
            }
        );
    }

    createOrUpdateUser() {
        if (this.id) {
            this.updateUser();
        } else {
            this.createUser();
        }
    }

    createUser() {
        this.usersService.createUser(this.user).subscribe(
            () => {
                this.notificationService.success('User created successfully');
                this.router.navigateByUrl('/user-list');
            },
            (error) => {
                console.error('Error occurred while creating user:', error);
                this.notificationService.error('Failed to create user. Email exist');
            }
        );
    }

  updateUser() {
    this.usersService.updateUser(this.user).subscribe(
      () => {
        this.notificationService.success('User updated successfully');
        if (this.fromNavbar) {
          this.router.navigate(['/dashboard']);
        } else {
          this.router.navigateByUrl('/user-list');
        }
      },
      (error) => {
        console.error('Error occurred while updating user:', error);
        this.notificationService.error('Failed to update user. Please try again');
      }
    );
  }

    cancel() {

    this.router.navigateByUrl('/user-list');
      if (this.fromNavbar) {
        this.router.navigate(['/dashboard']);
      }
    }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

}
