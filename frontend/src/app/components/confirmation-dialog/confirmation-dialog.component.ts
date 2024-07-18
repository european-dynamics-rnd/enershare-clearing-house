import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.css']
})


export class ConfirmationDialogComponent{
  title: string;
  message: string;

  constructor(public activeModal: NgbActiveModal) {}

  dismiss(reason: string) {
    this.activeModal.dismiss(reason);
  }

  close(reason: string) {
    this.activeModal.close(reason);
  }
}
