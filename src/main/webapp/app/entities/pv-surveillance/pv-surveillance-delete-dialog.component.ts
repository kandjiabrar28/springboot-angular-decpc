import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPVSurveillance } from 'app/shared/model/pv-surveillance.model';
import { PVSurveillanceService } from './pv-surveillance.service';

@Component({
  templateUrl: './pv-surveillance-delete-dialog.component.html'
})
export class PVSurveillanceDeleteDialogComponent {
  pVSurveillance?: IPVSurveillance;

  constructor(
    protected pVSurveillanceService: PVSurveillanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pVSurveillanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pVSurveillanceListModification');
      this.activeModal.close();
    });
  }
}
