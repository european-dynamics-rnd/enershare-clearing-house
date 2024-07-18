import { Injectable } from '@angular/core';
import {IndividualConfig, ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private toastr: ToastrService) { }

  success(message: string) {
    const options: Partial<IndividualConfig> = {
      timeOut: 8000,
      closeButton: true,
      enableHtml: false,
      toastClass: "alert alert-success alert-with-icon",
      positionClass: 'toast-top-center'
    };
    this.toastr.success(message, '',options);
  }

  error(message: string) {
    const options: Partial<IndividualConfig> = {
      timeOut: 8000,
      closeButton: true,
      enableHtml: false,
      toastClass: "alert alert-error alert-with-icon",
      positionClass: 'toast-top-center'
    };
    this.toastr.error(message, '',options);
  }
}
