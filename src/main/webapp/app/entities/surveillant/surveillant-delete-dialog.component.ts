import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISurveillant } from 'app/shared/model/surveillant.model';
import { SurveillantService } from './surveillant.service';

@Component({
  templateUrl: './surveillant-delete-dialog.component.html'
})
export class SurveillantDeleteDialogComponent {
  surveillant?: ISurveillant;

  constructor(
    protected surveillantService: SurveillantService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.surveillantService.delete(id).subscribe(() => {
      this.eventManager.broadcast('surveillantListModification');
      this.activeModal.close();
    });
  }
}
