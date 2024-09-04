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

    // Client-side validation
    if (!this.registerUser.firstname || !this.registerUser.lastname || !this.registerUser.email || !this.registerUser.password) {
      this.notificationService.error('All fields are required.');
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.registerUser.email)) {
      this.notificationService.error('Invalid email format.');
      return;
    }

    if (this.registerUser.password.length < 6) {
      this.notificationService.error('Password must be at least 6 characters long.');
      return;
    }

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

  }

}
