import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICorrecteur } from 'app/shared/model/correcteur.model';
import { CorrecteurService } from './correcteur.service';

@Component({
  templateUrl: './correcteur-delete-dialog.component.html'
})
export class CorrecteurDeleteDialogComponent {
  correcteur?: ICorrecteur;

  constructor(
    protected correcteurService: CorrecteurService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.correcteurService.delete(id).subscribe(() => {
      this.eventManager.broadcast('correcteurListModification');
      this.activeModal.close();
    });
  }
}
