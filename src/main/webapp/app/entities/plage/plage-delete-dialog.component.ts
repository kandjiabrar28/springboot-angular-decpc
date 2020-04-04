import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlage } from 'app/shared/model/plage.model';
import { PlageService } from './plage.service';

@Component({
  templateUrl: './plage-delete-dialog.component.html'
})
export class PlageDeleteDialogComponent {
  plage?: IPlage;

  constructor(protected plageService: PlageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('plageListModification');
      this.activeModal.close();
    });
  }
}
