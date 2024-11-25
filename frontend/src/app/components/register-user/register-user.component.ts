import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../services/users/users.service';
import { Router } from '@angular/router';
import { NotificationService } from '../../services/notification/notification.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

interface RegisterUser {
  firstname: string;
  lastname: string;
  username: string;
  email: string;
  password: string;
  repeatPassword?: string;
  participantId: string;
  connectorUrl: string;
}

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {
  registerUser: RegisterUser = {
    firstname: '',
    lastname: '',
    username: '',
    email: '',
    password: '',
    repeatPassword: '',
    participantId: '',
    connectorUrl: ''
  };

  participantIds: string[] = [];
  connectors: string[] = [];
  passwordsMatch: boolean = true;
  constructor(
    private usersService: UsersService,
    private router: Router,
    private notificationService: NotificationService,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    // Fetch available participants on component load
    this.fetchAvailableParticipants();
  }

  // Check if passwords match
  checkPasswords(): void {
    this.passwordsMatch = this.registerUser.password === this.registerUser.repeatPassword;
  }

  // Fetch participants from the backend without sending the JWT
  fetchAvailableParticipants(): void {
    const url = 'http://localhost:15111/api/user/fetch-available-participants';
    const headers = new HttpHeaders(); // No Authorization header

    this.httpClient.get<string[]>(url, { headers }).subscribe(
      (data) => {
        this.participantIds = data;
      },
      (error) => {
        this.handleHttpError(error, 'Failed to load participants');
      }
    );
  }

  // Fetch connectors based on selected participant
  onParticipantChange(): void {
    const selectedParticipantId = this.registerUser.participantId;
    if (selectedParticipantId) {
      const connectorsUrl = `http://localhost:15111/api/user/fetch-available-connectors/${selectedParticipantId}`;
      this.fetchAvailableConnectors(connectorsUrl);
    }
  }

  fetchAvailableConnectors(url: string): void {
    const headers = new HttpHeaders(); // No Authorization header

    this.httpClient.get<string[]>(url, { headers }).subscribe(
      (data) => {
        this.connectors = data;
      },
      (error) => {
        this.handleHttpError(error, 'Failed to load connectors');
      }
    );
  }

  register(): void {
    // Validation
    if (
      !this.registerUser.firstname ||
      !this.registerUser.lastname ||
      !this.registerUser.username ||  // Added validation for username
      !this.registerUser.email ||
      !this.registerUser.password ||
      !this.registerUser.repeatPassword || // Ensure repeatPassword is not empty
      !this.registerUser.participantId ||
      !this.registerUser.connectorUrl
    ) {
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

    // Register user without sending JWT
    const headers = new HttpHeaders(); // Ensure no Authorization header

    this.usersService.registerUser(this.registerUser).subscribe(
      (response) => {
        if (response.error) {
          this.notificationService.error(response.error.message);
        } else {
          this.notificationService.success('Registration successful!');
          setTimeout(() => this.router.navigate(['/login']), 2000);
        }
      },
      (error) => {
        this.handleHttpError(error, 'Registration failed. Please try again later.');
      }
    );
  }

  private handleHttpError(error: any, defaultMessage: string) {
    if (error.status === 401 || error.error?.message?.includes('JWT expired')) {
      this.notificationService.error('Session expired. Please log in again.');
      localStorage.clear(); // Clear any stored tokens
      this.router.navigate(['/login']);
    } else {
      this.notificationService.error(defaultMessage);
      console.error(error);
    }
  }
}
