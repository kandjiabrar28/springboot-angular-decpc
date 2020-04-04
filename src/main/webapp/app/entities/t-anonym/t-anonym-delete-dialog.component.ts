import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITAnonym } from 'app/shared/model/t-anonym.model';
import { TAnonymService } from './t-anonym.service';

@Component({
  templateUrl: './t-anonym-delete-dialog.component.html'
})
export class TAnonymDeleteDialogComponent {
  tAnonym?: ITAnonym;

  constructor(protected tAnonymService: TAnonymService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tAnonymService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tAnonymListModification');
      this.activeModal.close();
    });
  }
}
