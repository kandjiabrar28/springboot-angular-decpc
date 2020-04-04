import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExamen } from 'app/shared/model/examen.model';
import { ExamenService } from './examen.service';

@Component({
  templateUrl: './examen-delete-dialog.component.html'
})
export class ExamenDeleteDialogComponent {
  examen?: IExamen;

  constructor(protected examenService: ExamenService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.examenService.delete(id).subscribe(() => {
      this.eventManager.broadcast('examenListModification');
      this.activeModal.close();
    });
  }
}
