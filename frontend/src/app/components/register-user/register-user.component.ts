import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../services/users/users.service';
import { Router } from '@angular/router';
import { NotificationService } from '../../services/notification/notification.service';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {
  registerUser = {
    firstname: '',
    lastname: '',
    email: '',
    password: ''
  };

  constructor(
    private usersService: UsersService,
    private router: Router,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {}

  register(): void {
    if (this.validateForm()) {
      this.usersService.registerUser(this.registerUser).subscribe(
        response => {
          if (response.error) {
            this.notificationService.error(response.error.message);
          } else {
            this.notificationService.success('Registration successful!');
            setTimeout(() => this.router.navigate(['/login']), 2000); // Navigate after 2 seconds
          }
        },
        error => {
          // This block should generally handle network-level errors
          console.error('HTTP error:', error);
          this.notificationService.error('Registration failed. Please try again later.');
        }
      );
    } else {
      this.notificationService.error('Please correct the errors in the form.');
    }
  }
  

  private validateForm(): boolean {
    const { firstname, lastname, email, password } = this.registerUser;
    return firstname.length > 0 &&
           lastname.length > 0 &&
           email.length > 0 &&
           password.length >= 6;
  }
}
