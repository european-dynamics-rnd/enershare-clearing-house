import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/system/auth/auth.service";
import {Router} from "@angular/router";
import {AuthenticationRequest} from "../../dtos/authenticationRequest";
import {AuthenticationResponse} from "../../dtos/authenticationResponse";
import {User} from "../../dtos/user";
import { NotificationService } from '../../services/notification/notification.service'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email = '';
  password = '';

  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMsg: Array<string> = [];
  user: User;



  type: string ="password";
  isText: boolean = false;
  eyeIcon: string = "fa-eye-slash";
  loginForm!: FormGroup;
  constructor(private fb: FormBuilder,
              private authService: AuthenticationService,
              private router: Router,
              private notificationservice: NotificationService) { }

  ngOnInit(): void {
    // this.loginForm = this.fb.group({
    //   email: ['',Validators.required],
    //   password: ['',Validators.required],
    // })
  }

  login() {
    this.errorMsg = [];
    this.authService.onLogin(this.authRequest.email, this.authRequest.password).subscribe(
      (res: AuthenticationResponse) => {
        this.authService.storeAccessToken(res.accessToken);
        this.authService.storeRefreshToken(res.refreshToken);
        this.authService.storeUser(res.user);

        // Redirect based on user role
        if (res.user.role === 'ADMIN') {
          this.router.navigateByUrl('/user-list');
        } else {
          this.router.navigateByUrl('/dashboard');
        }
      },
      (err: any) => {
        console.error('Login error:', err);
        
        if (err.error && err.error.code) {
          switch (err.error.code) {
            case '501':
              this.notificationservice.error('Email and password are required.');
              break;
            case '502':
              this.notificationservice.error('Email is required.');
              break;
            case '503':
              this.notificationservice.error('Password is required.');
              break;
            case '504':
              this.notificationservice.error('Invalid email or password.');
              break;
            default:
              this.notificationservice.error('Incorrect User Credentials.');
              break;
          }
        } else {
          this.notificationservice.error('An error occurred during login.');
        }
      }
    );
  }
      
  //     (err: any) => {
  //       console.error('Login error:', err);
  //       if(err.error.message) {
  //         this.notificationservice.error(err.error.message);
  //       }
  //     }
  //   );
  // }
  navigateToRegister() {
    this.router.navigate(['/register-user']);
  }

  // hideShowPass(){
  //   this.isText = !this.isText;
  //   this.isText ? this.eyeIcon = "fa-eye" : this.eyeIcon = "fa-eye-slash";
  //   this.isText ? this.type = "text" : this.type = "password";
  // }
  //
  // onLogin(){
  //   if(this.loginForm.valid){
  //     console.log(this.loginForm.value)
  //
  //     //send the obj to database
  //   }else{
  //     this.validateAllFormFields(this.loginForm);
  //     // alert("Your form is invalid")
  //   }
  // }
  //
  // private validateAllFormFields(formGroup:FormGroup){
  //   Object.keys(formGroup.controls).forEach(field=>{
  //     const control = formGroup.get(field);
  //     if(control instanceof FormControl){
  //       control.markAsDirty({onlySelf:true});
  //     }else if(control instanceof FormGroup){
  //       this.validateAllFormFields(control)
  //     }
  //   })
  // }
}

