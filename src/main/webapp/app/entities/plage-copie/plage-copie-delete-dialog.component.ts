import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlageCopie } from 'app/shared/model/plage-copie.model';
import { PlageCopieService } from './plage-copie.service';

@Component({
  templateUrl: './plage-copie-delete-dialog.component.html'
})
export class PlageCopieDeleteDialogComponent {
  plageCopie?: IPlageCopie;

  constructor(
    protected plageCopieService: PlageCopieService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plageCopieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('plageCopieListModification');
      this.activeModal.close();
    });
  }
}
