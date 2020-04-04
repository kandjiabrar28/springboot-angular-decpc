import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITour } from 'app/shared/model/tour.model';
import { TourService } from './tour.service';

@Component({
  templateUrl: './tour-delete-dialog.component.html'
})
export class TourDeleteDialogComponent {
  tour?: ITour;

  constructor(protected tourService: TourService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tourService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tourListModification');
      this.activeModal.close();
    });
  }
}
