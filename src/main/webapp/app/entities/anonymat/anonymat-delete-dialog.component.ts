import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnonymat } from 'app/shared/model/anonymat.model';
import { AnonymatService } from './anonymat.service';

@Component({
  templateUrl: './anonymat-delete-dialog.component.html'
})
export class AnonymatDeleteDialogComponent {
  anonymat?: IAnonymat;

  constructor(protected anonymatService: AnonymatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anonymatService.delete(id).subscribe(() => {
      this.eventManager.broadcast('anonymatListModification');
      this.activeModal.close();
    });
  }
}
