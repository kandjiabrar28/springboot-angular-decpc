import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJury } from 'app/shared/model/jury.model';
import { JuryService } from './jury.service';

@Component({
  templateUrl: './jury-delete-dialog.component.html'
})
export class JuryDeleteDialogComponent {
  jury?: IJury;

  constructor(protected juryService: JuryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.juryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('juryListModification');
      this.activeModal.close();
    });
  }
}
