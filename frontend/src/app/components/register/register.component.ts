import { Component, OnInit } from '@angular/core';
import {AuthenticationRequest} from "../../dtos/authenticationRequest";
import {User} from "../../dtos/user";
import {RegisterRequest} from "../../dtos/registerRequest";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  registerUser: RegisterRequest =  {email: '', password: '',firstname: '',lastname: '',role: '',connectorUrl: ''};
  errorMsg: Array<string> = [];

  constructor() { }

  ngOnInit(): void {
  }

  register(){

  }

}
