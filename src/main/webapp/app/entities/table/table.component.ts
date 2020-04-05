import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITable } from 'app/shared/model/table.model';
import { TableService } from './table.service';
import { TableDeleteDialogComponent } from './table-delete-dialog.component';

@Component({
  selector: 'jhi-table',
  templateUrl: './table.component.html'
})
export class TableComponent implements OnInit, OnDestroy {
  tables?: ITable[];
  eventSubscriber?: Subscription;

  constructor(protected tableService: TableService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tableService.query().subscribe((res: HttpResponse<ITable[]>) => (this.tables = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTables();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITable): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTables(): void {
    this.eventSubscriber = this.eventManager.subscribe('tableListModification', () => this.loadAll());
  }

  delete(table: ITable): void {
    const modalRef = this.modalService.open(TableDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.table = table;
  }
}
