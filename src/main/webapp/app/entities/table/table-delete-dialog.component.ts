import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITable } from 'app/shared/model/table.model';
import { TableService } from './table.service';

@Component({
  templateUrl: './table-delete-dialog.component.html'
})
export class TableDeleteDialogComponent {
  table?: ITable;

  constructor(protected tableService: TableService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tableService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tableListModification');
      this.activeModal.close();
    });
  }
}
