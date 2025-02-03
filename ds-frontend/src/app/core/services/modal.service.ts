import {Injectable} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ModalComponent} from '../../shared/components/modal/modal.component';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  private modelRef: any;

  constructor(private ngModal: NgbModal) {}

  open(title: any, body: any) {
    const modalRef = this.ngModal.open(ModalComponent);
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.body = body;
  }
}
